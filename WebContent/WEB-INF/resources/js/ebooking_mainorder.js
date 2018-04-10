  //this variable is a global jQuery var instead of using "$" all the time. Very handy
  var jq = jQuery.noConflict();
  var counterIndex = 0;
  var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Please wait...";

  
  function setBlockUI(element){
	  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
  }
  
  jq(function() {
	  jq("#ebookingOrderForm").submit(function() {
		  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT}); 
	  });
  });
  
//Global functions
  function g_getCurrentYearStr(){
	  return new Date().getFullYear().toString();
  }
  function g_getCurrentMonthStr(){
	  var currentMonth = new Date().getMonth() + 1;
	  var currentMonthStr = currentMonth.toString();
	  if (currentMonth < 10) { currentMonthStr = '0' + currentMonth; }
	  return currentMonthStr;
  }
  
  
  jq(function() {
	  jq("#dateTODO").datepicker({ 
		  dateFormat: 'yymmdd'
		  //,defaultDate: "-1m"	  
	  });
  });
  
  
//-----------------------
  // UPLOAD FILE - ORDER
  //---------------------
  function myFileUploadDragEnter(e){
	  jq("#file").addClass( "isa_blue" );
  }
  function myFileUploadDragLeave(e){
	  jq("#file").removeClass( "isa_blue" );
  }
  
  jq(function() {
	  //Triggers drag-and-drop
	  jq('#file').hover(function(){
		  jq("#file").removeClass( "isa_success" );
		  jq("#file").removeClass( "isa_error" );
	  });   
	  
	  //Triggers drag-and-drop
	  jq('#file').change(function(){
		  //Init by removing the class used in dragEnter
		  jq("#file").removeClass( "isa_blue" );
		  
		  if(jq("#wstype").val() == 'ZP'){
			 showTimestampPopup();  
		  }else{
			 jq("#userDate").val("");
			 jq("#userTime").val("");
			 uploadFile();  
		  }
		 
	  });
  });
  function uploadFile(){
	//grab all form data  
	  var form = new FormData(document.getElementById('uploadFileForm'));
	  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
	  
	  jq.ajax({
	  	  type: 'POST',
	  	  url: 'uploadFileFromOrder_Ebooking.do',
	  	  data: form,  
	  	  dataType: 'text',
	  	  cache: false,
	  	  processData: false,
	  	  contentType: false,
  		  success: function(data) {
		  	  var len = data.length;
	  		  if(len>0){
	  			jq("#file").val("");
			  	//Check for errors or successfully processed
			  	var exists = data.indexOf("ERROR");
			  	if(exists>0){
			  		//ERROR on back-end
			  		jq("#file").addClass( "isa_error" );
			  		jq("#file").removeClass( "isa_success" );
			  	}else{
			  		//OK
			  		jq("#file").addClass( "isa_success" );
			  		jq("#file").removeClass( "isa_error" );
			  	}
			  	//response to end user 
			  	alert(data);
			  	if(data.indexOf('[OK') == 0) {
				  	var status = jq("#status").val();
				  	//var avd = jq("#wsavd").val();
				  	//var opd = jq("#wsopd").val();
				  	var heunik = jq("#heunik").val();
				  	var hereff = jq("#hereff").val();
				  	//reload
				  	reloadCallerParentOrder(status,hereff,heunik);
			  	}
			  	//unblock
			  	jq.unblockUI();
	  		  }
	  	  }, 
	  	  error: function() {
	  		  jq.unblockUI();
	  		  alert('Error loading ...');
	  		  jq("#file").val("");
	  		  //cosmetics
	  		  jq("#file").addClass( "isa_error" );
	  		  jq("#file").removeClass( "isa_success" );
		  }
	  });
  }
  
  //Reload the order after being coupled with the trip 
  function reloadCallerParentOrder(status, hereff, heunik) {
	  window.location = "ebooking_mainorder.do?action=doFetch&heunik=" + heunik + "&hereff=" + hereff + "&status=" + status;
  }
  
  //END UPLOAD ORDERS
  
  
 
  //Invoicee events
  jq(function() {
	  jq("#xfakBet").change(function(){
		  //now check the user input alternatives
		  var xfakBet = jq("#xfakBet").val();

		  //some help to the user
		  if(xfakBet == 'S'){
			  jq("#heknkf").val("");
			  //copy to invoicee
			  jq("#heknsf").val(jq("#hekns").val());
			  jq("#whenasf").val("");
			  
		  }else if(xfakBet == 'M'){
			  jq("#heknsf").val("");
			  //copy to invoicee
			  jq("#heknkf").val(jq("#heknk").val());
			  jq("#whenakf").val("");
			  
		  }else{
			  jq("#heknsf").val("");
			  jq("#whenasf").val("");
			  //
			  jq("#heknkf").val("");
			  jq("#whenakf").val("");
		  }
	  });
  });
  //----------------------
  //START ETD / ETA dates
  //----------------------
  jq(function() {
	  jq("#wsetdd").datepicker({ 
		  onSelect: function(date) {
		  	jq("#wsetdk").focus();
	      },
		  dateFormat: 'yymmdd',
		  firstDay: 1 //monday
		  /*showOn: "button",
	      buttonImage: "resources/images/calendar.gif",
	      buttonImageOnly: true,
	      buttonText: "Select date" 
		  */
		  //dateFormat: 'ddmmy', 
	  });
	  jq("#wsetdd").blur(function(){
		  //now check the user input alternatives
		  var str = jq("#wsetdd").val();
		  if(str!=''){
			  var length = str.length;
			  if(length==2){
				  jq("#wsetdd").val(g_getCurrentYearStr() + g_getCurrentMonthStr() + str);  
			  }else if (length==4){
				  var userDay = str.substring(0,2);
				  var userMonth = str.substring(2,4);
				  jq("#wsetdd").val(g_getCurrentYearStr() + userMonth + userDay);
			  }
		  }
	  });
	  jq("#wsetdk").blur(function(){
		  //now check the user input alternatives
		  var str = jq("#wsetdk").val();
		  if(str!=''){
			  var length = str.length;
			  if(length==2){
				  jq("#wsetdk").val(str + '00');  
			  }else if (length==1){
				  jq("#wsetdk").val('0' + str + '00');
			  }
		  }
	  });
	  
	  jq("#wsetad").datepicker({ 
		  onSelect: function(date) {
		  	jq("#wsetak").focus();
	      },
		  dateFormat: 'yymmdd',
		  firstDay: 1 //monday
	  });
	  jq("#wsetad").blur(function(){
		  //now check the user input alternatives
		  var str = jq("#wsetad").val();
		  if(str!=''){
			  var length = str.length;
			  if(length==2){
				  jq("#wsetad").val(g_getCurrentYearStr() + g_getCurrentMonthStr() + str);  
			  }else if (length==4){
				  var userDay = str.substring(0,2);
				  var userMonth = str.substring(2,4);
				  jq("#wsetad").val(g_getCurrentYearStr() + userMonth + userDay);
			  }
		  }
		  
	  });
	  jq("#wsetak").blur(function(){
		  //now check the user input alternatives
		  var str = jq("#wsetak").val();
		  if(str!=''){
			  var length = str.length;
			  if(length==2){
				  jq("#wsetak").val(str + '00');  
			  }else if (length==1){
				  jq("#wsetak").val('0' + str + '00');
			  }
		  }
	  });
  });
//----------------------
//END ETD / ETA dates
//----------------------
  
  
  
//==============================================================================
  //START - Postal codes On-Blur (required to be an exact number and nothing else)
  //==============================================================================
  var CITY_OWNwppns1 = 1;
  var CITY_OWNwppns2 = 2;
  jq(function() {
	  	//must be done since CustomValidity is HTML 5 and not jQuery
	    //otherwise the validation is never removed (when the value was setted via jQuery in some event)
	  	jq('#helka').focus(function() {
	    	if(jq('#helka').val()!=''){
	    		refreshCustomValidity(jq('#helka')[0]);
	  		}
	  	});
	  	jq('#hesdf').focus(function() {
	  		if(jq('#hesdf').val()!=''){
	    		refreshCustomValidity(jq('#hesdf')[0]);
	  		}else{
	  			if(jq('#heads3').val()!=''){
	  				var tmp = jq('#heads3').val();
	  				var SPACE = " ";
	  				var n = tmp.indexOf(SPACE);
	  				//default
	  				var postNr = tmp.substring(0,4);
	  				//in case other than default
	  				if(n > -1){
	  					postNr = tmp.substring(0, n);
	  				}
	  				jq('#hesdf').val(postNr);
	  			}
	  		}
	  	});
	  	
	  	
	    jq('#hesdf').blur(function() {
	    	var id = jq('#hesdf').val();
	    	if(id!=null && id!=""){
	    		var countryCode = jq('#helka').val();
	    		getCity(CITY_OWNwppns1,id,countryCode);
	    	}else{
	    		jq('#OWNwppns1').val("");
	    	}
		});
		
	    jq('#hesdfIdLink').click(function() {
	    	jq('#hesdfIdLink').attr('target','_blank');
	    	window.open('ebooking_childwindow_postalcodes.do?action=doInit&direction=fra&st2lk=' + jq('#helka').val() + '&st2kod=' + jq('#hesdf').val() + '&caller=hesdf', "postalcodeWin", "top=300px,left=450px,height=600px,width=800px,scrollbars=no,status=no,location=no");
	    });
	    jq('#hesdfIdLink').keypress(function(e){ //extra feature for the end user
			if(e.which == 13) {
				jq('#hesdfIdLink').click();
			}
	    });
	    
	    //must be done since CustomValidity is HTML 5 and not jQuery
	    //otherwise the validation is never removed (when the value was setted via jQuery in some event)
	    jq('#hetri').focus(function() {
	    	if(jq('#hetri').val()!=''){
	    		refreshCustomValidity(jq('#hetri')[0]);
	  		}
	  	});
	    
	    
	    //------
	    //hesdt
	    //------
	    jq('#hesdt').focus(function() {
	    	if(jq('#hesdt').val()!=''){
	    		refreshCustomValidity(jq('#hesdt')[0]);
	  		}else{
	  			if(jq('#headk3').val()!=''){
	  				var tmp = jq('#headk3').val();
	  				var SPACE = " ";
	  				var n = tmp.indexOf(SPACE);
	  				//default
	  				var postNr = tmp.substring(0,4);
	  				//in case other than default
	  				if(n > -1){
	  					postNr = tmp.substring(0, n);
	  				}
	  				
	  				jq('#hesdt	').val(postNr);
	  			}
	  		}
	  	});
	    jq('#hesdt').blur(function() {
    		var id = jq('#hesdt').val();
    		if(id!=null && id!=""){
    			var countryCode = jq('#hetri').val();
    			getCity(CITY_OWNwppns2,id,countryCode);
    		}else{
    			jq('#OWNwppns2').val("");
    		}
		});
	    
	    jq('#hesdtIdLink').click(function() {
	    	jq('#hesdtIdLink').attr('target','_blank');
	    	window.open('ebooking_childwindow_postalcodes.do?action=doInit&direction=til&st2lk=' + jq('#hetri').val() + '&st2kod=' + jq('#hesdt').val() + '&caller=hesdt', "postalcodeWin", "top=300px,left=450px,height=600px,width=800px,scrollbars=no,status=no,location=no");
	    });
	    jq('#hesdtIdLink').keypress(function(e){ //extra feature for the end user
			if(e.which == 13) {
				jq('#hesdtIdLink').click();
			}
	    });
	    
  });
  
  //Ajax on postal codes
  function getCity(target, id, countryCode){
	  jq.getJSON('searchPostNumber_Ebooking.do', {
		  applicationUser : jq('#applicationUser').val(),
		  id : id,
		  countryCode : countryCode,
		  ajax : 'true'
	  }, function(data) {
		var len = data.length;
		if(len==1){ //must be a single-valid value
			for ( var i = 0; i < len; i++) {
				if(target==CITY_OWNwppns1){
					jq('#OWNwppns1').val(data[i].st2nvn);
					jq('#helka').val(data[i].st2lk);
					jq('#hesdf').attr("class","inputTextMediumBlue11MandatoryField");
					
				}else if(target==CITY_OWNwppns2){
					jq('#OWNwppns2').val(data[i].st2nvn);
					jq('#hetri').val(data[i].st2lk);
					jq('#hesdt').attr("class","inputTextMediumBlue11MandatoryField");
					
				}/*else if(target==CITY_OWNwppns3){
					jq('#OWNwppns3').val(data[i].st2nvn);
					jq('#helks').val(data[i].st2lk);
					jq('#hesdff').attr("class","inputTextMediumBlue11");
					
				}else if(target==CITY_OWNwppns4){
					jq('#OWNwppns4').val(data[i].st2nvn);
					jq('#helkk').val(data[i].st2lk);
					jq('#hesdvt').attr("class","inputTextMediumBlue11");
				}*/
			}
		}else{
			//invalid postal code
			if(target==CITY_OWNwppns1){
				jq('#hesdf').addClass("text11RedBold");
				jq('#OWNwppns1').val("?");
			}else if(target==CITY_OWNwppns2){
				jq('#hesdt').addClass("text11RedBold");
				jq('#OWNwppns2').val("?");
			}/*else if(target==CITY_OWNwppns3){
				jq('#hesdff').addClass("text11RedBold");
				jq('#OWNwppns3').val("?");
			}else if(target==CITY_OWNwppns4){
				jq('#hesdvt').addClass("text11RedBold");
				jq('#OWNwppns4').val("?");
			}*/
		}
	});
  }
  //=================
  //END Postal codes
  //=================
  
  
  
//-----------------------
  //INIT CUSTOMER Object
  //-----------------------
  var map = {};
  //init the customer object in javascript (will be put into a map)
  var customer = new Object();
  //fields
  customer.kundnr = "";customer.knavn = "";customer.adr1 = "";
  customer.adr2 = "";customer.adr3 = ""; customer.land = ""; customer.auxnavn=""; customer.auxtlf=""; customer.auxmail="";
  //--------------------------------------------------------------------------------------
  //Extra behavior for Customer number ( without using (choose from list) extra roundtrip)
  //--------------------------------------------------------------------------------------
  jq(function() {  
	  	//SHIPPER/CONSIGNOR
	    jq('#hekns').blur(function() {
	    	getConsignor();
		});
	    //must be done since CustomValidity is HTML 5 and not jQuery
	    //otherwise the validation is never removed (when the value was setted via jQuery in some event)
	    jq('#henas').focus(function() {
	    	
	    	if(jq('#henas').val()!=''){
	  			refreshCustomValidity(jq('#henas')[0]);
	  		}
	  	});
	    jq('#heads1').focus(function() {
	    	if(jq('#heads1').val()!=''){
	    		refreshCustomValidity(jq('#heads1')[0]);
	  		}
	  	});
	    
	    function getConsignor(){
	    	var hekns = jq('#hekns').val();
    		if(hekns!=null && hekns!=""){
	    		jq.getJSON('searchCustomer_Ebooking.do', {
				applicationUser : jq('#applicationUser').val(),
				customerName : "",
				customerNumber : jq('#hekns').val(),
				ajax : 'true'
	    		}, function(data) {
					//alert("Hello");
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						customer = new Object();
						customer.kundnr = data[i].kundnr;
						customer.knavn = data[i].navn;
						customer.auxnavn = data[i].auxnavn;
						customer.adr1 = data[i].gateAdr;
						customer.adr2 = data[i].adresse2;
						customer.adr3 = data[i].postnrSted;
						customer.land = data[i].land;
						customer.auxtlf = data[i].auxtlf;
						customer.auxmail = data[i].auxmail;
						map[customer.kundnr] = customer;
					}
					if(len > 0){
						jq('#henas').removeClass( "isa_error" );
						
						//always show seller
						var seller = customer.knavn;
						jq('#whenas').val(seller);
		    			//now check ids (name and address in order to overdrive (when applicable)
						var name = jq('#henas').val().trim();
		    			//var address = jq('#heads1').val().trim();
		    			//only if name is empty
		    			//if(name==''){
							jq('#hekns').val(customer.kundnr);
							jq('#whenas').val(seller);
							if(customer.auxnavn!=''){
								jq('#henas').val(customer.auxnavn);
							}else{
								//fallback 
								jq('#henas').val(jq('#whenas').val());
							}
							jq('#heads1').val(customer.adr1);
							jq('#heads2').val(customer.adr2);
							jq('#heads3').val(customer.adr3 + " " +  customer.land);
							jq('#wsscont').val("");
							jq('#wsstlf').val(customer.auxtlf);
							jq('#wssmail').val(customer.auxmail);
							//Form field on "Fra"
							jq('#helka').val(customer.land);
							//Fra sted
							if(jq('#heads3').val()!=''){
				  				var tmp = jq('#heads3').val();
				  				var postNr = tmp.substring(0,4);
				  				jq('#hesdf').val(postNr);
				  			}
		    			//}	
					}else{
						//init fields
						//jq('#hekns').val("");
						jq('#whenas').val("");
						//
						jq('#henas').val("NOT EXISTS");
						jq('#henas').addClass( "isa_error" );
						//
						jq('#heads1').val("");
						jq('#heads2').val("");
						jq('#heads3').val("");
					}
	    		});
    		}
	    }
	    //Hurtigsøk on sender
	    jq('#wvakure').blur(function() {
	    	var wvakure = jq('#wvakure').val();
	    	//alert("hi");
	    	if(wvakure!=null && wvakure!=""){
	    		//alert("2");
	    		
	    		jq.getJSON('getSenderAddressHurtigSok_Ebooking.do', {
				applicationUser : jq('#applicationUser').val(),
				kundnr : jq('#trknfa').val(),
				vakure : wvakure,
				ajax : 'true'
	    		}, function(data) {
					//alert("Hello");
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						customer = new Object();
						customer.kundnr = data[i].vakure;
						customer.knavn = data[i].vadrna;
						//customer.auxnavn = data[i].auxnavn;
						customer.adr1 = data[i].vadrn1;
						customer.adr2 = data[i].vadrn2;
						customer.adr3 = data[i].vadrn3;
						customer.land = data[i].valand;
						customer.auxtlf = data[i].vatlf;
						customer.auxmail = data[i].vamail;
						map[customer.kundnr] = customer;
					}
					if(len > 0){
						//jq('#henas').removeClass( "isa_error" );
						
						//only if name is empty
		    			//if(name==''){
							jq('#vakure').val(customer.kundnr);
							jq('#henas').val(customer.knavn);
							jq('#heads1').val(customer.adr1);
							jq('#heads2').val(customer.adr2);
							jq('#heads3').val(customer.adr3 + " " +  customer.land);
							jq('#wsscont').val("");
							jq('#wsstlf').val(customer.auxtlf);
							jq('#wssmail').val(customer.auxmail);
							//extra fields to populate
							if(customer.land != '' && customer.land != jq('#helka').val()){
								jq('#helka').val(customer.land);
							}
							//
							jq('#wvakure').removeClass( "isa_error" );
		    			//}	
					}else{
						//init fields
						//jq('#hekns').val("");
						jq('#wvakure').val(jq('#wvakure').val() + '??');
						jq('#wvakure').addClass( "isa_error" );
					}
	    		});
    		}
	    	
		});
	    
	    //Hurtigsøk on receiver
	    jq('#wvakure2').blur(function() {
	    	var wvakure = jq('#wvakure2').val();
	    	//alert("hi");
	    	if(wvakure!=null && wvakure!=""){
	    		//alert("2");
	    		
	    		jq.getJSON('getSenderAddressHurtigSok_Ebooking.do', {
				applicationUser : jq('#applicationUser').val(),
				kundnr : jq('#trknfa').val(),
				vakure : wvakure,
				ajax : 'true'
	    		}, function(data) {
					//alert("Hello");
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						customer = new Object();
						customer.kundnr = data[i].vakure;
						customer.knavn = data[i].vadrna;
						//customer.auxnavn = data[i].auxnavn;
						customer.adr1 = data[i].vadrn1;
						customer.adr2 = data[i].vadrn2;
						customer.adr3 = data[i].vadrn3;
						customer.land = data[i].valand;
						customer.auxtlf = data[i].vatlf;
						customer.auxmail = data[i].vamail;
						map[customer.kundnr] = customer;
					}
					if(len > 0){
						//jq('#henas').removeClass( "isa_error" );
						
						//only if name is empty
		    			//if(name==''){
							jq('#vakure').val(customer.kundnr);
							jq('#henak').val(customer.knavn);
							jq('#headk1').val(customer.adr1);
							jq('#headk2').val(customer.adr2);
							jq('#headk3').val(customer.adr3 + " " +  customer.land);
							jq('#wskcont').val("");
							jq('#wsktlf').val(customer.auxtlf);
							jq('#wskmail').val(customer.auxmail);
							//
							//extra fields to populate
							if(customer.land != '' && customer.land != jq('#hetri').val()){
								jq('#hetri').val(customer.land);
							}
							
							jq('#wvakure2').removeClass( "isa_error" );
		    			//}	
					}else{
						//init fields
						//jq('#hekns').val("");
						jq('#wvakure2').val(jq('#wvakure2').val() + '??');
						jq('#wvakure2').addClass( "isa_error" );
					}
	    		});
    		}
	    	
		});
	    
	    //CONSIGNEE
	    jq('#heknk').blur(function() {
	    	getConsignee();
		});
	    //must be done since CustomValidity is HTML 5 and not jQuery
	    //otherwise the validation is never removed (when the value was setted via jQuery in some event)
	    jq('#henak').focus(function() {
	    	if(jq('#henak').val()!=''){
	    		refreshCustomValidity(jq('#henak')[0]);
	  		}
	  	});
	    jq('#headk1').focus(function() {
	    	if(jq('#headk1').val()!=''){
	    		refreshCustomValidity(jq('#headk1')[0]);
	  		}
	  	});
	    function getConsignee(){
	    	var heknk = jq('#heknk').val();
    		if(heknk!=null && heknk!=""){
				jq.getJSON('searchCustomer_Ebooking.do', {
				applicationUser : jq('#applicationUser').val(),
				customerName : "",
				customerNumber : jq('#heknk').val(),
				ajax : 'true'
				}, function(data) {
					//alert("Hello");
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						customer = new Object();
						customer.kundnr = data[i].kundnr;
						customer.knavn = data[i].navn;
						customer.adr1 = data[i].gateAdr;
						customer.adr2 = data[i].adresse2;
						customer.adr3 = data[i].postnrSted;
						customer.land = data[i].land;
						customer.auxnavn = data[i].auxnavn;
						customer.auxtlf = data[i].auxtlf;
						customer.auxmail = data[i].auxmail;
						map[customer.kundnr] = customer;
					}
					if(len > 0){
						jq('#henak').removeClass( "isa_error" );
						
						var buyer = customer.knavn;
						jq('#whenak').val(buyer);
						
						var name = jq('#henak').val().trim();
	    				//var address = jq('#headk1').val().trim();
	    				//only if name is empty
	    				//if(name==''){
							jq('#heknk').val(customer.kundnr);
							jq('#whenak').val(buyer);
							if(customer.auxnavn!=''){
								jq('#henak').val(customer.auxnavn);
							}else{
								//fallback
								jq('#henak').val(jq('#whenak').val());
							}
							jq('#headk1').val(customer.adr1);
							jq('#headk2').val(customer.adr2);
							jq('#headk3').val(customer.adr3 + " " + customer.land);
							jq('#wskcont').val("");
							jq('#wsktlf').val(customer.auxtlf);
							jq('#wskmail').val(customer.auxmail);
							//Form field on "Til"
							jq('#hetri').val(customer.land);
							//Til sted
							if(jq('#headk3').val()!=''){
				  				var tmp = jq('#headk3').val();
				  				var postNr = tmp.substring(0,4);
				  				jq('#hesdt').val(postNr);
				  			}
	    				//}
					}else{
						//init fields
						//jq('#heknk').val("");
						jq('#whenak').val("");
						//
						jq('#henak').val("NOT EXISTS");
						jq('#henak').addClass( "isa_error" );
						//
						jq('#headk1').val("");
						jq('#headk2').val("");
						jq('#headk3').val("");
					}
				});
    		}
	    }
	    //---------------------------------------------
	    //OPPDGIV - PRINCIPAL - Get cascade other id's
	    //---------------------------------------------
	    /*
	    jq('#trknfa').blur(function() {
    		getPrincipalName();
	    });
	    //Invoice parties
	    function setInvoiceParties() {
	    	var SELLER = "S"; var BUYER = "K";
			var id = jq('#trknfa').val();
			if(id!=null && id!=""){
				if(SELLER==jq('#trkdak').val()){
					//(A) Seller Invoice party
					//if(jq('#heknsf').val()==''){
						jq('#heknsf').val(jq('#varFakknr').val());
	    				jq('#heknkf').val("");
	    				jq('#henakf').val("");
	    				getInvoicePartySeller();
					//}
					//(B) Sender-Consignor
					if(jq('#hekns').val()==''){
	    				jq('#hekns').val(id);
	    				jq('#hekns').blur(); //trigger the Consignor event
					}
				}else if(BUYER==jq('#trkdak').val()){
					//(A) Buyer Invoice party
					//if(jq('#heknkf').val()==''){
	    				jq('#heknkf').val(jq('#varFakknr').val());
	    				jq('#heknsf').val("");
	    				jq('#henasf').val("");
	    				getInvoicePartyBuyer();
					//}
					//(B) Receiver-Consignee
					if(jq('#heknk').val()==''){
	    				jq('#heknk').val(id);
	    				jq('#heknk').blur(); //trigger the Consignee event
					}
				}
			}
	    	
	    };
	    //OPPDGIV. code
	    jq(function() { 
		    jq('#trkdak').change(function() {
		    	setInvoiceParties();
		    	jq('#trknfa').focus();
		    	if(jq('#trkdak').val()=='S'){
		    		//if(jq('#hefr').val() == ''){
		    			jq('#hefr').val('S');
		    		//}
		    	} else if(jq('#trkdak').val()=='K'){
		    		//if(jq('#hefr').val() == ''){
		    			jq('#hefr').val('M');
		    		//}
		    	}
		    });
	    });
	    */
	    //Fakturapart Seller
	    jq('#heknsf').blur(function() {
	    	getInvoicePartySeller();
		});
	    //Fakturapart Buyer
	    jq('#heknkf').blur(function() {
	    	getInvoicePartyBuyer();
	    });
	    //-------------------
	    //getPrincipalName()
	    //-------------------
	    /*
	    function getPrincipalName() {
	    	var id = jq('#trknfa').val();
    		if(id!=null && id!=""){
    			jq.getJSON('searchCustomer_TransportDisp.do', {
				applicationUser : jq('#applicationUser').val(),
				customerName : "",
				customerNumber : id,
				ajax : 'true'
	    		}, function(data) {
	    			//alert("Hello");
	    			jq('#henaa').val("");
	    			var len = data.length;
	    			for ( var i = 0; i < len; i++) {
	    				jq('#henaa').val(data[i].navn);
	    				jq('#varFakknr').val(data[i].fakknr);
	    				//----------------------------------------------------------------------------------------------
	    				//INVOICE Parties fragment
	    				//HAS TO BE HERE. 
	    				//Can not move this fragment outside this ajax call. Otherwise there will not be a sync call...
	    				//-----------------------------------------------------------------------------------------------
	    				setInvoiceParties();
	    			}
				});
    		}
	    }
	    */
	    //--------------------------
	    //getInvoicePartySeller()
	    //--------------------------
	    function getInvoicePartySeller() {
	    	var id = jq('#heknsf').val();
    		if(id!=null && id!=""){
	    		jq.getJSON('searchCustomer_Ebooking.do', {
				applicationUser : jq('#applicationUser').val(),
				customerName : "",
				customerNumber : id,
				ajax : 'true'
	    		}, function(data) {
	    			jq('#henasf').val("");
	    			var len = data.length;
	    			for ( var i = 0; i < len; i++) {
	    				if(data[i].aktkod != 'I'){
	    					jq('#henasf').val(data[i].navn);
	    					//jq('#heknsf').addClass( "inputTextMediumBlueUPPERCASE" );
	    					jq('#heknsf').removeClass ("isa_warning");
	    					jq('#henasf').removeClass ("isa_warning");
	    				}else{
	    					jq('#heknsf').addClass( "isa_warning" );
	    					jq('#henasf').addClass( "isa_warning" );
	    					//jq('#heknsf').removeClass ("inputTextMediumBlueUPPERCASE");
	    					jq('#henasf').val("adr.kunde?" + data[i].navn);
	    				}
	    			}
	    		});
    		}else{
    			jq('#henasf').val("");
    		}
	    }
	    //--------------------------
	    //getInvoicePartyBuyer()
	    //--------------------------
	    function getInvoicePartyBuyer() {
    		var id = jq('#heknkf').val();
    		if(id!=null && id!=""){
	    		jq.getJSON('searchCustomer_Ebooking.do', {
				applicationUser : jq('#applicationUser').val(),
				customerName : "",
				customerNumber : id,
				ajax : 'true'
	    		}, function(data) {
	    			jq('#henakf').val("");
	    			var len = data.length;
	    			for ( var i = 0; i < len; i++) {
	    				jq('#henakf').val(data[i].navn);
	    				
	    				if(data[i].aktkod != 'I'){
	    					jq('#henakf').val(data[i].navn);
	    					jq('#heknkf').removeClass ("isa_warning");
	    					jq('#henakf').removeClass ("isa_warning");
	    				}else{
	    					jq('#heknkf').addClass( "isa_warning" );
	    					jq('#henakf').addClass( "isa_warning" );
	    					jq('#henakf').val("adr.kunde?" + data[i].navn);
	    				}
	    				
	    			}
	    		});
    		}else{
    			jq('#henakf').val("");
    		}
		}
	   
	});
  

  
  //-------------------------------------------------------
  //Packing codes onBlur / child window (is triggered from jsp)
  //-------------------------------------------------------
  function searchPackingCodesOnBlur(element) {
	  var id = element.id;
	  var record = id.split('_');
	  var counter = record[1];
	  var codeId = jq("#fvpakn_" + counter).val();
	  jq.ajax({
	  	  type: 'GET',
	  	  url: 'searchPackingCodes_Ebooking.do',
	  	  data: { applicationUser : jq('#applicationUser').val(),
		  		  kode : codeId },
	  	  dataType: 'json',
	  	  cache: false,
	  	  contentType: 'application/json',
	  	  success: function(data) {
	  		var len = data.length;
	  		for ( var i = 0; i < len; i++) {
	  			jq("#fvvt_" + counter).val(data[i].entext);
	  			jq("#fvlen_" + counter).val(data[i].enlen);
	  			jq("#fvbrd_" + counter).val(data[i].enbrd);
	  			jq("#fvhoy_" + counter).val(data[i].enhoy);
	  			jq("#fvlm_" + counter).val(data[i].enlm);
	  			jq("#fvlm2_" + counter).val(data[i].enlm2);
	  		}
	  	  }
	  });
  }	
  //new line
  function searchPackingCodesNewLineOnBlur(element) {
	  var codeId = jq("#fvpakn").val();
	  jq.ajax({
	  	  type: 'GET',
	  	  url: 'searchPackingCodes_Ebooking.do',
	  	  data: { applicationUser : jq('#applicationUser').val(),
		  		  kode : codeId },
	  	  dataType: 'json',
	  	  cache: false,
	  	  contentType: 'application/json',
	  	  success: function(data) {
	  		var len = data.length;
	  		for ( var i = 0; i < len; i++) {
	  			jq("#fvvt").val(data[i].entext);
	  			jq("#fvlen").val(data[i].enlen);
	  			jq("#fvbrd").val(data[i].enbrd);
	  			jq("#fvhoy").val(data[i].enhoy);
	  			jq("#fvlm").val(data[i].enlm);
	  			jq("#fvlm2").val(data[i].enlm2);
	  		}
	  	  }
	  });
  }	
  function searchPackingCodes(element) {
	  var id = element.id;
	  var record = id.split('_');
	  var i = record[1]; 
	  //alert(jq('#fvpakn_' + counter).val());
	  jq(id).attr('target','_blank');
  	  window.open('ebooking_childwindow_packingcodes.do?action=doFind&kode=' + jq("#fvpakn_" + i).val() + '&callerLineCounter=' + i, 
  			  "packingCodesWin", "top=300px,left=450px,height=600px,width=800px,scrollbars=no,status=no,location=no");
  }
  
  function searchPackingCodesNewLine(element) {
	  jq(element.id).attr('target','_blank');
  	  window.open('ebooking_childwindow_packingcodes.do?action=doFind&kode=' + jq("#fvpakn").val() + '&callerLineCounter=', 
			  "packingCodesWin", "top=300px,left=450px,height=600px,width=800px,scrollbars=no,status=no,location=no");
  }
  
  //------------------------
  //SUM fields Item lines
  //----------------------
  function sumAntal(element) {
	  //element.id;
	  var sum = 0;
	  jq( ".clazzAntMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvant_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#hent').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
  }
  
  function sumVareslag(element){
	  var id = element.id;
	  var counter = id.replace("fvvt_","");
	  var targetStr = "";
	  //This feature is required only IF and only IF there is just a single line in the matrix. 
	  //Otherwise, the end-user must enter the total vareslag him/herself
	  if(counter=='1'){
		  var fvpakn = jq('#fvpakn_' + counter).val();
		  var fvvt = jq('#fvvt_' + counter).val();
		  var fvant = jq('#fvant_' + counter).val();
		  if(fvvt!='' && fvant!=''){
			  targetStr = fvpakn + " " + fvvt;
		  }
		  if(!jq('#hestl4').prop('checked')){
			  if(jq('#hevs1').val()==''){
				  jq('#hevs1').val(targetStr);
			  }
		  }
	  }
  }
	  
  function sumWeight(element) {
	  //element.id;
	  var sum = 0;
	  jq( ".clazzWeightMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvvkt_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#hevkt').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
  }

  function sumVolume(element) {
	  //element.id;
	  var MAX_VALUE = 9999.99;
	  var sum = 0;
	  jq( ".clazzVolMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvvol_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  var dblValue = Number(value);
			  if(dblValue > MAX_VALUE){
				  jq('#fvvol_' + counter).addClass( "isa_error" );
			  }else{
				  sum += Number(value);
				  jq('#fvvol_' + counter).removeClass( "isa_error" );
			  }
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#hem3').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
  }
  function calculateVolume(element) {
	  var id = element.id;
	  //if(jq(element).val()==''){
		  var antal; var length; var width; var height; var result;
		  antal = jq('#fvant').val();
		  length = jq('#fvlen').val();
		  width= jq('#fvbrd').val();
		  height= jq('#fvhoy').val();
		  	
		  result = Number(antal)*Number(length)*Number(width)*Number(height);
		  //Now to the math
		  if(result>0){
			  result = result * 0.000001;
			  jq("#fvvol").val(result.toLocaleString('de-DE', { useGrouping: false }));
		  }
	  //}
  }
  function sumLm() {
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  jq( ".clazzLmMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvlm_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  var dblValue = Number(value);
			  if(dblValue > MAX_VALUE){
				  jq('#fvlm_' + counter).addClass( "isa_error" );
			  }else{
				  sum += Number(value);
				  jq('#fvlm_' + counter).removeClass( "isa_error" );
				  //AUTO FILL Lmla (if applicable)
				  if(jq('#fvlm2_' + counter).val()==''){
					  jq('#fvlm2_' + counter).val(jq('#fvlm_' + counter).val());
				  }
			  }
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#helm').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  
	  
  }
  function sumLmla() {
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  jq( ".clazzLmlaMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvlm2_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  var dblValue = Number(value);
			  if(dblValue > MAX_VALUE){
				  jq('#fvlm2_' + counter).addClass( "isa_error" );
			  }else{
				  sum += Number(value);
				  jq('#fvlm2_' + counter).removeClass( "isa_error" );
			  }
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#helmla').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  
  }
  //ADR
  function private_sumAdr() {
	  //element.id;
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  var sum = 0;
	  jq( ".clazzAdrMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#ffpoen_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  //this ADR is THE ONLY FIELD not required to block the sum with the Protect checkbox (hestl4)
	  jq('#hepoen').val(sum.toLocaleString('de-DE', { useGrouping: false }));
  }
  
  
//------------------
  // CHECK functions
  //------------------
  function checkVolumeNewLine() {
	  var MAX_VALUE = 9999.99;
	  var sum = 0;
	  var value = jq('#fvvol').val();
	  if(value!=''){
		  value = value.replace(",",".");
		  var dblValue = Number(value);
		  if(dblValue > MAX_VALUE){
			  jq('#fvvol').addClass( "isa_error" );
		  }else{
			  sum += Number(value);
			  jq('#fvvol').removeClass( "isa_error" );
		  }
	  }
  }
  function checkLmNewLine() {
	  jq('#fvlm').removeClass( "isa_error" );
	  
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  var value = jq('#fvlm').val();
	  if(value!=''){
		  value = value.replace(",",".");
		  var dblValue = Number(value);
		  if(dblValue > MAX_VALUE){
			  jq('#fvlm').addClass( "isa_error" );
		  }else{
			  sum += Number(value);
			  jq('#fvlm').removeClass( "isa_error" );
			  //AUTO FILL cousin field
			  if(jq('#fvlm2').val() == ""){
				  jq('#fvlm2').val(jq('#fvlm').val());
			  }
		  }
	  }
  }
  function checkLm2NewLine() {
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  var value = jq('#fvlm2').val();
	  if(value!=''){
		  value = value.replace(",",".");
		  var dblValue = Number(value);
		  if(dblValue > MAX_VALUE){
			  jq('#fvlm2').addClass( "isa_error" );
		  }else{
			  sum += Number(value);
			  jq('#fvlm2').removeClass( "isa_error" );
		  }
	  }else{
		  //AUTO FILL cousin field
		  if(jq('#fvlm2').val() == ""){
			  jq('#fvlm2').val(jq('#fvlm').val());
		  }
	  }
  }
  function checkHem3() {
	  var MAX_VALUE = 9999.999;
	  var sum = 0;
	  var value = jq('#hem3').val();
	  if(value!=''){
		  value = value.replace(",",".");
		  var dblValue = Number(value);
		  if(dblValue > MAX_VALUE){
			  jq('#hem3').addClass( "isa_error" );
		  }else{
			  sum += Number(value);
			  jq('#hem3').removeClass( "isa_error" );
		  }
	  }
  }
  function checkHelm() {
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  var value = jq('#helm').val();
	  if(value!=''){
		  value = value.replace(",",".");
		  var dblValue = Number(value);
		  if(dblValue > MAX_VALUE){
			  jq('#helm').addClass( "isa_error" );
		  }else{
			  sum += Number(value);
			  jq('#helm').removeClass( "isa_error" );
		  }
	  }
  }
  function checkHelmla() {
	  var MAX_VALUE = 99.99;
	  var sum = 0;
	  var value = jq('#helmla').val();
	  if(value!=''){
		  value = value.replace(",",".");
		  var dblValue = Number(value);
		  if(dblValue > MAX_VALUE){
			  jq('#helmla').addClass( "isa_error" );
		  }else{
			  sum += Number(value);
			  jq('#helmla').removeClass( "isa_error" );
		  }
	  }
  }
  
  /**
   * Get item line
   * @returns
   */
  function getItemData(element) {
	  var id = element.id;
	  var lineNr = id.replace("recordUpdate_", ""); 
	  
	  //sycgip/TJGE22R.pgm?user=OSCAR&unik=10001201&reff=TARZAN%20X&fbn=1&lin=1
	  var requestString = "user=" + jq('#applicationUser').val() + "&unik=" + jq('#heunik').val() + 
	  	"&reff=" + jq('#hereff').val() + "&fbn=1" + "&lin=" +  lineNr;
	  	 
	  jq.ajax({
	  	  type: 'GET',
	  	  url: 'getSpecificTopicItemChosenFromGuiElement_Ebooking.do',
	  	  data: { applicationUser : jq('#applicationUser').val(), 
	  		  	  requestString : requestString }, 
	  	  dataType: 'json',
	  	  cache: false,
	  	  contentType: 'application/json',
	  	  success: function(data) {
	  		var len = data.length;
	  		for ( var i = 0; i < len; i++) {
	  			jq('#fvlinr').val(data[i].fvlinr);
		  		jq('#fmmrk1').val(data[i].fmmrk1);
		  		jq('#fvant').val(data[i].fvant);
		  		jq('#fvpakn').val(data[i].fvpakn);
		  		jq('#fvvt').val(data[i].fvvt);
		  		jq('#fvvkt').val(data[i].fvvkt);
		  		jq('#fvlen').val(data[i].fvlen);
		  		jq('#fvbrd').val(data[i].fvbrd);
		  		jq('#fvhoy').val(data[i].fvhoy);
		  		jq('#fvvol').val(data[i].fvvol);
		  		jq('#fvlm').val(data[i].fvlm);
		  		
		  		//farlig gods
		  		jq('#ffunnr').val(data[i].ffunnr);
		  		jq('#ffembg').val(data[i].ffembg);
		  		jq('#ffindx').val(data[i].ffindx);
		  		jq('#ffantk').val(data[i].ffantk);
		  		jq('#ffante').val(data[i].ffante);
		  		jq('#ffenh').val(data[i].ffenh);
		  		
		  		//cursor on line please
		  		jq('#fvant').focus();
	  		}
	  	  },
	  	  error: function() {
		  	    alert('Error loading ...');
	    
	  	  }
	  });
	  
	  
  }
  
  //--------------------------
  //Add Item line (in Order)
  //--------------------------
  function updateItemLine() {
	  /* OBSOLETE: Moved to the form post
	   
	  //[1] Validate new line first
	  //if(private_validateNewItemLine()){
	  
	  //default new line nr and mode=A (Add)
	  var lineNr = Number(jq('#upperCurrentItemlineNr').val()) + 1;  	
	  var mode = "A";
	  //check if this is an update 
	  if(jq('#lineNrForUpdateFvlinr').val()!=''){
		  lineNr = jq('#lineNrForUpdateFvlinr').val();
		  mode = "U";
	  }
	  //Build the request string here (create new/update line)
	  //TJGE23R.pgm?user=OSCAR&unik=75&reff=11&fbn=1&lin=2&mode=A/U&fvant=11&fvvkt=15
	  var requestString = "user=" + jq('#applicationUser').val() + "&unik=" + jq('#heunik').val() + "&reff=" + jq('#hereff').val() + 
	  					 "&fbn=1" + "&lin=" +  lineNr + "&mode=" + mode +
	  					 "&fmmrk1=" + jq('#fmmrk1').val() + "&fvant=" +  jq('#fvant').val() + "&fvpakn=" +  jq('#fvpakn').val() +	
	  					 "&fvvt=" + jq('#fvvt').val() + "&fvvkt=" +  jq('#fvvkt').val() + "&fvlen=" +  jq('#fvlen').val() +
	  					 "&fvbrd=" + jq('#fvbrd').val() + "&fvhoy=" +  jq('#fvhoy').val() + "&fvvol=" +  jq('#fvvol').val() +
	  					 "&fvlm=" + jq('#fvlm').val() + "&fvlm2=" +  jq('#fvlm2').val() + "&ffunnr=" +  jq('#ffunnr').val() +
	  					 "&ffembg=" + jq('#ffembg').val() + jq('#ffindx').val() + "&ffantk=" +  jq('#ffantk').val() + "&ffante=" +  jq('#ffante').val() +
	  					 "&ffenh=" + jq('#ffenh').val();
	  
	  var ant = jq('#fvant').val();
	  var weight = jq('#fvvkt').val();
	  var description = jq('#fvvt').val();
	  //mandatory fields
	  if ( ant!='' && weight!='' && description !='' ){
		  //-----------------------------
		  //[1] add line impl.
		  //-----------------------------
		  jq.ajax({
		  	  type: 'GET',
		  	  url: 'updateOrderDetailLine_Ebooking.do',
		  	  data: { applicationUser : jq('#applicationUser').val(), 
		  		  	  requestString : requestString }, 
		  	  dataType: 'json',
		  	  cache: false,
		  	  contentType: 'application/json',
		  	  success: function(data) {
		  		var len = data.length;
		  		for ( var i = 0; i < len; i++) {
		  			//we send the redirect after a successfull creation in order to refresh...
		  			//success code = 1
		  			if(data[i].fvlinr=='1'){
		  				//jq('#submit').click(); //doUpdate
		  				//doFind on redirect...with extra order line total fields in order to update them there...
		  				//Note: the reason for updating the totals in doFind is because no session object is possible to hand in an ajax call.
		  				//For the above reason we then use the doFind method in order to get the whole record and update its total fields once there.
		  				//There is no other way to do the update without breaking the Ajax design in Spring and good healthy session handling in the web infrastructure
			  			//OBSOLETE window.location = "ebooking_mainorder.do?action=doFetch&heunik=" + jq('#heunik').val() + "&hereff=" + jq('#hereff').val() + "&oltotals=" + orderLinesTotalString + "&status=" + jq('#status').val();
		  				window.location = "ebooking_mainorder.do?action=doFetch&heunik=" + jq('#heunik').val() + "&hereff=" + jq('#hereff').val() + "&status=" + jq('#status').val();
		  			}else{
		  				alert("[ERROR] when creating the order line...?");
		  			}
		  		}
		  	  },
		  	  error: function() {
			  	    alert('Error loading ...');
		    
		  	  }
		  });
		  
	  }else{
		  alert("[ERROR] missing mandatory fields for new line...");
	  }
  	  */
  
  }
  
  
  //----------------------------------
  //Add Item line (in existent Order)
  //----------------------------------
  function validateItemLineExtensionLmLma(element) { 
	  var id = element.id;
	  var record = id.split('_');
	  var lineNr = record[1];
	  if(record[0].startsWith('fvlm')){
		  if(jq('#fvlm_' + lineNr).val()==''){
			  validateItemLine(lineNr);  
		  }
	  }
	  if(record[0].startsWith('fvlm2')){
		  if(jq('#fvlm2_' + lineNr).val()==''){
			  validateItemLine(lineNr);  
		  }
	  }
	  
  }
  function validateItemLine(lineNr) {
	  var retval = true;
	  var i = Number(lineNr);
	  //Build the request string here (new line)
	  //user=JOVO&avd=75&opd=19&fmmrk1=&fvant=1&fvpakn=&fvvt=TEST&fvvkt=1000&fvvol=&fvlm=&fvlm2=&fvlen=&fvbrd=&fvhoy=&ffunnr=1234&ffemb=&ffantk=1&ffante=1&ffenh=KGM
	  var requestString = "user=" + jq('#applicationUser').val() + "&avd=" + jq('#heavd').val() + "&opdtyp=" + jq('#heot').val() +
			 "&opd=" + jq('#heopd').val() + "&fmmrk1=" + jq('#fmmrk1_' + i).val() + "&fvant=" +  jq('#fvant_' + i).val() + "&fvpakn=" +  jq('#fvpakn_' + i).val() +	
			 "&fvvt=" + jq('#fvvt_' + i).val() + "&fvvkt=" +  jq('#fvvkt_' + i).val() + "&fvlen=" +  jq('#fvlen_' + i).val() +
			 "&fvbrd=" + jq('#fvbrd_' + i).val() + "&fvhoy=" +  jq('#fvhoy_' + i).val() + "&fvvol=" +  jq('#fvvol_' + i).val() +
			 "&fvlm=" + jq('#fvlm_' + i).val() + "&fvlm2=" +  jq('#fvlm2_' + i).val() + "&ffunnr=" +  jq('#ffunnr_' + i).val() +
			 "&ffembg=" + jq('#ffembg_' + i).val() + "&ffindx=" + jq('#ffindx_' + i).val() + "&ffantk=" +  jq('#ffantk_' + i).val() + "&ffante=" +  jq('#ffante_' + i).val() +
			 "&ffenh=" + jq('#ffenh_' + i).val();
	  //alert(requestString);
	  //mandatory fields
	  var ant = jq('#fvant_' + i).val(); var weight = jq('#fvvkt_' + i).val(); var description = jq('#fvvt_' + i).val();
	  if ( ant!='' && weight!='' && description !='' ){
		  //Only for existent orders.New orders are handled in the reflection mechanism at the controller
		  var lineNrParam = lineNr;
		  if(jq('#fvlinr_' + i).val()==''){ lineNrParam = null; }
		  //if(jq('#heopd').val()!=''){
			  jq.ajax({
			  	  type: 'GET',
			  	  url: 'validateCurrentOrderDetailLine_Ebooking.do',
			  	  data: { applicationUser : jq('#applicationUser').val(), 
			  		  	  requestString : requestString,
			  		  	  lineNr : lineNrParam },
			  	  dataType: 'json',
			  	  cache: false,
			  	  contentType: 'application/json',
			  	  success: function(data) {
			  		var len = data.length;
			  		for ( var j = 0; j < len; j++) {
			  			//we send the redirect after a successfull creation in order to refresh...
			  			//success code = 1
			  			if(data[j].errMsg!=null && data[j].errMsg!=''){
			  				//alert(data[i].errMsg);
			  				var errorPrefix = "[ERROR] FATAL on line:" + lineNr;
				  			jq('#orderLineErrMsgPlaceHolder').text(errorPrefix + " -->" + data[j].errMsg);
				  			jq('#fvvkt_' + j).focus(); //always to weight column otherwise we lose control
				  			retval = false;
			  			}else{
			  				jq('#orderLineErrMsgPlaceHolder').text("");
			  				//here we take care of the parameters that will complete some values
			  				if(jq('#fvlm_' + i).val()==''){ jq('#fvlm_' + i).val(data[j].fvlm); }
			  				if(jq('#fvlm2_' + i).val()==''){ jq('#fvlm2_' + i).val(data[j].fvlm2); }
			  				//trigger local function to keep in sync the math
			  				sumLm();sumLmla();
			  			}	
			  		}
			  		
			  	  },
			  	  error: function() {
				  	    alert('Error loading ...');
				  	    retval = false;
			  	  }
			  });
		  //}
	  }else{
		  var fvlinrExists = jq('#fvlinr_' + i).val(); 
		  if(fvlinrExists!='' || (ant!='' || weight!='' || description !='')  ){
			  alert("[Linje ERROR] Antall, Vareslag, Vekt er obligatoriske");
			  if(ant==''){
				  jq('#fvant_' + i).focus();  
			  }else if (description==''){
				  jq('#fvvt_' + i).focus();
			  }else if (weight==''){
				  jq('#fvvkt_' + i).focus();
			  }
			  retval = false;
		  }
	  }
	  return retval;
	  
  }
//----------------------------------
  //NEW Item line
  //----------------------------------
  function validateNewItemLine() {
	  var retval = false;
	  //Build the request string here (new line)
	  //user=JOVO&avd=75&opd=19&fmmrk1=&fvant=1&fvpakn=&fvvt=TEST&fvvkt=1000&fvvol=&fvlm=&fvlm2=&fvlen=&fvbrd=&fvhoy=&ffunnr=1234&ffemb=&ffantk=1&ffante=1&ffenh=KGM
	  var requestString = "user=" + jq('#applicationUser').val() + "&avd=" + jq('#heavd').val() + "&opdtyp=" + jq('#heot').val() +
			 "&opd=" + jq('#heopd').val() + "&fmmrk1=" + jq('#fmmrk1').val() + "&fvant=" +  jq('#fvant').val() + "&fvpakn=" +  jq('#fvpakn').val() +	
			 "&fvvt=" + jq('#fvvt').val() + "&fvvkt=" +  jq('#fvvkt').val() + "&fvlen=" +  jq('#fvlen').val() +
			 "&fvbrd=" + jq('#fvbrd').val() + "&fvhoy=" +  jq('#fvhoy').val() + "&fvvol=" +  jq('#fvvol').val() +
			 "&fvlm=" + jq('#fvlm').val() + "&fvlm2=" +  jq('#fvlm2').val() + "&ffunnr=" +  jq('#ffunnr').val() +
			 "&ffembg=" + jq('#ffembg').val() + "&ffindx=" + jq('#ffindx').val() + "&ffantk=" +  jq('#ffantk').val() + "&ffante=" +  jq('#ffante').val() +
			 "&ffenh=" + jq('#ffenh').val();
	  //alert(requestString);
	  //mandatory fields
	  var ant = jq('#fvant').val(); var weight = jq('#fvvkt').val(); var description = jq('#fvvt').val();
	  if ( ant!='' && weight!='' && description !='' ){
		  jq.ajax({
		  	  type: 'GET',
		  	  url: 'validateCurrentOrderDetailLine_Ebooking.do',
		  	  data: { applicationUser : jq('#applicationUser').val(), 
		  		  	  requestString : requestString,
		  		  	  lineNr : null },
		  	  dataType: 'json',
		  	  cache: false,
		  	  contentType: 'application/json',
		  	  async: false, //only way to make synchronous calls. Otherwise will all ajax dependent functions will execute asynchronously
		  	  success: function(data) {
		  		var len = data.length;
		  		for ( var j = 0; j < len; j++) {
		  			//we send the redirect after a successfull creation in order to refresh...
		  			//success code = 1
		  			if(data[j].errMsg!=null && data[j].errMsg!=''){
		  				//alert(data[i].errMsg);
		  				var errorPrefix = "[ERROR] FATAL on new line:";
			  			jq('#orderLineErrMsgPlaceHolder').text(errorPrefix + " -->" + data[j].errMsg);
			  			jq('#fvvkt').focus(); //always to weight column otherwise we lose control
		  			}else{
		  				jq('#orderLineErrMsgPlaceHolder').text("");
		  				//here we take care of the parameters that will complete some values
		  				if(jq('#fvlm').val()==''){ jq('#fvlm').val(data[j].fvlm); }
		  				if(jq('#fvlm2').val()==''){ jq('#fvlm2').val(data[j].fvlm2); }
		  				//trigger local function to keep in sync the math
		  				retval = true;
		  						  				
		  			}	
		  		}
		  	  },
		  	  error: function() {
			  	  alert('Error loading ...');
		  	  }
		  });
	  }else{
		  alert("[ERROR] missing mandatory fields for new line...");
		  if(ant==''){
			  jq('#fvant').focus();  
		  }else if (weight==''){
			  jq('#fvvkt').focus();
		  }else if (description==''){
			  jq('#fvvt').focus();
		  }
	  }
	  return retval;
  }
  
  
  
  //-------------------------------------------------------
  //Dangerous goods child window (is triggered from jsp)
  //-------------------------------------------------------
  function searchDangerousGoods(element) {
	  var id = element.id;
	  var record = id.split('_');
	  var i = record[1]; 
	  //alert(jq('#ffunnr_' + counter).val());
	  jq(id).attr('target','_blank');
  	  window.open('ebooking_childwindow_dangerousgoods.do?action=doFind&unnr=' + jq("#ffunnr_" + i).val() + 
  			  '&embg=' + jq("#ffembg_" + i).val() + '&indx=' + jq("#ffindx_" + i).val() + '&callerLineCounter=' + i, 
  			  "dangerousgoodsWin", "top=300px,left=450px,height=600px,width=800px,scrollbars=no,status=no,location=no");
  }
  
  function searchDangerousGoodsNewLine(element) {
	  jq(element.id).attr('target','_blank');
  	  window.open('ebooking_childwindow_dangerousgoods.do?action=doFind&unnr=' + jq("#ffunnr").val() + 
  			  '&embg=' + jq("#ffembg").val() + '&indx=' + jq("#ffindx").val() + '&callerLineCounter=', 
  			  "dangerousgoodsWin", "top=300px,left=450px,height=600px,width=800px,scrollbars=no,status=no,location=no");
  }
  
  
  //--------------------------------------------------------------
  //Dangerous goods validation in order to demand the indx or not
  //--------------------------------------------------------------
  function validateDangerousGoodsUnnr(lineNr) {
	  var counter = Number(lineNr);
	  var keyUnnr =jq("#ffunnr_" + counter).val();
	  if(keyUnnr!=""){
		  if(jq("#ffembg_" + counter).val()=="?" ){
			  jq("#ffembg_" + counter).val("");
		  }
		  if(jq("#ffindx_" + counter).val()=="?" ){
			  jq("#ffindx_" + counter).val("");
		  }
		  
		  jq.ajax({
		  	  type: 'GET',
		  	  url: 'searchDangerousGoods_Ebooking.do',
		  	  data: { applicationUser : jq('#applicationUser').val(),
			  		  unnr : jq("#ffunnr_" + counter).val(),
		  		  	  embg : jq("#ffembg_" + counter).val() ,
		  		  	  indx : jq("#ffindx_" + counter).val()  },
		  	  dataType: 'json',
		  	  cache: false,
		  	  contentType: 'application/json',
		  	  success: function(data) {
		  		var len = data.length;
		  		for ( var i = 0; i < len; i++) {
		  			if(len>1){
		  				if(jq("#ffembg_" + counter).val()==''){ 
		  					//jq("#ffembg_" + counter).val("?");
		  					jq("#ffembg_" + counter).addClass( "isa_warning" );
	  					}
		  				//jq("#ffindx_" + counter).val("?");
		  				jq("#ffindx_" + counter).addClass( "isa_warning" );
		  				jq("#ffunnr_" + counter).removeClass( "isa_error" );
		  				jq("#ffpoen_" + counter).val("");
		  				
		  			}else if (len==1){
		  				jq("#ffunnr_" + counter).val(data[i].adunnr);
		  				jq("#ffembg_" + counter).val(data[i].adembg);
		  				jq("#ffindx_" + counter).val(data[i].adindx);
		  				//[1] ADR->Update line and line ADR
		  				if(jq("#ffante_" + counter).val()!='' && jq("#ffante_" + counter).val()!='?'){
		  					//var unit = parseInt(jq("#ffante_" + counter).val()); //OBSOLETE -->ffante as Integer
		  					var unitStr = jq("#ffante_" + counter).val();
		  					unitStr = unitStr.replace(",",".");
		  					var unit = Number(unitStr);
		  					var fakt = parseInt(data[i].adfakt);
		  					if(jq("#ffantk_" + counter).val()!='' && jq("#ffantk_" + counter).val()!='?' && jq("#ffenh_" + counter).val()!=''){
		  						jq("#ffpoen_" + counter).val(unit * fakt);
			  					//cosmetics
			  					jq("#ffantk_" + counter).removeClass( "isa_warning" );
			  					jq("#ffante_" + counter).removeClass( "isa_warning" );
		  					}else{
		  						jq("#ffpoen_" + counter).val("");
		  						if(jq("#ffantk_" + counter).val()==''){
		  							//jq("#ffantk_" + counter).val("?");
		  							jq("#ffantk_" + counter).addClass( "isa_warning" );
		  						}
		  					}
		  					
		  				}else{
		  					jq("#ffpoen_" + counter).val("");
		  					//jq("#ffante_" + counter).val("?");
	  						jq("#ffante_" + counter).addClass( "isa_warning" );
		  				}
		  				//[2] ADR->Update always total ADR to keep it in sync
	  					private_sumAdr();
	  					
		  				//cosmetics
		  				jq("#ffunnr_" + counter).removeClass( "isa_error" );
		  				jq("#ffembg_" + counter).removeClass( "isa_error isa_warning" );
		  				jq("#ffindx_" + counter).removeClass( "isa_error isa_warning" );
		  			}
		  		}
		  		//if invalid number acknowledge this...
		  		if(len<=0){
		  			//cosmetics
	  				jq("#ffunnr_" + counter).addClass( "isa_error" );
	  				if(jq("#ffembg_" + counter).val()!='') { jq("#ffembg_" + counter).addClass( "isa_error" ); }
	  				if(jq("#ffindx_" + counter).val()!='') { jq("#ffindx_" + counter).addClass( "isa_error" ); }
	  				jq("#ffpoen_" + counter).val("");
	  			}
		  	  },
		  	  error: function() {
			  	    alert('Error loading on Ajax callback (?)...check js');
		  	  }
		  });
		  
	  }else{
		  jq("#ffunnr_" + counter).val("");jq("#ffembg_" + counter).val("");jq("#ffindx_" + counter).val("");
		  jq("#ffantk_" + counter).val("");jq("#ffante_" + counter).val("");jq("#ffenh_" + counter).val("");
		  jq("#ffpoen_" + counter).val("");
		  //cosmetics
		  jq("#ffunnr_" + counter).removeClass( "isa_error" );
		  jq("#ffembg_" + counter).removeClass( "isa_error isa_warning" );jq("#ffindx_" + counter).removeClass( "isa_error isa_warning" );
		  jq("#ffantk_" + counter).removeClass( "isa_error isa_warning" );jq("#ffante_" + counter).removeClass( "isa_error isa_warning" );
		  jq("#ffenh_" + counter).removeClass( "isa_error isa_warning" );
	  }
  }
  
//--------------------------------------------------------------
  //Dangerous goods validation in order to demand the indx or not
  //--------------------------------------------------------------
  function validateDangerousGoodsUnnrNewLine() {
	  var keyUnnr =jq("#ffunnr").val();
	  if(keyUnnr!=""){
		  if(jq("#ffembg").val()=="?" ){
			  jq("#ffembg").val("");
		  }
		  if(jq("#ffindx").val()=="?" ){
			  jq("#ffindx").val("");
		  }
		  jq.ajax({
		  	  type: 'GET',
		  	  url: 'searchDangerousGoods_Ebooking.do',
		  	  data: { applicationUser : jq('#applicationUser').val(),
			  		  unnr : jq("#ffunnr").val(),
		  		  	  embg : jq("#ffembg").val() ,
		  		  	  indx : jq("#ffindx").val()  },
		  	  dataType: 'json',
		  	  cache: false,
		  	  contentType: 'application/json',
		  	  success: function(data) {
		  		var len = data.length;
		  		for ( var i = 0; i < len; i++) {
		  			if(len>1){
		  				if(jq("#ffembg").val()==''){ 
		  					//jq("#ffembg").val("?");
		  					jq("#ffembg").addClass( "isa_warning" );
	  					}
		  				//jq("#ffindx").val("?");
		  				jq("#ffindx").addClass( "isa_warning" );
		  				jq("#ffunnr").removeClass( "isa_error" );
		  				
		  			}else if (len==1){
		  				jq("#ffunnr").val(data[i].adunnr);
		  				jq("#ffembg").val(data[i].adembg);
		  				jq("#ffindx").val(data[i].adindx);
		  				//[1] ADR->get the ADR factor
		  				if(jq("#ffante").val()!='' && jq("#ffante").val()!='?'){
		  					if(jq("#ffantk").val()!='' && jq("#ffantk").val()!='?' && jq("#ffenh").val()!=''){
		  						jq("#ownAdrFaktNewLine").val(data[i].adfakt);
			  					//TODO Tentative ?
		  						//var hepoen = Number(jq("#hepoen").val());
			  					//hepoen = hepoen + (unit * fakt);
			  					//Update total ADR. Note: notice that this is the only update in ADR-Total. When NEW LINE...
			  					//jq("hepoen").val(hepoen);
			  					
			  					//cosmetics
			  					jq("#ffantk").removeClass( "isa_warning" );
			  					jq("#ffante").removeClass( "isa_warning" );
		  					}else{
		  						jq("#ownAdrFaktNewLine").val("");
		  						if(jq("#ffantk").val()==''){
		  							//jq("#ffantk").val("?");
		  							jq("#ffantk").addClass( "isa_warning" );
		  						}
		  					}
		  					
		  				}else{
		  					jq("#ownAdrFaktNewLine").val("");
		  					//jq("#ffante").val("?");
	  						jq("#ffante").addClass( "isa_warning" );
		  				}
		  				//cosmetics
		  				jq("#ffunnr").removeClass( "isa_error" );
		  				jq("#ffembg").removeClass( "isa_error isa_warning" );
		  				jq("#ffindx").removeClass( "isa_error isa_warning" );
		  			}
		  		}
		  		//if invalid number acknowledge this...
		  		if(len<=0){
		  			//cosmetics
	  				jq("#ffunnr").addClass( "isa_error" );
	  				if(jq("#ffembg").val()!='') { jq("#ffembg").addClass( "isa_error" ); }
	  				if(jq("#ffindx").val()!='') { jq("#ffindx").addClass( "isa_error" ); }
	  				jq("#ownAdrFaktNewLine").val("");
	  			}
		  	  },
		  	  error: function() {
			  	    alert('Error loading on Ajax callback (?)...check js');
		  	  }
		  });
		  
	  }else{
		  jq("#ffunnr").val("");jq("#ffembg").val("");jq("#ffindx").val("");
		  jq("#ffantk").val("");jq("#ffante").val("");jq("#ffenh").val("");
		  //cosmetics
		  jq("#ffunnr").removeClass( "isa_error" );
		  jq("#ffembg").removeClass( "isa_error isa_warning" );jq("#ffindx").removeClass( "isa_error isa_warning" );
		  jq("#ffantk").removeClass( "isa_error isa_warning" );jq("#ffante").removeClass( "isa_error isa_warning" );
		  jq("#ffenh").removeClass( "isa_error isa_warning" );
		  
	  }
	  
  }
  
  //------------------
  //DELETE order line
  //------------------
  function deleteItemLine(element){
	  var id = element.id;
	  var record = id.split('_');
	  var counter = Number(record[1]); 
	  var r = confirm("Are you sure you want to remove this item line?");
	  if (r == true){
		  updateOrderLineTotalsBeforeDelete(counter);
		  var params = "heunik=" + jq('#heunik').val() + "&hereff=" + jq('#hereff').val() + jq('#status').val() + 
						"&lin=" + jq('#fvlinr_' + counter).val() + "&hent=" + jq('#hent').val() + "&hevkt=" + jq('#hevkt').val() + 
						"&hem3=" + jq('#hem3').val() + "&helm=" + jq('#helm').val() + "&helmla=" + jq('#helmla').val() + "&hepoen=" + jq('#hepoen').val();
		  				//append the protect checkbox value (if applicable)
						if(jq('#hestl4').prop('checked')){ params += "&hestl4=" + jq('#hestl4').val();}
						else{ params += "&hestl4="; }
						//shoot now to the controller!
						window.location = "ebooking_delete_order_line.do?" + params;
	  }else{
		  //nothing
	  }
  }
  
//--------------------------------
  // UPDATE before DELETE ORDER LINE
  //--------------------------------
  function updateOrderLineTotalsBeforeDelete(counter_delete) { 
	  /*
      //Antall
	  var sum = 0;
	  jq( ".clazzAntMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvant_' + counter).val();
		  if(value!='' && counter!=counter_delete){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#hent').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //Weight
	  var sum = 0;
	  jq( ".clazzWeightMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvvkt_' + counter).val();
		  if(value!='' && counter!=counter_delete){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#hevkt').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //M3
	  var sum = 0;
	  jq( ".clazzVolMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvvol_' + counter).val();
		  if(value!='' && counter!=counter_delete){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#hem3').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //LM
	  var sum = 0;
	  jq( ".clazzLmMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvlm_' + counter).val();
		  if(value!='' && counter!=counter_delete){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#helm').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //LM-la
	  var sum = 0;
	  jq( ".clazzLmlaMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvlm2_' + counter).val();
		  if(value!='' && counter!=counter_delete){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  jq('#helmla').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //ADR
	  var sum = 0;
	  jq( ".clazzAdrMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#ffpoen_' + counter).val();
		  if(value!='' && counter!=counter_delete){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  //this ADR-field in NOT REQUIRED to be blocked by Protected checkbox: hestl4
	  jq("#hepoen").attr("readonly", false); 
	  jq('#hepoen').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  */
  }
  
//-----------------------------
  //UPDATE before ADD ORDER LINE
  //-----------------------------
  function updateOrderLineTotalsBeforeAdd() { 
	  /*
      //Antall
	  var sum = 0;
	  jq( ".clazzAntMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvant_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  var fvant = jq('#fvant').val();
		  if(fvant!=''){ fvant = fvant.replace(",","."); sum += Number(fvant); }
		  jq('#hent').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //Weight
	  var sum = 0;
	  jq( ".clazzWeightMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvvkt_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  var fvvkt = jq('#fvvkt').val();
		  if(fvvkt!=''){ fvvkt = fvvkt.replace(",","."); sum += Number(fvvkt); }
		  jq('#hevkt').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //M3
	  var sum = 0;
	  jq( ".clazzVolMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvvol_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  var fvvol = jq('#fvvol').val();
		  if(fvvol!=''){ fvvol = fvvol.replace(",","."); sum += Number(fvvol); }
		  jq('#hem3').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //LM
	  var sum = 0;
	  jq( ".clazzLmMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvlm_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  var fvlm = jq('#fvlm').val();
		  if(fvlm!=''){ fvlm = fvlm.replace(",","."); sum += Number(fvlm); }
		  jq('#helm').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //LM-la
	  var sum = 0;
	  jq( ".clazzLmlaMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#fvlm2_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  if(!jq('#hestl4').prop('checked')){
		  var fvlm2 = jq('#fvlm2').val();
		  if(fvlm2!=''){ fvlm2 = fvlm2.replace(",","."); sum += Number(fvlm2); }
		  jq('#helmla').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  }
	  //ADR
	  var sum = 0;
	  jq( ".clazzAdrMathAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var value = jq('#ffpoen_' + counter).val();
		  if(value!=''){
			  value = value.replace(",",".");
			  sum += Number(value);
		  }
	  });
	  //Update the ADR-sum with
	  /* OLD With ffante as Integer
	  var unitStr = jq('#ffante').val();
	  var faktStr = jq('#ownAdrFaktNewLine').val();
	  if(unitStr!='' && faktStr!=''){
		  unit = parseInt(unitStr);
		  fakt = parseInt(faktStr);
		  sum += (unit*fakt);
	  }*/
	  
	  /*
	  var unitStr = jq('#ffante').val();
	  var faktStr = jq('#ownAdrFaktNewLine').val();
	  if(unitStr!='' && faktStr!=''){
		  unitStr = unitStr.replace(",",".");
		  unit = Number(unitStr);
		  fakt = parseInt(faktStr);
		  sum += (unit*fakt);
	  }
	  
	  //ADR field NOT REQUIRED to be blocked by checkbox: hestl4
	  jq("#hepoen").attr("readonly", false); 
	  jq('#hepoen').val(sum.toLocaleString('de-DE', { useGrouping: false }));
	  */
  }
  
  //UPDATE before SUBMIT Vareslag - TOT
  //Usually if the field hasn't been populated earlier by other jQuery functions on Vareslag
  function private_isSingleOrderLine() {
	  //element.id;
	  var sum = 0;
	  var isTrue = true;
	  jq( ".clazzVareslagAware" ).each(function( i ) {
		  var id = this.id;
		  var counter = i + 1;
		  var fvant = jq('#fvant_' + counter).val();
		  var fvvt = jq('#fvvt_' + counter).val();
		  var fvvkt = jq('#fvvkt_' + counter).val();
		  
		  if(counter>1 && (fvant!='' && fvvt!='' && fvvkt!='')){
			 isTrue = false;  
		  }
		  
	  });
	  
	  return isTrue;
	  
  }
  
//-------------------
  //Datatables jquery
  //-------------------
  //private function
  function filterGlobal () {
    jq('#tblItemLines').dataTable().search(
    	jq('#tblItemLines_filter').val()
    ).draw();
  }
  jq(document).ready(function(){
	  var lang = jq('#language').val(); 
	  //init table (no ajax, no columns since the payload is already there by means of HTML produced on the back-end)
      jq('#tblItemLines').dataTable( {
    	  "dom": '<"top">t<"bottom"flip><"clear">',
    	  "scrollY":    "180px",
  		  "scrollCollapse":  true,
  		  "columnDefs": [{ "type": "num", "targets": 0 }],
  		  "lengthMenu": [ 100, 300, 400, 900],
  		  //"paging" : false,
  		  "info" : false,
  		  "language": { "url": getLanguage(lang) }
  	  });
      
      //event on input field for search
      jq('input.tblItemLines_filter').on( 'keyup click', function () {
      		filterGlobal();
      });
	      
	});
	
  //TEXTAREA jQuery function to limit the number of characters per line
  //and per textarea
  //messageNoteConsignee
  jq(function() {
	    var limit = function (event) {
        var linha = jq(this).attr("limit").split(",")[0];
        var coluna = jq(this).attr("limit").split(",")[1];

        var array = jq(this)
            .val()
            .split("\n");

        jq.each(array, function (i, value) {
            array[i] = value.slice(0, linha);
        });

        if (array.length >= coluna) {
            array = array.slice(0, coluna);
        }

        jq(this).val(array.join("\n"))

	    }
	    //function call
	    jq("#messageNoteConsignee[limit]")
	        .keydown(limit)
	        .keyup(limit);
  });
  //messageNoteCarrier
  jq(function() {
	    var limit = function (event) {
      var linha = jq(this).attr("limit").split(",")[0];
      var coluna = jq(this).attr("limit").split(",")[1];

      var array = jq(this)
          .val()
          .split("\n");

      jq.each(array, function (i, value) {
          array[i] = value.slice(0, linha);
      });

      if (array.length >= coluna) {
          array = array.slice(0, coluna);
      }

      jq(this).val(array.join("\n"))

	    }
	   
	    jq("#messageNoteCarrier[limit]")
	    .keydown(limit)
	    .keyup(limit);
	   
  });
  //messageNoteInternal
  jq(function() {
	  var limit = function (event) {
      var linha = jq(this).attr("limit").split(",")[0];
      var coluna = jq(this).attr("limit").split(",")[1];

      var array = jq(this)
          .val()
          .split("\n");

      jq.each(array, function (i, value) {
          array[i] = value.slice(0, linha);
      });

      if (array.length >= coluna) {
          array = array.slice(0, coluna);
      }

      jq(this).val(array.join("\n"))

	  }
	  
	  jq("#messageNoteInternal[limit]")
      .keydown(limit)
      .keyup(limit);

  });
//END TEXTAREA
  
  