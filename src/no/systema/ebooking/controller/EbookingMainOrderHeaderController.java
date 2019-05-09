package no.systema.ebooking.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.ServletRequestDataBinder;


//application imports
import no.systema.main.context.TdsAppContext;
import no.systema.main.service.UrlCgiProxyService;
import no.systema.main.service.general.notisblock.NotisblockService;
import no.systema.main.validator.LoginValidator;
import no.systema.main.url.store.MainUrlDataStore;
import no.systema.main.util.AppConstants;
import no.systema.main.util.JsonDebugger;
import no.systema.main.util.MessageNoteManager;
import no.systema.main.util.NumberFormatterLocaleAware;
import no.systema.main.util.StringManager;
import no.systema.main.util.io.FileContentRenderer;
import no.systema.main.model.SystemaWebUser;
import no.systema.main.model.jsonjackson.general.notisblock.JsonNotisblockContainer;


//eBooking
import no.systema.ebooking.url.store.EbookingUrlDataStore;
import no.systema.ebooking.util.EbookingConstants;
import no.systema.ebooking.util.RpgReturnResponseHandler;
import no.systema.ebooking.util.manager.CodeDropDownMgr;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderFraktbrevContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderFraktbrevRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderMessageNoteContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderMessageNoteRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderArchivedDocsContainer;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderArchivedDocsRecord;

import no.systema.ebooking.model.jsonjackson.JsonMainOrderHeaderRecord;
import no.systema.ebooking.model.jsonjackson.JsonMainOrderTypesNewRecord;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerContainer;
import no.systema.ebooking.model.jsonjackson.order.childwindow.JsonEbookingCustomerRecord;
import no.systema.ebooking.service.EbookingChildWindowService;
import no.systema.ebooking.service.EbookingMainOrderHeaderService;
import no.systema.ebooking.service.html.dropdown.EbookingDropDownListPopulationService;
import no.systema.ebooking.mapper.url.request.UrlRequestParameterMapper;
import no.systema.ebooking.validator.OrderHeaderValidator;


/**
 * ebooking Order Header Controller 
 * 
 * @author oscardelatorre
 * @date Jan 06, 2017
 * 
 */

@Controller
@SessionAttributes(AppConstants.SYSTEMA_WEB_USER_KEY)
@Scope("session")
public class EbookingMainOrderHeaderController {
	private static final JsonDebugger jsonDebugger = new JsonDebugger(1500);
	private static Logger logger = Logger.getLogger(EbookingMainOrderHeaderController.class.getName());
	private ModelAndView loginView = new ModelAndView("redirect:logout.do");
	private ApplicationContext context;
	private LoginValidator loginValidator = new LoginValidator();
	//
	private CodeDropDownMgr codeDropDownMgr = new CodeDropDownMgr();
	private UrlRequestParameterMapper urlRequestParameterMapper = new UrlRequestParameterMapper();
	private RpgReturnResponseHandler rpgReturnResponseHandler = new RpgReturnResponseHandler();
	private MessageNoteManager messageNoteMgr = new MessageNoteManager();
	private NumberFormatterLocaleAware numberFormatter = new NumberFormatterLocaleAware();
	private StringManager strMgr = new StringManager();
	
	//private ReflectionUrlStoreMgr reflectionUrlStoreMgr = new ReflectionUrlStoreMgr();
	@PostConstruct
	public void initIt() throws Exception {
		if("DEBUG".equals(AppConstants.LOG4J_LOGGER_LEVEL)){
			logger.setLevel(Level.DEBUG);
		}
	}
	
	/**
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ebooking_mainorder.do", method={RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView doMainOrderEdit(@ModelAttribute ("record") JsonMainOrderHeaderRecord recordToValidate, BindingResult bindingResult, HttpSession session, HttpServletRequest request){
		this.context = TdsAppContext.getApplicationContext();
		Map model = new HashMap();
		
		String action = request.getParameter("action");
		boolean isValidRecord = true;
		boolean isValidItemLineRecord = true;
		
		//String orderLineTotalsString = request.getParameter("oltotals");
		String orderStatus = recordToValidate.getStatus(); //Since this is not comming from the back-end
		//logger.info("ORDER TOTALS STRING:" +  orderLineTotalsString);
		//special case on Create New comming from the order list "Create new order"
		String selectedTypeWithCreateNew = request.getParameter("selectedType");
		JsonMainOrderTypesNewRecord orderTypes = this.getDefaultValuesForCreateNewOrder(model, selectedTypeWithCreateNew); 
		
		ModelAndView successView = new ModelAndView("ebooking_mainorder");
		SystemaWebUser appUser = this.loginValidator.getValidUser(session);
		
		//check user (should be in session already)
		if(appUser==null){
			return loginView;
			
		}else{
			logger.info(Calendar.getInstance().getTime() + " CONTROLLER start - timestamp");
			
			if(EbookingConstants.ACTION_UPDATE.equals(action)){
				//Validation here TODO ... 
				//don't forget model.put("selectedTypeWithCreateNew", selectedTypeWithCreateNew) --> if selectedTypeWithCreateNew!=null...
				//...
				OrderHeaderValidator validator = new OrderHeaderValidator();
				logger.info("Host via HttpServletRequest.getHeader('Host'): " + request.getHeader("Host"));
				//populate all order lines with end-user input in order to validate that at least one line exists.
				this.populateOrderLineRecordsWithUserInput(request, recordToValidate);
				//populate list of record for validation purposes
				this.populateFraktbrev(appUser, recordToValidate);
				//validate
			    validator.validate(recordToValidate, bindingResult);
			    if(bindingResult.hasErrors()){
		    		logger.info("[ERROR Validation] record does not validate)");
		    		isValidRecord = false;
		    		//check if user is allowed to choose invoicee (fakturaBetalare)
					this.setFakturaBetalareFlag(recordToValidate, appUser);
					//populate all message notes
					//removed for toten, this was a bug: -->this.populateMessageNotes( appUser, recordToValidate);
					//populate fraktbrev lines
					this.populateFraktbrev( appUser, recordToValidate);
					//set always status as in list (since we do not get this value from back-end)
					recordToValidate.setStatus(orderStatus);
					logger.info("VALIDATE error msgNoteConsigneeOriginal:" + recordToValidate.getMessageNoteConsigneeOriginal());
		    		model.put(EbookingConstants.DOMAIN_RECORD, recordToValidate);
		    		
			    }else{	
					//Start DML operations if applicable
					StringBuffer errMsg = new StringBuffer();
					int dmlRetval = 0;
					if(recordToValidate.getHeunik()!=null && !"".equals(recordToValidate.getHeunik())){
						//update
						logger.info("doUpdate");
						
						//First in order to update totals (for the new item line)
						/*
						if(this.validMandatoryFieldsFraktbrev(recordToValidate.getFraktbrevRecord()) ){
			    			if(!this.processOrderLine(model, request, recordToValidate, appUser)){
			    				isValidItemLineRecord = false;
			    			}else{
			    				JsonMainOrderHeaderRecord headerOrderRecordForTotals = this.getOrderRecord(appUser, model, orderTypes, recordToValidate.getHereff(), recordToValidate.getHeunik());
			    				this.populateFraktbrev( appUser, headerOrderRecordForTotals);
			    				//hand-over
			    				recordToValidate.setHent(headerOrderRecordForTotals.getHent());
			    				recordToValidate.setHevkt(headerOrderRecordForTotals.getHevkt());
			    				recordToValidate.setHem3(headerOrderRecordForTotals.getHem3());
			    				recordToValidate.setHelm(headerOrderRecordForTotals.getHelm());
			    			}
			    		}*/
						
						//update with integrated back-end validity (in case of user parameterized )
						dmlRetval = this.updateRecord(model, appUser.getUser(), recordToValidate, EbookingConstants.MODE_UPDATE, errMsg);
						if(dmlRetval==0){
							logger.info("[INFO] Record successfully updated, OK ");
							logger.info("[START]: process children <meessageNotes>, <itemLines>, etc update... ");
							//Update the message notes (2 steps: 1.Delete the original ones, 2.Create the new ones)
				    		this.processNewMessageNotes(model, recordToValidate, appUser, request, null );
				    		
				    		//Update the order lines if applicable
				    		if(this.validMandatoryFieldsFraktbrev(recordToValidate.getFraktbrevRecord()) ){
				    			if(!this.processOrderLine(model, request, recordToValidate, appUser)){
				    				isValidItemLineRecord = false;
				    			}else{
				    				//------------------------
				    	    		//update item line totals
				    	    		//------------------------
				    	    		JsonMainOrderHeaderRecord headerOrderRecord = this.getOrderRecord(appUser, model, null, recordToValidate.getHereff(), recordToValidate.getHeunik());
				    				this.populateFraktbrev( appUser, headerOrderRecord);
				    				//update with new totals
				    				errMsg = new StringBuffer(); //init
				    				int dmlRetvalIL = this.updateRecord(model, appUser.getUser(), headerOrderRecord, EbookingConstants.MODE_UPDATE, errMsg);
				    				if(dmlRetvalIL<0){
				    					logger.info("[ERROR]: Unsuccessful item line totals' update ... ? ");
				    				}
				    			}
				    		}
			    			
				    		//postUpdate events on back-end
			    			//this.processPostUpdateEvents(recordToValidate, appUser);
			    			logger.info("[END]: children update");
						}
					}else{
						//create new
						logger.info("doCreate");
						dmlRetval = this.updateRecord(model, appUser.getUser(), recordToValidate, EbookingConstants.MODE_ADD, errMsg);
						model.put("selectType", "");
						if(dmlRetval==0){
							orderStatus = "E"; //since we do not get the value from back-end
							logger.info("[INFO] Record successfully created, OK ");
							logger.info("[START]: process children <meessageNotes>, etc create... ");
							//Update the message notes (2 steps: 1.Delete the original ones, 2.Create the new ones)
				    		this.processNewMessageNotes(model, recordToValidate, appUser, request, "doCreate" );
				    		
				    		//Create the order line if applicable
				    		if(this.validMandatoryFieldsFraktbrev(recordToValidate.getFraktbrevRecord()) ){
				    			if(!this.processOrderLine(model, request, recordToValidate, appUser)){
				    				isValidItemLineRecord = false;
				    			}else{
				    				//------------------------
				    	    		//update item line totals
				    	    		//------------------------
				    	    		JsonMainOrderHeaderRecord headerOrderRecord = this.getOrderRecord(appUser, model, null, recordToValidate.getHereff(), recordToValidate.getHeunik());
				    				this.populateFraktbrev( appUser, headerOrderRecord);
				    				//update with new totals
				    				errMsg = new StringBuffer(); //init
				    				int dmlRetvalIL = this.updateRecord(model, appUser.getUser(), headerOrderRecord, EbookingConstants.MODE_UPDATE, errMsg);
				    				if(dmlRetvalIL<0){
				    					logger.info("[ERROR]: Unsuccessful item line totals' update ... ? ");
				    				}
				    			}
				    		}
			    			logger.info("[END]: children create new...");
						}
			    		
					}
					if(dmlRetval<0){
						isValidRecord = false;
						model.put(EbookingConstants.DOMAIN_RECORD, recordToValidate);
					}
			    }
				
			}else if(EbookingConstants.ACTION_DELETE.equals(action)){
				
			}
			
			//--------------
			//Fetch record
			//--------------
			if(isValidRecord){
				logger.info("UNIK:" + recordToValidate.getHeunik());
				JsonMainOrderHeaderRecord headerOrderRecord = this.getOrderRecord(appUser, model, orderTypes, recordToValidate.getHereff(), recordToValidate.getHeunik());
				//check if user is allowed to choose invoicee (fakturaBetalare)
				this.setFakturaBetalareFlag(headerOrderRecord, appUser);
				//populate all message notes
				this.populateMessageNotes( appUser, headerOrderRecord);
				//populate fraktbrev lines
				this.populateFraktbrev( appUser, headerOrderRecord);
				//populate archive docs
				if( strMgr.isNotNull(headerOrderRecord.getHeunik()) ) {
					this.populateArchiveDocs(appUser, headerOrderRecord);
				}
				
				//check if there was an error in item line "save" and put values back in that line
				//Note: the header is already saved but at this stage: shit happens (TODO another day when I feel happier)
				if(!isValidItemLineRecord){
					headerOrderRecord.setFraktbrevRecord(recordToValidate.getFraktbrevRecord());
				}
				//Only in case of Create new order (INSERT ORDER)
				if(orderTypes!=null){
					if( "".equals(headerOrderRecord.getXfakBet()) ){
						headerOrderRecord.setXfakBet(orderTypes.getNewSideSK());
					}
				}
				//set always status as in list (since we do not get this value from back-end)
				headerOrderRecord.setStatus(orderStatus);
				//domain objects
				model.put(EbookingConstants.DOMAIN_RECORD, headerOrderRecord);
			}
			//get dropdowns
			this.setCodeDropDownMgr(appUser, model);
			this.setDropDownsFromFiles(model);
			//populate model
			if(action==null || "".equals(action)){
				action = "doUpdate";
			}
			model.put("action", action);
			successView.addObject(EbookingConstants.DOMAIN_MODEL , model);
			
			logger.info("Host via HttpServletRequest.getHeader('Host'): " + request.getHeader("Host"));
			return successView;

		}
		
	}
	
	/**
	 * 
	 * @param recordToValidate
	 * @param bindingResult
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ebooking_delete_order_line.do",  method={RequestMethod.GET} )
	public ModelAndView doDeleteOrderLine(@ModelAttribute ("record") JsonMainOrderHeaderRecord recordToValidate, BindingResult bindingResult, HttpSession session, HttpServletRequest request){
		this.context = TdsAppContext.getApplicationContext();
		logger.info("#HEUNIK:" + recordToValidate.getHeunik());
		logger.info("#HREFF:" + recordToValidate.getHereff());
		//logger.info("#HESTL4:" + recordToValidate.getHestl4());
		//set the order line nr in a place-holder
		recordToValidate.setOrderLineToDelete(request.getParameter("lin"));
		logger.info("#LINENR:" + recordToValidate.getOrderLineToDelete());
		
		ModelAndView successView = new ModelAndView("redirect:ebooking_mainorder.do?action=doFetch&heunik=" + recordToValidate.getHeunik() + "&hereff=" + recordToValidate.getHereff() + "&status=" + recordToValidate.getStatus());
		SystemaWebUser appUser = this.loginValidator.getValidUser(session);
		Map model = new HashMap();
		
		//check user (should be in session already)
		if(appUser==null){
			return loginView;
			
		}else{
			logger.info(Calendar.getInstance().getTime() + " CONTROLLER start - timestamp");
    		//UPDATE (Delete)
			logger.info("UPDATE (DELETE) transaction...");
			
			final String UPDATE_BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_WORKFLOW_UPDATE_LINE_MAIN_ORDER_FRAKTBREV_URL;
			String urlRequestKeyParams = this.getRequestUrlKeyParameters(recordToValidate, appUser, EbookingConstants.MODE_DELETE );
			logger.info(Calendar.getInstance().getTime() + " CGI-start timestamp");
			logger.info("URL: " + UPDATE_BASE_URL);
	    	logger.info("URL PARAMS: " + urlRequestKeyParams );
	    	//-----------------------------------------------
	    	//EXECUTE the UPDATE - DELETE (RPG program) here 
	    	//-----------------------------------------------
	    	 
	    	String rpgReturnPayload = this.urlCgiProxyService.getJsonContent(UPDATE_BASE_URL, urlRequestKeyParams);
			//Debug --> 
	    	logger.info("Checking errMsg in rpgReturnPayload [UPDATE - DELETE]:" + rpgReturnPayload);
	    	//we must evaluate a return RPG code in order to know if the Update was OK or not
	    	rpgReturnResponseHandler = new RpgReturnResponseHandler(); //init
	    	rpgReturnResponseHandler.evaluateRpgResponseOnEditSpecificOrder(rpgReturnPayload);
	    	if(rpgReturnResponseHandler.getErrorMessage()!=null && !"".equals(rpgReturnResponseHandler.getErrorMessage())){
	    		rpgReturnResponseHandler.setErrorMessage("[ERROR] FATAL on DELETE: " + rpgReturnResponseHandler.getErrorMessage());
	    		this.setFatalError(model, rpgReturnResponseHandler, recordToValidate);
	    	}else{
	    		//Update successfully done!
	    		logger.info("[INFO] Record successfully deleted, OK ");
	    		//now update totals
	    		
	    		//------------------------
	    		//update item line totals
	    		//------------------------
	    		JsonMainOrderHeaderRecord headerOrderRecord = this.getOrderRecord(appUser, model, null, recordToValidate.getHereff(), recordToValidate.getHeunik());
				this.populateFraktbrev( appUser, headerOrderRecord);
				//update with new totals
				StringBuffer errMsg = new StringBuffer();
				int dmlRetvalIL = this.updateRecord(model, appUser.getUser(), headerOrderRecord, EbookingConstants.MODE_UPDATE, errMsg);
				if(dmlRetvalIL<0){
					logger.info("[ERROR]: Unsuccessful item lines totals' update ... ? ");
				}
    		}	
	    	
    		return successView;
		
		}
	}
	
	/**
	 * 
	 * @param headerOrderRecord
	 * @param appUser
	 */
	public void setFakturaBetalareFlag(JsonMainOrderHeaderRecord headerOrderRecord, SystemaWebUser appUser){
		//prepare the access CGI with RPG back-end
		String BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_CHILDWINDOW_CUSTOMER_URL;
		String urlRequestParamsKeys = "user=" + appUser.getUser();
		logger.info("URL: " + BASE_URL);
		logger.info("PARAMS: " + urlRequestParamsKeys);
		logger.info(Calendar.getInstance().getTime() +  " CGI-start timestamp");
		String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParamsKeys);
		//Debug -->
    	//logger.debug(jsonDebugger.debugJsonPayloadWithLog4J(jsonPayload));
		//logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
    
		if(jsonPayload!=null){
			JsonEbookingCustomerContainer container = this.ebookingChildWindowService.getCustomerContainer(jsonPayload);
    		if(container!=null){
    			if(container.getInqFkund()!=null && container.getInqFkund().size()>0){
    				//nothing. At least one record
    			}else{
    				//this makes the user not allowed to choose faktura part
    				headerOrderRecord.setFakBetExists(false);
    			}
    			
    		}
		}	
	}
	/**
	 * 
	 * @param recordToValidate
	 * @param appUser
	 * @param mode
	 * @return
	 */
	private String getRequestUrlKeyParameters(JsonMainOrderHeaderRecord recordToValidate, SystemaWebUser appUser, String mode){
		StringBuffer urlRequestParamsKeys = new StringBuffer();
		
		if(EbookingConstants.MODE_UPDATE.equalsIgnoreCase(mode) || EbookingConstants.MODE_ADD.equalsIgnoreCase(mode)){
			urlRequestParamsKeys.append("user=" + appUser.getUser());
			urlRequestParamsKeys.append("&unik=" + recordToValidate.getHeunik());
			urlRequestParamsKeys.append("&reff=" + recordToValidate.getHereff());
			urlRequestParamsKeys.append("&mode=" + mode);
			
			
		}else if(EbookingConstants.MODE_DELETE.equalsIgnoreCase(mode)){
			urlRequestParamsKeys.append("user=" + appUser.getUser());
			urlRequestParamsKeys.append("&unik=" + recordToValidate.getHeunik());
			urlRequestParamsKeys.append("&reff=" + recordToValidate.getHereff());
			urlRequestParamsKeys.append("&fbn=1");
			urlRequestParamsKeys.append("&lin=" + recordToValidate.getOrderLineToDelete());
			urlRequestParamsKeys.append("&mode=" + EbookingConstants.MODE_DELETE);
			
		}
		
		return urlRequestParamsKeys.toString();
	}
	
	/**
	 * @param model
	 * @param recordToValidate
	 * @param appUser
	 * @param request
	 * @param dmlModeCreateNew
	 */
	private void processNewMessageNotes(Map model, JsonMainOrderHeaderRecord recordToValidate, SystemaWebUser appUser, HttpServletRequest request, String dmlModeCreateNew){
		//-------------------------------------------------------
		//get the key values for a DML operation in messageNote
		//-------------------------------------------------------
		List<String> ownMessageNoteReceiverLineNrRawList = new ArrayList<String>();
		List<String> ownMessageNoteCarrierLineNrRawList = new ArrayList<String>();
		List<String> ownMessageNoteInternalLineNrRawList = new ArrayList<String>();
		
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
		    String name = entry.getKey();
		    String value = entry.getValue()[0];
		    if(name.contains("ownMessageNoteReceiverLineNr")){
		    	logger.info("Mottaker msg:" + value);
		    	ownMessageNoteReceiverLineNrRawList.add(value); 
		    }
		    if(name.contains("ownMessageNoteCarrierLineNr")){ 
		    	logger.info("Transportør msg:" + value);
		    	ownMessageNoteCarrierLineNrRawList.add(value); 
		    }
		    if(name.contains("ownMessageNoteInternalLineNr")){ 
		    	logger.info("Internal msg:" + value);
		    	ownMessageNoteInternalLineNrRawList.add(value); 
		    }
		}
		
		if(recordToValidate !=null){
			String messageNoteConsigneeOriginal = request.getParameter("messageNoteConsigneeOriginal");
			if(!messageNoteConsigneeOriginal.equals(recordToValidate.getMessageNoteConsignee())){
				logger.info("CONSIGNEE NOT EQUAL" + messageNoteConsigneeOriginal + "XX" + recordToValidate.getMessageNoteConsignee());
				logger.info("AA:" + ownMessageNoteReceiverLineNrRawList.size());
				//CONSIGNEE (RECEIVER)
				//Delete all values
				this.deleteOriginalMessageNote(JsonMainOrderHeaderRecord.MESSAGE_NOTE_CONSIGNEE, recordToValidate, appUser, ownMessageNoteReceiverLineNrRawList);
				//Add new values
				String [] messageNoteConsignee = this.messageNoteMgr.getChunksOfMessageNote(recordToValidate.getMessageNoteConsignee());
				this.updateMessageNote(model, messageNoteConsignee, JsonMainOrderHeaderRecord.MESSAGE_NOTE_CONSIGNEE, recordToValidate, appUser);
				//init values
				//recordToValidate.setMessageNoteConsigneeOriginal(recordToValidate.getMessageNoteConsignee());
				
			}else{
				logger.info("CONSIGNEE EQUAL"); 
				if(dmlModeCreateNew!=null){
					//Add new values
					String [] messageNoteConsignee = this.messageNoteMgr.getChunksOfMessageNote(recordToValidate.getMessageNoteConsignee());
					this.updateMessageNote(model, messageNoteConsignee, JsonMainOrderHeaderRecord.MESSAGE_NOTE_CONSIGNEE, recordToValidate, appUser);
				}else{
					//do not update
				}	
			}
			
			String messageNoteCarrierOriginal = request.getParameter("messageNoteCarrierOriginal");
			if(!messageNoteCarrierOriginal.equals(recordToValidate.getMessageNoteCarrier())){
				logger.info("CARRIER NOT EQUAL");
				//CARRIER
				//Delete all values
				this.deleteOriginalMessageNote(JsonMainOrderHeaderRecord.MESSAGE_NOTE_CARRIER, recordToValidate, appUser, ownMessageNoteCarrierLineNrRawList);
				//Add new values
				String [] messageNoteCarrier = this.messageNoteMgr.getChunksOfMessageNote(recordToValidate.getMessageNoteCarrier());
				this.updateMessageNote(model, messageNoteCarrier, JsonMainOrderHeaderRecord.MESSAGE_NOTE_CARRIER, recordToValidate, appUser);
			}else{
				logger.info("CARRIER EQUAL"); 
				if(dmlModeCreateNew!=null){
					//Add new values
					String [] messageNoteCarrier = this.messageNoteMgr.getChunksOfMessageNote(recordToValidate.getMessageNoteCarrier());
					this.updateMessageNote(model, messageNoteCarrier, JsonMainOrderHeaderRecord.MESSAGE_NOTE_CARRIER, recordToValidate, appUser);
				}else{
					//do not update
				}
			}
			
			String messageNoteInternalOriginal = request.getParameter("messageNoteInternalOriginal");
			if(!messageNoteInternalOriginal.equals(recordToValidate.getMessageNoteInternal())){
				logger.info("INTERNAL NOT EQUAL");
				//INTERNAL
				//Delete all values
				this.deleteOriginalMessageNote(JsonMainOrderHeaderRecord.MESSAGE_NOTE_INTERNAL, recordToValidate, appUser, ownMessageNoteInternalLineNrRawList);
				//Add new values
				String [] messageNoteInternal = this.messageNoteMgr.getChunksOfMessageNote(recordToValidate.getMessageNoteInternal());
				this.updateMessageNote(model, messageNoteInternal, JsonMainOrderHeaderRecord.MESSAGE_NOTE_INTERNAL, recordToValidate, appUser);
			}else{
				logger.info("INTERNAL EQUAL"); 
				if(dmlModeCreateNew!=null){
					//Add new values
					String [] messageNoteInternal = this.messageNoteMgr.getChunksOfMessageNote(recordToValidate.getMessageNoteInternal());
					this.updateMessageNote(model, messageNoteInternal, JsonMainOrderHeaderRecord.MESSAGE_NOTE_INTERNAL, recordToValidate, appUser);
				}else{
					//do not update
				}
			}

		}
	}
	
	
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private boolean isNull(String value){
		boolean retval = true;
		if(value!=null && !"".equals(value)){
			retval = false;
		}
		return retval;
	}
	
	/**
	 * @param model
	 * @param messageNote
	 * @param messageParty
	 * @param record
	 * @param appUser
	 */
	private void updateMessageNote(Map model, String[] messageNote, String messageParty, JsonMainOrderHeaderRecord record, SystemaWebUser appUser){
		String CARRIAGE_RETURN = "[\n\r]";
		List<String> messageNotePayload = Arrays.asList(messageNote);
		//logger.info("A" + messageNotePayload);
		for(String linePayload: messageNotePayload){
			linePayload = linePayload.replaceAll(CARRIAGE_RETURN, "");
			//linePayload = linePayload.trim();
			//---------------------------
			//get BASE URL = RPG-PROGRAM
	        //---------------------------
			if(linePayload!=null && !"".equals(linePayload)){
				String BASE_URL_UPDATE = MainUrlDataStore.SYSTEMA_NOTIS_BLOCK_UPDATE_ITEMLINE_URL;
				//------------------
				//add URL-parameter
				//------------------
				//no line no parameter is required
				StringBuffer urlRequestParamsKeysBuffer = new StringBuffer();
				urlRequestParamsKeysBuffer.append("user=" + appUser.getUser());
				urlRequestParamsKeysBuffer.append("&frunik=" + record.getHeunik());
				urlRequestParamsKeysBuffer.append("&frreff=" + record.getHereff());
				urlRequestParamsKeysBuffer.append("&frttxt=" + linePayload);
				urlRequestParamsKeysBuffer.append("&frtkod=" + messageParty);		 
				urlRequestParamsKeysBuffer.append("&mode=A");
				
				String urlRequestParams = urlRequestParamsKeysBuffer.toString();
				logger.info("URL: " + BASE_URL_UPDATE);
				logger.info("PARAMS: " + urlRequestParams);
				//logger.info(Calendar.getInstance().getTime() +  " CGI-start timestamp");
				String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL_UPDATE, urlRequestParams);
				//logger.debug(jsonDebugger.debugJsonPayloadWithLog4J(jsonPayload));
				//logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
				 
				JsonNotisblockContainer jsonNotisblockContainer = this.notisblockService.getNotisblockListContainer(jsonPayload);
				//logger.info("JsonNotisblockContainer:" + jsonNotisblockContainer);
				if(jsonNotisblockContainer!=null){
					//logger.info("A:" + jsonNotisblockContainer.getErrMsg());
					if( !"".equals(jsonNotisblockContainer.getErrMsg()) ){
						//Debug
						String fatalError = "[ERROR]:" + jsonNotisblockContainer.getErrMsg(); 
						model.put(EbookingConstants.ASPECT_ERROR_MESSAGE, fatalError);
						logger.info(fatalError);
					}
				}

			}
		}
	}
	/**
	 * 
	 * @param messageParty
	 * @param record
	 * @param appUser
	 */
	private void deleteOriginalMessageNote( String messageParty, JsonMainOrderHeaderRecord record, SystemaWebUser appUser, List<String> ownMessageNoteLineNrRawList){
		logger.info("LIST:" + ownMessageNoteLineNrRawList);
		for(String msgNoteRawRecord : ownMessageNoteLineNrRawList){
			String [] msgNoteRecord = msgNoteRawRecord.split("@");
			if(msgNoteRecord!=null && msgNoteRecord.length==2){
				//---------------------------
				//get BASE URL = RPG-PROGRAM
		        //---------------------------
				String BASE_URL_UPDATE = MainUrlDataStore.SYSTEMA_NOTIS_BLOCK_UPDATE_ITEMLINE_URL;
				//------------------
				//add URL-parameter
				//------------------
				StringBuffer urlRequestParamsKeysBuffer = new StringBuffer();
				urlRequestParamsKeysBuffer.append("user=" + appUser.getUser());
				urlRequestParamsKeysBuffer.append("&frunik=" + record.getHeunik());
				urlRequestParamsKeysBuffer.append("&frreff=" + record.getHereff());
				urlRequestParamsKeysBuffer.append("&frtli=" + msgNoteRecord[0]);
				urlRequestParamsKeysBuffer.append("&frtdt=" + msgNoteRecord[1]);
				urlRequestParamsKeysBuffer.append("&mode=D");
				
				String urlRequestParams = urlRequestParamsKeysBuffer.toString();
				//DEBUG
				logger.info("URL: " + BASE_URL_UPDATE);
				logger.info("PARAMS: " + urlRequestParams);
				//logger.info(Calendar.getInstance().getTime() +  " CGI-start timestamp");
				String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL_UPDATE, urlRequestParams);
				//DEBUG
				//logger.debug(jsonDebugger.debugJsonPayloadWithLog4J(jsonPayload));
				//logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
				JsonNotisblockContainer jsonNotisblockContainer = this.notisblockService.getNotisblockListContainer(jsonPayload);
				//logger.info("JsonNotisblockContainer:" + jsonNotisblockContainer);
				if(jsonNotisblockContainer!=null){
					//logger.info("A:" + jsonNotisblockContainer.getErrMsg());
					if( !"".equals(jsonNotisblockContainer.getErrMsg()) ){
						//Debug
						logger.info("[WARNING (delete lines)]:" + jsonNotisblockContainer.getErrMsg() + msgNoteRecord[0] + "/" + msgNoteRecord[1] + "(heunik:" + record.getHeunik()+"hereff:"+ record.getHereff() + ")");
					}
				}
				
			}
		}
	}
	/**
	 * 
	 * @param appUser
	 * @param orderRecord
	 */
	private void populateMessageNotes(SystemaWebUser appUser, JsonMainOrderHeaderRecord orderRecord){
		logger.info("Inside populateMessageNotes");
		Collection<JsonMainOrderHeaderMessageNoteRecord> messageNoteConsignee = null;
		Collection<JsonMainOrderHeaderMessageNoteRecord> messageNoteCarrier = null;
		Collection<JsonMainOrderHeaderMessageNoteRecord> messageNoteInternal = null;
		
		//logger.info("AA:" + orderRecord.getMessageNoteConsignee());
		messageNoteConsignee = this.fetchMessageNote(appUser.getUser(), orderRecord, JsonMainOrderHeaderRecord.MESSAGE_NOTE_CONSIGNEE);
		messageNoteCarrier = this.fetchMessageNote(appUser.getUser(), orderRecord, JsonMainOrderHeaderRecord.MESSAGE_NOTE_CARRIER);
		messageNoteInternal = this.fetchMessageNote(appUser.getUser(), orderRecord, JsonMainOrderHeaderRecord.MESSAGE_NOTE_INTERNAL);
		//logger.info("BB:" + messageNoteConsignee);
		
		StringBuffer brConsignee = new StringBuffer();
		for(JsonMainOrderHeaderMessageNoteRecord record: messageNoteConsignee ){
			if(record.getFrtli()!=null || !"".equals(record.getFrtli())){
				brConsignee.append(record.getFrttxt() + "\n");
			}
			
		}
		StringBuffer brCarrier = new StringBuffer();
		for(JsonMainOrderHeaderMessageNoteRecord record: messageNoteCarrier ){
			if(record.getFrtli()!=null || !"".equals(record.getFrtli())){
				brCarrier.append(record.getFrttxt() + "\n");
			}
		}
		StringBuffer brInternal = new StringBuffer();
		for(JsonMainOrderHeaderMessageNoteRecord record: messageNoteInternal ){
			if(record.getFrtkod()==null || "".equals(record.getFrtkod())){ //since we must filter in this specific type (blank)
				if(record.getFrtli()!=null || !"".equals(record.getFrtli())){
					brInternal.append(record.getFrttxt() + "\n");
				}
			}
		}
		//logger.info("******************************B" + brInternal.toString());
		//populate final message notes now
		orderRecord.setMessageNoteConsignee(brConsignee.toString());
		orderRecord.setMessageNoteCarrier(brCarrier.toString());
		orderRecord.setMessageNoteInternal(brInternal.toString());
		//populate original
		orderRecord.setMessageNoteConsigneeOriginal(brConsignee.toString());
		orderRecord.setMessageNoteCarrierOriginal(brCarrier.toString());
		orderRecord.setMessageNoteInternalOriginal(brInternal.toString());
		//populate auxiliary arrays
		orderRecord.setMessageNoteConsigneeRaw((List)messageNoteConsignee);
		orderRecord.setMessageNoteCarrierRaw((List)messageNoteCarrier);
		orderRecord.setMessageNoteInternalRaw((List)messageNoteInternal);
		
	}
	
	
	/**
	 * 
	 * @param appUser
	 * @param orderRecord
	 */
	private void populateArchiveDocs(SystemaWebUser appUser, JsonMainOrderHeaderRecord orderRecord){
		//===========
		 //FETCH LIST
		 //===========
		 logger.info("Inside: populateArchiveDocs");
		 //prepare the access CGI with RPG back-end
		 String BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_WORKFLOW_FETCH_MAIN_ORDER_UPLOADED_DOCS_URL;
		 
		 String urlRequestParamsKeys = "user=" + appUser.getUser() + "&wsunik=" + orderRecord.getHeunik();
		 logger.info("URL: " + BASE_URL);
		 logger.info("PARAMS: " + urlRequestParamsKeys);
		 logger.info(Calendar.getInstance().getTime() +  " CGI-start timestamp");
		 String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParamsKeys);
		 logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
		 logger.info(jsonPayload);
		 Collection<JsonMainOrderHeaderArchivedDocsRecord> archivedDocList = new ArrayList<JsonMainOrderHeaderArchivedDocsRecord>();
		    
		 if(jsonPayload!=null){
		 	try{
		 		JsonMainOrderHeaderArchivedDocsContainer container = this.ebookingMainOrderHeaderService.getArchiveDocsContainer(jsonPayload);
				if(container!=null){
					archivedDocList = container.getGetdoctrip();
					for(JsonMainOrderHeaderArchivedDocsRecord record : container.getGetdoctrip()){
						//DEBUG -->logger.info("####Link:" + record.getDoclnk());
					}
				}
				
		 	}catch(Exception e){
		 		e.printStackTrace();
		 	}
		 }
		//populate the list on parent record
		 orderRecord.setArchivedDocsRecord(archivedDocList);
		 
	}
	
	/**
	 * 
	 * @param applicationUser
	 * @param orderRecord
	 * @param type
	 * @return
	 */
	public Collection<JsonMainOrderHeaderMessageNoteRecord> fetchMessageNote(String applicationUser, JsonMainOrderHeaderRecord orderRecord, String type){
		Collection<JsonMainOrderHeaderMessageNoteRecord> outputList = new ArrayList<JsonMainOrderHeaderMessageNoteRecord>();
		//===========
		//FETCH LIST
		//===========
		//get BASE URL
			logger.info("FETCH messageNote ...");
    		final String BASE_LIST_URL = EbookingUrlDataStore.EBOOKING_BASE_WORKFLOW_FETCH_MAIN_ORDER_MESSAGE_NOTE_URL;
    		//add URL-parameters
    		StringBuffer urlRequestParams = new StringBuffer();
    		urlRequestParams.append("user=" + applicationUser);
    		if(orderRecord.getHeunik()!=null && !"".equals(orderRecord.getHeunik())){ urlRequestParams.append("&unik=" + orderRecord.getHeunik()); }
    		if(orderRecord.getHereff()!=null && !"".equals(orderRecord.getHereff())){ urlRequestParams.append("&reff=" + orderRecord.getHereff()); }
    		urlRequestParams.append("&part=" + type);
    		
    		
    		//logger.info(Calendar.getInstance().getTime() + " CGI-start timestamp");
	    	logger.info("URL: " + BASE_LIST_URL);
	    	logger.info("URL PARAMS: " + urlRequestParams);
	    	String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_LIST_URL, urlRequestParams.toString());
	    	//Debug --> 
	    	logger.debug(jsonDebugger.debugJsonPayloadWithLog4J(jsonPayload));
	    	logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
	    	if(jsonPayload!=null){
	    		JsonMainOrderHeaderMessageNoteContainer messageNoteContainer = this.ebookingMainOrderHeaderService.getMessageNoteContainer(jsonPayload);
	    		Collection<JsonMainOrderHeaderMessageNoteRecord> tmpList = messageNoteContainer.getFreetextlist();
	    		if(type!=null && !"".equals(type)){
	    			outputList = tmpList;
	    		}else{
	    			//all records with no part type (blank) must be filtered 
		    		for(JsonMainOrderHeaderMessageNoteRecord record: tmpList){
		    			if(record.getFrtkod()==null || "".equals(record.getFrtkod())){ //since we must filter in this specific type (blank)
		    				if(record.getFrtli()!=null || !"".equals(record.getFrtli())){
		    					outputList.add(record);		    					
		    				}
		    			}	
		    		}
	    		}
			logger.info(Calendar.getInstance().getTime() + " CONTROLLER end - timestamp");
		}
	    
	    	return outputList;
		
	}
	
	/**
	 * 
	 * @param request
	 * @param recordToValidate
	 */
	private void populateOrderLineRecordsWithUserInput(HttpServletRequest request, JsonMainOrderHeaderRecord recordToValidate){
		JsonMainOrderHeaderFraktbrevRecord fraktbrevRecord = new JsonMainOrderHeaderFraktbrevRecord();
		
			String lineNr = request.getParameter("fvlinr");
			if(lineNr!=null && !"".equals(lineNr)){
				fraktbrevRecord.setFvlinr(lineNr);
			}
			fraktbrevRecord.setFmmrk1(request.getParameter("fmmrk1"));
			fraktbrevRecord.setFvant(request.getParameter("fvant"));
			fraktbrevRecord.setFvpakn(request.getParameter("fvpakn"));
			fraktbrevRecord.setFvvt(request.getParameter("fvvt"));
			fraktbrevRecord.setFvvkt(request.getParameter("fvvkt"));
			fraktbrevRecord.setFvvol(request.getParameter("fvvol"));
			fraktbrevRecord.setFvlm(request.getParameter("fvlm"));
			fraktbrevRecord.setFvlm2(request.getParameter("fvlm2"));
			fraktbrevRecord.setFvlen(request.getParameter("fvlen"));
			fraktbrevRecord.setFvbrd(request.getParameter("fvbrd"));
			fraktbrevRecord.setFvhoy(request.getParameter("fvhoy"));
			//farlig goods
			fraktbrevRecord.setFfunnr(request.getParameter("ffunnr"));
			fraktbrevRecord.setFfembg(request.getParameter("ffembg"));
			fraktbrevRecord.setFfindx(request.getParameter("ffindx"));
			
			fraktbrevRecord.setFfantk(request.getParameter("ffantk"));
			fraktbrevRecord.setFfante(request.getParameter("ffante"));
			fraktbrevRecord.setFfenh(request.getParameter("ffenh"));
			//set record
			recordToValidate.setFraktbrevRecord(fraktbrevRecord);
		
	}
	/**
	 * 
	 * @param recordToValidate
	 * @return
	 */
	private int getTotalNumberOfLines(JsonMainOrderHeaderRecord recordToValidate){
		//check the total number of lines
		int totalNumberOfLines = EbookingConstants.CONSTANT_TOTAL_NUMBER_OF_ORDER_LINES; //Default
		if(!"".equals(recordToValidate.getTotalNumberOfLines()) && recordToValidate.getTotalNumberOfLines()!=null){
			try{
				int tmpLimit = Integer.parseInt(recordToValidate.getTotalNumberOfLines());
				if(tmpLimit>totalNumberOfLines){
					totalNumberOfLines = Integer.parseInt(recordToValidate.getTotalNumberOfLines());
				}
			}catch(Exception e){
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.info(errors);
			}
		}
		return totalNumberOfLines;
	}
	
	/**
	 * 
	 * @param request
	 * @param recordToValidate
	 * @param appUser
	 */
	private boolean processOrderLine(Map model, HttpServletRequest request, JsonMainOrderHeaderRecord recordToValidate, SystemaWebUser appUser){
		boolean retval = true;
		
		logger.info("Inside:processOrderLines");
		//check the total number of lines in order to input a new linenr
		String upperCurrentItemlineNr = request.getParameter("upperCurrentItemlineNr");
		
		if(recordToValidate!=null && recordToValidate.getFraktbrevRecord()!=null){
			String lineNr = recordToValidate.getFraktbrevRecord().getFvlinr();
			/* Debug
		 	logger.info("RETURN RECORD fvli:" + fraktbrevRecord.getFvlinr());
			logger.info("RETURN RECORD desc:" + fraktbrevRecord.getFvvt());
			logger.info("RETURN RECORD ant:" + fraktbrevRecord.getFvant());
			logger.info("RETURN RECORD brd:" + fraktbrevRecord.getFvbrd());
			logger.info("RETURN RECORD lm:" + fraktbrevRecord.getFvlm());
			*/
			
			String mode = EbookingConstants.MODE_ADD;
			if(lineNr!=null && !"".equals(lineNr) ){ 
				logger.info("lineNr (update):" + lineNr);
				mode = EbookingConstants.MODE_UPDATE; }
			else{
				//this line is new!
				if(upperCurrentItemlineNr!=null && !"".equals(upperCurrentItemlineNr)){
					int lastLineNr = Integer.parseInt(upperCurrentItemlineNr);
					lineNr = String.valueOf(++lastLineNr);
					logger.info("lineNr (new):" + lineNr);
				}else{
					logger.info("lineNr start from scratch:" + lineNr);
					lineNr = "1";
				}
			}
			//only when at least the mandatory fields are in place
			if(this.validMandatoryFieldsFraktbrev(recordToValidate.getFraktbrevRecord()) ){
				//Start with the update (mode=(A)dd,(D)elete,(U)pdate)
				String BASE_URL_UPDATE = EbookingUrlDataStore.EBOOKING_BASE_WORKFLOW_UPDATE_LINE_MAIN_ORDER_FRAKTBREV_URL;
				//------------------
				//add URL-parameter
				//------------------
				StringBuffer urlRequestParamsKeysBuffer = new StringBuffer();
				urlRequestParamsKeysBuffer.append("user=" + appUser.getUser());
				urlRequestParamsKeysBuffer.append("&unik=" + recordToValidate.getHeunik());
				urlRequestParamsKeysBuffer.append("&reff=" + recordToValidate.getHereff());
				urlRequestParamsKeysBuffer.append("&fbn=1");
				urlRequestParamsKeysBuffer.append("&lin=" + lineNr);
				urlRequestParamsKeysBuffer.append(this.getFvUrlRequestParamsForUpdate(recordToValidate.getFraktbrevRecord()));
				urlRequestParamsKeysBuffer.append("&mode=" + mode);
				
				String urlRequestParams = urlRequestParamsKeysBuffer.toString();
				logger.info("URL: " + BASE_URL_UPDATE);
				logger.info("PARAMS: " + urlRequestParams);
				//logger.info(Calendar.getInstance().getTime() +  " CGI-start timestamp");
				String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL_UPDATE, urlRequestParams);
				logger.info(jsonPayload);
				//logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
				if(jsonPayload!=null){ 
					JsonMainOrderHeaderFraktbrevContainer fraktbrevContainer = this.ebookingMainOrderHeaderService.getFraktbrevContainer(jsonPayload);
					//logger.info("JsonNotisblockContainer:" + jsonNotisblockContainer);
					if(fraktbrevContainer!=null){
						//logger.info("A:" + jsonNotisblockContainer.getErrMsg());
						if( !"".equals(fraktbrevContainer.getErrMsg()) ){
							//Debug
							String fatalError = "[ERROR]:" + fraktbrevContainer.getErrMsg(); 
							model.put(EbookingConstants.ASPECT_ERROR_MESSAGE, fatalError);
							logger.info(fatalError);
							retval = false;
						}
					}
				}
			}
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
	 * @param fraktbrevRecord
	 * @return
	 */
	private String getFvUrlRequestParamsForUpdate(JsonMainOrderHeaderFraktbrevRecord fraktbrevRecord){
		StringBuffer urlRequestParams = new StringBuffer();
		//Build the param-string
		if(fraktbrevRecord.getFvlinr()!=null && !"".equals(fraktbrevRecord.getFvlinr())){
			urlRequestParams.append("&fvlinr=" + fraktbrevRecord.getFvlinr());
		}
		urlRequestParams.append("&fmmrk1=" + fraktbrevRecord.getFmmrk1());
		urlRequestParams.append("&fvant=" + fraktbrevRecord.getFvant());
		urlRequestParams.append("&fvpakn=" + fraktbrevRecord.getFvpakn());
		urlRequestParams.append("&fvvt=" + fraktbrevRecord.getFvvt());
		urlRequestParams.append("&fvvkt=" + fraktbrevRecord.getFvvkt());
		urlRequestParams.append("&fvvol=" + fraktbrevRecord.getFvvol());
		urlRequestParams.append("&fvlm=" + fraktbrevRecord.getFvlm());
		urlRequestParams.append("&fvlm2=" + fraktbrevRecord.getFvlm2());
		urlRequestParams.append("&fvlen=" + fraktbrevRecord.getFvlen());
		urlRequestParams.append("&fvbrd=" + fraktbrevRecord.getFvbrd());
		urlRequestParams.append("&fvhoy=" + fraktbrevRecord.getFvhoy());
		//farlig goods
		urlRequestParams.append("&ffunnr=" + fraktbrevRecord.getFfunnr());
		urlRequestParams.append("&ffembg=" + fraktbrevRecord.getFfembg());
		urlRequestParams.append("&ffindx=" + fraktbrevRecord.getFfindx());
		
		urlRequestParams.append("&ffantk=" + fraktbrevRecord.getFfantk());
		urlRequestParams.append("&ffante=" + fraktbrevRecord.getFfante());
		urlRequestParams.append("&ffenh=" + fraktbrevRecord.getFfenh());
		
		return urlRequestParams.toString();
	}
	/**
	 * 
	 * @param appUser
	 * @param orderRecord
	 */
	private void populateFraktbrev(SystemaWebUser appUser, JsonMainOrderHeaderRecord orderRecord){
		
		//---------------------------
		//get BASE URL = RPG-PROGRAM
        //---------------------------
		String BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_WORKFLOW_FETCH_LIST_MAIN_ORDER_FRAKTBREV_URL;
		
		StringBuffer urlRequestParamsKeys = new StringBuffer();
		urlRequestParamsKeys.append("user=" + appUser.getUser());
		urlRequestParamsKeys.append("&unik=" + orderRecord.getHeunik());
		urlRequestParamsKeys.append("&reff=" + orderRecord.getHereff());
		urlRequestParamsKeys.append("&fbn=1");
		
		logger.info(Calendar.getInstance().getTime() + " CGI-start timestamp");
    	List<JsonMainOrderHeaderFraktbrevRecord> fraktbrevList = new ArrayList<JsonMainOrderHeaderFraktbrevRecord>();
    	
    		
    	//Only with EXISTENT ORDER
    	if( (orderRecord.getHereff()!=null && !"".equals(orderRecord.getHereff())) &&
			(orderRecord.getHeunik()!=null && !"".equals(orderRecord.getHeunik())) 	){
			//----------------------------------------------------------------------------
	    	//EXECUTE the UPDATE (RPG program) here (STEP [2] when creating a new record)
	    	//----------------------------------------------------------------------------
    		logger.info("URL: " + BASE_URL);
        	logger.info("URL PARAMS: " + urlRequestParamsKeys.toString());
        	String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParamsKeys.toString());
	    	//Debug -->
		    logger.info(jsonPayload);
		    if(jsonPayload!=null){
		    	JsonMainOrderHeaderFraktbrevContainer container = this.ebookingMainOrderHeaderService.getFraktbrevContainer(jsonPayload);
				if(container!=null){
		    		for (JsonMainOrderHeaderFraktbrevRecord fraktbrevRecord: container.getAwblinelist()){
						fraktbrevList.add(fraktbrevRecord);
					}
		    		//set totals
		    		this.setFraktbrevsTotals(container, orderRecord);
					
				}
	    	}
    	}else{
    		//OBSOLETE this.populateEmptyFraktbrevList(fraktbrevList);
    	}
    	logger.info(Calendar.getInstance().getTime() + " CGI-stop timestamp");
    	
    	//populate the list on parent record
		orderRecord.setFraktbrevList(fraktbrevList);
	}
	/**
	 * 
	 * @param container
	 * @param orderRecord
	 */
	private void setFraktbrevsTotals(JsonMainOrderHeaderFraktbrevContainer container, JsonMainOrderHeaderRecord orderRecord ){
		Integer hent = 0;
		Integer hevkt = 0;
		Double hem3 = 0.00D;
		Double helm = 0.00D;
		
		if(container!=null){
    		for (JsonMainOrderHeaderFraktbrevRecord fraktbrevRecord: container.getAwblinelist()){
				hent += Integer.valueOf(this.getNumericString(fraktbrevRecord.getFvant()));
				//logger.info(hent);
				hevkt += Integer.valueOf(this.getNumericString(fraktbrevRecord.getFvvkt()));
				hem3 += Double.valueOf(this.getNumericString(fraktbrevRecord.getFvvol()));
				helm += Double.valueOf(this.getNumericString(fraktbrevRecord.getFvlm()));
				
			}
    		orderRecord.setHent(String.valueOf(hent));
    		orderRecord.setHevkt( String.valueOf( hevkt));
    		orderRecord.setHem3( this.numberFormatter.getDoubleToPlainString(hem3, 3)); //3 decimals in DB
    		orderRecord.setHelm( this.numberFormatter.getDoubleToPlainString(helm, 2)); //2 decimals in DB
		}	
	}
	/**
	 * help function
	 * @param value
	 * @return
	 */
	private String getNumericString(String value){
		String retval = "0";
		if(value!=null && !"".equals(value)){
			retval = value.replace("," , ".");
		}
		return retval;
	}
	/**
	 * 
	 * @param fraktbrevList
	 */
	/** N/A
	private void populateEmptyFraktbrevList (List<JsonMainOrderHeaderFraktbrevRecord> fraktbrevList){
		if(fraktbrevList==null || fraktbrevList.size()<EbookingConstants.CONSTANT_TOTAL_NUMBER_OF_ORDER_LINES){
			int start = fraktbrevList.size();
			for(int i = ++start;i<=EbookingConstants.CONSTANT_TOTAL_NUMBER_OF_ORDER_LINES;i++){
				fraktbrevList.add(new JsonMainOrderHeaderFraktbrevRecord());
				//logger.info("#########################:" + i);
			}
		}
	}
	**/
	/**
	 * 
	 * @param model
	 * @param applicationUser
	 * @param recordToValidate
	 * @param mode
	 * @param errMsg
	 * @return
	 */
	private int updateRecord(Map model, String applicationUser, JsonMainOrderHeaderRecord recordToValidate, String mode, StringBuffer errMsg){
		int retval = 0;
		
		final String BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_UPDATE_SPECIFIC_ORDER_URL;
		String urlRequestParamsKeys = "user=" + applicationUser + "&mode=" + mode;
		String urlRequestParams = this.urlRequestParameterMapper.getUrlParameterValidString((recordToValidate));
		//put the final valid param. string
		urlRequestParams = urlRequestParamsKeys + urlRequestParams;
		
		logger.info(Calendar.getInstance().getTime() + " CGI-start timestamp");
    	logger.info("URL: " + jsonDebugger.getBASE_URL_NoHostName(BASE_URL));
    	logger.info("URL PARAMS:" + urlRequestParams);
    	
    	
    	String rpgReturnPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParams);
    	logger.info(jsonDebugger.debugJsonPayloadWithLog4J(rpgReturnPayload));
    	rpgReturnResponseHandler = new RpgReturnResponseHandler(); //init
    	rpgReturnResponseHandler.evaluateRpgResponseOnUpdate(rpgReturnPayload);
    	if(rpgReturnResponseHandler.getErrorMessage()!=null && !"".equals(rpgReturnResponseHandler.getErrorMessage())){
    		rpgReturnResponseHandler.setErrorMessage("[ERROR] FATAL on UPDATE: " + rpgReturnResponseHandler.getErrorMessage());
    		this.setFatalError(model, rpgReturnResponseHandler, recordToValidate);
    		//isValidCreatedRecordTransactionOnRPG = false;
    		retval = -1; 
    		
    	}else{
    		//Update successfully done!
    		logger.info("[INFO] Record successfully updated, OK ");
    		//newly created id. Set it in the recordToValidate in order to fetch (refresh) later on
    		if(EbookingConstants.MODE_ADD.equals(mode)){
    			recordToValidate.setHeunik(rpgReturnResponseHandler.getHeunik());
    			recordToValidate.setHereff(rpgReturnResponseHandler.getHereff());
    			
    		}
    		
    		/*TODO messages ...
    		this.updateMessageNote(messageNote, recordToValidate.getTuavd(), recordToValidate.getTupro(), appUser);
    		
    		//set message note (after update aka refresh)
    		Collection<JsonTransportDispWorkflowSpecificTripMessageNoteRecord> messageNoteAfterUpdate = null;
    		messageNoteAfterUpdate = this.controllerAjaxCommonFunctionsMgr.fetchMessageNote(appUser.getUser(), recordToValidate.getTuavd(), recordToValidate.getTupro());
    		StringBuffer br = new StringBuffer();
    		for(JsonTransportDispWorkflowSpecificTripMessageNoteRecord record:messageNoteAfterUpdate ){
    			br.append(record.getFrttxt() + "\n");
    		}
    		recordToValidate.setMessageNote(br.toString());
    		//logger.info(recordToValidate.getMessageNote());
    		*/ 
			//put domain objects
	    	//this.setDomainObjectsInView(session, model, recordToValidate );
	    	
    	}
    	
    	  	
    	return retval;
	}
	
	/**
	 * 
	 * @param appUser
	 * @param model
	 * @param orderTypes
	 * @param hereff
	 * @param heunik
	 * @return
	 */
	private JsonMainOrderHeaderRecord getOrderRecord(SystemaWebUser appUser, Map model, JsonMainOrderTypesNewRecord orderTypes, String hereff, String heunik ){
		JsonMainOrderHeaderRecord record = new JsonMainOrderHeaderRecord();
			
		final String BASE_URL = EbookingUrlDataStore.EBOOKING_BASE_FETCH_SPECIFIC_ORDER_URL;
		//add URL-parameters
		StringBuffer urlRequestParams = new StringBuffer();
		urlRequestParams.append("user=" + appUser.getUser() + "&mode=G");
		if( (hereff!=null && !"".equals(hereff)) && (heunik!=null && !"".equals(heunik)) ){
			//Meaning fetching to an update
			urlRequestParams.append("&heunik=" + heunik + "&hereff=" + hereff );
		}else{
			//Meaning preparing a create new ...
			urlRequestParams.append("&heunik=&hereff=");
			if(orderTypes!=null){
				urlRequestParams.append("&newavd=" + orderTypes.getNewAvd() + "&newmodul=" + orderTypes.getNewModul()+ "&newmodul2=" + orderTypes.getNewModul2());
				urlRequestParams.append("&newlandkode=" + orderTypes.getNewLandKode() + "&newsidesk=" + orderTypes.getNewSideSK() + "&newtext=" +  orderTypes.getNewText());
			}
		}
		
		//session.setAttribute(TransportDispConstants.ACTIVE_URL_RPG_TRANSPORT_DISP, BASE_URL + "==>params: " + urlRequestParams.toString()); 
    	logger.info(Calendar.getInstance().getTime() + " CGI-start timestamp");
    	logger.info("URL: " + BASE_URL);
    	logger.info("URL PARAMS: " + urlRequestParams);
    	String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParams.toString());
    	//Debug --> 
    	logger.debug(jsonDebugger.debugJsonPayloadWithLog4J(jsonPayload));
    	logger.info(Calendar.getInstance().getTime() +  " CGI-end timestamp");
    	if(jsonPayload!=null){
    		JsonMainOrderHeaderContainer container = this.ebookingMainOrderHeaderService.getContainer(jsonPayload);
    		model.put(EbookingConstants.DOMAIN_CONTAINER_OPEN_ORDERS, container);
    		if(container!=null){
    			if(container.getOneorder()!=null){
	    			for( JsonMainOrderHeaderRecord headerRecord: container.getOneorder()){
	    				record = headerRecord;
		    		}
    			}
    		}
    	}		
    	
		return record;
	}
	
	/**
	 * 
	 * @param model
	 * @param record
	 */
	private void setDomainObjectsInView(Map model, JsonMainOrderHeaderRecord record){
		model.put(EbookingConstants.DOMAIN_RECORD, record);
	}
	
	/**
	 * 
	 * @param appUser
	 * @param model
	 */
	private void setCodeDropDownMgr(SystemaWebUser appUser, Map model){
		this.codeDropDownMgr.populateCodesHtmlDropDownsFromJsonString(this.urlCgiProxyService, this.ebookingDropDownListPopulationService,
				 model,appUser,CodeDropDownMgr.CODE_2_COUNTRY, null, null);
		this.codeDropDownMgr.populateHtmlDropDownsFromJsonStringFrankatur(this.urlCgiProxyService, this.ebookingDropDownListPopulationService, model, appUser);
		this.codeDropDownMgr.populateHtmlDropDownsFromJsonStringOppdragsType(this.urlCgiProxyService, this.ebookingDropDownListPopulationService, model, appUser);
	}
	
	private void setDropDownsFromFiles(Map<String, Object> model){
		model.put(EbookingConstants.RESOURCE_MODEL_KEY_CURRENCY_CODE_LIST, this.ebookingDropDownListPopulationService.getCurrencyList());
	}


	/**
	 * 
	 * @param model
	 * @param selectedTypeWithCreateNew
	 * @return
	 */
	private JsonMainOrderTypesNewRecord getDefaultValuesForCreateNewOrder(Map model, String selectedTypeWithCreateNew){
		final String FIELD_SEPARATOR = "@";
		JsonMainOrderTypesNewRecord record = new JsonMainOrderTypesNewRecord();
		//this will be true ONLY when the record is new. Normal Updates of existent records will not be in this category...
		if(selectedTypeWithCreateNew!=null && !"".equals(selectedTypeWithCreateNew)){
			if(selectedTypeWithCreateNew.contains(FIELD_SEPARATOR)){
				String[] str = selectedTypeWithCreateNew.split(FIELD_SEPARATOR);
				if(str.length==6){
					record = new JsonMainOrderTypesNewRecord();
					record.setNewAvd(str[0]);
					record.setNewModul(str[1]);
					record.setNewModul2(str[2]);
					record.setNewLandKode(str[3]);
					record.setNewSideSK(str[4]);
					record.setNewText(str[5]);
					//save to future validation errors
					model.put("selectedType", selectedTypeWithCreateNew);
				}
				
			}
		}
		return record;
	}
	
	/**
	 * 
	 * @param model
	 * @param rpgReturnResponseHandler
	 * @param record
	 */
	private void setFatalError(Map model, RpgReturnResponseHandler rpgReturnResponseHandler, JsonMainOrderHeaderRecord record){
		logger.info(rpgReturnResponseHandler.getErrorMessage());
		this.setAspectsInView(model, rpgReturnResponseHandler);
		//No refresh on jsonRecord is done for the GUI (form fields). Must be implemented right here, if required. !!
        this.setDomainObjectsInView(model, record);
	}
	/**
	 * 
	 * @param model
	 * @param rpgReturnResponseHandler
	 */
	private void setAspectsInView (Map model, RpgReturnResponseHandler rpgReturnResponseHandler){
		model.put(EbookingConstants.ASPECT_ERROR_MESSAGE, rpgReturnResponseHandler.getErrorMessage());
		//extra error information
		StringBuffer errorMetaInformation = new StringBuffer();
		errorMetaInformation.append(rpgReturnResponseHandler.getUser());
		errorMetaInformation.append(rpgReturnResponseHandler.getHereff());
		model.put(EbookingConstants.ASPECT_ERROR_META_INFO, errorMetaInformation);
	}
	
	
	//SERVICES
	@Qualifier ("urlCgiProxyService")
	private UrlCgiProxyService urlCgiProxyService;
	@Autowired
	@Required
	public void setUrlCgiProxyService (UrlCgiProxyService value){ this.urlCgiProxyService = value; }
	public UrlCgiProxyService getUrlCgiProxyService(){ return this.urlCgiProxyService; }
	
	@Qualifier ("ebookingMainOrderHeaderService")
	private EbookingMainOrderHeaderService ebookingMainOrderHeaderService;
	@Autowired
	@Required
	public void setEbookingMainOrderHeaderService (EbookingMainOrderHeaderService value){ this.ebookingMainOrderHeaderService = value; }
	public EbookingMainOrderHeaderService getEbookingMainOrderHeaderService(){ return this.ebookingMainOrderHeaderService; }
	
	
	@Qualifier ("ebookingDropDownListPopulationService")
	private EbookingDropDownListPopulationService ebookingDropDownListPopulationService;
	@Autowired
	@Required
	public void setEbookingDropDownListPopulationService (EbookingDropDownListPopulationService value){ this.ebookingDropDownListPopulationService = value; }
	public EbookingDropDownListPopulationService getEbookingDropDownListPopulationService(){ return this.ebookingDropDownListPopulationService; }
	
	
	@Qualifier ("notisblockService")
	private NotisblockService notisblockService;
	@Autowired
	public void setNotisblockService (NotisblockService value){ this.notisblockService=value; }
	public NotisblockService getNotisblockService(){return this.notisblockService;}
	
	
	@Qualifier ("ebookingChildWindowService")
	private EbookingChildWindowService ebookingChildWindowService;
	@Autowired
	@Required
	public void setEbookingChildWindowService (EbookingChildWindowService value){ this.ebookingChildWindowService = value; }
	public EbookingChildWindowService getEbookingChildWindowService(){ return this.ebookingChildWindowService; }
	
	
}

