package org.hch.book.manager.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/index")
public class IndexController extends BaseController{

	@RequestMapping(value = "/list")
	public String list() {
		String json = "{\"id\":\"001\", \"name\":\"hch\"}";
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		for(Entry<String, String[]> entry: parameterMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		return json;
	}
}
