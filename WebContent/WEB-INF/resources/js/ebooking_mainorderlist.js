  //this variable is a global jQuery var instead of using "$" all the time. Very handy
  var jq = jQuery.noConflict();
  var counterIndex = 0;
  var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Please wait...";

  
  function setBlockUI(element){
	  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
  }
  
  function printDocument(element){
	  var id = element.id;
	  var record = id.split('_');
	  var prefix = record[0];
	  var unikId = record[1]; 
	  //alert(unikId);
	  
	  //jq(id).attr('target','_blank');  //not needed in order to avoid strange behavior in non-Chrome browsers... (example: Firefox pop-up blank tab in addition of the PDF tab ??)
	  var userIP = jq("#userHttpJQueryDocRoot").val().replace("http://", "");
	  
	  if(prefix.indexOf("fraktbrev")>=0){
		  var link = jq("#userHttpJQueryDocRoot").val() + '/sycgip/esop11fb.pgm?user=' + jq("#applicationUser").val() + '&curtur=' + unikId + '&UserIP=' + userIP;
		  //alert(link);
		  window.open(link, "printDocWinFb", "top=300px,left=50px,height=800px,width=900px,scrollbars=no,status=no,location=no");
	  
	  }else if(prefix.indexOf("cmr")>=0){
		  var link = jq("#userHttpJQueryDocRoot").val() + '/sycgip/esop11cm.pgm?user=' + jq("#applicationUser").val() + '&curtur=' + unikId + '&UserIP=' + userIP;
		  window.open(link, "printDocWinCm", "top=300px,left=50px,height=800px,width=900px,scrollbars=no,status=no,location=no");
	  
	  }else if(prefix.indexOf("merkPdf")>=0){
		  var link = jq("#userHttpJQueryDocRoot").val() + '/sycgip/ss115.pgm?user=' + jq("#applicationUser").val() + '&curtur=' + unikId + '&UserIP=' + userIP;
		  window.open(link + '&lay=HZ&copyprt=J&labeltyp=L', "printDocWinPf", "top=300px,left=50px,height=800px,width=900px,scrollbars=no,status=no,location=no");
	  
	  }else if(prefix.indexOf("merkZpl")>=0){
		  var link = jq("#userHttpJQueryDocRoot").val() + '/sycgip/ss115.pgm?user=' + jq("#applicationUser").val() + '&curtur=' + unikId + '&UserIP=' + userIP;
		  window.open(link + '&lay=HZ&copyprt=J&labeltyp=Z', "printDocWinMz", "top=300px,left=50px,height=800px,width=900px,scrollbars=no,status=no,location=no");
	  }
	  
	  //refresh parent window
	  setTimeout(refreshOrderListWindow, 4000);
  }
  
  function refreshOrderListWindow(){
	  window.location.reload();
  }
  
  jq(function() {
	  jq("#date").datepicker({ 
		  dateFormat: 'yymmdd'
		  //,defaultDate: "-1m"	  
	  });
	  jq("#fromDateF").datepicker({ 
		  dateFormat: 'yymmdd'  
	  });
	  jq("#fromDateT").datepicker({ 
		  dateFormat: 'yymmdd'  
	  });
	  //
	  jq("#opd").focus();
  });
  
  //----------------------------------------
  //START Model dialog "Create new order"
  //--------------------------------------
  //Initialize <div> here
  jq(function() { 
	  jq("#dialogCreateNewOrder").dialog({
		  autoOpen: false,
		  maxWidth:400,
          maxHeight: 220,
          width: 400,
          height: 220,
		  modal: true
	  });
  });
  //Present dialog box onClick (href in parent JSP)
  jq(function() {
	  jq("#createNewOrderTabIdLink").click(function() {
		  //setters (add more if needed)
		  jq('#dialogCreateNewOrder').dialog( "option", "title", "Lag ny Ordre" );
		  
		  //deal with buttons for this modal window
		  jq('#dialogCreateNewOrder').dialog({
			 buttons: [ 
	            {
				 id: "dialogSaveTU",	
				 text: "Fortsett",
				 click: function(){
					 		jq('#createNewOrderForm').submit();
				 		}
			 	 },
	 	 		{
			 	 id: "dialogCancelTU",
			 	 text: "Avbryt", 
				 click: function(){
					 		//back to initial state of form elements on modal dialog
					 		//jq("#dialogSaveSU").button("option", "disabled", true);
					 		
					 		jq( this ).dialog( "close" );
					 		jq("#opd").focus();
				 		} 
	 	 		 } ] 
		  });
		  //init values
		  //jq("#dialogSaveTU").button("option", "disabled", false);
		  //open now
		  jq('#dialogCreateNewOrder').dialog('open');
	  });
  });
  //-----------------------------
  //END Create new order - Dialog
  //-----------------------------
  
  
//---------------------------------------
  //DELETE Order
  //This is done in order to present a jquery
  //Alert modal pop-up
  //----------------------------------------
  function doPermanentlyDeleteOrder(element){
	  //start
	  var record = element.id.split('@');
	  var hereff = record[0];
	  var heunik = record[1];
	  hereff= hereff.replace("hereff_","");
	  heunik= heunik.replace("heunik_","");
	  	//Start dialog
	  	jq('<div></div>').dialog({
	        modal: true,
	        title: "Dialog - Slett Oppdrag: " + hereff,
	        buttons: {
		        Fortsett: function() {
	        		jq( this ).dialog( "close" );
		            //do delete
		            jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
		            window.location = "ebooking_mainorderlist_permanently_delete_order.do?action=doDelete" + "&heunik=" + heunik + "&hereff=" + hereff;
		        },
		        Avbryt: function() {
		            jq( this ).dialog( "close" );
		        }
	        },
	        open: function() {
		  		  var markup = "Er du sikker på at du vil slette denne?";
		          jq(this).html(markup);
		          //make Cancel the default button
		          jq(this).siblings('.ui-dialog-buttonpane').find('button:eq(1)').focus();
		     }
		});  //end dialog
  }
  
  
  
  //-------------------
  //Datatables jquery
  //-------------------
  jq(document).ready(function() {
	 var lang = jq('#language').val(); 
    //init table (no ajax, no columns since the payload is already there by means of HTML produced on the back-end)
	jq('#openOrders').dataTable( {
	  "jQueryUI": false,
	  "dom": '<"top"fli>rt<"bottom"p><"clear">',
	  "scrollY":        	"700px",
	  "scrollCollapse":  	true,
	  //"columnDefs": [{ className: "dt-head-left", "targets": [ 2 ] }],
	  "order": [[ 2, "desc" ]],
	  
	  //"autoWidth": false, //for optimization purposes when initializing the table
	  "lengthMenu": [ 50, 75, 100],
	  "language": { "url": getLanguage(lang) }
	} );
	
    //event on input field for search
    jq('input.openOrders_filter').on( 'keyup click', function () {
    		filtersInit();
    });
    
  });
  
  
  

  
  