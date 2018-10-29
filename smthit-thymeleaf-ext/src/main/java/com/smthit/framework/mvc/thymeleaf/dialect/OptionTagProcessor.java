/**
 * 
 */
package com.smthit.framework.mvc.thymeleaf.dialect;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.ICloseElementTag;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import com.mchange.lang.IntegerUtils;
import com.smthit.lang.utils.EnumStatus;

/**
 * @author Bean
 *
 */
public class OptionTagProcessor extends AbstractElementTagProcessor {
	private static final String TAG_NAME = "option";
	private static final int PRECEDENCE = 300;

	public OptionTagProcessor(String dialectPrefix) {
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
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		final IModelFactory modelFactory = context.getModelFactory();
		final IModel model = modelFactory.createModel();
		final IEngineConfiguration configuration = context.getConfiguration();
		final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

		//菜单类型
		String enumClassPath = tag.getAttributeValue("enum-path");
		String attrDefault = (String) (parser.parseExpression(context, tag.getAttributeValue("default")).execute(context) + "");
		
		int defaultValue = -1;
		if(StringUtils.isNotEmpty(attrDefault)) {
			defaultValue = IntegerUtils.parseInt(attrDefault, -1);
		}
		
		if(StringUtils.isNotEmpty(enumClassPath) ) {
			try {
				Class<?> enumStatus = EnumStatus.class.getClassLoader().loadClass(enumClassPath);	
				if(enumStatus != null && enumStatus.isEnum()) {
					Object[] objs = enumStatus.getEnumConstants();
					
					if(objs != null && objs.length > 0) {
						IModel optionModel = modelFactory.createModel();
							
						IStandaloneElementTag liTag = modelFactory.createStandaloneElementTag("option", "value", "", false, false);
						optionModel.add(liTag);
						optionModel.add(modelFactory.createText(""));
						ICloseElementTag optionCloseTag = modelFactory.createCloseElementTag("option");
						
						for(Object obj : objs) {
							EnumStatus es = (EnumStatus)obj;
							Map<String, String> attrs = new HashMap<String, String>();
							attrs.put("value", es.getValue() + "");
							
							if(defaultValue == es.getValue()) {
								attrs.put("selected", "selected");
							}
							
							liTag = modelFactory.createStandaloneElementTag("option", attrs, null, false, false);
							optionModel.add(liTag);
							
							optionModel.add(modelFactory.createText(es.getDesc()));
							
							optionCloseTag = modelFactory.createCloseElementTag("option");
							optionModel.add(optionCloseTag);
						}
						
						model.addModel(optionModel);
						
						structureHandler.replaceWith(model, false);
					}
				}
			} catch(Exception exp) {
			}
		}
	}

}
