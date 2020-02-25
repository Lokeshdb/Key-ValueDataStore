/**
 * 
 */
package com.keystore.data.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keystore.data.bean.CommonValues;
import com.keystore.data.bean.DataStoreDetails;
import com.keystore.data.business.BusinessValidation;
import com.keystore.data.business.BusinessValidationConstants;
import com.keystore.data.error.ErrorDetails;

/**
 * @author lokeshkannadb
 *
 */
public class DataStoreFunction implements BusinessValidationConstants {

		/**
	 * Create  Function to Create  the data in Datastore. 
	 * @param  key  an absolute URL giving the base location of the image
	 * @param  filePath to save the dataStore, if given Empty then Default file path will be taken which is D:\\. The File will be generated in the Given location in the format of FILE_DATASTORE_DD-MM-YYYY
	 * @param timetoLiveSeconds This parameter is for expiry calculation.The key will be alive for given number of seconds from the insertion time.
	 * @return      String data will be returned
	 */
	
	public String createFunction(String key, JSONObject json, String filePath, int timetoLiveSeconds) {

		ErrorDetails errorDetails = new ErrorDetails();
		BusinessValidation businessValidation = new BusinessValidation();
		DataStoreDetails dataStoreDetails = new DataStoreDetails();
		CommonValues commonValues = new CommonValues();

		errorDetails.setErrorDesc("");
		commonValues.setGivenJson(json);
		dataStoreDetails.setConValue(json.toString());
		dataStoreDetails.setKey(key);
		dataStoreDetails.setTimetoLiveSeconds(timetoLiveSeconds);
		commonValues.setFilePath(filePath);
		commonValues.setFileName(getFileNameInDate());
		commonValues.setWriteOperation(true);
		commonValues.setReadSuccess(false);

		if (commonValues.getFilePath().equals(EMPTY) || commonValues.getFilePath().equals(null)) {
			commonValues.setFilePath(DEFAULT_FILE_PATH);
		}
		// commonValues.setFilePath(commonValues.getFilePath()+commonValues.getFileName());

		if (businessValidation.createDataValidation(errorDetails, dataStoreDetails, commonValues)) {
			if (businessValidation.dataWriting(errorDetails, dataStoreDetails, commonValues)) {
				return WRITE_SUCCESS;
			}
		}

		return WRITE_FAIL + errorDetails.getErrorDesc();

	}

	/**
	 * Read Function to read the data in Datastore.
	 * 
	 * @param key - Key to read the data
	 * @param filePath - file to read the data, if given empty, it will take the default path to read which is D://  
	 * @param fileName -         The File Generated Date in dd-MM-yyyy format.If given Empty the it will read the data from current date. In create function data will be created on this format FILE_DATASTORE_DD-MM-YYYY, You can refer the date from file name also
	 * @return      String data will be returned
	 */

	public String readFunction(String key, String filePath, String fileGeneratedDate) {
		ErrorDetails errorDetails = new ErrorDetails();
		BusinessValidation businessValidation = new BusinessValidation();
		DataStoreDetails dataStoreDetails = new DataStoreDetails();
		CommonValues commonValues = new CommonValues();

		errorDetails.setErrorDesc("");
		dataStoreDetails.setKey(key);
		commonValues.setFilePath(filePath);
		commonValues.setFileName(fileGeneratedDate);
		commonValues.setReadOperation(true);
		commonValues.setReadSuccess(false);

		if (commonValues.getFilePath().equals(EMPTY) || commonValues.getFilePath().equals(null)) {
			commonValues.setFilePath(DEFAULT_FILE_PATH);
		}
		if (commonValues.getFileName().equals(EMPTY) || commonValues.getFileName().equals(null)) {
			commonValues.setFileName(getFileNameInDate());
		} else {
			commonValues.setFileName(FILE_NAME_PATTERN + fileGeneratedDate);
		}

		if (businessValidation.readDataValidation(errorDetails, dataStoreDetails, commonValues)) {
			if (commonValues.isReadSuccess() && errorDetails.getErrorDesc().equals(EMPTY)) {
				return commonValues.getReadDataValue().getConValue();
			}
		}

		return READ_FAIL + errorDetails.getErrorDesc();

	}

	/**
	 * Delete Function to Delete the data in Datastore.
	 * 
	 * @param key - Key to Delete the data
	 * @param filePath - file to Delete the data, if given empty, it will take the default path to read which is D://  
	 * @param fileName -         The File Generated Date in dd-MM-yyyy format.If given Empty the it will Delete the data from current date. In create function data will be created on this format FILE_DATASTORE_DD-MM-YYYY, You can refer the date from file name also
	 * @return     String data will be returned 
	 */ 
	
	public String removeFunction(String key, String filePath, String fileGeneratedDate) {
		ErrorDetails errorDetails = new ErrorDetails();
		BusinessValidation businessValidation = new BusinessValidation();
		DataStoreDetails dataStoreDetails = new DataStoreDetails();
		CommonValues commonValues = new CommonValues();

		commonValues.setDeleteOperation(true);
		commonValues.setDeleteSuccess(false);
		errorDetails.setErrorDesc("");
		dataStoreDetails.setKey(key);
		commonValues.setFilePath(filePath);
		commonValues.setFileName(fileGeneratedDate);
		commonValues.setReadOperation(true);

		if (commonValues.getFilePath().equals(EMPTY) || commonValues.getFilePath().equals(null)) {
			commonValues.setFilePath(DEFAULT_FILE_PATH);
		}

		if (commonValues.getFileName().equals(EMPTY) || commonValues.getFileName().equals(null)) {
			commonValues.setFileName(getFileNameInDate());
		} else {
			commonValues.setFileName(FILE_NAME_PATTERN + fileGeneratedDate);
		}

		if (businessValidation.deleteDataValidation(errorDetails, dataStoreDetails, commonValues)) {
			if (commonValues.isDeleteSuccess() && errorDetails.getErrorDesc().equals(EMPTY)) {
				return DELETE_SUCCESS;
			}
		}

		return DELETE_FAIL + errorDetails.getErrorDesc();

	}

	public String getFileNameInDate() {
		String filename = "";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		filename = FILE_NAME_PATTERN + dateFormat.format(date);
		return filename;
	}

}
