package no.systema.ebooking.service;

import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingDangerousGoodsContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingLoadUnloadPlacesContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingPackingCodesContainer;
import no.systema.ebooking.mapper.jsonjackson.JsonEbookingChildWindowMapper;
import no.systema.main.model.jsonjackson.general.postalcodes.JsonPostalCodesContainer;
import no.systema.main.mapper.jsonjackson.general.PostalCodesMapper;


/**
 * 
 * @author oscardelatorre
 * @date Feb 2, 2016 
 * 
 */
public class EbookingChildWindowServiceImpl implements EbookingChildWindowService {
	/**
	 * 
	 */
	public JsonPostalCodesContainer getPostalCodesContainer(String utfPayload){
		JsonPostalCodesContainer container = null;
		try{
			PostalCodesMapper mapper = new PostalCodesMapper();
			container = mapper.getContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	
	
	/**
	 * 
	 */
	public JsonEbookingCustomerContainer getCustomerContainer(String utfPayload){
		JsonEbookingCustomerContainer container = null;
		try{
			JsonEbookingChildWindowMapper mapper = new JsonEbookingChildWindowMapper();
			container = mapper.getCustomerContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
		
	}
	/**
	 * 
	 */
	public JsonEbookingCustomerDeliveryAddressContainer getCustomerDeliveryAddressContainer(String utfPayload){
		JsonEbookingCustomerDeliveryAddressContainer container = null;
		try{
			JsonEbookingChildWindowMapper mapper = new JsonEbookingChildWindowMapper();
			container = mapper.getCustomerDeliveryAddressesContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	/**
	 * 
	 */
	public JsonEbookingLoadUnloadPlacesContainer getLoadUnloadPlacesContainer(String utfPayload){
		JsonEbookingLoadUnloadPlacesContainer container = null;
		try{
			JsonEbookingChildWindowMapper mapper = new JsonEbookingChildWindowMapper();
			container = mapper.getLoadUnloadPlacesContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	
	/**
	 * 
	 */
	public JsonEbookingPackingCodesContainer getPackingCodesContainer(String utfPayload){
		JsonEbookingPackingCodesContainer container = null;
		try{
			JsonEbookingChildWindowMapper mapper = new JsonEbookingChildWindowMapper();
			container = mapper.getPackingCodesContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
	/**
	 * 
	 */
	public JsonEbookingDangerousGoodsContainer getDangerousGoodsContainer(String utfPayload){
		JsonEbookingDangerousGoodsContainer container = null;
		try{
			JsonEbookingChildWindowMapper mapper = new JsonEbookingChildWindowMapper();
			container = mapper.getDangerousGoodsContainer(utfPayload);
		}catch(Exception e){
			e.printStackTrace();
		}
		return container;
	}
}
