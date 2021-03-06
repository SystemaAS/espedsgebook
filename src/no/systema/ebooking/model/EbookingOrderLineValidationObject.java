/**
 * 
 */
package no.systema.ebooking.model;

import java.util.Collection;

/**
 * @author oscardelatorre
 * @date Feb 15, 2017
 */
public class EbookingOrderLineValidationObject {
	
	private String linenr = null;
	public void setLinenr(String value){ this.linenr = value;}
	public String getLinenr(){ return this.linenr; }
	
	private String errMsg = null;
	public void setErrMsg(String value){ this.errMsg = value;}
	public String getErrMsg(){ return this.errMsg; }
	
	private String infoMsg = null;
	public void setInfoMsg(String value) {  this.infoMsg = value; }
	public String getInfoMsg() { return this.infoMsg;}
	
	// for last.meter validation and automatic calculation
	private String fvlm = null;
	public void setFvlm(String value) {  this.fvlm = value; }
	public String getFvlm() {return this.fvlm;}
	
	private String fvlm2 = null;
	public void setFvlm2(String value) {  this.fvlm2 = value; }
	public String getFvlm2() {return this.fvlm2;}
	
}
