/**
 * 
 */
package no.systema.ebooking.mapper.jsonjackson;

//jackson library
import org.apache.log4j.Logger;

//application library
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderFraktbrevContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderFraktbrevRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderMessageNoteContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderMessageNoteRecord;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressRecord;
import no.systema.main.mapper.jsonjackson.general.ObjectMapperAbstractGrandFather;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderArchivedDocsContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderArchivedDocsRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderFileUploadValidationContainer;




/**
 * @author oscardelatorre
 * @date Jan 06, 2017
 * 
 */
public class JsonOrderHeaderMapper extends ObjectMapperAbstractGrandFather {
	private static final Logger logger = Logger.getLogger(JsonOrderHeaderMapper.class.getName());
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonMainOrderHeaderContainer getContainer(String utfPayload) throws Exception{
		
		//At this point we now have an UTF-8 payload
		JsonMainOrderHeaderContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonMainOrderHeaderContainer.class); 
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		for (JsonMainOrderHeaderRecord record : container.getOneorder()){
			//DEBUG
		}
		
		return container;
	}
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonMainOrderHeaderFraktbrevContainer getFraktbrevContainer(String utfPayload) throws Exception{
		//At this point we now have an UTF-8 payload
		JsonMainOrderHeaderFraktbrevContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonMainOrderHeaderFraktbrevContainer.class); 
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		
		
		return container;
	}
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingCustomerDeliveryAddressContainer getDeliveryAddressContainer(String utfPayload) throws Exception{
		//At this point we now have an UTF-8 payload
		JsonEbookingCustomerDeliveryAddressContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingCustomerDeliveryAddressContainer.class); 
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		for (JsonEbookingCustomerDeliveryAddressRecord record : container.getInqdeladdr()){
			//DEBUG
		}
		
		return container;
	}
	
	/**
	 * 
	 * @param Payload
	 * @return
	 */
	public JsonMainOrderHeaderMessageNoteContainer getMessageNoteContainer(String utfPayload) throws Exception{
		//At this point we now have an UTF-8 payload
		JsonMainOrderHeaderMessageNoteContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonMainOrderHeaderMessageNoteContainer.class); 
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		for (JsonMainOrderHeaderMessageNoteRecord record : container.getFreetextlist()){
			//DEBUG
		}
		
		return container;
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonMainOrderHeaderArchivedDocsContainer getArchiveDocsContainer(String utfPayload) throws Exception{
		//At this point we now have an UTF-8 payload
		JsonMainOrderHeaderArchivedDocsContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonMainOrderHeaderArchivedDocsContainer.class); 
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		for (JsonMainOrderHeaderArchivedDocsRecord record : container.getGetdoctrip()){
			//DEBUG
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
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		
		
		return container;
	}
	
	
}
