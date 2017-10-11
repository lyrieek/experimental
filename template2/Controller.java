package com.versionsystem.${package}.${module}.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.versionsystem.${package}.${module}.model.${^module}UI;
import com.versionsystem.${package}.${module}.impl.${^module}Service;

@RestController
@RequestMapping("/${module}")
public class ${^module}Controller {
	
	@Autowired
	private ${^module}Service ${module}Service;
	
	private Logger logger = Logger.getLogger(${^module}Controller.class);
	
	@RequestMapping(value = "/read")
	public List<${^module}UI> read() {
		try {
			return ${module}Service.findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public Page<${^module}UI> page(@RequestBody Map<String,Object> request) {
		try {
			if (request == null || request.isEmpty()) {
				return new PageImpl<${^module}UI>(new ArrayList<${^module}UI>());
			}
			return ${module}Service.page(request);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/destroy")
	public Boolean destroy(@RequestBody ${^module}UI ${module}) {
		try {
			return ${module}Service.destroy(${module});
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/update")
	public ${^module}UI update(@RequestBody ${^module}UI ${module}) {
		try {
			return ${module}Service.update(${module});
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@RequestMapping(value = "/create")
	public ${^module}UI create(@RequestBody ${^module}UI ${module}) {
		try {
			return ${module}Service.save(${module});
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}


}