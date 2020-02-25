/**
 * 
 */
package com.keystore.data.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keystore.data.bean.CommonValues;
import com.keystore.data.bean.DataStoreDetails;
import com.keystore.data.error.ErrorConstants;
import com.keystore.data.error.ErrorDetails;

/**
 * @author lokeshkannadb
 *
 */
public class BusinessValidation implements Serializable, BusinessValidationConstants, ErrorConstants {
	/**
	 * Add additional validation for Write Function if required
	 * 
	 * @param commonValues
	 * @param dataStoreDetails
	 * @param errorDetails
	 */
	public boolean createDataValidation(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {

		boolean validationStatus = false;
		if (keyLengethValidation(errorDetails, dataStoreDetails, commonValues)) {
			if (!keyExistsValidation(errorDetails, dataStoreDetails, commonValues)) {
				validationStatus = true;
			} else {
				errorDetails.setErrorDesc(KEY_EXISTS);
			}
		}

		return validationStatus;
	}

	/**
	 * Add additional validation for Read Function if required
	 */
	public boolean readDataValidation(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {

		boolean validationStatus = false;
		if (keyLengethValidation(errorDetails, dataStoreDetails, commonValues)) {
			if (keyExistsValidation(errorDetails, dataStoreDetails, commonValues)) {
				validationStatus = true;
			} else {
				errorDetails.setErrorDesc(KEY_NOT_AVAILABLE);
			}
		}

		return validationStatus;
	}

	/**
	 * Add additional validation for Delete Function if required
	 */
	public boolean deleteDataValidation(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {

		boolean validationStatus = false;
		if (keyLengethValidation(errorDetails, dataStoreDetails, commonValues)) {
			if (keyExistsValidation(errorDetails, dataStoreDetails, commonValues)) {
				validationStatus = true;
			} else {
				errorDetails.setErrorDesc(KEY_NOT_AVAILABLE);
			}
		}

		return validationStatus;
	}

	public boolean keyLengethValidation(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {

		if (dataStoreDetails.getKey().length() > KEY_LENGTH) {
			errorDetails.setErrorDesc(KEY_LENGTH_EXCEEDED);
			return false;

		}
		return true;
	}

	public boolean keyExistsValidation(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {

		// FileInputStream fileip = null;
		HashMap<String, DataStoreDetails> mapDetails = new HashMap<String, DataStoreDetails>();
		// ObjectInputStream objectip = null;
		FileOutputStream fileop = null;
		ObjectOutputStream objectop = null;
		commonValues.setFilePresent(false);
		boolean exists = false;

		try {
			File file = new File(commonValues.getFilePath() + commonValues.getFileName());
			if (file.exists()) {
				commonValues.setFilePresent(true);
				try (FileInputStream fileip = new FileInputStream(file)) {
					try (ObjectInputStream objectip = new ObjectInputStream(fileip)) {
						mapDetails = (HashMap<String, DataStoreDetails>) objectip.readObject();
						if (mapDetails.containsKey(dataStoreDetails.getKey())) {
							exists = true;
							commonValues.setReadDataValue(mapDetails.get(dataStoreDetails.getKey()));
							if (commonValues.isReadOperation()) {
								if (timeToLiveValidation(errorDetails, dataStoreDetails, commonValues)) {
									commonValues.setReadSuccess(true);
								} else {
									errorDetails.setErrorDesc(KEY_EXPIRED);
								}
							}
							if (commonValues.isDeleteOperation()) {
								if (timeToLiveValidation(errorDetails, dataStoreDetails, commonValues)) {
									mapDetails.remove(dataStoreDetails.getKey());
									commonValues.setDeleteSuccess(true);
								} else {
									errorDetails.setErrorDesc(KEY_EXPIRED);
								}
								fileop = new FileOutputStream(file);
								objectop = new ObjectOutputStream(fileop);
								objectop.writeObject(mapDetails);
								fileop.close();
								objectop.close();
							}

						}

						fileip.close();
						objectip.close();

					}

				}

			} else {
				errorDetails.setErrorDesc(FILE_NOT_EXISTS);
			}
		} catch (Exception e) {
			if (exists && commonValues.isReadOperation()) {
				errorDetails.setErrorDesc(READ_FAILURE);
			}
			if (exists && commonValues.isDeleteOperation()) {
				errorDetails.setErrorDesc(DELETE_FAILURE);
			}
		}

		return exists;
	}

	public boolean timeToLiveValidation(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {
		boolean timeTiLive = false;
		String insertTimeStamp = commonValues.getReadDataValue().getInsertTimeStamp();
		long expiry = commonValues.getReadDataValue().getTimetoLiveSeconds();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = dateFormat.parse(insertTimeStamp);
			d2 = dateFormat.parse(currentDate);

			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000;
			if (diffSeconds <= expiry) {
				timeTiLive = true;
			}
		} catch (Exception e) {
			errorDetails.setErrorDesc(OPERATION_FAILED);
		}
		return timeTiLive;
	}

	public boolean dataWriting(ErrorDetails errorDetails, DataStoreDetails dataStoreDetails,
			CommonValues commonValues) {

		boolean writeStatus = false;
		FileInputStream fileip = null;
		HashMap<String, DataStoreDetails> mapDetails = new HashMap<String, DataStoreDetails>();
		ObjectInputStream objectip = null;
		// FileOutputStream fileop = null;
		ObjectOutputStream objectop = null;
		FileOutputStream fileop = null;
		FileWriter fileWriter = null;
		commonValues.setFilePresent(false);
		boolean exists = false;
		File file = null;
		try {
			file = new File(commonValues.getFilePath() + commonValues.getFileName());
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dataStoreDetails.setInsertTimeStamp(dateFormat.format(date));

			if (!file.exists()) {
				fileop = new FileOutputStream(file);
				fileop = new FileOutputStream(file);
				objectop = new ObjectOutputStream(fileop);
				mapDetails.put(dataStoreDetails.getKey(), dataStoreDetails);
				objectop.writeObject(mapDetails);
				writeStatus = true;
				commonValues.setReadSuccess(true);
			} else {

				double bytes = file.length();
				// double bytes =1.25e+8;
				double kilobytes = (bytes / 1024);
				double megabytes = (kilobytes / 1024);
				double gigabytes = (megabytes / 1024);

				if (gigabytes < 0.11641532182693481) {
					fileip = new FileInputStream(file);
					objectip = new ObjectInputStream(fileip);
					mapDetails = (HashMap<String, DataStoreDetails>) objectip.readObject();
					mapDetails.put(dataStoreDetails.getKey(), dataStoreDetails);
					fileop = new FileOutputStream(file);
					objectop = new ObjectOutputStream(fileop);
					objectop.writeObject(mapDetails);
					writeStatus = true;
					commonValues.setReadSuccess(true);
					fileip.close();
					objectip.close();
				} else {
					errorDetails.setErrorDesc(FILE_SIZE_EXCEEDS);
				}

			}

		} catch (Exception e) {
			errorDetails.setErrorDesc(READ_FAILURE);
		} finally {
			try {
				fileop.close();
				objectop.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return writeStatus;
	}

}
