/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author oscardelatorre
 * @date Maj 17, 2015
 *
 */
public class JsonMainOrderHeaderFraktbrevContainer {
	
	private String user = null;
	public void setUser(String value) {  this.user = value; }
	public String getUser() { return this.user;}
	
	private String avd = null;
	public void setAvd(String value) {  this.avd = value; }
	public String getAvd() { return this.avd;}
	
	private String opd = null;
	public void setOpd(String value) {  this.opd = value; }
	public String getOpd() { return this.opd;}
	
	private String fbn = null;
	public void setFbn(String value) {  this.fbn = value; }
	public String getFbn() { return this.fbn;}
	
	private String lin = null;
	public void setLin(String value) {  this.lin = value; }
	public String getLin() { return this.lin;}
	
	private String mode = null;
	public void setMode(String value) {  this.mode = value; }
	public String getMode() { return this.mode;}
	
	private String errMsg = null;
	public void setErrMsg(String value) {  this.errMsg = value; }
	public String getErrMsg() { return this.errMsg;}
	
	private String infoMsg = null;
	public void setInfoMsg(String value) {  this.infoMsg = value; }
	public String getInfoMsg() { return this.infoMsg;}
	
	private Collection<JsonMainOrderHeaderFraktbrevRecord> awblinelist;
	public void setAwblinelist(Collection<JsonMainOrderHeaderFraktbrevRecord> value){ this.awblinelist = value; }
	public Collection<JsonMainOrderHeaderFraktbrevRecord> getAwblinelist(){ return awblinelist; }
	
	//when fetch for specific item line (ajax)
	private Collection<JsonMainOrderHeaderFraktbrevRecord> awblineGet;
	public void setAwblineGet(Collection<JsonMainOrderHeaderFraktbrevRecord> value){ this.awblineGet = value; }
	public Collection<JsonMainOrderHeaderFraktbrevRecord> getAwblineGet(){ return awblineGet; }
	
	private Collection<JsonMainOrderHeaderFraktbrevRecord> awblineValidate;
	public void setAwblineValidate(Collection<JsonMainOrderHeaderFraktbrevRecord> value){ this.awblineValidate = value; }
	public Collection<JsonMainOrderHeaderFraktbrevRecord> getAwblineValidate(){ return awblineValidate; }
	
	//used in validation (awblineValidate)
	private String fvlinr = null;
	public void setFvlinr(String value) {  this.fvlinr = value; }
	public String getFvlinr() { return this.fvlinr;}
	
	
}
