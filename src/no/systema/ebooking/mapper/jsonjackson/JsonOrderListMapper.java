/**
 * 
 */
package no.systema.ebooking.mapper.jsonjackson;

//jackson library
import org.apache.log4j.Logger;
//application library
import no.systema.ebooking.model.jsonjackson.JsonMainOrderListContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderListRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderTypesNewRecord;
import no.systema.main.mapper.jsonjackson.general.ObjectMapperAbstractGrandFather;



/**
 * @author oscardelatorre
 * @date Jun 24, 2016
 * 
 */
public class JsonOrderListMapper extends ObjectMapperAbstractGrandFather {
	private static final Logger logger = Logger.getLogger(JsonOrderListMapper.class.getName());
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonMainOrderListContainer getContainer(String utfPayload) throws Exception{
		//At this point we now have an UTF-8 payload
		JsonMainOrderListContainer container = super.getObjectMapper().readValue(utfPayload.getBytes(), JsonMainOrderListContainer.class); 
		logger.info("[JSON-String payload status=OK]  " + container.getUser());
		for (JsonMainOrderListRecord record : container.getOrderList()){
			//DEBUG
		}
		if(container!=null && container.getOrderTypesNew()!=null){
			for (JsonMainOrderTypesNewRecord record : container.getOrderTypesNew()){
				//DEBUG logger.info(record.getNewAvd());
			}
		}
		return container;
	}
	
}
