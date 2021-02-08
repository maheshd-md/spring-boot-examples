package com.example.reloadfilewithoutrestart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPropertiesController {
	
	@Autowired
	private MyProperties myProperties;
	
	@GetMapping("/getproperty/{propertyName}")
	public String getProperty(@PathVariable String propertyName) {
		
		return myProperties.getProperty(propertyName);
	}
	
		 
}
