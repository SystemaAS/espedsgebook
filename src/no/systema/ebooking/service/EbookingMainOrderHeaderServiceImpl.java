/**
 * 
 */
package no.systema.ebooking.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import no.systema.ebooking.mapper.jsonjackson.JsonOrderHeaderMapper;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderFileUploadValidationContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderArchivedDocsContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderFraktbrevContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderMessageNoteContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressContainer;


/**
 * 
 * @author oscardelatorre
 * @date Jan 06, 2017
 * 
 * 
 */
public class EbookingMainOrderHeaderServiceImpl implements EbookingMainOrderHeaderService {

	/**
	 * 
	 */
	public JsonMainOrderHeaderContainer getContainer(String utfPayload) {
		JsonMainOrderHeaderContainer container = null;
		try{
			JsonOrderHeaderMapper mapper = new JsonOrderHeaderMapper();
			container = mapper.getContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	
	/**
	 * 
	 */
	public JsonMainOrderHeaderFraktbrevContainer getFraktbrevContainer(String utfPayload){
		JsonMainOrderHeaderFraktbrevContainer container = null;
		try{
			JsonOrderHeaderMapper mapper = new JsonOrderHeaderMapper();
			container = mapper.getFraktbrevContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	/**
	 * 
	 */
	public JsonEbookingCustomerDeliveryAddressContainer getDeliveryAddressContainer(String utfPayload){
		JsonEbookingCustomerDeliveryAddressContainer container = null;
		try{
			JsonOrderHeaderMapper mapper = new JsonOrderHeaderMapper();
			container = mapper.getDeliveryAddressContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	/**
	 * 
	 */
	public JsonMainOrderHeaderMessageNoteContainer getMessageNoteContainer(String utfPayload){
		JsonMainOrderHeaderMessageNoteContainer container = null;
		try{
			JsonOrderHeaderMapper mapper = new JsonOrderHeaderMapper();
			container = mapper.getMessageNoteContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return container;
		
	}
	/**
	 * 
	 */
	public JsonMainOrderHeaderArchivedDocsContainer getArchiveDocsContainer(String utfPayload){
		JsonMainOrderHeaderArchivedDocsContainer container = null;
		try{
			JsonOrderHeaderMapper mapper = new JsonOrderHeaderMapper();
			container = mapper.getArchiveDocsContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return container;
		
	}
	/**
	 * 
	 */
	public JsonMainOrderFileUploadValidationContainer getFileUploadValidationContainer(String utfPayload){
		JsonMainOrderFileUploadValidationContainer container = null;
		try{
			JsonOrderHeaderMapper mapper = new JsonOrderHeaderMapper();
			container = mapper.getFileUploadValidationContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
		
	}

}
