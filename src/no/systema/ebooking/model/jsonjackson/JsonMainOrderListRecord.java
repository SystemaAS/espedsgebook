/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import no.systema.main.model.jsonjackson.general.JsonAbstractGrandFatherRecord;
/**
 * @author oscardelatorre
 * @date Jun 24, 2016
 * 
 */
public class JsonMainOrderListRecord extends JsonAbstractGrandFatherRecord {
	
	private String unik = null;
	public void setUnik(String value){ this.unik = value;}
	public String getUnik(){ return this.unik; }
	
	private String hereff = null;
	public void setHereff(String value){ this.hereff = value;}
	public String getHereff(){ return this.hereff; }
	
	private String hedtop = null;
	public void setHedtop(String value){ this.hedtop = value;}
	public String getHedtop(){ return this.hedtop; }
	
	private String hegn = null;
	public void setHegn(String value){ this.hegn = value;}
	public String getHegn(){ return this.hegn; }
	
	private String henas = null;
	public void setHenas(String value){ this.henas = value;}
	public String getHenas(){ return this.henas; }
	
	private String henak = null;
	public void setHenak(String value){ this.henak = value;}
	public String getHenak(){ return this.henak; }
	
	private String hefr = null;
	public void setHefr(String value){ this.hefr = value;}
	public String getHefr(){ return this.hefr; }
	
	private String hent = null;
	public void setHent(String value){ this.hent = value;}
	public String getHent(){ return this.hent; }
	
	private String hevkt = null;
	public void setHevkt(String value){ this.hevkt = value;}
	public String getHevkt(){ return this.hevkt; }
	
	private String hem3 = null;
	public void setHem3(String value){ this.hem3 = value;}
	public String getHem3(){ return this.hem3; }
	
	private String hesdf = null;
	public void setHesdf(String value){ this.hesdf = value;}
	public String getHesdf(){ return this.hesdf; }
	
	private String hesdt = null;
	public void setHesdt(String value){ this.hesdt = value;}
	public String getHesdt(){ return this.hesdt; }
	
	private String xfralk = null;
	public void setXfralk(String value){ this.xfralk = value;}
	public String getXfralk(){ return this.xfralk; }
	
	private String xtillk = null;
	public void setXtillk(String value){ this.xtillk = value;}
	public String getXtillk(){ return this.xtillk; }
	
	private String hepk1 = null;
	public void setHepk1(String value){ this.hepk1 = value;}
	public String getHepk1(){ return this.hepk1; }
	
	private String hepk2 = null;
	public void setHepk2(String value){ this.hepk2 = value;}
	public String getHepk2(){ return this.hepk2; }
	
	private String hepk3 = null;
	public void setHepk3(String value){ this.hepk3 = value;}
	public String getHepk3(){ return this.hepk3; }
	
	private String hepk4 = null;
	public void setHepk4(String value){ this.hepk4 = value;}
	public String getHepk4(){ return this.hepk4; }
	
	private String hepk5 = null;
	public void setHepk5(String value){ this.hepk5 = value;}
	public String getHepk5(){ return this.hepk5; }
	
	private String herfa = null;
	public void setHerfa(String value){ this.herfa = value;}
	public String getHerfa(){ return this.herfa; }
	
	private String status = null;
	public void setStatus(String value){ this.status = value;}
	public String getStatus(){ return this.status; }
	
	private String trsdfd = null;
	public void setTrsdfd(String value){ this.trsdfd = value;}
	public String getTrsdfd(){ return this.trsdfd; }
	
	private String trsdfk = null;
	public void setTrsdfk(String value){ this.trsdfk = value;}
	public String getTrsdfk(){ return this.trsdfk; }
	
	private String trsdtd = null;
	public void setTrsdtd(String value){ this.trsdtd = value;}
	public String getTrsdtd(){ return this.trsdtd; }
	
	private String trsdtk = null;
	public void setTrsdtk(String value){ this.trsdtk = value;}
	public String getTrsdtk(){ return this.trsdtk; }
	
	
	
	/**
	 * User for java reflection in other classes
	 * @return
	 * @throws Exception
	 */
	public List<Field> getFields() throws Exception{
		Class cl = Class.forName(this.getClass().getCanonicalName());
		Field[] fields = cl.getDeclaredFields();
		List<Field> list = Arrays.asList(fields);
		
		return list;
	}
	
}
