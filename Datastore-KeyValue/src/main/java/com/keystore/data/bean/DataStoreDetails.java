/**
 * 
 */
package com.keystore.data.bean;

import java.io.Serializable;
import java.sql.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lokeshkannadb
 *
 */
@Getter
@Setter
@ToString
@Component
public class DataStoreDetails implements Serializable {

	String key;
	// String stringValue;
	String conValue;
	long timetoLiveSeconds;
	String insertTimeStamp;

}
