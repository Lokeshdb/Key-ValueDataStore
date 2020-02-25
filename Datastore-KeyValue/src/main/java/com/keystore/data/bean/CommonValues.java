package com.keystore.data.bean;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class CommonValues {

	boolean filePresent;
	String filePath;
	String fileName;
	JSONObject givenJson;
	DataStoreDetails readDataValue;
	boolean deleteOperation;
	boolean readOperation;
	boolean writeOperation;
	boolean readSuccess;
	boolean writeSuccess;
	boolean deleteSuccess;
	long timeToLive;

}
