/**
 * 
 */
package no.systema.ebooking.service;

import no.systema.ebooking.mapper.jsonjackson.JsonOrderListMapper;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderListContainer;

/**
 * 
 * @author oscardelatorre
 * @date Jun 22, 2015
 * 
 * 
 */
public class EbookingMainOrderListServiceImpl implements EbookingMainOrderListService {

	/**
	 * 
	 */
	public JsonMainOrderListContainer getMainListContainer(String utfPayload) {
		JsonMainOrderListContainer container = null;
		try{
			JsonOrderListMapper mapper = new JsonOrderListMapper();
			container = mapper.getContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	

}
