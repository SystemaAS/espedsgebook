package no.systema.ebooking.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerDeliveryAddressRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderRecord;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerRecord;
import no.systema.ebooking.url.store.EbookingUrlDataStore;
import no.systema.ebooking.util.EbookingConstants;
import no.systema.main.util.StringManager;

import no.systema.ebooking.service.EbookingChildWindowService;
import no.systema.ebooking.service.EbookingChildWindowServiceImpl;
import no.systema.main.service.UrlCgiProxyService;
import no.systema.main.service.UrlCgiProxyServiceImpl;
import no.systema.main.validator.EmailValidator;

/**
 * 
 * @author oscardelatorre
 * @date Aug 30, 2017
 * 
 *
 */
public class CustomerDeliveryAdressValidator implements Validator {
	private static final Logger logger = Logger.getLogger(CustomerDeliveryAdressValidator.class.getName());
	//Init services here
	private EbookingChildWindowService ebookingChildWindowService = new EbookingChildWindowServiceImpl();
	private UrlCgiProxyService urlCgiProxyService = new UrlCgiProxyServiceImpl();
	private EmailValidator emailValidator = new EmailValidator();
	private StringManager strMgr = new StringManager();
	/**
	 * 
	 */
	public boolean supports(Class clazz) {
		return CustomerDeliveryAdressValidator.class.isAssignableFrom(clazz); 
	}
	
	/**
	 * @param obj
	 * @param errors
	 * 
	 */
	public void validate(Object obj, Errors errors) { 
		//Check for Mandatory fields
		JsonEbookingCustomerDeliveryAddressRecord record = (JsonEbookingCustomerDeliveryAddressRecord)obj;
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hereff", "systema.ebooking.orders.form.update.error.null.from.hereff");
		
		//Check rules
		if(record!=null){
			//kundnr validation
			if( strMgr.isNotNull(record.getVakure()) ){
				/*OBSOLETE since vakure is a free field and not a customerNr (as for today)
				if(!this.isValidPartId(record, record.getVakure())){
					errors.rejectValue("ownKundnr", "systema.ebooking.orders.customeraddresses.vedlikehold.form.update.error.rule.customernr.invalid");
					
				}
				*/
			}
			//email validation
			if( strMgr.isNotNull( record.getVamail()) ){
				if(!this.emailValidator.validateEmail(record.getVamail())){
					errors.rejectValue("vamail", "systema.ebooking.orders.customeraddresses.vedlikehold.form.update.error.rule.both.email.invalid");
				}
			}
			
		}
	}
	
	
	/**
	 * 
	 * @param record
	 * @param partId
	 * @return
	 */
	private boolean isValidPartId(JsonEbookingCustomerDeliveryAddressRecord record, String partId){
		boolean retval = false;
		
		//If the part is the same as the customer (login customer)
		if(partId!=null && partId.equals(record.getAppCustnr())){
			retval = true;
		}else{
			//prepare the access CGI with RPG back-end
			String BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_CHILDWINDOW_CUSTOMER_URL;
			String urlRequestParamsKeys = "user=" + record.getApplicationUser();
			logger.info("URL: " + BASE_URL);
			logger.info("PARAMS: " + urlRequestParamsKeys);
			logger.info(Calendar.getInstance().getTime() +  " CGI-start timestamp");
			String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParamsKeys);
			//Debug -->
	    	//logger.debug(jsonDebugger.debugJsonPayloadWithLog4J(jsonPayload));
			logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
	    
			if(jsonPayload!=null){
				JsonEbookingCustomerContainer container = this.ebookingChildWindowService.getCustomerContainer(jsonPayload);
	    		if(container!=null){
	    			for(JsonEbookingCustomerRecord  cusRecord : container.getInqFkund()){
	    				if(cusRecord.getKundnr().equals(partId)){
	    					retval = true;
	    				}
	    			}
	    		}
			}
		}
		return retval;
	}
}
