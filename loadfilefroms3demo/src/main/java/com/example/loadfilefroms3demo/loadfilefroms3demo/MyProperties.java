package com.example.loadfilefroms3demo.loadfilefroms3demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
public class MyProperties {

	private PropertiesConfiguration configuration;

	@PostConstruct
	private void init() {

		String bucketName = "maheshd-md";
		String keyName = "myproperties.properties";
		String path = "s3://maheshd-md/myproperties.properties";

		/*
		 * Note: You need to setup aws credentials on machine where server is running
		 * To know how to setup aws credentials visit: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
		 */
		
		Region region = Region.US_EAST_2;
		S3Client s3 = S3Client.builder().region(region).build();
		
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().key(keyName).bucket(bucketName).build();

			ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
			byte[] data = objectBytes.asByteArray();

			// Write the data to a local file
			File myFile = new File("myproperties.properties");
			OutputStream os = new FileOutputStream(myFile);
			os.write(data);
			System.out.println("Successfully obtained bytes from an S3 object");
			os.close();
			
			System.out.println("Loading the properties file: " + myFile);
			configuration = new PropertiesConfiguration(myFile);
			configuration.setAutoSave(true);
			FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
			fileChangedReloadingStrategy.setRefreshDelay(10 * 1000);
			configuration.setReloadingStrategy(fileChangedReloadingStrategy);

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
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