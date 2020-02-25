# Key-ValueDataStore
A DataStore which supports Create,Read and Delete

Sample Methods Call for the Read ,Delete and Write Operation:

		DataStoreFunction service= new DataStoreFunction();
		service.createFunction(key, json, filePath, timetoLiveSeconds);
		service.readFunction(key,filePath,fileName);
		service.removeFunction(key, filePath,fileName);
		
Parameter Definitions are Given below -- 

		/**
	 * Create  Function to Create  the data in Datastore. 
	 * @param  key  an absolute URL giving the base location of the image
	 * @param  filePath to save the dataStore, if given Empty then Default file path will be taken which is D:\\. The File will be generated in the Given location in the format of FILE_DATASTORE_DD-MM-YYYY
	 * @param timetoLiveSeconds This parameter is for expiry calculation.The key will be alive for given number of seconds from the insertion time.
	 * @return      String data will be returned
	 */
	
  
  
  
  
  	/**
	 * Read Function to read the data in Datastore.
	 * 
	 * @param key - Key to read the data
	 * @param filePath - file to read the data, if given empty, it will take the default path to read which is D://  
	 * @param fileName -         The File Generated Date in dd-MM-yyyy format.If given Empty the it will read the data from current date. In create function data will be created on this format FILE_DATASTORE_DD-MM-YYYY, You can refer the date from file name also
	 * @return      String data will be returned
	 */
   
   
   
   	/**
	 * Delete Function to Delete the data in Datastore.
	 * 
	 * @param key - Key to Delete the data
	 * @param filePath - file to Delete the data, if given empty, it will take the default path to read which is D://  
	 * @param fileName -         The File Generated Date in dd-MM-yyyy format.If given Empty the it will Delete the data from current date. In create function data will be created on this format FILE_DATASTORE_DD-MM-YYYY, You can refer the date from file name also
	 * @return     String data will be returned 
	 */ 
	
