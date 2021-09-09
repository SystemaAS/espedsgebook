<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>
<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerEbooking.jsp" />
<!-- =====================end header ==========================-->
	<%-- specific jQuery functions for this JSP (must reside under the resource map since this has been
		specified in servlet.xml as static <mvc:resources mapping="/resources/**" location="WEB-INF/resources/" order="1"/> --%>
	<SCRIPT type="text/javascript" src="resources/js/ebookingglobal_edit.js?ver=${user.versionEspedsg}"></SCRIPT>	
	<SCRIPT type="text/javascript" src="resources/js/ebooking_mainorder.js?ver=${user.versionEspedsg}"></SCRIPT>
	<%-- for dialog popup --%>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	
	<style type = "text/css">
	.ui-dialog{font-size:10pt;}
	.ui-datepicker { font-size:9pt;}
	</style>
	


<table id="topTableLocal" width="100%"  class="text11" cellspacing="0" border="0" cellpadding="0">
	<tr>
	<td>
	<%-- tab container component --%>
	<table width="100%"  class="text14" cellspacing="0" border="0" cellpadding="0">
		<tr height="2"><td></td></tr>
		<tr height="25"> 
			<td width="20%" valign="bottom" class="tabDisabled" align="center" nowrap>
				<a class="text14" href="ebooking_mainorderlist.do?action=doFind" > 	
					<img style="vertical-align:middle;" src="resources/images/bulletGreen.png" width="6px" height="6px" border="0" alt="open orders">
					<font class="tabDisabledLink">&nbsp;<spring:message code="systema.ebooking.orderlist.tab"/></font>
				</a>
				
			</td>
			<c:choose>
				<c:when test="${empty model.record.heunik}">
					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
					<td width="20%" valign="bottom" class="tab" align="center" nowrap>
						<img style="vertical-align:middle;" src="resources/images/add.png" width="12px" height="12px" border="0" alt="create new">
						<font class="tabLink"><spring:message code="systema.ebooking.createnew.order.tab"/></font>
					</td>
				</c:when>
				<c:otherwise>
					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
					<td width="20%" valign="bottom" class="tab" align="center" nowrap>
						<img style="vertical-align:middle;" src="resources/images/update.gif" width="12px" height="12px" border="0" alt="update order">
						<font class="tabLink"><spring:message code="systema.ebooking.order.tab"/></font><font class="text14">&nbsp;${model.record.hereff}</font>
						&nbsp;&nbsp;
						<div title="FileUpload" style="display:inline-block; cursor:pointer;" onClick="showDialogFileUploadDraggable();" >
							<font class="text14OrangeBold">e</font>
						</div>	
					</td>
				</c:otherwise>
			</c:choose>
			<td width="60%" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>	
		</tr>
	</table>
	</td>
	</tr>
	
	<%-- --------------------------- --%>
	<%-- Validation errors FRONT END --%>
	<%-- --------------------------- --%>
	<spring:hasBindErrors name="record"> <%-- name must equal the command object name in the Controller --%>
	<tr height="5"><td></td></tr>
	<tr>
		<td>
           	<table class="tabThinBorderWhiteWithSideBorders" width="100%" align="left" border="0" cellspacing="0" cellpadding="0">
           	<tr>
			<td valign="bottom" class="textError">					
	            <ul>
	            <c:forEach var="error" items="${errors.allErrors}">
	                <li >
	                	<spring:message code="${error.code}" text="${error.defaultMessage}"/>
	                </li>
	            </c:forEach>
	            </ul>
			</td>
			</tr>
			</table>
		</td>
	</tr>
	</spring:hasBindErrors>	
	
	<%-- -------------------------- --%>
	<%-- Validation errors BACK END --%>
	<%-- -------------------------- --%>
	<c:if test="${not empty model.containerValidationBackend && not empty model.containerValidationBackend.errMsgListFromValidationBackend}">
		<tr>
		<td>
           	<table class="tabThinBorderWhiteWithSideBorders" width="100%" align="left" border="0" cellspacing="0" cellpadding="0">
           	<tr>
			<td valign="bottom" class="textError">					
	            <ul>
	            <c:forEach var="errMsg" items="${model.containerValidationBackend.errMsgListFromValidationBackend}">
	                <li >${errMsg}</li>
	            </c:forEach>
	            </ul>
			</td>
			</tr>
			</table>
		</td>
		</tr>		
	</c:if>
	<%-- -------------------------- --%>
	<%-- Validation errors on model --%>
	<%-- -------------------------- --%>
	<c:if test="${not empty model.errorMessage}">
		<tr>
		<td>
           	<table class="tabThinBorderWhiteWithSideBorders" width="100%" align="left" border="0" cellspacing="0" cellpadding="0">
           	<tr>
			<td valign="bottom" class="textError">					
	            <ul>
	            	<li >${model.errorMessage}</li>
	            </ul>
			</td>
			</tr>
			</table>
		</td>
		</tr>		
	</c:if>
	
	<%-- ------------------------------- --%>
	<%-- Floating window for file upload --%>
	<%-- ------------------------------- --%>
		<c:if test="${not empty model.record.heunik}">
			<tr>
				<td valign="bottom" >
						<div id="dialogDraggableFileUpload" title="File Upload">
		           		<p>
		           		<table class="popupFloatingWithRoundCorners3D">
						    <tr height="2"><td></td></tr>
					    	<tr>
							<td valign="top">
							<form name="uploadFileForm" id="uploadFileForm" method="post" enctype="multipart/form-data">
								<input type="hidden" name="applicationUserUpload" id="applicationUserUpload" value='${user.user}'>
								<input type="hidden" name="wsavd" id="wsavd" value='${Xmodel.record.heavd}'>
								<input type="hidden" name="wsopd" id="wsopd" value='${Xmodel.record.heopd}'>
								<input type="hidden" name="wsunik" id="wsunik" value='${model.record.heunik}'>
								<input type="hidden" name="userDate" id="userDate" value=''>
								<input type="hidden" name="userTime" id="userTime" value=''>
								<input type="hidden" name="callerModule" id="callerModule" value='ebooking'>
								
									<table id="containerdatatableTable" cellspacing="2" align="left">
										<tr>
											<td colspan="3" class="text14Bold">&nbsp;
												<img style="vertical-align:bottom;" src="resources/images/upload.png" border="0" width="20" height="20" alt="upload">
												&nbsp;File Upload&nbsp;							
											</td>
										</tr>
										<tr>
										<tr height="5"><td></td></tr>
										<tr>
										<td>
											<table>
											
											<tr>
												<td class="text12">&nbsp;Arkiv typen:</td>
												<td class="text12">&nbsp;
													<select class="selectMediumBlueE2" tabindex=-1 name="wstype" id="wstype">
														<c:forEach var="record" items="${user.arkivKodOpdList}" >
								                       	 	<option value="${record.arkKod}">${record.arkKod}-${record.arkTxt}</option>
														</c:forEach> 
													</select>	
												</td>
											</tr>
											<tr>	
												<td class="text12">&nbsp;Fil:</td>
												<td class="text12">
					           						&nbsp;<input ondragenter="myFileUploadDragEnter(event)" ondragleave="myFileUploadDragLeave(event)" tabindex=-1 class="tableBorderWithRoundCornersLightYellow3D noFileChosenTransparent" style="width:220px;height:95px;display:block;" type="file" name="file" id="file" />
					       						</td>
							           		</tr>
							           		</table>
										</td>
										</tr>
										<tr height="5"><td></td></tr>
					       			</table>
							</form>	
							</td>
							</tr>
						</table>
						</div>
						
					  
				</td>
			</tr>
		</c:if>
	
		
		<tr>
		<td>
			<%-- this table wrapper is necessary to apply the css class with the thin border --%>
			<form action="ebooking_mainorder.do"  name="ebookingOrderForm" id="ebookingOrderForm" method="post">
			<input type="hidden" name="parentTrip" id="parentTrip" value="${model.parentTrip}">
			<table style="width:100%" id="wrapperTable" class="tabThinBorderWhite" cellspacing="0">
			<tr height="10"><td>&nbsp;</td></tr> 
			<%-- FORM HEADER --%>
	 		<tr>
            		<td>
	        			<table style="width:98%;" align="left" class="formFrameHeader" border="0" cellspacing="0" cellpadding="0">
				 		<tr height="15">
				 			<c:choose>
					 			<c:when test="${not empty model.record.heunik}">
						 			<td align="left" class="text14White">
										&nbsp;<img style="vertical-align:bottom;" src="resources/images/update.gif" border="0" alt="edit">	
										&nbsp;<spring:message code="systema.ebooking.orders.form.update.label.header.edit"/>
										&nbsp;&nbsp;<b>${model.record.heunik} / ${model.record.hereff}</b>
					 				</td>
				 				</c:when>
				 				<c:otherwise>
									<td align="left" class="text14White">
										&nbsp;<spring:message code="systema.ebooking.orders.form.update.label.header.add"/>
					 				</td>	
				 				</c:otherwise>
			 				</c:choose>
			 				<td align="right" class="text14White" width="50%">
								&nbsp;<spring:message code="systema.ebooking.orders.form.update.label.header.customerIdAndName"/>	
								&nbsp;&nbsp;&nbsp;&nbsp;<b>${model.record.trknfaNavn}&nbsp;&nbsp;</b>${model.record.trknfa}&nbsp;&nbsp;
								
			 				</td>
		 				</tr>
	 					</table>
            		</td>
            </tr>
            <%-- FORM DETAIL --%>
            <tr ondrop="drop(event)" ondragover="allowDrop(event)" >
            		<td>
            		<input type="hidden" name="applicationUser" id="applicationUser" value='${user.user}'>
					<input type="hidden" name="heunik" id="heunik" value='${model.record.heunik}'>
					<input type="hidden" name="hereff" id="hereff" value='${model.record.hereff}'>
					<input type="hidden" name="heur" id="heur" value='${model.record.heur}'>
					<input type="hidden" name="heavd" id="heavd" value='${model.record.heavd}'>
					<input type="hidden" name="trknfa" id="trknfa" value='${model.record.trknfa}'>
					<input type="hidden" name="status" id="status" value='${model.record.status}'>
					<input type="hidden" name="action" id="action" value='doUpdate'>
					<input type="hidden" name="selectedType" id="selectedType" value='${model.selectedType}'>
					<input type="hidden" name="modul1_2" id="modul1_2" value='${model.record.modul1_2}'>
					
					<input type="hidden" name="messageNoteConsigneeOriginal" id="messageNoteConsigneeOriginal" value='${model.record.messageNoteConsigneeOriginal}'>
					<input type="hidden" name="messageNoteCarrierOriginal" id="messageNoteCarrierOriginal" value='${model.record.messageNoteCarrierOriginal}'>
					<input type="hidden" name="messageNoteInternalOriginal" id="messageNoteInternalOriginal" value='${model.record.messageNoteInternalOriginal}'>
					<table style="width:98%;" align="left" class="formFrame" border="0" cellspacing="0" cellpadding="0">
				 		<tr height="10"><td ></td></tr>
				 		<tr>
							<td colspan="2">
							<table border="0">
							 	<tr>
							 		<%-- hereff has been replaced by: Avs.ref (herfa) or Mott.ref (herfk) 
						 		 	<td class="text14">&nbsp;&nbsp;<font class="text16RedBold" >*</font><span title="hereff"><spring:message code="systema.ebooking.orders.form.update.label.orderref"/>&nbsp;</span></td>
				 					<td class="text14">
					 					<input required oninvalid="this.setCustomValidity('Obligatoriskt')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlueMandatoryField" name="hereff" id="hereff" size="10" maxlength="10" value="${model.record.hereff}">
					 				</td>
					 				--%>
					 				<td align="left" class="text14Bold" >
					 					&nbsp;&nbsp;&nbsp;<span title="xfakBet"><spring:message code="systema.ebooking.orders.form.update.label.fraktbetaler"/></span>
					 					
							        </td>
					 				<td class="text14">
										<select class="selectMediumBlueE2" <c:if test="${not model.record.fakBetExists}"> disabled </c:if> name="xfakBet" id="xfakBet">
											<option value=''>-<spring:message code="systema.ebooking.dropdown.velg"/>-</option>
						 					<option value='S' <c:if test="${model.record.xfakBet == 'S'}"> selected </c:if> ><spring:message code="systema.ebooking.orders.form.update.label.shipper.seller"/></option>
				 							<option value='M' <c:if test="${model.record.xfakBet == 'M'}"> selected </c:if> ><spring:message code="systema.ebooking.orders.form.update.label.consignee"/></option>
				 							<option value='A' <c:if test="${model.record.xfakBet == 'A'}"> selected </c:if> ><spring:message code="systema.ebooking.orders.form.update.label.annen"/></option>
										</select>
										
										<c:if test="${not model.record.fakBetExists}">
											<%-- EXTRA FIX for select- with disabled --> since the disabled select does not send the value --%>
											<input type="hidden" name="xfakBet" id="xfakBet" value="${model.record.xfakBet}" >
										</c:if>
					 				</td>
					 				<%--
					 				<td align="left" class="text14Bold" >
					 					&nbsp;&nbsp;&nbsp;
					 					<a href="javascript:void(0);" onClick="window.open('/espedsg2/sporringoppdraggate.do?lang=NO&cw=true','opdWin','top=100px,left=200px,height=900px,width=1500px,scrollbars=no,status=no,location=no')">
					 						TEST till Spørr.Oppd.
					 						<img id="imgOpdSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
					 					</a>
							        </td>
							        --%> 
					 			</tr>
					 			
							</table>
							</td>
						</tr>
						
						<tr height="5"><td ></td></tr>
						<tr height="3"><td colspan="2" style="border-bottom:1px solid;border-color:#DDDDDD;" class="text"></td></tr>
						<tr height="5"><td ></td></tr>
						<tr>
							<td class="text14Bold">&nbsp;<font class="text16RedBold" >*</font>
								<spring:message code="systema.ebooking.orders.form.update.label.shipper"/></td>
							<td class="text14Bold">&nbsp;<font class="text16RedBold" >*</font>
								<spring:message code="systema.ebooking.orders.form.update.label.consignee"/>
							</td>
						</tr>
						<tr height="5"><td ></td></tr>
						
						<tr>
							<td >
							<table class="tableBorderWithRoundCornersLightGray">
								<tr>
									<td class="text14Bold"><spring:message code="systema.ebooking.orders.form.update.label.shippingDates"/></td>
									<td class="text14">&nbsp;<span title="wsetdd/wsetdk"><spring:message code="systema.ebooking.orders.form.update.label.shippingDates.etd"/></span></td>
									<td class="text14">
										<input type="text" class="inputTextMediumBlue" name="wsetdd" id="wsetdd" size="9" maxlength="8" value="${model.record.wsetdd}">
									</td>
									<td class="text14"><input type="text" class="inputTextMediumBlue" name="wsetdk" id="wsetdk" size="4" maxlength="4" value="${model.record.wsetdk}"></td>
									
						
								</tr>
							</table>
							</td>
							<td >
							<table class="tableBorderWithRoundCornersLightGray">
								<tr>
									<td class="text14Bold"><spring:message code="systema.ebooking.orders.form.update.label.arrivalDates"/></td>
									<td class="text14">&nbsp;<span title="wsetad/wsetak"><spring:message code="systema.ebooking.orders.form.update.label.arrivalDates.eta"/></span></td>
									<td class="text14">
										<input type="text" class="inputTextMediumBlue" name="wsetad" id="wsetad" size="9" maxlength="8" value="${model.record.wsetad}">
									</td>
									<td class="text14"><input onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlue" name="wsetak" id="wsetak" size="4" maxlength="4" value="${model.record.wsetak}"></td>
									
								</tr>
							</table>
							</td>		
						</tr>
						<tr height="5"><td ></td></tr>
						
						
						<tr>
				 			<td valign="top" width="50%" >
				 			 <table style="width:98%" class="tableBorderWithRoundCornersGray" cellspacing="1" cellpadding="0" border="0">
						 		<tr height="10"><td ></td></tr>
						 		<tr>
					 				<td class="text14">
					 					&nbsp;<span title="hekns"><spring:message code="systema.ebooking.orders.form.update.label.shipper.id"/>&nbsp;</span>
					 					<%-- <c:if test="${model.record.fakBetExists}">  --%>
						 					<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_customer.do?action=doFind&ctype=s','customerWin','top=300px,left=150px,height=800px,width=900px,scrollbars=no,status=no,location=no')">
		 										<img id="imgShipperSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
		 									</a>
	 									<%-- </c:if> --%>
					 				</td>
					 				<td class="text14">
					 					&nbsp;<span title="whenas"><spring:message code="systema.ebooking.orders.form.update.label.shipper.seller"/>&nbsp;</span>
					 	
					 				</td>
					 			</tr>
					 			<tr>	
				 					<td class="text14" ><input type="text" class="inputTextMediumBlueUPPERCASE" name="hekns" id="hekns" size="10" maxlength="8" value="${model.record.hekns}"></td>
								 	<td class="text14" ><input readonly tabindex=-1 type="text" class="inputTextReadOnly" name="whenas" id="whenas" size="50" value="${model.record.heknsNavn}&nbsp;-&nbsp;${model.record.heknsPnSt}"></td>
				 				</tr>
								<tr height="5"><td ></td></tr>
						 		<tr>
					 				<td class="text14">&nbsp;<font class="text16RedBold" >*</font><span title="henas"><spring:message code="systema.ebooking.orders.form.update.label.shipper.name"/></span>
					 					<%-- <c:if test="${model.record.fakBetExists}"> --%>
						 					<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_customer_addresses.do?action=doFind&ctype=s&wkundnr=${model.record.trknfa}&wkundnvn=${model.record.trknfaNavn}','customerWin','top=80px,left=250px,height=950px,width=900px,scrollbars=no,status=no,location=no')">
		 										<img id="imgShipperSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
		 									</a>
	 									<%-- </c:if> --%>
	 									<font style="margin-left:10em;color:darkgreen;" title="wvakure">&nbsp;<spring:message code="systema.ebooking.orders.form.update.label.hurtigsok.knr"/></font>
					 				</td>
					 				<td class="text14">&nbsp;<font class="text16RedBold" >*</font><span title="heads1"><spring:message code="systema.ebooking.orders.form.update.label.shipper.adr1"/></span></td>
					 			</tr>
					 			<tr>	
				 					<td class="text14">
				 						<input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlueUPPERCASEMandatoryField" name="henas" id="henas" size="25" maxlength="30" value="${model.record.henas}">
				 						&nbsp;<input type="text" class="inputTextMediumBlue" name="wvakure" id="wvakure" size="10" maxlength="25" value="${Xmodel.record.todo}">
				 					</td>
				 					<td class="text14"><input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlueUPPERCASEMandatoryField" name="heads1" id="heads1" size="25" maxlength="30" value="${model.record.heads1}"></td>
				 				</tr>
					 			<tr>	
					 				<td class="text14">&nbsp;<span title="heads2"><spring:message code="systema.ebooking.orders.form.update.label.shipper.adr2"/></span></td>
					 				<td class="text14">&nbsp;<span title="heads3"><spring:message code="systema.ebooking.orders.form.update.label.shipper.adr3"/></span></td>
					 			</tr>
								<tr>	
				 					<td class="text14" >
				 					<input type="text" class="inputTextMediumBlueUPPERCASE" name="heads2" id="heads2" size="25" maxlength="30" value="${model.record.heads2}">
				 					</td>
				 					<td class="text14"><input type="text" class="inputTextMediumBlueUPPERCASE" name="heads3" id="heads3" size="25" maxlength="30" value="${model.record.heads3}"></td>
				 				</tr>
				 				
				 				<tr height="15"><td ></td></tr>	
				 				<tr>	
				 					<td class="text14">
										<img onMouseOver="showPop('herfa_info');" onMouseOut="hidePop('herfa_info');"style="vertical-align:bottom;" width="12px" height="12px" src="resources/images/info3.png" border="0" alt="info">
					 					<span title="herfa"><spring:message code="systema.ebooking.orders.form.update.label.avsRef"/></span>						 				
										<div class="text11" style="position: relative;" align="left">
										<span style="position:absolute; left:0px; top:0px; width:250px" id="herfa_info" class="popupWithInputText"  >
											<font class="text11">
							           			<b>Søk Avs.</b>
							           			<div>
							           			<p>Avsenders søkereferanse <br>
							           				Begrep for senere søk/gjenfinning.</p>
							           			</div>
						           			</font>
										</span>
										</div>
									</td>
				 					<td class="text14">
				 						<span title="hesdla">
				 							<img style="vertical-align:middle;" src="resources/images/loading.png" width="15px" height="15px" border="0" alt="load/unload">
				 							<spring:message code="systema.ebooking.orders.form.update.label.load"/>
				 							<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_loadunloadplaces.do?action=doInit&&caller=hesdla','postalcodeWin','top=300px,left=50px,height=600px,width=800px,scrollbars=no,status=no,location=no')">						 				
						 						<img id="imgToSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
						 					</a>
				 						</span>
				 					</td>
				 				</tr>
				 				<tr>	
				 					<td class="text14" >
				 						<input type="text" class="inputTextMediumBlueUPPERCASE" name="herfa" id="herfa" size="20" maxlength="35" value="${model.record.herfa}">
								 	</td>
				 					<td class="text14" >
						 				<input type="text" class="inputTextMediumBlue" name="hesdla" id="hesdla" size="21" maxlength="20" value="${model.record.hesdla}">
										
						 			</td>
				 				</tr>
				 				<tr height="5"><td ></td></tr>
								<tr>
					 				<td class="text14">&nbsp;<span title="wsscont"><spring:message code="systema.ebooking.orders.form.update.label.shipper.contactName"/></span></td>
					 				<td class="text14">&nbsp;<span title="wsstlf"><spring:message code="systema.ebooking.orders.form.update.label.shipper.telephone"/></span></td>
					 			</tr>
					 			<tr>	
				 					<td class="text14"><input type="text" class="inputTextMediumBlue" name="wsscont" id="wsscont" size="25" maxlength="30" value="${model.record.wsscont}"></td>
				 					<td class="text14"><input type="text" class="inputTextMediumBlue" name="wsstlf" id="wsstlf" size="25" maxlength="30" value="${model.record.wsstlf}"></td>
				 				</tr>
					 			<tr>	
					 				<td class="text14" colspan="2">&nbsp;<span title="wssmail"><spring:message code="systema.ebooking.orders.form.update.label.shipper.email"/></span></td>
					 			</tr>
					 			<tr>	
				 					<td class="text14" colspan="2"><input type="text" class="inputTextMediumBlue" name="wssmail" id="wssmail" size="50" maxlength="70" value="${model.record.wssmail}"></td>
				 				</tr>
				 				<tr height="8"><td ></td></tr>
				 				
				 				<c:choose>
				 				<c:when test="${model.record.fakBetExists}">													 				
									<tr>
					 					<td class="text14Bold">&nbsp;
					 						<img style="vertical-align: bottom;" width="24px" height="24px" src="resources/images/invoice.png" border="0" alt="invoice">
					 						<spring:message code="systema.ebooking.orders.form.update.label.shipper.invoicee"/>
				 						</td>
									</tr>
					 				<tr>
					 					<td colspan="2">
					 					<table class="tableBorderWithRoundCornersLightGray">
						 					<tr>
								 				<td class="text14">
								 					&nbsp;<span title="heknsf"><spring:message code="systema.ebooking.orders.form.update.label.shipper.invoicee.id"/>&nbsp;</span>
								 					<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_customer.do?action=doFind&ctype=sf','customerWin','top=300px,left=150px,height=800px,width=900px,scrollbars=no,status=no,location=no')">
					 										<img id="imgConsigneeSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
				 									</a>
								 				</td>
								 				<td class="text14">
								 					&nbsp;<span title="whenasf"><spring:message code="systema.ebooking.orders.form.update.label.shipper.invoicee.name"/>&nbsp;</span>
								 				</td>
								 				<td class="text14">
								 					<img onMouseOver="showPop('shipperCurr_info');" onMouseOut="hidePop('shipperCurr_info');"style="vertical-align:bottom;" width="12px" height="12px" src="resources/images/info3.png" border="0" alt="info">
									 				<span title="hevals"><spring:message code="systema.ebooking.orders.form.update.label.shipper.invoicee.currencyCode"/>&nbsp;</span>
									 				<div class="text11" style="position: relative;" align="left">
														<span style="position:absolute; left:0px; top:0px; width:250px" id="shipperCurr_info" class="popupWithInputText"  >
															<font class="text11">
										           			<b>Valuta</b>
										           			<div>
										           			<p>Valuta for fakturautstedelse - hentes fra kunderegister, kan overstyres. 
										           				Ved ulik NOK går fremmedvaluta inn i reskontro.
										           			</p>
										           			</div>
									           			</font>
													</span>
													</div>
								 				</td>
								 				
							 				</tr>
							 				<tr>	
							 					<td class="text14" ><input type="text" class="inputTextMediumBlueUPPERCASE" name="heknsf" id="heknsf" size="10" maxlength="8" value="${model.record.heknsf}"></td>
											 	<td class="text14" ><input readonly tabindex=-1 type="text" class="inputTextReadOnly" name="whenasf" id="whenasf" size="50" value="${model.record.heknsfNavn} - ${model.record.heknsfPnSt}"></td>
							 					<td class="text14" >
							 						<select class="selectMediumBlueE2" name="hevals" id="hevals">
								 						<option value="">-valuta-</option>
									 				  	<c:forEach var="currency" items="${model.currencyCodeList}" >
									 				  		<option value="${currency}"<c:if test="${model.record.hevals == currency || (empty model.record.hevals && currency=='NOK')}"> selected </c:if> >${currency}</option>
														</c:forEach>  
													</select>
							 					</td>
							 					
						 					</tr>
										</table>
										</td>				 				
						 			</tr>
					 			</c:when>
					 			<c:otherwise>
					 				<input type="hidden" name="heknsf" id="heknsf" value="${model.record.heknsf}" >   
					 			</c:otherwise>	
					 			</c:choose>			
					 			 	
				 				<tr height="10"><td ></td></tr>
							 </table>
						 	</td>
						 	<td valign="top" width="50%">
				 			 <table style="width:98%" class="tableBorderWithRoundCornersGray" cellspacing="1" cellpadding="0">
					 			<tr height="10"><td ></td></tr>
						 		<tr>
					 				<td class="text14">
					 					&nbsp;<span title="heknk"><spring:message code="systema.ebooking.orders.form.update.label.consignee.id"/>&nbsp;</span>
					 					<%-- <c:if test="${model.record.fakBetExists}"> --%>
						 					<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_customer.do?action=doFind&ctype=c','customerWin','top=300px,left=150px,height=800px,width=900px,scrollbars=no,status=no,location=no')">
		 										<img id="imgConsigneeSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
		 									</a>	
	 									<%-- </c:if> --%>
	 									
					 				</td>
					 				<td class="text14">
					 					&nbsp;<span title="whenak"><spring:message code="systema.ebooking.orders.form.update.label.consignee.buyer"/>&nbsp;</span>
					 				</td>	
					 			</tr>
					 			<tr>	
				 					<td class="text14"><input type="text" class="inputTextMediumBlueUPPERCASE" name="heknk" id="heknk" size="10" maxlength="8" value="${model.record.heknk}"></td>
				 					<td class="text14" ><input readonly tabindex=-1 type="text" class="inputTextReadOnly" name="whenak" id="whenak" size="50" value="${model.record.heknkNavn}&nbsp;-&nbsp;${model.record.heknkPnSt}"></td>
				 				</tr>
				 				<tr height="5"><td ></td></tr>
						 		<tr>
					 				<td class="text14">&nbsp;<font class="text16RedBold" >*</font><span title="henak"><spring:message code="systema.ebooking.orders.form.update.label.consignee.name"/></span>
					 					<%-- <c:if test="${model.record.fakBetExists}"> --%>
						 					<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_customer_addresses.do?action=doFind&ctype=c&wkundnr=${user.custNr}','customerWin','top=80px,left=250px,height=950px,width=900px,scrollbars=no,status=no,location=no')">
		 										<img id="imgConsigneeSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
		 									</a>
	 									<%-- </c:if> --%>
	 									<font style="margin-left:10em;color:darkgreen;" title="wvakure2">&nbsp;<spring:message code="systema.ebooking.orders.form.update.label.hurtigsok.knr"/></font>
					 				</td>
					 				<td class="text14">&nbsp;<font class="text16RedBold" >*</font><span title="headk1"><spring:message code="systema.ebooking.orders.form.update.label.consignee.adr1"/></span></td>
					 			</tr>
					 			<tr>	
				 					<td class="text14"><input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlueUPPERCASEMandatoryField" name="henak" id="henak" size="25" maxlength="30" value="${model.record.henak}">
				 						&nbsp;<input type="text" class="inputTextMediumBlue" name="wvakure2" id="wvakure2" size="10" maxlength="25" value="${Xmodel.record.todo}">
				 					</td>
				 					<td class="text14"><input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlueUPPERCASEMandatoryField" name="headk1" id="headk1" size="25" maxlength="30" value="${model.record.headk1}"></td>
				 				</tr>
					 			<tr>	
					 				<td class="text14">&nbsp;<span title="headk2"><spring:message code="systema.ebooking.orders.form.update.label.consignee.adr2"/></span></td>
					 				<td class="text14">&nbsp;<span title="headk3"><spring:message code="systema.ebooking.orders.form.update.label.consignee.adr3"/></span></td>
					 			</tr>
								<tr>	
				 					<td class="text14"><input type="text" class="inputTextMediumBlueUPPERCASE" name="headk2" id="headk2" size="25" maxlength="30" value="${model.record.headk2}"></td>
				 					<td class="text14"><input type="text" class="inputTextMediumBlueUPPERCASE" name="headk3" id="headk3" size="25" maxlength="30" value="${model.record.headk3}"></td>
				 				</tr>
				 				
				 				<tr height="15"><td ></td></tr>
				 				<tr>	
				 					<td class="text14"><img onMouseOver="showPop('herfk_info');" onMouseOut="hidePop('herfk_info');"style="vertical-align:bottom;" width="12px" height="12px" src="resources/images/info3.png" border="0" alt="info">
					 					&nbsp;&nbsp;<span title="herfk"><spring:message code="systema.ebooking.orders.form.update.label.consignee.ref"/></span>
						 				<div class="text11" style="position: relative;" align="left">
										<span style="position:absolute; left:0px; top:0px; width:250px" id="herfk_info" class="popupWithInputText"  >
											<font class="text11">
							           			<b>Søk Mott.</b>
							           			<div>
							           			<p>Mottakers søkereferanse Fritt felt for utfylling. Begrep for senere søk/gjenfinning.</p>
							           			</div>
						           			</font>
										</span>
										</div>
				 					</td>
				 					<td class="text14">
				 						<img style="vertical-align:middle;" src="resources/images/loading.png" width="15px" height="15px" border="0" alt="load/unload">
				 						<span title="hesdl"><spring:message code="systema.ebooking.orders.form.update.label.unload"/></span>
				 						<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_loadunloadplaces.do?action=doInit&caller=hesdl','postalcodeWin','top=300px,left=50px,height=600px,width=800px,scrollbars=no,status=no,location=no')">						 				
						 					<img id="imgToSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
						 				</a>
				 					</td>
				 				</tr>
				 				<tr>	
				 					<td class="text14" >
				 						<input type="text" class="inputTextMediumBlueUPPERCASE" name="herfk" id="herfk" size="20" maxlength="35" value="${model.record.herfk}">
				 					</td>
				 					<td class="text14" >
						 				<input type="text" class="inputTextMediumBlue" name="hesdl" id="hesdl" size="21" maxlength="20" value="${model.record.hesdl}">
										
						 			</td>
				 				</tr>
				 				<tr height="5"><td ></td></tr>
				 				<tr>
					 				<td class="text14">&nbsp;<span title="wskcont"><spring:message code="systema.ebooking.orders.form.update.label.consignee.contactName"/></span></td>
					 				<td class="text14">&nbsp;<span title="wsktlf"><spring:message code="systema.ebooking.orders.form.update.label.consignee.telephone"/></span></td>
					 			</tr>
					 			<tr>	
				 					<td class="text14"><input type="text" class="inputTextMediumBlue" name="wskcont" id="wskcont" size="25" maxlength="30" value="${model.record.wskcont}"></td>
				 					<td class="text14"><input type="text" class="inputTextMediumBlue" name="wsktlf" id="wsktlf" size="25" maxlength="30" value="${model.record.wsktlf}"></td>
				 				</tr>
					 			<tr>	
					 				<td class="text14" colspan="2">&nbsp;<span title="wskmail"><spring:message code="systema.ebooking.orders.form.update.label.consignee.email"/></span></td>
					 			</tr>
					 			<tr>	
				 					<td class="text14" colspan="2"><input type="text" class="inputTextMediumBlue" name="wskmail" id="wskmail" size="50" maxlength="70" value="${model.record.wskmail}"></td>
				 				</tr>
				 				<tr height="8"><td ></td></tr>
				 				
				 				<c:choose>
				 				<c:when test="${model.record.fakBetExists}">
									<tr>
					 					<td class="text14Bold">&nbsp;
					 						<img style="vertical-align: bottom;" width="24px" height="24px" src="resources/images/invoice.png" border="0" alt="invoice">
					 						<spring:message code="systema.ebooking.orders.form.update.label.consignee.invoicee"/>
					 					</td>
									</tr>
					 				<tr>
					 					<td colspan="2">
					 					<table class="tableBorderWithRoundCornersLightGray">
						 					<tr>
								 				<td class="text14">
								 					&nbsp;<span title="heknkf"><spring:message code="systema.ebooking.orders.form.update.label.consignee.invoicee.id"/>&nbsp;</span>
							 						<a href="javascript:void(0);" onClick="window.open('ebooking_childwindow_customer.do?action=doFind&ctype=kf','customerWin','top=300px,left=150px,height=800px,width=900px,scrollbars=no,status=no,location=no')">
				 										<img id="imgShipperSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="13px" width="13px" border="0" alt="search">
				 									</a>
								 				</td>
								 				<td class="text14">
								 					&nbsp;<span title="whenakf"><spring:message code="systema.ebooking.orders.form.update.label.consignee.invoicee.name"/>&nbsp;</span>
								 				</td>
								 				
							 				</tr>
							 				<tr>	
							 					<td class="text14" ><input type="text" class="inputTextMediumBlueUPPERCASE" name="heknkf" id="heknkf" size="10" maxlength="8" value="${model.record.heknkf}"></td>
											 	<td class="text14" ><input readonly tabindex=-1 type="text" class="inputTextReadOnly" name="whenakf" id="whenakf" size="50" value="${model.record.heknkfNavn} - ${model.record.heknkfPnSt}"></td>
							 					<td class="text14">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 					</tr>
										</table>
										</td>				 				
						 			</tr>
					 			</c:when>	
					 			<c:otherwise>
					 				<input type="hidden" name="heknkf" id="heknkf" value="${model.record.heknkf}" >   
					 			</c:otherwise>	
					 			</c:choose>						 
				 				<tr height="10"><td ></td></tr>
			 				</table>
						 	</td>
					 	</tr>
					 	<tr height="10"><td ></td></tr>
					 </table>
				</td>
			</tr>
			<tr>
            		<td>
	        			<table style="width:98%;" align="left" class="tableBorderWithRoundCornersGray" cellspacing="0" cellpadding="0">
				 		<tr height="5"><td colspan="2" ></td></tr>
				 		<tr>
							<td valign="top" style="width:50%;border-right:1px solid;border-color:#FFFFFF;""  >
								<table>
						 		<tr height="2"><td ></td></tr>
							 	<tr>	
						 			<td class="text14">
						 				<img onMouseOver="showPop('helka_info');" onMouseOut="hidePop('helka_info');"style="vertical-align:bottom;" width="12px" height="12px" src="resources/images/info3.png" border="0" alt="info">
						 				<font class="text14RedBold" >*</font><span title="helka/hesdf"><spring:message code="systema.ebooking.orders.form.update.label.from"/>&nbsp;</span>
						 				<div class="text11" style="position: relative;" align="left">
											<span style="position:absolute;top:0px; width:250px" id="helka_info" class="popupWithInputText"  >
												<font class="text11">
							           			<b>Fra sted</b>
							           			<div>
							           			<p>Landkode + postnr / kode for "kundefraktens" frasted. Ved IKKE postnr.basert er det kun ett kodefelt (5 langt)
												</p>
							           			</div>
						           			</font>
										</span>
										</div>
				 					</td>
					 				<td class="text14">
					 					<select required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="helka" id="helka">
					 						<option value="">-landkode-</option>
						 				  	<c:forEach var="country" items="${model.countryCodeList}" >
						 				  		<option value="${country.zkod}"<c:if test="${model.record.helka == country.zkod}"> selected </c:if> >${country.zkod}</option>
											</c:forEach>  
										</select>
										
					 				</td>
						 			<td class="text14" nowrap>
						 				<input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlueMandatoryField" name="hesdf" id="hesdf" size="6" maxlength="5" value="${model.record.hesdf}">
						 				<a tabindex=0 id="hesdfIdLink">
	 										<img id="imgFromSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" width="13px" height="13px" border="0" alt="search">
	 									</a>
					 				</td>
					 				<td class="text14" colspan="2">
						 				<input readonly tabindex=-1 type="text" class="inputTextReadOnly" name="OWNwppns1" id="OWNwppns1" size="20" maxlength="14" value="${Xmodel.record.wppns1}">
					 				</td>
					 				<td class="text14">&nbsp;&nbsp;</td>
					 			</tr>
					 				 			
							 	</table>
							</td>
							<td align="left">
							<table >
								<tr>
									<td colspan="4">
									<table>
							 			<tr>	
								 			<td class="text14">
								 				<img onMouseOver="showPop('hetri_info');" onMouseOut="hidePop('hetri_info');"style="vertical-align:bottom;" width="12px" height="12px" src="resources/images/info3.png" border="0" alt="info">
								 				<font class="text14RedBold" >*</font><span title="hetri/hesdt"><spring:message code="systema.ebooking.orders.form.update.label.to"/></span>
								 				<div class="text14" style="position: relative;" align="left">
													<span style="position:absolute;top:0px; width:250px" id="hetri_info" class="popupWithInputText"  >
														<font class="text14">
									           			<b>Til sted</b>
									           			<div>
									           			<p>Landkode + postnr / kode for "kundefraktens" tilsted. Ved IKKE postnr.basert er det kun ett kodefelt (5 langt).
														</p>
									           			</div>
								           			</font>
												</span>
												</div>
							 				</td>
							 				<td class="text14">
							 					<select required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="hetri" id="hetri">
							 						<option value="">-landkode-</option>
								 				  	<c:forEach var="country" items="${model.countryCodeList}" >
								 				  		<option value="${country.zkod}"<c:if test="${model.record.hetri == country.zkod}"> selected </c:if> >${country.zkod}</option>
													</c:forEach>  
												</select>
							 				</td>
								 			<td class="text14" nowrap>
								 				<input type="text" required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="hesdt" id="hesdt" size="6" maxlength="5" value="${model.record.hesdt}">
								 				<a tabindex=0 id="hesdtIdLink" >
			 										<img id="imgToSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" width="13px" height="13px" border="0" alt="search">
			 									</a>
							 				</td>
											<td class="text14" colspan="2">
								 				<input readonly tabindex=-1 type="text" class="inputTextReadOnly" name="OWNwppns2" id="OWNwppns2" size="20" maxlength="14" value="${Xmodel.record.wppns2}">
							 				</td>
							 				<td class="text14">&nbsp;&nbsp;</td>
							 			</tr>			
							 		</table>
							 		</td>
							 	</tr>
							</table>
							</td>
						</tr>
						
						
						
						<tr height="8"><td colspan="2" ></td></tr>
						<tr height="1"><td colspan="2" style="border-bottom:1px solid;border-color:#FFFFFF;" class="text"></td></tr>
						<tr height="8"><td colspan="2" ></td></tr>
						<tr>
							<td colspan="4">
							<table>	
								<tr>
									<td>
									<table>
						 			<tr>		
										<td class="text14">&nbsp;
											<span title="heot"><spring:message code="systema.ebooking.orders.form.update.label.oppdragstype"/></span>
							 			</td>
							 			<td class="text14">
							 				<select class="selectMediumBlueE2" name="heot" id="heot">
							 					<c:forEach var="record" items="${model.oppdragstypeList}" varStatus="counter">
							 						<option value='${record.opdTyp}' <c:if test="${record.opdTyp == model.record.heot}"> selected </c:if> >${record.opdTyp}</option>
							 						<c:set var="listSizeHeot" value="${counter.count}" scope="request" /> 
							 					</c:forEach>
							 					<c:if test="${listSizeHeot > 1}">
							 						<option value="" <c:if test="${empty model.record.heot}"> selected </c:if> >-<spring:message code="systema.ebooking.dropdown.velg"/>-</option>
							 					</c:if>
											</select>
							 			</td>
										<td width="30px">&nbsp;</td>
										<td class="text14">&nbsp;
											<span title="hefr"><spring:message code="systema.ebooking.orders.form.update.label.incoterms"/></span>
							 			</td>
							 			<td class="text14">
							 				<select class="selectMediumBlueE2" name="hefr" id="hefr">
							 					<c:forEach var="record" items="${model.incotermsList}" varStatus="counter">
							 						<option value='${record.franka}' <c:if test="${record.franka == model.record.hefr}"> selected </c:if> >${record.franka}</option>
							 						<c:set var="listSizeHefr" value="${counter.count}" scope="request" />
							 					</c:forEach>
							 					<c:if test="${listSizeHefr > 1}">
							 						<option value="" <c:if test="${empty model.record.hefr}"> selected </c:if> >-<spring:message code="systema.ebooking.dropdown.velg"/>-</option>
							 					</c:if>
											</select>
							 			</td>
										<td width="30px">&nbsp;</td>
										<td class="text14">&nbsp;
											<span title="todo"><spring:message code="systema.ebooking.orders.form.update.label.productcode"/></span>
							 			</td>
							 			<td class="text14">
							 				<select class="selectMediumBlueE2" name="todo" id="todo">
							 					<option value='todo' <c:if test="${fraktbrevRecord.ffenh == 'KG' || fraktbrevRecord.ffenh == 'kg'}"> selected </c:if> >Normalfrakt</option>
											</select>
							 			</td>
						 			</tr>
						 			</table>
						 			</td>
						 		</tr>
					 		</table>
					 		</td>
				 		</tr>
						
						<tr height="5"><td ></td></tr>	
	 				</table>
            		</td>
            </tr>
            <tr height="2"><td></td></tr>
            
            
            <tr>
            		<td>
	        			<table width="98%" id="containerdatatableTable" cellspacing="2" align="left" >
				 		<tr height="6"><td></td></tr>
				 		<%-- UPDATE LINEs SECTION --%>
				 		<tr>
							<td >
				 				<table id="tblItemLines" class="display compact cell-border" >
								<thead>
						 		<tr style="background-color:#DDDDDD" >
						 			<th align="center" class="text14"><spring:message code="systema.ebooking.orders.form.detail.update.label.update"/></th>
							 		<th align="left" class="text14"><span title="fvlinr">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.linenr"/></span></th>
							 		<th align="left" class="text14"><span title="fmmrk1/hegm1(Tot)">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.marks"/></span></th>
						 			<th align="right" class="text14"><span title="fvant/hent(Tot)">&nbsp;<font class="text14RedBold" >*</font><spring:message code="systema.ebooking.orders.form.detail.update.label.antal"/>&nbsp;</span></th>
						 			<th align="left" class="text14"><span title="fvpakn">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.forpak"/></span></th>
						 			<th align="left" class="text14"><span title="fvvt/hevs1(Tot)">&nbsp;<font class="text14RedBold" >*</font><spring:message code="systema.ebooking.orders.form.detail.update.label.goodsDesc"/></span></th>
						 			<th align="right" class="text14"><span title="fvvkt/hevkt(Tot)">&nbsp;<font class="text14RedBold" >*</font><spring:message code="systema.ebooking.orders.form.detail.update.label.weight"/>&nbsp;</span></th>
						 			<th align="right" class="text14"><span title="fvlen">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.length"/>&nbsp;</span></th>
						 			<th align="right" class="text14"><span title="fvbrd">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.width"/>&nbsp;</span></th>
						 			<th align="right" class="text14"><span title="fvhoy">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.height"/>&nbsp;</span></th>
						 			<th align="right" class="text14"><span title="fvvol/hem3(Tot)">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.m3"/>&nbsp;</span></th>
						 			<th align="right" class="text14"><span title="fvlm/helm(Tot)">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.lm.fa"/>&nbsp;</span></th>
						 			<th width="3%" align="left" valign="bottom" class="tableHeaderFieldFirst10"><span title="FARLIG GODS ffunnr">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.unNr"/>&nbsp;</span></th>
						 			<th width="3%" align="right" valign="bottom" class="tableHeaderField10"><span title="FARLIG GODS ffembg">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.emg"/>&nbsp;</span></th>
						 			<th width="3%" align="right" valign="bottom" class="tableHeaderField10"><span title="FARLIG GODS ffindx">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.emg.index"/>&nbsp;</span></th>
						 			<th width="5%" align="right" valign="bottom" class="tableHeaderField10"><span title="FARLIG GODS ffantk">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.ant2"/>&nbsp;</span></th>
						 			<th width="5%" align="right" valign="bottom" class="tableHeaderField10"><span title="FARLIG GODS ffante">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.mengd"/>&nbsp;</span></th>
						 			<th width="5%" align="right" valign="bottom" class="tableHeaderField10"><span title="FARLIG GODS ffenh">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.unit"/>&nbsp;</span></th>
						 			<th align="right" valign="bottom" class="tableHeaderField10"><span title="FARLIG GODS ffpoen/hepoen(Tot)">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.adr"/>&nbsp;</span></th>
						 			<%--<th align="center"  class="text14"><span title="Farlig gods">&nbsp;Farlig gods&nbsp;</span></th> --%>
						 			<th align="center" class="text14"><span title=""><spring:message code="systema.ebooking.orders.form.detail.update.label.remove"/></span></th>										 			
						 		</tr>
						 		</thead>
						 		<tbody>
						 		
						 		<c:forEach items="${model.record.fraktbrevList}" var="fraktbrevRecord" varStatus="counter">
						 			<c:if test="${not empty fraktbrevRecord.fvlinr}">
						 				<c:set var="upperCurrentItemlineNr" scope="request" value="${fraktbrevRecord.fvlinr}"/>
						 				<c:set var="totalNumberOfLines" scope="request" value="${counter.count}"/>
						 			</c:if>
						 			
							 		<tr >
							 			<%-- lineNr will always be sent(to the controller) in case this is a new line (when fvlinr=null) --%>
						 				<input type="hidden" name="lineNr_${counter.count}" id="lineNr_${counter.count}" value="${counter.count}" >   
							 			<input type="hidden" name="fvlinr_${counter.count}" id="fvlinr_${counter.count}" value="${fraktbrevRecord.fvlinr}" >
							 			
							 			<td align="center" class="text14" >
							 				<a tabindex=-1 id="recordUpdate_${fraktbrevRecord.fvlinr}" href="#" onClick="getItemData(this);">
					               				<img style="vertical-align:middle;" src="resources/images/update.gif" width="12px" height="12px" border="0" alt="update line">
						               		</a>
							 			</td>
							 			<td align="center" class="text14" nowrap>${counter.count}</td>
					               		<td align="left" class="text14" nowrap>${fraktbrevRecord.fmmrk1}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvant}</td>
						 				<td align="left" class="text14" nowrap>${fraktbrevRecord.fvpakn}</td>
						 				<td align="left" class="text14" nowrap>${fraktbrevRecord.fvvt}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvvkt}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvlen}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvbrd}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvhoy}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvvol}</td>
						 				<td align="right" class="text14" nowrap>${fraktbrevRecord.fvlm}</td>
						 				
						 				<td width="3%" align="left" class="tableCellDangerousGoods" nowrap >${fraktbrevRecord.ffunnr}</td>
						 				<td width="3%" align="right" class="tableCellDangerousGoods" >${fraktbrevRecord.ffembg}</td>
						 				<td width="3%" align="right" class="tableCellDangerousGoods" >${fraktbrevRecord.ffindx}</td>
						 				<td width="5%" align="right" class="tableCellDangerousGoods" >${fraktbrevRecord.ffantk}</td>
						 				<td width="5%" align="right" class="tableCellDangerousGoods" >${fraktbrevRecord.ffante}</td>
						 				<td width="5%" align="right" class="tableCellDangerousGoods">${fraktbrevRecord.ffenh}</td>
						 				<td align="right" class="tableCellDangerousGoods" >${fraktbrevRecord.ffpoen}</td>
						 				
					 					<td width="2%" align="center" class="text14" >
						               		<c:if test="${not empty fraktbrevRecord.fvlinr}">
						               			<c:if test="${ model.record.singleLine == 'N' }">
							               			<a id="deleteLine_${counter.count}" onClick="deleteItemLine(this);">
							               				<img valign="bottom" src="resources/images/delete.gif" border="0" alt="remove">
								               		</a>
							               		</c:if>
						               		</c:if> 	
							 			</td>
						 			</tr>
					 			</c:forEach>					 			
					 			</tbody>
					 			<tfoot>
					 				<tr style="background-color:#DDDDDD" >	
									
									<td align="center" class="text14"><b>TOT</b></td>
									<td align="left" class="text14"></td>
									<td align="left" class="text14">${model.record.hegm1}</td>
						 			<td align="right" class="text14">${model.record.hent}</td>
						 			<td align="left" class="text14">&nbsp;</td>
						 			<td align="left" class="text14">${model.record.hevs1}</td>
						 			<td align="right" class="text14">${model.record.hevkt}</td>
						 			<td align="right" class="text14">&nbsp;</td>
						 			<td align="right" class="text14">&nbsp;</td>
						 			<td align="right" class="text14">&nbsp;</td>
						 			<td align="right" class="text14">${model.record.hem3}</td>
						 			<td align="right" class="text14"><span title="helm"></span>${model.record.helm}</td>
					 				<%--
						 			<td align="right" class="text14"><span title="helmla">&nbsp;</span>
						 				<input onBlur="checkHelmla(this);" onKeyPress="return amountKey(event)" type="text" class="inputTextMediumBlue10Bold" style="text-align:right;" name="helmla" id="helmla" size="5" maxlength="5" value="${model.record.helmla}">
						 			</td>
						 			 --%>
						 			<td width="3%" align="left" class="text14">&nbsp;</td>
						 			<td width="3%" align="right" class="text14">&nbsp;</td>
						 			<td width="3%" align="right" class="text14">&nbsp;</td>
						 			<td width="5%" align="right" class="text14">&nbsp;</td>
						 			<td width="5%" align="right" class="text14">&nbsp;</td>
						 			<td width="5%" align="right" class="text14">&nbsp;</td>
						 			<%--heopen = sum of ADR field (Dang.goods) --%>
						 			<td align="right" class="text14"><span title="hepoen">&nbsp;</span>${model.record.hepoen}</td>
						 			
						 			<td width="2%" align="right" class="text14" ></td>
						 			</tr>
					 			</tfoot>
					 			</table>
							</td>
						</tr>
						<tr height="1">
							<td >
								<input type="hidden" name="totalNumberOfLines" id="totalNumberOfLines" value="${totalNumberOfLines}" >
				 				<%--this hidden field is crucial for ADD NEW line functionality. We will send the new line = upperCurrentItemlineNr + 1 --%>
								<input type="hidden" id="upperCurrentItemlineNr" name="upperCurrentItemlineNr" value="${upperCurrentItemlineNr}">
							</td>
						</tr>
						
						<%-- <c:if test="${not empty model.record.heunik}"> --%>
							<tr height="10"><td align="left" ></td></tr>
							
					 		<%-- CREATE NEW / UPDATE LINE only when the ORDER has been saved first (heunik!=null) --%>
					 		<tr >
								<td align="bottom" colspan="3" class="text14">&nbsp;&nbsp;<img style="vertical-align:bottom;" src="resources/images/update.gif" width="10px" height="10px" border="0" alt="update item line">
								&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.itemLine"/>
								</td>
							</tr>
					 		<tr>
								<td colspan="2" style="padding: 3px;">
									<table align="left" border="0" style="width:100%; background-color:#778899">
									<tr class="tableHeaderField10" >
										<td align="center" valign="bottom" class="tableHeaderFieldFirst12"><span title="">&nbsp;Lnr</span></td>
							 			<td align="center" valign="bottom" class="tableHeaderFieldFirst12"><span title="fmmrk1">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.marks"/></span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvant/hent(Tot)">&nbsp;<font class="text14RedBold" >*</font><spring:message code="systema.ebooking.orders.form.detail.update.label.antal"/>&nbsp;</span></td>
							 			<td align="center" valign="bottom" class="tableHeaderField12"><span title="fvpakn">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.forpak"/></span></td>
							 			<td align="center" valign="bottom" class="tableHeaderField12"><span title="fvvt">&nbsp;<font class="text14RedBold" >*</font><spring:message code="systema.ebooking.orders.form.detail.update.label.goodsDesc"/></span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvvkt/hevkt(Tot)">&nbsp;<font class="text14RedBold" >*</font><spring:message code="systema.ebooking.orders.form.detail.update.label.weight"/>&nbsp;</span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvlen">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.length"/>&nbsp;</span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvbrd">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.width"/>&nbsp;</span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvhoy">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.height"/>&nbsp;</span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvvol/hem3(Tot)">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.m3"/>&nbsp;</span></td>
							 			<td align="right" valign="bottom" class="tableHeaderField12"><span title="fvlm/helm(Tot)">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.lm.fa"/>&nbsp;</span></td>
							 			<td align="center" valign="bottom" class="tableHeaderField12"><span title="Farlig gods">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.farliggoods"/>&nbsp;</span></td>
						 			
							 		</tr>
							 		<tr >
							 			<td align="center" >&nbsp;
											<input title="from model" tabindex=-1 class="text14BoldLightGreenForItemLinenrBookingBg" readonly type="text" name="fvlinr" id="fvlinr" size="3" value='${model.record.fraktbrevRecord.fvlinr}'>
										</td>	
							 			<td align="center" class="text14" nowrap>
						 					<input type="text" class="inputTextMediumBlue" name="fmmrk1" id="fmmrk1" size="16" maxlength="15" value="${model.record.fraktbrevRecord.fmmrk1}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlueMandatoryField" style="text-align:right;" name="fvant" id="fvant" size="7" maxlength="7" value="${model.record.fraktbrevRecord.fvant}">
						 				</td>
						 				<td align="center" class="text14" nowrap>
							 				<input type="text" onBlur="searchPackingCodesNewLineOnBlur(this);" class="inputTextMediumBlue" name="fvpakn" id="fvpakn" size="8" maxlength="7" value="${model.record.fraktbrevRecord.fvpakn}">
							 				<a tabindex=-1 id="fvpaknIdLinkNewLine" onClick="searchPackingCodesNewLine(this);">
	 											<img id="imgfvpaknSearch" align="bottom" style="cursor:pointer;" src="resources/images/find.png" height="12px" width="12px" border="0" alt="search">
	 										</a>
						 				</td>
						 				<td align="center" class="text14" nowrap>
							 				<input type="text" class="inputTextMediumBlueMandatoryField" name="fvvt" id="fvvt" size="20" maxlength="25" value="${model.record.fraktbrevRecord.fvvt}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlueMandatoryField" style="text-align:right;" name="fvvkt" id="fvvkt" size="5" maxlength="9" value="${model.record.fraktbrevRecord.fvvkt}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onBlur="calculateVolume(this);" onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlue" style="text-align:right;" name="fvlen" id="fvlen" size="4" maxlength="4" value="${model.record.fraktbrevRecord.fvlen}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onBlur="calculateVolume(this);" onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlue" style="text-align:right;" name="fvbrd" id="fvbrd" size="4" maxlength="4" value="${model.record.fraktbrevRecord.fvbrd}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onBlur="calculateVolume(this);validateNewItemLine();" onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlue" style="text-align:right;" name="fvhoy" id="fvhoy" size="4" maxlength="4" value="${model.record.fraktbrevRecord.fvhoy}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onFocus="calculateVolume(this);" onBlur="checkVolumeNewLine(this);" onKeyPress="return amountKey(event)" type="text" class="inputTextMediumBlue" style="text-align:right;" name="fvvol" id="fvvol" size="6" maxlength="8" value="${model.record.fraktbrevRecord.fvvol}">
						 				</td>
						 				<td align="right" class="text14" nowrap>
							 				<input onBlur="checkLmNewLine(this);validateNewItemLine();" onKeyPress="return amountKey(event)" type="text" class="inputTextMediumBlue" style="text-align:right;" name="fvlm" id="fvlm" size="4" maxlength="5" value="${model.record.fraktbrevRecord.fvlm}">
						 				</td>
						 				
						 				<td align="center" class="text14" >
							 				<button name="dangerousGoodsNewLineButton" class="buttonGray" type="button" onClick="showPop('dangerousGoodsNewLine');" ><spring:message code="systema.ebooking.button.mere"/>...</button>
							 					<div class="text14" style="position: relative;" align="left">
							 					<span style="position:absolute;left:-350px;top:-25px;" id="dangerousGoodsNewLine" class="popupWithInputText"  >
								           		<table>
								        			<tr>
								        				<td align="center" valign="bottom" class="tableHeaderField11"><span title="ffunnr">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.unNr"/>&nbsp;</span></td>
											 			<td align="right" valign="bottom" class="tableHeaderField11"><span title="ffembg">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.emg"/>&nbsp;</span></td>
											 			<td align="right" valign="bottom" class="tableHeaderField11"><span title="ffindx">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.emg.index"/>&nbsp;</span></td>
											 			<td align="right" valign="bottom" class="tableHeaderField11"><span title="ffantk">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.ant2"/>&nbsp;</span></td>
											 			<td align="right" valign="bottom" class="tableHeaderField11"><span title="ffante">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.mengd"/>&nbsp;</span></td>
											 			<td align="right" valign="bottom" class="tableHeaderField11"><span title="ffenh">&nbsp;<spring:message code="systema.ebooking.orders.form.detail.update.label.unit"/>&nbsp;</span></td>
								        			</tr>
								        			<tr>
								        				<td align="center" class="tableCellDangerousGoods" nowrap>
											 				<input onBlur="validateDangerousGoodsUnnrNewLine();"  type="text" class="inputTextMediumBlue10" style="text-align:right;" name="ffunnr" id="ffunnr" size="5" maxlength="4" value="${model.record.fraktbrevRecord.ffunnr}">
											 				<a tabindex=0 id="ffunnrIdLinkNewLine" onClick="searchDangerousGoodsNewLine(this);">
					 											<img id="imgUnnrSearch" align="bottom" style="cursor:pointer;" src="resources/images/find2.png" height="11px" width="11px" border="0" alt="search">
					 										</a>
										 				</td>
										 				<td align="right" class="tableCellDangerousGoods" nowrap>
											 				<input onBlur="validateDangerousGoodsUnnrNewLine();" type="text" class="inputTextMediumBlue10" style="text-align:right;" name="ffembg" id="ffembg" size="5" maxlength="3" value="${model.record.fraktbrevRecord.ffembg}">
										 				</td>
										 				<td align="right" class="tableCellDangerousGoods" nowrap>
											 				<input onBlur="validateDangerousGoodsUnnrNewLine();" type="text" class="inputTextMediumBlue10" style="text-align:right;" name="ffindx" id="ffindx" size="2" maxlength="2" value="${model.record.fraktbrevRecord.ffindx}">
										 				</td>
										 				<td align="right" class="tableCellDangerousGoods" nowrap>
											 				<input onBlur="validateDangerousGoodsUnnrNewLine();" onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlue10" style="text-align:right;" name="ffantk" id="ffantk" size="5" maxlength="5" value="${model.record.fraktbrevRecord.ffantk}">
										 				</td>
										 				<td align="right" class="tableCellDangerousGoods" nowrap>
											 				<input onBlur="validateDangerousGoodsUnnrNewLine();" onKeyPress="return numberKey(event)" type="text" class="inputTextMediumBlue10" style="text-align:right;" name="ffante" id="ffante" size="7" maxlength="7" value="${model.record.fraktbrevRecord.ffante}">
										 				</td>
										 				<input type="hidden" id="ownAdrFaktNewLine" name="ownAdrFaktNewLine" value="">
										 				<td align="right" class="tableCellDangerousGoods" nowrap>
											 				<select onChange="validateDangerousGoodsUnnrNewLine();" name="ffenh" id="ffenh">
									 							<option value="">?</option>
									 							<option value="KG">KG</option>
									 							<option value="LTR">LTR</option>
									 							<option value="GR">GR</option>
															</select>
										 				</td>
										 				<td class="text14"><button name="dangerousGoodsNewLineButtonClose" class="buttonGrayInsideDivPopup" type="button" onClick="hidePop('dangerousGoodsNewLine');">&nbsp;Ok</button> 
														</td>
								        			</tr>
						 						</table>
						 						</span>
						 						</div>
						 				</td>		
						 				<%-- OBSOLETE: the functionality has been moved to the form post "Save" 
						 				<c:if test="${not empty model.record.status}">
						 					<td align="center"><input onClick="updateItemLine();" class="inputFormSubmit11Slim" type="button" value='<spring:message code="systema.ebooking.orders.form.detail.update.saveLine"/>'></td>
						 				</c:if>
						 				--%>
						 			</tr>
						 			<tr></tr>
						 			</table>
					 			</td>
				 			</tr>
				 			<%-- </c:if>  --%>
						<tr height="5"><td ></td></tr>
	 				</table>
           		</td>
            </tr>
            <tr height="2"><td></td></tr>
             
            <tr>
				<td colspan="2">
					<table style="width:98%;">
					<tr>
						<td align="right">
		 				    <label class="text14Red" id="orderLineErrMsgPlaceHolder"></label>
	 				    </td>
						<td align="right">
							<c:choose>
			 				    <c:when test="${ not empty model.record.heunik }">
			 				    	<c:if test="${not empty model.record.status}">
				 				    	<input tabindex=-1 class="inputFormSubmit submitSaveClazz" type="submit" name="submit" id="submit" value='<spring:message code="systema.ebooking.submit.save"/>'/>
				 				    </c:if>
			 				    </c:when>
			 				    <c:otherwise>
		 				    		<input tabindex=-1 class="inputFormSubmit submitSaveClazz" type="submit" name="submitnew" id="submitnew" value='<spring:message code="systema.ebooking.submit.createnew.order"/>'/>
			 				    </c:otherwise>	
		 				    </c:choose>
	 				    </td>
				    </tr>
				    </table>
			    </td>
			</tr> 	
            <%-- HEADER --%>
	 		<tr>
            		<td>
	        			<table style="width:98%;" class="formFrameHeader" border="0" cellspacing="0" cellpadding="0">
				 		<tr height="15">
				 			<td class="text14White" colspan="5">
							<img onMouseOver="showPop('messageNote_info');" onMouseOut="hidePop('messageNote_info');"style="vertical-align:bottom;" width="12px" height="12px" src="resources/images/info3.png" border="0" alt="info">
			 				<span title="messageNoteConsignee/Carrier/Internal"><spring:message code="systema.ebooking.orders.form.update.label.messageNotes"/></span>
			 				<div class="text14" style="position: relative;" align="left">
								<span style="position:absolute; left:0px; top:0px;" id="messageNote_info" class="popupWithInputText"  >
									<font class="text14">
				           			<b>Message/Notes</b>
				           			<div>
				           			<p>The message will be printed as shown in screen.</p>
				           			<ul>
				           				<li>Max.character per line: 70-characters</li>
				           			    <li>Max.number of lines per message area: 2</li>
				           			</ul>	
				           			</div>
			           			</font>
							</span>
							</div>	
			 				</td>
		 				</tr>
	 				</table>
            		</td>
            </tr>
            <tr>
				<td valign="top" colspan="10">
					<table style="width:98%" class="formFrame" > 		
			 		<tr>
			 			<td valign="top" align="left" style="width:70%" >
				 			<table>
				 				
								<tr>
						 			<td class="text14"><spring:message code="systema.ebooking.orders.form.update.label.messageNotes.receiver"/></td>
						 			<td class="text14">
						 				<%-- this is used ONLY for the delete line operation (mandatory date/linenr) --%>
						 				<c:forEach items="${model.record.messageNoteConsigneeRaw}" var="freeTextRecord" varStatus="counter">
						 					<c:if test="${not empty freeTextRecord.frtli}">
						 						<%-- DEBUG <input type="text" id="ownMessageNoteReceiverLineNr_${freeTextRecord.frtli}" name="ownMessageNoteReceiverLineNr_${freeTextRecord.frtli}" value="${freeTextRecord.frtli}@${freeTextRecord.frtdt}">  --%>
						 						<input type="hidden" id="ownMessageNoteReceiverLineNr_${freeTextRecord.frtli}" name="ownMessageNoteReceiverLineNr_${freeTextRecord.frtli}" value="${freeTextRecord.frtli}@${freeTextRecord.frtdt}">
						 					</c:if>
						 				</c:forEach>
						 				<%-- this is ONLY for presentation issues and the INSERT DML  --%>
						 				
						 				<textarea class="text14UPPERCASE" style="resize: none;overflow-y: scroll;" id="messageNoteConsignee" name="messageNoteConsignee" limit='65,4' cols="80" rows="4" >${model.record.messageNoteConsignee}</textarea>
					 				</td>
				 				</tr>
								<tr>
						 			<td class="text14"><spring:message code="systema.ebooking.orders.form.update.label.messageNotes.carrier"/></td>
						 			<td class="text14">
						 				<%-- this is used ONLY for the delete line operation (mandatory date/linenr) --%>
						 				<c:forEach items="${model.record.messageNoteCarrierRaw}" var="freeTextRecord" varStatus="counter">
						 					<c:if test="${not empty freeTextRecord.frtli}">
						 						<input type="hidden" id="ownMessageNoteCarrierLineNr_${freeTextRecord.frtli}" name="ownMessageNoteCarrierLineNr_${freeTextRecord.frtli}" value="${freeTextRecord.frtli}@${freeTextRecord.frtdt}">
						 					 </c:if>
						 				</c:forEach>
						 				<%-- this is ONLY for presentation issues and the INSERT DML  --%>
						 				<textarea class="text14UPPERCASE" style="resize: none;overflow-y: scroll;" id="messageNoteCarrier" name="messageNoteCarrier" limit='65,4' cols="80" rows="4">${model.record.messageNoteCarrier}</textarea>
					 				</td>
				 				</tr>
				 				<tr>
						 			<td class="text14"><spring:message code="systema.ebooking.orders.form.update.label.messageNotes.sender"/></td>
						 			<td class="text14">
						 				<%-- this is used ONLY for the delete line operation (mandatory date/linenr) --%>
						 				<c:forEach items="${model.record.messageNoteInternalRaw}" var="freeTextRecord" varStatus="counter">
						 					<c:if test="${not empty freeTextRecord.frtli}">
						 						<input type="hidden" id="ownMessageNoteInternalLineNr_${freeTextRecord.frtli}" name="ownMessageNoteInternalLineNr_${freeTextRecord.frtli}" value="${freeTextRecord.frtli}@${freeTextRecord.frtdt}">
						 					</c:if> 
						 				</c:forEach>
						 				<%-- this is ONLY for presentation issues and the INSERT DML  --%>
						 				<textarea class="text14UPPERCASE" style="resize: none;overflow-y: scroll;" id="messageNoteInternal" name="messageNoteInternal" limit='65,4' cols="80" rows="4">${model.record.messageNoteInternal}</textarea>
					 				</td>
				 				</tr>
				 				<tr height="5"><td></td></tr>
				 				<tr>
						 			<td class="text14"><spring:message code="systema.ebooking.orders.form.update.label.email"/></td>
						 			<td class="text14">
						 				<input type="text" class="inputTextMediumBlue11" size="55" maxlength="70" name="wsmail" id="wsmail" value="${model.record.wsmail}">
						 				&nbsp;(<spring:message code="systema.ebooking.orders.form.update.label.email.extra.booking"/>)
					 				</td>									
				 				</tr>
			 				</table>
		 				</td>
		 				<td valign="top" align="left" style="width:30%" >
							<table> 		
					 		<tr>
					 			<td align="left">
					 			<table class="tableBorderWithRoundCorners" width="480px">
									<tr>
							 			<td valign="top" class="text14"><spring:message code="systema.ebooking.orders.form.update.label.archivedocs"/> &nbsp;
						 					<ul>
						 					<c:forEach items="${model.record.archivedDocsRecord}" var="record" varStatus="counter">
						 						<li>
						 						<a target="_blank" href="ebooking_mainorderlist_renderArchivedDocs.do?doclnk=${record.doclnk}">
		    		    							<img title="Archive" style="vertical-align:middle;" src="resources/images/pdf.png" width="14" height="14" border="0" alt="PDF arch.">
		    		    							${record.doctxt}
				   								</a>&nbsp;&nbsp;&nbsp;
				   								</li>
						 					</c:forEach>
						 					</ul>
						 				</td>
									</tr>
								</table>
								</td>
							</tr>	
							</table>
						</td>
			 		</tr>
					</table>
				</td>
			</tr>
			<tr height="10"><td ></td></tr>
	</table>
	</form>

		
<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->
