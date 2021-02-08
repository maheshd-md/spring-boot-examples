package com.example.reloadfilewithoutrestart;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.stereotype.Component;

@Component
public class MyProperties {
	
	private PropertiesConfiguration configuration;

	@PostConstruct
	private void init() {
		try {
			String filePath = "/home/emgda/git/maheshd-md/spring-boot-examples/ReloadFileWithoutRestartDemo/src/main/resources/myproperties.properties";
			System.out.println("Loading the properties file: " + filePath);
			configuration = new PropertiesConfiguration(filePath);
			configuration.setAutoSave(true);

			// Create new FileChangedReloadingStrategy to reload the properties file based
			// on the given time interval
			FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
			fileChangedReloadingStrategy.setRefreshDelay(10 * 1000);
			configuration.setReloadingStrategy(fileChangedReloadingStrategy);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public String getProperty(String key) {
		return (String) configuration.getProperty(key);
	}

	public void setProperty(String key, Object value) {
		configuration.setProperty(key, value);
	}

	public void save() {
		try {
			configuration.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}