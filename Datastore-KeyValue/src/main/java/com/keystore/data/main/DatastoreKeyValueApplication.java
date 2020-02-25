package com.keystore.data.main;

import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.datastore.main.utils.CommonUtils;
import com.keystore.data.error.ErrorDetails;

@SpringBootApplication
@ComponentScan(basePackages = "com.keystore")
public class DatastoreKeyValueApplication {

	public static void main(String[] args) {
	        SpringApplication.run(DatastoreKeyValueApplication.class, args);
		// System.out.println(dsf.test());
		//ApplicationContext applicationContext = SpringApplication.run(DatastoreKeyValueApplication.class, args);
	//	DataStoreFunction service = applicationContext.getBean(DataStoreFunction.class);

		JSONObject json = new JSONObject();
		try {
			json.put("firstName", "onee");
		} catch (JSONException e) {

			e.printStackTrace();
		}

		String filePath = "D:\\DataStore\\";
		String key = "4";
		int timetoLiveSeconds = 10;
		String fileName="25-02-2020";
		DataStoreFunction service= new DataStoreFunction();
		System.out.println("Execution Result Write>>>" + service.createFunction(key, json, filePath, timetoLiveSeconds));
		System.out.println( "Execution Result for Read>>>"+service.readFunction(key,filePath,fileName));
		//System.out.println("Execution Result for Read>>>" + service.removeFunction(key, filePath,fileName));

	}

}
