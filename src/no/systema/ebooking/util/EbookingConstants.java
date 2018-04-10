/**
 * 
 */
package no.systema.ebooking.util;

/**
 * 
 * All type of system constants for EBOOKING in general
 * 
 * @author oscardelatorre
 * @date Jan 26, 2016
 * 
 *
 */
public final class EbookingConstants {
	
	
	//session constants
	public static final String ACTIVE_URL_RPG_EBOOKING = "activeUrlRPG_Ebooking";
	public static final String ACTIVE_URL_RPG_UPDATE_EBOOKING = "activeUrlRPGUpdate_Ebooking";
	public static final String ACTIVE_URL_RPG_FETCH_ITEM_EBOOKING = "activeUrlRPGFetchItem_Ebooking"; //Ajax
	public static final String ACTIVE_URL_RPG_INITVALUE = "=)";
	
	//actions
	public static final String EDIT_ACTION_ON_TOPIC = "editActionOnTopic";
	public static final String EDIT_ACTION_ON_TOPIC_ITEM = "editActionOnTopicItem";
	
	public static final String ACTION_FETCH = "doFetch";
	public static final String ACTION_UPDATE = "doUpdate";
	public static final String ACTION_CREATE = "doCreate";
	public static final String ACTION_DELETE = "doDelete";
	public static final String ACTION_SEND = "doSend";
	
	//update modes
	public static final String MODE_UPDATE = "U";
	public static final String MODE_ADD = "A";
	public static final String MODE_DELETE = "D";
	public static final String MODE_SEND = "S";
	
	
	//url
	public static final String URL_CHAR_DELIMETER_FOR_URL_WITH_HTML_REQUEST_GET = "?";
	public static final String URL_CHAR_DELIMETER_FOR_PARAMS_WITH_HTML_REQUEST = "&"; //Used for GET and POST
	//base path for resource files (for drop-downs or other convenient files
	public static final String RESOURCE_FILES_PATH = "/WEB-INF/resources/files/";
	public static final String RESOURCE_MODEL_KEY_YEAR_LIST = "yearList";
	public static final String RESOURCE_MODEL_KEY_MONTH_LIST = "monthList";
	public static final String RESOURCE_MODEL_KEY_CURRENCY_CODE_LIST = "currencyCodeList";
	public static final String RESOURCE_MODEL_KEY_COUNTRY_CODE_LIST = "countryCodeList";
	public static final String RESOURCE_MODEL_KEY_SIGN_LIST = "signList";
	public static final String RESOURCE_MODEL_KEY_INCOTERMS_LIST = "incotermsList";
	public static final String RESOURCE_MODEL_KEY_OPPDRAGSTYPE_LIST = "oppdragstypeList";
	public static final String RESOURCE_MODEL_KEY_GEBYRCODES_LIST = "gebyrCodesList";
	
	
	public static final Integer CONSTANT_TOTAL_NUMBER_OF_ORDER_LINES = 4;
	/*N/A at the moment
	public static final String RESOURCE_MODEL_KEY_LANGUAGE_LIST = "languageList";
	public static final String RESOURCE_MODEL_KEY_HOURS_LIST = "hoursList";
	public static final String RESOURCE_MODEL_KEY_MINUTES_LIST = "minutesList";
	public static final String RESOURCE_MODEL_KEY_UOM_LIST = "uomList";
	*/
	
	

	//domain objects for model-view passing values
	public static final String DOMAIN_MODEL = "model";
	public static final String DOMAIN_RECORD = "record";
	
	public static final String DOMAIN_CONTAINER = "container";
	public static final String DOMAIN_CONTAINER_VALIDATION_BACKEND = "containerValidationBackend";
	public static final String DOMAIN_CONTAINER_OPEN_ORDERS = "containerOpenOrders";
	public static final String DOMAIN_CONTAINER_CURRENT_ORDERS = "containerCurrentOrders";
	public static final String DOMAIN_CONTAINER_TRIP_LIST = "containerTripList";
	
	public static final String DOMAIN_RECORD_ORDER_EBOOKING = "recordOrderEbooking";
	
	public static final String DOMAIN_LIST = "list";
	public static final String DOMAIN_LIST_CURRENT_ORDERS = "listCurrentOrders";
	public static final String DOMAIN_LIST_OPEN_ORDERS = "listOpenOrders";
	public static final String DOMAIN_MAX_WARNING_OPEN_ORDERS = "maxWarningOpenOrders";
	public static final String DOMAIN_MAX_WARNING_CURRENT_ORDERS = "maxWarningCurrentOrders";
	
	public static final String DOMAIN_RECORD_ITEM_CONTAINER_TOPIC = "recordItemContainerTopic";
	public static final String ITEM_LIST = "itemList";
	public static final String SESSION_LIST = "sessionList";
	public static final String SESSION_SEARCH_FILTER = "searchFilter";
	public static final String SESSION_CHILDWINDOW_FLAG = "cw";
	

	//aspects in view (sucha as errors, logs, other
	public static final String ASPECT_ERROR_MESSAGE = "errorMessage";
	public static final String ASPECT_ERROR_META_INFO = "errorInfo";
	

	   
}
