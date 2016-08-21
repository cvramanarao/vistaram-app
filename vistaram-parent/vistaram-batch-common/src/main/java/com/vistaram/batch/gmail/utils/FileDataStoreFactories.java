package com.vistaram.batch.gmail.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

public class FileDataStoreFactories {
	
	 private static final java.io.File DATA_STORE_DIR_VISTARAMROOMS = new java.io.File(
		        System.getProperty("user.home"), ".credentials/vistaramrooms-credentials.json");
	 private static final java.io.File DATA_STORE_DIR_VISTARAMROOMS_WEB = new java.io.File(
		        System.getProperty("user.home"), ".credentials/vistaramrooms-web-credentials.json");
	 private static final java.io.File DATA_STORE_DIR_SHIRIVARSHINI = new java.io.File(
		        System.getProperty("user.home"), ".credentials/shirivarshini-credentials.json");
	 private static final java.io.File DATA_STORE_DIR_HOLIDAYINN = new java.io.File(
		        System.getProperty("user.home"), ".credentials/holidayinn-credentials.json");
	 private static final java.io.File DATA_STORE_DIR_LEELAGRAND = new java.io.File(
		        System.getProperty("user.home"), ".credentials/leelagrand-credentials.json");
	 private static final java.io.File DATA_STORE_DIR_YOVERSEAS = new java.io.File(
		        System.getProperty("user.home"), ".credentials/yoverseas-credentials.json");
	 private static final java.io.File DATA_STORE_DIR_SHIRDISAI = new java.io.File(
		        System.getProperty("user.home"), ".credentials/shirdisai-credentials.json");
	 
	 
	  private static FileDataStoreFactory DATA_STORE_FACTORY_VISTARAMROOMS;
	  private static FileDataStoreFactory DATA_STORE_FACTORY_VISTARAMROOMS_WEB;
	  private static FileDataStoreFactory DATA_STORE_FACTORY_SHIRIVARSHINI;
	  private static FileDataStoreFactory DATA_STORE_FACTORY_HOLIDAYINN;
	  private static FileDataStoreFactory DATA_STORE_FACTORY_LEELAGRAND;
	  private static FileDataStoreFactory DATA_STORE_FACTORY_YOVERSEAS;
	  private static FileDataStoreFactory DATA_STORE_FACTORY_SHIRDISAI;
	  
	  static {
	        try {
	            DATA_STORE_FACTORY_VISTARAMROOMS = new FileDataStoreFactory(DATA_STORE_DIR_VISTARAMROOMS);
	            DATA_STORE_FACTORY_VISTARAMROOMS_WEB = new FileDataStoreFactory(DATA_STORE_DIR_VISTARAMROOMS_WEB);
	            DATA_STORE_FACTORY_SHIRIVARSHINI = new FileDataStoreFactory(DATA_STORE_DIR_SHIRIVARSHINI);
	            DATA_STORE_FACTORY_HOLIDAYINN = new FileDataStoreFactory(DATA_STORE_DIR_HOLIDAYINN);
	            DATA_STORE_FACTORY_LEELAGRAND = new FileDataStoreFactory(DATA_STORE_DIR_LEELAGRAND );
	            DATA_STORE_FACTORY_YOVERSEAS = new FileDataStoreFactory(DATA_STORE_DIR_YOVERSEAS);
	            DATA_STORE_FACTORY_SHIRDISAI = new FileDataStoreFactory(DATA_STORE_DIR_SHIRDISAI);
	            
	        } catch (Throwable t) {
	            t.printStackTrace();
	            System.exit(1);
	        }
	    }
	  
	  
	  public static FileDataStoreFactory getFileDataStoreFactory(String userFileDirectoryParam){
		  if(userFileDirectoryParam.equalsIgnoreCase("vistaramrooms")) {
			  return DATA_STORE_FACTORY_VISTARAMROOMS;
		  } else  if(userFileDirectoryParam.equalsIgnoreCase("shirivarshini")) {
			  return DATA_STORE_FACTORY_SHIRIVARSHINI;
		  } else  if(userFileDirectoryParam.equalsIgnoreCase("holidayinn")) {
			  return DATA_STORE_FACTORY_HOLIDAYINN;
		  } else  if(userFileDirectoryParam.equalsIgnoreCase("leelagrand")) {
			  return DATA_STORE_FACTORY_LEELAGRAND;
		  } else  if(userFileDirectoryParam.equalsIgnoreCase("shirdisai")) {
			  return DATA_STORE_FACTORY_SHIRDISAI;
		  } if(userFileDirectoryParam.equalsIgnoreCase("vistaramrooms-web")) {
			  return DATA_STORE_FACTORY_VISTARAMROOMS_WEB;
		  }
		  return null;
		  
	  }

}
