package com.keystore.data.error;

import org.springframework.stereotype.Component;

@Component
public interface ErrorConstants {

	String KEY_LENGTH_EXCEEDED = "The Length of the Given key is Exceeded ! Should be less than 32 Chars";
	String KEY_EXISTS = "The Given Key Values is already exists! Please provide unique key";
	String KEY_NOT_AVAILABLE = "The Given Key Value is not present in the Generated Datastore";
	String READ_FAILURE = "Operation Failed while Reading the data in Datastore";
	String DELETE_FAILURE = "Operation Failed while Deleting the data in Datastore";
	String WRITE_FAILURE = "Operation Failed while Writing the data in Datastore";
	String FILE_SIZE_EXCEEDS = "The Size of the file is already more than 1GB, Please create new file !!";
	String OPERATION_FAILED = "Operation Failure";
	String KEY_EXPIRED = "The Key Time was Expired , Not applicable to perform Read/Delete";
	String FILE_NOT_EXISTS="The Given file date not exists in the Given filepath!! Unable to proceed read/Delete Operation ";

}
