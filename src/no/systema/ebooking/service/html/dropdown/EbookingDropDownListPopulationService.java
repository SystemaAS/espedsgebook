/**
 * 
 */
package no.systema.ebooking.service.html.dropdown;

import java.util.List;

import org.apache.log4j.Logger;

import no.systema.main.context.TdsServletContext;
import no.systema.main.util.io.TextFileReaderService;
import no.systema.ebooking.util.EbookingConstants;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingCodeContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingFrankaturContainer;
import no.systema.ebooking.model.jsonjackson.codes.JsonEbookingOppdragTypeContainer;

import no.systema.ebooking.mapper.jsonjackson.JsonEbookingCodeMapper;








/**
 * This service fetches values into drop downs in HTML
 * Criteria is based upon whether the drop down list is static or dynamic.
 * 
 * This class is used ONLY for STATIC lists (e.g. currency codes, country codes, etc)
 * 
 * The servlet context is necessary in order to get a text-xml file within the web-application path...
 * All static lists are retrieved from a file on disk.
 *  
 * @author oscardelatorre
 * @date Jan 07, 2017
 * 
 */
public class EbookingDropDownListPopulationService {
	private static final Logger logger = Logger.getLogger(EbookingDropDownListPopulationService.class.getName());
	
	private final String FILE_RESOURCE_PATH = EbookingConstants.RESOURCE_FILES_PATH;
	private TextFileReaderService textFileReaderService = new TextFileReaderService();
	private JsonEbookingCodeMapper codeMapper = new JsonEbookingCodeMapper();
	
	/*private JsonTransportDispSignatureMapper signMapper = new JsonTransportDispSignatureMapper();
	private JsonTransportDispFrankaturMapper frankaturMapper = new JsonTransportDispFrankaturMapper();
	private JsonTransportDispOppdragTypeMapper oppdragTypeMapper = new JsonTransportDispOppdragTypeMapper();
	private JsonTransportDispChildWindowMapper childWindowMapper = new JsonTransportDispChildWindowMapper();*/
	
	/**
	 * Years
	 * @return
	 */
	public List<String> getYearList(){
		String LIST_FILE = "yearList.txt";
		List<String> list = textFileReaderService.getFileLines(TdsServletContext.getTdsServletContext().getResourceAsStream(this.FILE_RESOURCE_PATH + LIST_FILE));
		//Debug
		/*
		for(String record : list){
			logger.info(record + "X");
		}*/
		return list;
	}
	
	/**
	 * Months
	 * @return
	 */
	public List<String> getMonthList(){
		String LIST_FILE = "monthList.txt";
		List<String> list = textFileReaderService.getFileLines(TdsServletContext.getTdsServletContext().getResourceAsStream(this.FILE_RESOURCE_PATH + LIST_FILE));
		//Debug
		/*
		for(String record : list){
			logger.info(record + "X");
		}*/
		return list;
	}
	/**
	 * 
	 * @return
	 */
	public List<String> getCurrencyList(){
		String LIST_FILE = "currencyList.txt";
		List<String> list = textFileReaderService.getFileLines(TdsServletContext.getTdsServletContext().getResourceAsStream(this.FILE_RESOURCE_PATH + LIST_FILE));
		//Debug
		/*
		for(String record : list){
			logger.info(record + "X");
		}*/
		return list;
	}
	
	/**
	 * 	
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	public JsonEbookingCodeContainer getCodeContainer(String utfPayload) throws Exception{
		return this.codeMapper.getContainer(utfPayload);
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	/*
	public JsonTransportDispSignatureContainer getSignContainer(String utfPayload) throws Exception{
		return this.signMapper.getContainer(utfPayload);
	}*/
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	
	public JsonEbookingFrankaturContainer getFrankaturContainer(String utfPayload) throws Exception{
		return this.codeMapper.getFrankaturContainer(utfPayload);
	}
	
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	
	public JsonEbookingOppdragTypeContainer getOppdragTypeContainer(String utfPayload) throws Exception{
		return this.codeMapper.getOppdragTypeContainer(utfPayload);
	}
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	/*
	public JsonTransportDispOppdragTypeParametersContainer getOppdragTypeTimeContainer(String utfPayload) throws Exception{
		return this.oppdragTypeMapper.getOppdragTypeParametersContainer(utfPayload);
	}*/
	/**
	 * 
	 * @param utfPayload
	 * @return
	 * @throws Exception
	 */
	/*
	public JsonTransportDispGebyrCodeContainer getGebyrCodeContainer(String utfPayload) throws Exception{
		return this.childWindowMapper.getGebyrContainer(utfPayload);
	}*/
	
}
