	/**
 * 
 */
package no.systema.ebooking.util.manager;

import java.util.*;

import org.apache.log4j.Logger;

import no.systema.main.model.SystemaWebUser;
import no.systema.main.service.UrlCgiProxyService;
//eBooking
import no.systema.ebooking.url.store.EbookingUrlDataStore;
import no.systema.ebooking.util.EbookingConstants;
import no.systema.ebooking.util.manager.CodeDropDownMgr;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingCodeContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingCodeRecord;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingFrankaturContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingFrankaturRecord;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingOppdragTypeContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingOppdragTypeRecord;



import no.systema.ebooking.service.html.dropdown.EbookingDropDownListPopulationService;



/**
 * The class handles general gui drop downs aspect population for Work with Trips - Transport Disponering
 *
 * This Manager is not instantiated by the Spring Container at start up. 
 * Instead, it is instantiated by a controller when needed.
 * 
 * 
 * 
 * @author oscardelatorre
 * @date Maj 1, 2015
 * 
 * 	2=Landkoder                     
 * 
 */

public class CodeDropDownMgr {
	private static final Logger logger = Logger.getLogger(CodeDropDownMgr.class.getName());
	//
	public static final String CODE_2_COUNTRY = "2";
	
	/**
	 * 
	 * @param urlCgiProxyService
	 * @param skatDropDownListPopulationService
	 * @param model
	 * @param appUser
	 * @param paramTYP
	 * @param paramKODE2
	 * @param paramKODE3
	 */
	public void populateCodesHtmlDropDownsFromJsonString(UrlCgiProxyService urlCgiProxyService, EbookingDropDownListPopulationService listPopulationService,
														Map model, SystemaWebUser appUser, String paramTYP, String paramKODE2, String paramKODE3){
		//fill in html lists here
		try{
			
			String CODES_URL = EbookingUrlDataStore.EBOOKING_CODES_URL;
			StringBuffer urlRequestParamsKeys = new StringBuffer();
			urlRequestParamsKeys.append("user=" + appUser.getUser());
			urlRequestParamsKeys.append(EbookingConstants.URL_CHAR_DELIMETER_FOR_PARAMS_WITH_HTML_REQUEST + "typ=" + paramTYP);
			if(paramKODE2 !=null){
				urlRequestParamsKeys.append(EbookingConstants.URL_CHAR_DELIMETER_FOR_PARAMS_WITH_HTML_REQUEST + "kode2=" + paramKODE2);
			}
			
			//Now build the payload and send to the back end via the drop down service
			//logger.info("CODES_URL:" + CODES_URL);
			//logger.info("CODES PARAMS:" + urlRequestParamsKeys.toString());
			String utfPayload = urlCgiProxyService.getJsonContent(CODES_URL, urlRequestParamsKeys.toString());
			//debug
			//logger.info(utfPayload);
			JsonEbookingCodeContainer codeContainer = listPopulationService.getCodeContainer(utfPayload);
			List<JsonEbookingCodeRecord> list = new ArrayList();
			
			//Take some exception into consideration here or run the default to populate the final list
			for(JsonEbookingCodeRecord codeRecord: codeContainer.getKodlista()){
				//default
				list.add(codeRecord);
				//logger.info("CODE_RECORD: " + codeRecord.getZtxt());
			}
			
			if(this.CODE_2_COUNTRY.equalsIgnoreCase(paramTYP)){
				model.put(EbookingConstants.RESOURCE_MODEL_KEY_COUNTRY_CODE_LIST,list);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param urlCgiProxyService
	 * @param listPopulationService
	 * @param model
	 * @param appUser
	 */
	public void populateHtmlDropDownsFromJsonStringFrankatur(UrlCgiProxyService urlCgiProxyService, EbookingDropDownListPopulationService listPopulationService,
		Map model, SystemaWebUser appUser){
		//fill in html lists here
		try{
			String URL = EbookingUrlDataStore.EBOOKING_GENERAL_FRANKATUR_INCOTERMS_URL;
			StringBuffer urlRequestParamsKeys = new StringBuffer();
			urlRequestParamsKeys.append("user=" + appUser.getUser());
			
			//Now build the payload and send to the back end via the drop down service
			//logger.info("URL:" + URL);
			String utfPayload = urlCgiProxyService.getJsonContent(URL, urlRequestParamsKeys.toString());
			//logger.info(utfPayload);
			JsonEbookingFrankaturContainer frankaturContainer = listPopulationService.getFrankaturContainer(utfPayload);
			
			//Take some exception into consideration here or run the default to populate the final list
			for(JsonEbookingFrankaturRecord record: frankaturContainer.getFrankaturer()){
				//logger.info("FRANKATUR RECORD: " + record.getFranka());
			}
			model.put(EbookingConstants.RESOURCE_MODEL_KEY_INCOTERMS_LIST,frankaturContainer.getFrankaturer());
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}	
	
	/**
	 * 
	 * @param urlCgiProxyService
	 * @param listPopulationService
	 * @param model
	 * @param appUser
	 */
	public void populateHtmlDropDownsFromJsonStringOppdragsType(UrlCgiProxyService urlCgiProxyService, EbookingDropDownListPopulationService listPopulationService,
			Map model, SystemaWebUser appUser){
			//fill in html lists here
			try{
				String URL = EbookingUrlDataStore.EBOOKING_GENERAL_OPPDRAGSTYPE_URL;
				StringBuffer urlRequestParamsKeys = new StringBuffer();
				urlRequestParamsKeys.append("user=" + appUser.getUser());
				
				//Now build the payload and send to the back end via the drop down service
				logger.info("URL:" + URL);
				String utfPayload = urlCgiProxyService.getJsonContent(URL, urlRequestParamsKeys.toString());
				logger.info(utfPayload);
				JsonEbookingOppdragTypeContainer container = listPopulationService.getOppdragTypeContainer(utfPayload);
				
				//Take some exception into consideration here or run the default to populate the final list
				for(JsonEbookingOppdragTypeRecord record: container.getOppdragsTyper()){
					//logger.info("FRANKATUR RECORD: " + record.getFranka());
				}
				model.put(EbookingConstants.RESOURCE_MODEL_KEY_OPPDRAGSTYPE_LIST, container.getOppdragsTyper());
				
			}catch(Exception e){
				e.printStackTrace();
			}
				
		}	
		
		/**
		 * 
		 * @param urlCgiProxyService
		 * @param listPopulationService
		 * @param model
		 * @param appUser
		 * @param paramsMap
		 */
	/*
		public void populateHtmlDropDownsFromJsonStringGebyrCodes(UrlCgiProxyService urlCgiProxyService, TransportDispDropDownListPopulationService listPopulationService,
			Map model, SystemaWebUser appUser){
			//fill in html lists here
			try{
				String URL = TransportDispUrlDataStore.TRANSPORT_DISP_BASE_CHILDWINDOW_GEBYR_CODES_URL;
				StringBuffer urlRequestParamsKeys = new StringBuffer();
				urlRequestParamsKeys.append("user=" + appUser.getUser() + "&fullinfo=N");
				
				//Now build the payload and send to the back end via the drop down service
				logger.info("URL:" + URL);
				logger.info("PARAMS:" + urlRequestParamsKeys.toString());
				
				String utfPayload = urlCgiProxyService.getJsonContent(URL, urlRequestParamsKeys.toString());
				//logger.info(utfPayload);
				JsonTransportDispGebyrCodeContainer container = listPopulationService.getGebyrCodeContainer(utfPayload);
				
				//Take some exception into consideration here or run the default to populate the final list
				for(JsonTransportDispGebyrCodeRecord record: container.getGebyrKoder()){
					//logger.info("GEBYR RECORD: " + record.getKgekod());
				}
				model.put(TransportDispConstants.RESOURCE_MODEL_KEY_GEBYRCODES_LIST, container.getGebyrKoder());
				
			}catch(Exception e){
				e.printStackTrace();
			}
				
		}	
	
	*/
}
