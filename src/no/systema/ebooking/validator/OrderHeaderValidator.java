package no.systema.ebooking.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderFraktbrevRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderRecord;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerRecord;
import no.systema.ebooking.url.store.EbookingUrlDataStore;
import no.systema.ebooking.util.EbookingConstants;
import no.systema.ebooking.service.EbookingChildWindowService;
import no.systema.ebooking.service.EbookingChildWindowServiceImpl;
import no.systema.main.service.UrlCgiProxyService;
import no.systema.main.service.UrlCgiProxyServiceImpl;
import no.systema.main.util.DateTimeManager;
import no.systema.main.validator.EmailValidator;

/**
 * 
 * @author oscardelatorre
 * @date Jan 07, 2017
 * 
 *
 */
public class OrderHeaderValidator implements Validator {
	private static final Logger logger = Logger.getLogger(OrderHeaderValidator.class.getName());
	//Init services here
	private EbookingChildWindowService ebookingChildWindowService = new EbookingChildWindowServiceImpl();
	private UrlCgiProxyService urlCgiProxyService = new UrlCgiProxyServiceImpl();
	private EmailValidator emailValidator = new EmailValidator();
	/**
	 * 
	 */
	public boolean supports(Class clazz) {
		return OrderHeaderValidator.class.isAssignableFrom(clazz); 
	}
	
	/**
	 * @param obj
	 * @param errors
	 * 
	 */
	public void validate(Object obj, Errors errors) { 
		//Check for Mandatory fields
		JsonMainOrderHeaderRecord record = (JsonMainOrderHeaderRecord)obj;
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hereff", "systema.ebooking.orders.form.update.error.null.from.hereff");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "henas", "systema.ebooking.orders.form.update.error.null.shipper.name.henas");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "heads1", "systema.ebooking.orders.form.update.error.null.shipper.name.heads1");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "henak", "systema.ebooking.orders.form.update.error.null.consignee.name.henak");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "headk1", "systema.ebooking.orders.form.update.error.null.consignee.name.headk1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "helka", "systema.ebooking.orders.form.update.error.null.from.helka");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hesdf", "systema.ebooking.orders.form.update.error.null.from.hesdf");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hetri", "systema.ebooking.orders.form.update.error.null.to.hetri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hesdt", "systema.ebooking.orders.form.update.error.null.to.hesdt");
		
		
		//Check rules
		if(record!=null){
			//Date check
			if(record.getWsetdd()!=null && !"".equals(record.getWsetdd()) ){
				boolean isValidDate = new DateTimeManager().isValidCurrentAndForwardDate(record.getWsetdd(), "yyyyMMdd");
				if(!isValidDate){
					errors.rejectValue("wsetdd", "systema.ebooking.orders.form.update.error.rule.avgangTidDtm1NotValid"); 
				}
			}
			
			//Fakturapart
			if( (record.getHeknsf() !=null && !"".equals(record.getHeknsf())) && (record.getHeknkf()!=null && !"".equals(record.getHeknkf())) ){
				errors.rejectValue("heknsf", "systema.ebooking.orders.form.update.error.rule.both.invoicees.invalid");
			}else{
				if( (record.getHeknsf() != null && !"".equals(record.getHeknsf())) || (record.getHeknkf() != null && !"".equals(record.getHeknkf())) ){
					//OK (at least one)
				}else{
					errors.rejectValue("heknsf", "systema.ebooking.orders.form.update.error.rule.sendersOrReceiversInvoicee.mustExist");
				}
			}
			
			//-----------------------------------------------------------------------------------------------------------
			//START Check References & Invoicees (one of them is always mandatory. In certain cases, both are mandatory)
			//These keys replaced hereff (ref.JOVO).
			//-----------------------------------------------------------------------------------------------------------
			boolean herfaTrigger = false;
			
			if( (record.getHerfa()!=null && !"".equals(record.getHerfa())) || (record.getHerfk()!=null && !"".equals(record.getHerfk())) ){
				//OK. Go on with further validation
				//(2)
				if(this.isInvoiceeOnSeller(record)){
					if (record.getHerfa()==null || "".equals(record.getHerfa()) ){
						herfaTrigger = true;
						errors.rejectValue("herfa", "systema.ebooking.orders.form.update.error.rule.senderRef.mustExist");
					}
				}else{
					if(this.isInvoiceeOnReceiver(record)){
						if (record.getHerfk()==null || "".equals(record.getHerfk()) ){
							errors.rejectValue("herfa", "systema.ebooking.orders.form.update.error.rule.receiverRef.mustExist");
						}else{
							if (record.getHerfa()==null || "".equals(record.getHerfa()) ){
								errors.rejectValue("herfa", "systema.ebooking.orders.form.update.error.rule.both.senderAndReceiverRefs.mustExist");
							}
						}
					}
				}
			}else{
				//OBSOLETE?--> errors.rejectValue("herfa", "systema.ebooking.orders.form.update.error.rule.senderOrReceiverRef.mustExist");
			}
			
			//Specific Invoicee validation
			if("M".equals(record.getXfakBet()) ){
				if(record.getHerfk()==null || "".equals(record.getHerfk()) ){
					errors.rejectValue("herfa", "systema.ebooking.orders.form.update.error.rule.receiverRef.mustExist");
				}
				if(record.getHeknkf()==null || "".equals(record.getHeknkf()) ){
					errors.rejectValue("heknsf", "systema.ebooking.orders.form.update.error.rule.receiversInvoicee.mustExist");
				}
				
			}else if("S".equals(record.getXfakBet()) ){
				if(!herfaTrigger){
					if(record.getHerfa()==null || "".equals(record.getHerfa()) ){
						errors.rejectValue("herfa", "systema.ebooking.orders.form.update.error.rule.senderRef.mustExist");
					}
				}
				
				if(record.getHeknsf()==null || "".equals(record.getHeknsf()) ){
					errors.rejectValue("heknsf", "systema.ebooking.orders.form.update.error.rule.sendersInvoicee.mustExist");
				}
			}
			//-----------------------------------
			//END Check References and Invoicees
			//-----------------------------------
			
			//Check validity of part id - Sender
			if(record.getHekns()!=null && !"".equals(record.getHekns())){
				if(!this.isValidPartId(record, record.getHekns())){
					errors.rejectValue("hekns", "systema.ebooking.orders.form.update.error.rule.sender.isNotValid");
				}
			}
			//Check validity of part id - Receiver
			if(record.getHeknk()!=null && !"".equals(record.getHeknk())){
				if(!this.isValidPartId(record, record.getHeknk())){
					errors.rejectValue("heknk", "systema.ebooking.orders.form.update.error.rule.receiver.isNotValid");
				}
			}
			//Check validity of part id - Seller's invoicee
			if(record.getHeknsf()!=null && !"".equals(record.getHeknsf())){
				if(!this.isValidPartId(record, record.getHeknsf())){
					errors.rejectValue("heknsf", "systema.ebooking.orders.form.update.error.rule.sendersInvoicee.isNotValid");
				}
			}
			//Check validity of part id - Buyer's invoicee
			if(record.getHeknkf()!=null && !"".equals(record.getHeknkf())){
				if(!this.isValidPartId(record, record.getHeknkf())){
					errors.rejectValue("heknkf", "systema.ebooking.orders.form.update.error.rule.receiversInvoicee.isNotValid");
				}
			}
			
			//Check that there is at least one item line
			if(this.itemLineRecordExist(record)){
				//OK = valid
			}else{
				//check fraktbrev lines (if applicable)
				if(this.fraktbrevRecordExists(record.getFraktbrevRecord())){
					//OK go on
				}else{
					errors.rejectValue("hereff", "systema.ebooking.orders.form.update.error.rule.itemLines.atleastOneLine.mustExist");
				}
			}
			
			//Check Dangerous goods
			if(record.getFraktbrevRecord()!=null){
				JsonMainOrderHeaderFraktbrevRecord fr = record.getFraktbrevRecord();
				if(this.isNotNull(fr.getFfunnr()) ){
					if( !this.isNotNull(fr.getFfantk())){
						errors.rejectValue("frbPhantom", "systema.ebooking.orders.form.update.error.rule.itemLines.mandatoryFields.dangerousGoods.antal.mustExist");
					}else if( !this.isNotNull(fr.getFfante())){
						errors.rejectValue("frbPhantom", "systema.ebooking.orders.form.update.error.rule.itemLines.mandatoryFields.dangerousGoods.mengde.mustExist");
					}else if( !this.isNotNull(fr.getFfenh())){
						errors.rejectValue("frbPhantom", "systema.ebooking.orders.form.update.error.rule.itemLines.mandatoryFields.dangerousGoods.enhet.mustExist");
					}
				}
			}
			
			//Check validity of email address
			if(record.getWsmail()!=null && !"".equals(record.getWsmail())){
				if( !this.emailValidator.validateEmail(record.getWsmail()) ){
					errors.rejectValue("wsmail", "systema.ebooking.orders.form.update.error.rule.email.isNotValid");
				}
			}
			//check fraktbrev lines (if applicable)
			if(this.fraktbrevRecordExists(record.getFraktbrevRecord())){
				if(!this.validMandatoryFieldsFraktbrev(record.getFraktbrevRecord())){
					errors.rejectValue("frbPhantom", "systema.ebooking.orders.form.update.error.rule.itemLines.mandatoryFields.mustExist");
				}
			}
			
		}
		
	}	
	/**
	 * 
	 * @param record
	 * @return
	 */
	private boolean isInvoiceeOnSeller(JsonMainOrderHeaderRecord record){
		boolean retval = false;
		if( (record.getTrknfa().equals(record.getHekns())) || (record.getTrknfa().equals(record.getHeknsf())) ){
			retval = true;
		}
		if( (record.getHekns()!=null && !"".equals(record.getHekns())) && (record.getHeknsf()!=null && !"".equals(record.getHeknsf())) ){
			retval = true;
		}
		return retval;
		
	}
	/**
	 * 
	 * @param record
	 * @return
	 */
	private boolean isInvoiceeOnReceiver(JsonMainOrderHeaderRecord record){
		boolean retval = false;
		if( (record.getTrknfa().equals(record.getHeknk())) || (record.getTrknfa().equals(record.getHeknkf())) ){
			retval = true;
		}
		if( (record.getHeknk()!=null && !"".equals(record.getHeknk())) && (record.getHeknkf()!=null && !"".equals(record.getHeknkf())) ){
			retval = true;
		}
		return retval;
		
	}
	
	/**
	 * At least one item line must exist
	 * 
	 * @param record
	 */
	private boolean itemLineRecordExist(JsonMainOrderHeaderRecord record){
		boolean retval = false;
		
		if(record!=null && record.getFraktbrevList() !=null){
		    for(JsonMainOrderHeaderFraktbrevRecord itemLine : record.getFraktbrevList()){
		    	if( (itemLine.getFvant()!=null && !"".equals(itemLine.getFvant())) && 
		    		(itemLine.getFvvt()!=null && !"".equals(itemLine.getFvvt())) &&
		    		(itemLine.getFvvkt()!=null && !"".equals(itemLine.getFvvkt())) ){
		    		retval = true;
		    	}
		    	break;
		    }
		}
		return retval;
	}
	
	
	/**
	 * 
	 * @param fraktbrevRecord
	 * @return
	 */
	private boolean fraktbrevRecordExists(JsonMainOrderHeaderFraktbrevRecord fraktbrevRecord){
		boolean retval = false;
		//Check if the end user has enter at least one field in the fraktbrev record. 
		//It is the only way to elucidate if the end user wants to input a fraktbrev record.
		if( this.isNotNull(fraktbrevRecord.getFvlinr()) || this.isNotNull(fraktbrevRecord.getFvant())  || this.isNotNull(fraktbrevRecord.getFvvkt()) || 
			this.isNotNull(fraktbrevRecord.getFvvt()) || this.isNotNull(fraktbrevRecord.getFmmrk1())  || this.isNotNull(fraktbrevRecord.getFvpakn()) || 
			this.isNotNull(fraktbrevRecord.getFvlen()) || this.isNotNull(fraktbrevRecord.getFvbrd())  || this.isNotNull(fraktbrevRecord.getFvhoy()) || 
			this.isNotNull(fraktbrevRecord.getFvvol()) || this.isNotNull(fraktbrevRecord.getFvlm()) ){
			retval = true;
		}
		return retval;
	}
	/**
	 * 
	 * @param fraktbrevRecord
	 * @return
	 */
	private boolean validMandatoryFieldsFraktbrev(JsonMainOrderHeaderFraktbrevRecord fraktbrevRecord){
		boolean retval = false;
		if( this.isNotNull(fraktbrevRecord.getFvant())  && this.isNotNull(fraktbrevRecord.getFvvt())  && this.isNotNull(fraktbrevRecord.getFvvkt()) ){
			retval = true;
		}
		return retval;
	}
	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean isNotNull(String value){
		boolean retval = false;
		if(value!=null && !"".equals(value)){
			retval = true;
		}
		return retval;
	}
	/**
	 * 
	 * @param record
	 * @param partId
	 * @return
	 */
	private boolean isValidPartId(JsonMainOrderHeaderRecord record, String partId){
		boolean retval = false;
		
		//If the part is the same as the customer (login customer)
		if(partId!=null && partId.equals(record.getTrknfa())){
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
