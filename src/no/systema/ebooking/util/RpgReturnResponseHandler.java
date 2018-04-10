/**
 * 
 */
package no.systema.ebooking.util;

import java.util.*;


import org.apache.log4j.Logger;

/**
 * The class evaluates return codes from RPG operations.
 * 
 * @author oscardelatorre
 * @date Jan 7, 2017
 * 
 */
public class RpgReturnResponseHandler {
	private static final Logger logger = Logger.getLogger(RpgReturnResponseHandler.class.getName());
	
	private String errorMessage = null;
	public void setErrorMessage(String value){ this.errorMessage=value;  }
	public String getErrorMessage(){ return this.errorMessage;  }
	
	private String user = null;
	public void setUser(String value){ this.user=value;  }
	public String getUser(){ return this.user;  }
	
	private String hereff = null;
	public void setHereff(String value){ this.hereff=value;  }
	public String getHereff(){ return this.hereff;  }
	
	private String heunik = null;
	public void setHeunik(String value){ this.heunik=value;  }
	public String getHeunik(){ return this.heunik;  }
	
	
	/**
	 * Sets the error message code after an RPG-call been made by an HTML-POST request on a trip update
	 * 
	 * @param rpgRawReturnPayload
	 * @return
	 */
	public void evaluateRpgResponseOnUpdate(String rpgRawResponsePayload){
		
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("errMsg")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("user")){
						this.user = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("hereff")){
						//this.tupro = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.hereff = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heunik")){
						//this.tuavd = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.heunik = keyValuePair[1].trim();
					}
				}
			}
		}
		
		
	}
	/**
	 * Return json when add-remove order from trip (WorkflowShippingPlanning)
	 * @param rpgRawResponsePayload
	 */
	/*
	public void evaluateRpgResponseOnAddRemoveOrder(String rpgRawResponsePayload){
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("error")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("user")){
						this.user = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("wstur")){
						//this.tupro = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.wstur = keyValuePair[1].trim();
					}
				}
			}
		}
	}
	*/
	/**
	 * 
	 * @param rpgRawResponsePayload
	 */
	/*
	public void evaluateRpgResponseOnPermanentDeleteOrder(String rpgRawResponsePayload){
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("errMsg")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heavd")){
						this.heavd = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heopd")){
						//this.tupro = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.heopd = keyValuePair[1].trim();
					}
				}
			}
		}
	}
	*/
	/**
	 * Copy
	 * @param rpgRawResponsePayload
	 */
	/*
	public void evaluateRpgResponseOnCopyOrder(String rpgRawResponsePayload){
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("errMsg")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heavd")){
						this.heavd = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heopd")){
						this.heopd = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("newavd")){
						this.newavd = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("newopd")){
						this.newopd = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("newhesg")){
						this.newhesg = keyValuePair[1].trim();
					}
				}
			}
		}

	}
	*/
	/**
	 * 
	 * @param rpgRawResponsePayload
	 */
	/*
	public void evaluateRpgResponseOnMoveOrder(String rpgRawResponsePayload){
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("errMsg")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heavd")){
						this.heavd = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heopd")){
						this.heopd = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("newavd")){
						this.newavd = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("newopd")){
						this.newopd = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("newhesg")){
						this.newhesg = keyValuePair[1].trim();
					}
				}
			}
		}
	}
	*/
	/**
	 * 
	 * @param rpgRawResponsePayload
	 */
	
	public void evaluateRpgResponseOnEditSpecificOrder(String rpgRawResponsePayload){
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("errMsg")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("user")){
						this.user = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("hereff")){
						//this.tupro = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.hereff = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heunik")){
						//this.tuavd = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.heunik = keyValuePair[1].trim();
					}
				}
			}
		}
		
		
	}
	
	/**
	 * 
	 * @param rpgRawResponsePayload
	 */
	
	public void evaluateRpgResponseOnValidateSpecificOrderLine(String rpgRawResponsePayload){
		if(rpgRawResponsePayload!=null){
			String tmp = rpgRawResponsePayload.replaceAll("\"", "");
			String tmp2 = tmp.replace("{", "");
			String cleanRawPayload = tmp2.replace("}", "");
			
			String[] record = cleanRawPayload.split(",");
			List <String>list = Arrays.asList(record);
			for(String field: list){
				//logger.info(field);
				String[] keyValuePair = field.split(":");
				if(keyValuePair[0]!=null){
					if(keyValuePair[0].trim().equalsIgnoreCase("errMsg")){
						if(keyValuePair.length>1){
							String errorCode = keyValuePair[1];
							if(errorCode!=null && !"".equals(errorCode.trim())){
								this.errorMessage = errorCode ;
								logger.info(this.errorMessage);
							}
						}
					}else if(keyValuePair[0].trim().equalsIgnoreCase("user")){
						this.user = keyValuePair[0] + ":" + keyValuePair[1] + ",";
					}else if(keyValuePair[0].trim().equalsIgnoreCase("hereff")){
						//this.tupro = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.hereff = keyValuePair[1].trim();
					}else if(keyValuePair[0].trim().equalsIgnoreCase("heunik")){
						//this.tuavd = keyValuePair[0] + ":" + keyValuePair[1]+ ",";
						this.heunik = keyValuePair[1].trim();
					}
				}
			}
		}
		
		
	}
	
	
	
	
}
