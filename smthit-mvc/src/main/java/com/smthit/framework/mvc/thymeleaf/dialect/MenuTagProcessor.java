/**
 * 
 */
package com.smthit.framework.mvc.thymeleaf.dialect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import com.smthit.framework.mvc.thymeleaf.menu.Menu;
import com.smthit.framework.mvc.thymeleaf.menu.MenuLoader;
import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 * 菜单标签
 */
public class MenuTagProcessor extends AbstractElementTagProcessor {
	private static final String TAG_NAME = "menu";
	private static final int PRECEDENCE = 300;

	public MenuTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, // 处理thymeleaf 的模型
				dialectPrefix, // 标签前缀名
				TAG_NAME, // No tag name: match any tag name
				true, // No prefix to be applied to tag name
				null, // 标签前缀的 属性 例如：<sm:menu >
				false, // Apply dialect prefix to attribute name
				PRECEDENCE // Precedence (inside dialect's precedence)
		); // Remove the matched attribute afterwards
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
		final IEngineConfiguration configuration = context.getConfiguration();
		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

		//ApplicationContext ac = SpringContextUtils.getApplicationContext(context);
		
		List<Menu> menus = MenuLoader.loadMainMenus();
		//菜单类型
		String type = tag.getAttributeValue("type");
		//当前所在模块
		String module = tag.getAttributeValue("module");
		
		IStandardExpression expression = parser.parseExpression(context, module);
		module = (String)expression.execute(context);
		
		if ("root".equals(type)) {
			createRootMenu(context, tag, structureHandler, menus);
		} else if ("sub".equals(type)) {
			for(Menu menu : menus) {
				if(menu.getKey() != null && module != null  && module.startsWith(menu.getKey())) {
					createSubMenu(context, tag, structureHandler, menu.getChildren());		
				}
			}
		}
	}

	/**
	 * 创建一级菜单
	 * @param context
	 * @param tag
	 * @param structureHandler
	 * @param rootMenus
	 */
	private void createRootMenu(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler, List<Menu> rootMenus) {
		final IModelFactory modelFactory = context.getModelFactory();
		final IModel model = modelFactory.createModel();
		final IEngineConfiguration configuration = context.getConfiguration();
		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

		String module = tag.getAttributeValue("module");
		IStandardExpression expression = parser.parseExpression(context, module);
		module = (String) expression.execute(context);
		
		//根据样式风格动态生成一级菜单
		final String elementCompleteName = "ul";

		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("class", "layui-nav fastui-menubar");
		attributes.put("lay-filter", "fastui-menubar");

		IStandaloneElementTag ulTag = modelFactory.createStandaloneElementTag(elementCompleteName, attributes, null, false, false);
		model.add(ulTag);

		for (Menu menu : rootMenus) {
			//判断菜单是否需要进行鉴权
			if(!hasMenuPermission(menu)) {
				continue;
			}
			
			IModel ulModel = modelFactory.createModel();
			Map<String, String> liAttr = new HashMap<String, String>();

			if (StringUtils.isEmpty(menu.getKey())) {
				throw new ServiceException("Menu must has a key.");
			}

			String[] keyPath = menu.getKey().split("\\.");
			
			//当前菜单项是否选中
			if (module != null && keyPath.length > 0 && module.startsWith(keyPath[0])) {
				liAttr.put("class", "layui-nav-item layui-this");
			} else {
				liAttr.put("class", "layui-nav-item");
			}

			IStandaloneElementTag liTag = modelFactory.createStandaloneElementTag("li", liAttr, null, false, false);

			ulModel.add(liTag);
			
			//计算菜单实际的URL地址
			String url = "@{" + menu.getUrl() + "}";
			expression = parser.parseExpression(context, url);
			String newUrl = (String) expression.execute(context);

			IStandaloneElementTag aTag = modelFactory.createStandaloneElementTag("a", "href", newUrl);
			ulModel.add(aTag);
			ulModel.add(modelFactory.createText(menu.getName()));

			ICloseElementTag aCloseTag = modelFactory.createCloseElementTag("a");
			ulModel.add(aCloseTag);

			ICloseElementTag liCloseTag = modelFactory.createCloseElementTag("li");
			ulModel.add(liCloseTag);

			model.addModel(ulModel);
		}

		model.add(modelFactory.createCloseElementTag(elementCompleteName));
		
		structureHandler.replaceWith(model, false);
	}
	
	/**
	 * 创建二级菜单
	 * @param context
	 * @param tag
	 * @param structureHandler
	 * @param subMenus
	 */
	private void createSubMenu(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler, List<Menu> subMenus) {
		final IModelFactory modelFactory = context.getModelFactory();
		final IModel model = modelFactory.createModel();
		final IEngineConfiguration configuration = context.getConfiguration();
		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

		final String elementCompleteName = "ul";

		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("class", "layui-nav layui-nav-tree fastui-sidemenu");
		attrs.put("lay-filter", "left_sub_menu");
		
		//ul
		IStandaloneElementTag ulTag = modelFactory.createStandaloneElementTag(elementCompleteName, attrs, null, false, false);
		model.add(ulTag);
		
		for(Menu menu : subMenus) {
			//判断菜单是否需要进行鉴权
			if(!hasMenuPermission(menu)) {
				continue;
			}
			
			Map<String, String> attrsLi = new HashMap<String, String>();
			attrsLi.put("class", "layui-nav-item layui-nav-itemed layui-tree fastui-sidemenu-group");
			
			//li tag
			IStandaloneElementTag liTag = modelFactory.createStandaloneElementTag("li", attrsLi, null, false, false);
			model.add(liTag);
			
			//<a href="javascript:;"><i class="icon-flag"></i><cite>虚拟银行</cite></a>
			IStandaloneElementTag aTag = modelFactory.createStandaloneElementTag("a", "href", "javascript:;", false, false);
			model.add(aTag);
			
			//菜单图标
			IStandaloneElementTag iTag = modelFactory.createStandaloneElementTag("i", "class", menu.getCls(), false, false);
			model.add(iTag);
			model.add(modelFactory.createCloseElementTag("i"));
			//菜单名
			IStandaloneElementTag citeTag = modelFactory.createStandaloneElementTag("cite");
			model.add(citeTag);
			model.add(modelFactory.createText(menu.getName()));
			model.add(modelFactory.createCloseElementTag("cite"));
			
			//close a
			model.add(modelFactory.createCloseElementTag("a"));
			
			//如果有下一级继续创建
			if(menu.getChildren() != null && menu.getChildren().size() > 0) {
				//dl
				IStandaloneElementTag dlTag = modelFactory.createStandaloneElementTag("dl", "class", "layui-nav-child");
				model.add(dlTag);
				
				for(Menu child : menu.getChildren()) {
					//判断菜单是否需要进行鉴权
					if(!hasMenuPermission(child)) {
						continue;
					}
					
					//dd->a->i+cite
					IStandaloneElementTag ddTag = modelFactory.createStandaloneElementTag("dd");
					model.add(ddTag);
					
					
					attrs = new HashMap<String, String>();
					attrs.put("href", "javascript:void(0);");
					attrs.put("class", "fastui-menuitem");
					
					IStandardExpression se = parser.parseExpression(context, "@{" + child.getUrl() + "}");
					
					attrs.put("data-href", (String)se.execute(context));
					attrs.put("data-target", menu.getTarget() != null ? menu.getTarget() : "iframe");
					attrs.put("data-id", child.getKey());
					
					aTag = modelFactory.createStandaloneElementTag("a", attrs, null, false, false);
					model.add(aTag);
					
					iTag = modelFactory.createStandaloneElementTag("i", "class", child.getCls(), false, false);
					model.add(iTag);
					model.add(modelFactory.createCloseElementTag("i"));
					
					citeTag = modelFactory.createStandaloneElementTag("cite");
					model.add(citeTag);
					model.add(modelFactory.createText(child.getName()));
					model.add(modelFactory.createCloseElementTag("cite"));
					
					model.add(modelFactory.createCloseElementTag("a"));
					
					model.add(modelFactory.createCloseElementTag("dd"));
				}
				model.add(modelFactory.createCloseElementTag("dl"));
			}
			
			//close li tag
			model.add(modelFactory.createCloseElementTag("li"));
		}
		
		
		//close ul
		model.add(modelFactory.createCloseElementTag(elementCompleteName));
		
		structureHandler.replaceWith(model, false);
	}
	
	private boolean hasMenuPermission(Menu menu) {
		if(!(StringUtils.isEmpty(menu.getPermission()) || "*".equals(menu.getPermission()))) {
			if(!SecurityUtils.getSubject().isPermitted(menu.getPermission())) {
				return false;
			}
		}
		
		return true;
	}
}
