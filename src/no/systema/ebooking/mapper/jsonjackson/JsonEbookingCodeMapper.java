/**
 * 
 */
package no.systema.ebooking.mapper.jsonjackson;

//jackson library
import org.apache.log4j.Logger;

//application library
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingCodeContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingCodeRecord;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingFrankaturContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingFrankaturRecord;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingOppdragTypeContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingOppdragTypeRecord;
import no.systema.main.mapper.jsonjackson.general.ObjectMapperAbstractGrandFather;

import java.util.*;

/**
 * 
 * @author oscardelatorre
 * @date Jan 07, 2017
 * 
 * 
 */
public class JsonEbookingCodeMapper extends ObjectMapperAbstractGrandFather {
	private static final Logger logger = Logger.getLogger(JsonEbookingCodeMapper.class.getName());
	
	public JsonEbookingCodeContainer getContainer(String utfPayload) throws Exception{
		
		JsonEbookingCodeContainer codeContainer = null;
		
		if(utfPayload!=null){
			//At this point we now have an UTF-8 payload
			codeContainer = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingCodeContainer.class); 
			//logger.info("Mapping Code object from JSON payload...");
			//logger.info("[JSON-String payload status=OK]  " + codeContainer.getUser());
			
			//DEBUG
			Collection<JsonEbookingCodeRecord> fields = codeContainer.getKodlista();
			for(JsonEbookingCodeRecord record : fields){

			}
		}	
		return codeContainer;
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingFrankaturContainer getFrankaturContainer(String utfPayload) throws Exception{
		
		JsonEbookingFrankaturContainer container = null;
		
		if(utfPayload!=null){
			//At this point we now have an UTF-8 payload
			container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingFrankaturContainer.class); 
			//logger.info("Mapping Code object from JSON payload...");
			//logger.info("[JSON-String payload status=OK]  " + codeContainer.getUser());
			
			//DEBUG
			Collection<JsonEbookingFrankaturRecord> fields = container.getFrankaturer();
			for(JsonEbookingFrankaturRecord record : fields){

			}
		}	
		return container;
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingOppdragTypeContainer getOppdragTypeContainer(String utfPayload) throws Exception{
		
		JsonEbookingOppdragTypeContainer container = null;
		
		if(utfPayload!=null){
			//At this point we now have an UTF-8 payload
			container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonEbookingOppdragTypeContainer.class); 
			//logger.info("Mapping Code object from JSON payload...");
			//logger.info("[JSON-String payload status=OK]  " + codeContainer.getUser());
			
			//DEBUG
			Collection<JsonEbookingOppdragTypeRecord> fields = container.getOppdragsTyper();
			for(JsonEbookingOppdragTypeRecord record : fields){

			}
		}	
		return container;
	}
	
	
}
