/**
 * 
 */
package com.smthit.framework.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.smthit.framework.dal.data.ResponseData;

/**
 * @author Bean
 *
 */
public abstract class AbstractFormController<T> extends AbstractController {
	private static Logger logger = Logger.getLogger(AbstractFormController.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}
	
    protected void registDateEditor(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));    	
    }

	@ModelAttribute("model")
    public T createFormModel() {
        return null;
    }

    public ModelAndView form(@RequestParam(value="id", required=false) Long id) {
    	throw new UnsupportedOperationException();
    }
    
    public abstract @ResponseBody ResponseData save(@ModelAttribute("user") @Validated T user, 
    		BindingResult bindingResult, SessionStatus status);
    
    public ResponseData wrapBindingResult(BindingResult bindingResult) {
		ObjectError oe = bindingResult.getAllErrors().get(0);
		ResponseData rd = ResponseData.newFailed(oe.getCode(), "error!");
		logger.error(rd.getMessage());
		return rd;
    }
    
    public ResponseData success(String msg, Object data) {
    	ResponseData rd = ResponseData.newSuccess("");
    	
    	if(!StringUtils.isEmpty(msg)) {
    		rd.setMessage(msg);
    	} else {
    		rd.setMessage("操作成功");
    	}
    	
    	if(data != null) {
    		rd.setData(data);
    	}
    	
    	return rd;
    }
    
    public ResponseData success() {
    	return success(null, null);
    }
    
    public ResponseData failed(String msg, Exception exp) {
    	ResponseData rd = ResponseData.newFailed("error!");
    	if(!StringUtils.isEmpty(msg)) {
    		rd.setMessage(msg + exp != null ?  exp.getMessage() : "");
    	} else {
    		rd.setMessage("数据保存失败!" + exp != null ? exp.getMessage() : "");	
    	}
    	
    	if(logger.isDebugEnabled())
    		logger.debug("保存失败", exp);
		
		return rd;
    }
    
    public ResponseData failed() {
    	return failed(null, null);
    }
}
