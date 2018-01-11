/**
 * 
 */
package com.smthit.framework.api.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.common.base.MoreObjects;
import com.smthit.framework.api.handlebars.ext.SubHelper;
import com.smthit.framework.api.protocol.meta.ProtocolDescriptor;
import com.smthit.framework.api.servlet.action.IndexAction;
import com.smthit.framework.api.servlet.action.LoginAction;
import com.smthit.framework.api.servlet.action.LogoutAction;
import com.smthit.framework.api.servlet.action.PostProtocolAction;
import com.smthit.framework.api.servlet.action.PrintRecentlyProtocolLogAction;
import com.smthit.framework.api.servlet.action.ShowAllProtocolAction;

/**
 * @author Bean
 *
 */
public class DocServlet extends HttpServlet {
	private static final long serialVersionUID = 1830925994277691449L;
	private static Logger logger = Logger.getLogger(DocServlet.class);
	
	public static String ACTION_SHOW_PROTOCOL = "show_all_protocol";
	
	private WebApplicationContext context;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}
	
	
	
	private static Map<String, Class<?>> actionMaps = new HashMap<String, Class<?>>();
	private static List<Navigator> navUrls = new ArrayList<Navigator>();
	
	{
		actionMaps.put("login", LoginAction.class);
		actionMaps.put("logout", LogoutAction.class);
		actionMaps.put("show_all_protocol", ShowAllProtocolAction.class);
		actionMaps.put("post_request", PostProtocolAction.class);
		actionMaps.put("print_recently_protocol", PrintRecentlyProtocolLogAction.class);
	}
	

	public static void registerAction(String name, Class<?> actionClass) {
		actionMaps.put(name, actionClass);
	}

	public static void registerNavigator(String name, String url) {
		navUrls.add(new Navigator(name, url));
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doAction(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doAction(req, resp);
	}
	
	protected void doAction(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
			String action = req.getParameter("action");
		
			//check user is login or not.
			Boolean alreadyLogin = MoreObjects.firstNonNull((Boolean)req.getSession(true).getAttribute("login"), false) ;
			if(!alreadyLogin) {
				action = "login";
			} else {
				if("login".equals(action)) {
					action = "index";
				}
			}
			
			Class<?> actionClass = actionMaps.get(action);
			if(actionClass == null) {
				actionClass = IndexAction.class;
			}
			
			AbstractAction  actionInstance = null;
			
			try {
				actionInstance = 
						(AbstractAction)context.getAutowireCapableBeanFactory().autowire(actionClass, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
			
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String, String[]> params = req.getParameterMap();
			
			for(Entry<String, String[]> entry : params.entrySet()) {
				model.put(entry.getKey(), entry.getValue());
			}
			
			String template = "";
			if(actionClass != null) {
				try {
					template = actionInstance.doAction(req, resp, model);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			if(template.startsWith("redirect:")) {
				String redirectPath = template.substring("redirect:".length());
				resp.sendRedirect(redirectPath);
			} else {
				merge(resp, template, model);
			}
	}

	private void merge(HttpServletResponse resp, String templatePath, Map<String, Object> model) {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");

		TemplateLoader loader = new ClassPathTemplateLoader();
		
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		
		Handlebars handlebars = new Handlebars(loader);
		handlebars.registerHelper(SubHelper.NAME, new SubHelper());
		
		handlebars.registerHelper("protocol-list", new Helper<Collection<ProtocolDescriptor>>() {
			  public CharSequence apply(Collection<ProtocolDescriptor> list, Options options) {
			    String ret = "<ul>";
			    for (ProtocolDescriptor descriptor: list) {
			      ret += "<li>" + descriptor.printProtocol(true) + "</li>";
			    }
			    return new Handlebars.SafeString(ret + "</ul>");
			  }
			});

		
		try {
			Template template = handlebars.compile(templatePath);
			model.put("navs", navUrls);
			template.apply(model, resp.getWriter());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
