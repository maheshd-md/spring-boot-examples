package com.example.reloadfilewithoutrestart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class MyProperties {

	private PropertiesConfiguration configuration;

	@PostConstruct
	private void init() {
		try {

			// Reload local file without restarting the application
			// Note - Use full path, auto reload does not work with path like "resource:myproperties.properties"
			String filePath = "/home/emgda/git/maheshd-md/spring-boot-examples/ReloadFileWithoutRestartDemo/src/main/resources/myproperties.properties";
			System.out.println("Loading the properties file: " + filePath);
			configuration = new PropertiesConfiguration(filePath);
			configuration.setAutoSave(true);
			FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
			fileChangedReloadingStrategy.setRefreshDelay(10 * 1000);
			configuration.setReloadingStrategy(fileChangedReloadingStrategy);

			// Load file from git repository
			// Auto reload without restarting application is not working for remote file
//			URL url;
//			String username = "username";
//			String password = "password";
//			try {
//				// Note - Use raw file path
//				// Tested with public file in public repo, not tested with files in private
//				// repository
//				url = new URL(
//						"https://raw.githubusercontent.com/maheshd-md/spring-boot-examples/master/ReloadFileWithoutRestartDemo/src/main/resources/myproperties.properties");
//				URLConnection uc = url.openConnection();
//				uc.setRequestProperty("X-Requested-With", "Curl");
//
//				String userpass = username + ":" + password;
//				String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
//
//				uc.setRequestProperty("Authorization", basicAuth);
//
//				configuration = new PropertiesConfiguration(url);
//				configuration.setAutoSave(true);
//				FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
//				fileChangedReloadingStrategy.setRefreshDelay(10 * 1000);
//				configuration.setReloadingStrategy(fileChangedReloadingStrategy);
//
//			} catch (IOException e) {
//				System.out.println("Wrong username and password");
//			}

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