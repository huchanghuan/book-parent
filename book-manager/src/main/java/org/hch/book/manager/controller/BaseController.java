package org.hch.book.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hch.book.util.util.JsonUtil;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * base controller
 * @ClassName BaseController
 * @Description TODO
 * @author huchanghuan
 * @Date 2017年3月28日 下午6:19:49
 * @version 1.0.0
 */
public class BaseController {

	protected HttpServletRequest req;
	
	protected HttpServletResponse resp;
	
	@ModelAttribute
	public void before(HttpServletRequest request, HttpServletResponse response) {
		this.req = request;
		this.resp = response;
	}
	
	public String WriteAsJsonString(Object obj) {
		return JsonUtil.obj2JsonString(obj);
	}
	
	protected String getRequestAddr() {
		String requestAddr = req.getHeader("x-forward-for");
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getHeader("X-Real-IP");
		}
		
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getHeader("Proxy-Client-Ip");
		}
		
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getHeader("WL-Proxy-Client-IP");
		}
		
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getHeader("HTTP_CLIENT_IP");
		}
		
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getHeader("HTTP-X-FORWARD-FOR");
		}
		
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getRemoteAddr();
		}
		
		if (null == requestAddr || requestAddr.isEmpty() || "unknown".equalsIgnoreCase(requestAddr)) {
			requestAddr = req.getRemoteHost();
		}
		
		return requestAddr;
	}
}
