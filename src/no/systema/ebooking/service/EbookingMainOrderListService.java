/**
 * 
 */
package no.systema.ebooking.service;

import no.systema.ebooking.model.jsonjackson.JsonMainOrderListContainer;


/**
 * 
 * @author oscardelatorre
 * @date Jun 24, 2016
 * 
 *
 */
public interface EbookingMainOrderListService {
	public JsonMainOrderListContainer getMainListContainer(String utfPayload);

}
