/**
 * 
 */
package no.systema.ebooking.mapper.jsonjackson;


import org.apache.log4j.Logger;
//application library

import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerRecord;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressRecord;

import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingLoadUnloadPlacesContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingLoadUnloadPlacesRecord;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingPackingCodesContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingPackingCodesRecord;
import no.systema.main.mapper.jsonjackson.general.ObjectMapperAbstractGrandFather;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderFileUploadValidationContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingDangerousGoodsContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingDangerousGoodsRecord;

//
import java.util.*;

/**
 * 
 * @author oscardelatorre
 * @date Jan 30, 2017
 * 
 * 
 */
public class JsonEbookingChildWindowMapper extends ObjectMapperAbstractGrandFather {
	private static final Logger logger = Logger.getLogger(JsonEbookingChildWindowMapper.class.getName());
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingCustomerContainer getCustomerContainer(String utfPayload) throws Exception{
		
		//At this point we now have an UTF-8 payload
		JsonEbookingCustomerContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingCustomerContainer.class); 
		
		//DEBUG
		Collection<JsonEbookingCustomerRecord> fields = container.getInqFkund();
		for(JsonEbookingCustomerRecord record : fields){
			//logger.info("knavn: " + record.getKnavn());
			//logger.info("kundnr: " + record.getKundnr());
		}
		return container;
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingCustomerDeliveryAddressContainer getCustomerDeliveryAddressesContainer(String utfPayload) throws Exception{
		
		//At this point we now have an UTF-8 payload
		JsonEbookingCustomerDeliveryAddressContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingCustomerDeliveryAddressContainer.class); 
		
		//DEBUG
		Collection<JsonEbookingCustomerDeliveryAddressRecord> fields = container.getInqdeladdr();
		for(JsonEbookingCustomerDeliveryAddressRecord record : fields){
			//logger.info("knavn: " + record.getKnavn());
			//logger.info("kundnr: " + record.getKundnr());
		}
		return container;
	}
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingLoadUnloadPlacesContainer getLoadUnloadPlacesContainer(String utfPayload) throws Exception{
		
		//At this point we now have an UTF-8 payload
		JsonEbookingLoadUnloadPlacesContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingLoadUnloadPlacesContainer.class); 
		
		//DEBUG
		Collection<JsonEbookingLoadUnloadPlacesRecord> fields = container.getInqlosslass();
		for(JsonEbookingLoadUnloadPlacesRecord record : fields){
			//logger.info("knavn: " + record.getKnavn());
			//logger.info("kundnr: " + record.getKundnr());
		}
		return container;
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingPackingCodesContainer getPackingCodesContainer(String utfPayload) throws Exception{
		
		//At this point we now have an UTF-8 payload
		JsonEbookingPackingCodesContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingPackingCodesContainer.class); 
		
		//DEBUG
		Collection<JsonEbookingPackingCodesRecord> fields = container.getForpaknKoder();
		for(JsonEbookingPackingCodesRecord record : fields){
			//logger.info("knavn: " + record.getKnavn());
			//logger.info("kundnr: " + record.getKundnr());
		}
		return container;
	}
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingDangerousGoodsContainer getDangerousGoodsContainer(String utfPayload) throws Exception{
		
		//At this point we now have an UTF-8 payload
		JsonEbookingDangerousGoodsContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingDangerousGoodsContainer.class); 
		
		//DEBUG
		Collection<JsonEbookingDangerousGoodsRecord> fields = container.getUnNumbers();
		for(JsonEbookingDangerousGoodsRecord record : fields){
			//logger.info("knavn: " + record.getKnavn());
			//logger.info("kundnr: " + record.getKundnr());
		}
		return container;
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonMainOrderFileUploadValidationContainer getFileUploadValidationContainer(String utfPayload) throws Exception{
		//At this point we now have an UTF-8 payload
		JsonMainOrderFileUploadValidationContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonMainOrderFileUploadValidationContainer.class); 
		logger.info("[JSON-String payload errMsg]:" + container.getErrMsg());
		
		return container;
	}
}
