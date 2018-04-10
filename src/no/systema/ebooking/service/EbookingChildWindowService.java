package no.systema.ebooking.service;

import no.systema.main.model.jsonjackson.general.postalcodes.JsonPostalCodesContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingLoadUnloadPlacesContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingPackingCodesContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingDangerousGoodsContainer;



public interface EbookingChildWindowService {
	public JsonPostalCodesContainer getPostalCodesContainer(String utfPayload);
	public JsonEbookingCustomerContainer getCustomerContainer(String utfPayload);
	public JsonEbookingLoadUnloadPlacesContainer getLoadUnloadPlacesContainer(String utfPayload);
	public JsonEbookingPackingCodesContainer getPackingCodesContainer(String utfPayload);
	public JsonEbookingDangerousGoodsContainer getDangerousGoodsContainer(String utfPayload);
	public JsonEbookingCustomerDeliveryAddressContainer getCustomerDeliveryAddressContainer(String utfPayload);
}
