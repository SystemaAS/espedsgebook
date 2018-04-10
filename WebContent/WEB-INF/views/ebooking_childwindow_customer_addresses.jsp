<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!-- ======================= header =====================================-->
<jsp:include page="/WEB-INF/views/headerEbookingChildWindows.jsp" />
<!-- =====================end header ====================================-->

	<%-- specific jQuery functions for this JSP (must reside under the resource map since this has been
	specified in servlet.xml as static <mvc:resources mapping="/resources/**" location="WEB-INF/resources/" order="1"/> --%>
	<SCRIPT type="text/javascript" src="resources/js/ebookingglobal_edit.js?ver=${user.versionEspedsg}"></SCRIPT>
	<SCRIPT type="text/javascript" src="resources/js/ebooking_childwindow.js?ver=${user.versionEspedsg}"></SCRIPT>
	<%-- for dialog popup --%>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<style type = "text/css">
	.ui-dialog{font-size:10pt;}
	.ui-datepicker { font-size:9pt;}
	</style>
	
	<table width="90%" height="500px" class="tableBorderWithRoundCorners3D_RoundOnlyOnBottom" cellspacing="0" border="0" cellpadding="0">
		<tr height="5"><td colspan="2"></td></tr>
		<tr>
			<td>
			<table width="90%" cellspacing="0" border="0" cellpadding="0">
				<tr>
					<td valign="top" class="text14Bold">&nbsp;&nbsp;&nbsp;
						<img title="search" src="resources/images/search.gif" width="20px" height="20px" border="0" alt="search">
						<spring:message code="systema.ebooking.childwindow.customeraddresses.label.title"/>
					</td>
					<td height="25" valign="bottom" align="right" class="text12MediumBlue">Kundenr&nbsp;<b>${user.custNr}&nbsp;${model.custName}</b>&nbsp;&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
		<td valign="top">
				
				<%-- =====================================================  --%>
          		<%-- Here we have the search [Customer] popup window --%>
          		<%-- =====================================================  --%>
       			<%-- this container table is necessary in order to separate the datatables element and the frame above, otherwise
		 		the cosmetic frame will not follow the whole datatable grid including the search field... --%>
		 									           		
	           		<tr height="10"><td></td></tr>
					
					<tr><td><hr size="1" width="100%"/></td></tr>								           		
	           		<tr height="15"><td></td></tr>
					
					<tr class="text12" >
					<td class="ownScrollableSubWindowDynamicWidthHeight" width="100%" style="height:35em;">
					<%-- this is the datatables grid (content)--%>
					<table id="customerAddressesList" class="display compact cell-border" width="100%">
						<thead>
						<tr style="background-color:#EEEEEE">
							<th width="2%" class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.id"/></th>   
		                    <th width="2%" class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.customerid"/></th>   
		                    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.name"/></th>
		                    <th width="2%" class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.update"/></th>
		                    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.address"/></th>
		                    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.postnrStedLand"/></th>
		                    <th width="2%" class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.label.customeradr.address.delete"/></th>
		                    
		                </tr> 
		                </thead>
		                
		                <tbody>
		                <c:forEach var="record" items="${model.customerAdressesList}" varStatus="counter">    
			               <c:choose>           
			                   <c:when test="${counter.count%2==0}">
			                       <tr class="text11" >
			                   </c:when>
			                   <c:otherwise>   
			                       <tr class="text11" >
			                   </c:otherwise>
			               </c:choose>
			               
			               <td width="2%" class="text11MediumBlue" style="cursor:pointer;" id="vadrnr_${record.vadrnr}#navn_${record.vadrna}#adr1_${record.vadrn1}#adr2_${record.vadrn2}#postnrsted_${record.vadrn3}#tlf_${record.vatlf}#mail_${record.vamail}#valand_${record.valand}#counter_${counter.count}">
			               	 <font class="text11MediumBlue" >${record.vadrnr}</font>
			               </td>
			               <td align="center" width="2%" class="text11MediumBlue" ><font class="text11MediumBlue" >${record.vakure}</font></td>
			               <td class="text11" >&nbsp;${record.vadrna}</td>
			               <td id="recordUpdate_${user.custNr}_${record.vadrnr}" onClick="getRecord(this);" align="center" width="2%" class="tableCellFirst" style="cursor:pointer; border-style: solid;border-width: 0px 1px 1px 0px;border-color:#FAEBD7;">
           					 <img src="resources/images/update.gif" border="0" alt="edit">
			               </td>
			               <td class="text11" >&nbsp;${record.vadrn1}&nbsp;${record.vadrn2}</td>
			               <td class="text11" >&nbsp;${record.vadrn3}&nbsp;${record.valand}</td>
			               <td width="2%" align="center" class="text11" >&nbsp;
			               		<a style="cursor:pointer;" id="kundnr_${user.custNr}@vadrnr_${record.vadrnr}" onClick="doDeleteCustomerAddress(this);" tabindex=-1>
	    		    				<img title="Delete" style="vertical-align:bottom;" src="resources/images/delete.gif" border="0" alt="delete">
    		    				</a>
			               </td>
			            </tr> 
			            </c:forEach>
			            </tbody>
		            </table>
           		</td>
   			</tr>
   			
   			<%-- Vedlikehold on adresses --%>
				<tr height="5"><td></td></tr>
				<tr>
					<td align="center" colspan="3" class="text12">
						<img title="search" valign="bottom" src="resources/images/vedlikehold.png" width="30px" height="30px" border="0" alt="search">
						<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.title"/><br>
					</td>
				</tr>
				<tr>
				<td align="center">
					<form action="ebooking_childwindow_customer_addresses_vedlikehold.do?action=doUpdate" name="addressVedlikeholdForm" id="addressVedlikeholdForm" method="post">
					<input type="hidden" name="ctype" id="ctype" value="${model.ctype}">
					<%--<input type="hidden" name="wkundnr" id="wkundnr" value="${model.container.wkundnr}">--%>
					<input type="hidden" name="wkundnvn" id="wkundnvn" value="${model.custName}">
					<input type="hidden" name="applicationUser" id="applicationUser" value='${user.user}'> 
					<input type="hidden" name="vadrnr" id="vadrnr" value=''> 
				
					<table class="tableBorderWithRoundCornersLightGray">
						<tr>
							<td class="text11">
								<span title="vakure">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.customernr"/></span>
							</td>
							<td class="text11">
								<input type="text" class="inputTextMediumBlue11" name="vakure" id="vakure" size="9" maxlength="8" value="">
									<%--
									<c:otherwise>
										<input type="text" class="inputTextMediumBlue11" name="ownKundnr" id="ownKundnr" size="9" maxlength="8" value="${user.custNr}">
									</c:otherwise>
								</c:choose>
								 --%>
								&nbsp;&nbsp;<button name="newRecordButton" id="newRecordButton" class="inputFormSubmitStd" type="button" >Slett verdiene</button>&nbsp;
							</td>
						</tr>
						<tr>	
							<td class="text11">
								<span title="vadrna">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.name"/></span>
							</td>
							<td class="text11">
								<input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlue11MandatoryField" name="vadrna" id="vadrna" size="31" maxlength="30" value="${model.record.vadrna}">
								<font class="text16RedBold" >*</font>
							</td>
						</tr>
						<tr>	
							<td class="text11">
								<span title="vadrn1">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.address1"/></span>
							</td>
							<td class="text11">
								<input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlue11MandatoryField" name="vadrn1" id="vadrn1" size="31" maxlength="30" value="${model.record.vadrn1}">
								<font class="text16RedBold" >*</font>
							</td>
						</tr>
						
						<tr>
							<td class="text11">
								<span title="vadrn2">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.address2"/></span>
							</td>
							<td class="text11">
								<input type="text" class="inputTextMediumBlue11" name="vadrn2" id="vadrn2" size="30" maxlength="30" value="${model.record.vadrn2}">
							</td>
						</tr>
						<tr>	
							<td class="text11">
								<span title="vadrn3">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.postnrSted"/></span>
							</td>
							<td class="text11">
								<input required oninvalid="this.setCustomValidity('Obligatorisk')" oninput="setCustomValidity('')" type="text" class="inputTextMediumBlue11MandatoryField" name="vadrn3" id="vadrn3" size="31" maxlength="30" value="${model.record.vadrn3}">
								<font class="text16RedBold" >*</font>
							</td>
						</tr>
						<tr>	
							<td class="text11">
								<span title="vatlf">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.countryCode"/></span>
							</td>
							<td class="text11">
								<input type="text" class="inputTextMediumBlue11" name="valand" id="valand" size="5" maxlength="2" value="${model.record.valand}">
							</td>
						</tr>
						
						<tr>
							<td class="text11">
								<span title="vatlf">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.phone"/></span>
							</td>
							<td class="text11">
								<input type="text" class="inputTextMediumBlue11" name="vatlf" id="vatlf" size="31" maxlength="30" value="${model.record.vatlf}">
							</td>
						</tr>
						<tr>	
							<td class="text11">
								<span title="vamail">&nbsp;<spring:message code="systema.ebooking.childwindow.customeraddresses.maint.label.customeradr.address.email"/></span>
							</td>
							<td class="text11">
								<input type="text" class="inputTextMediumBlue11" name="vamail" id="vamail" size="31" maxlength="60" value="${model.record.vamail}">
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								<input tabindex=-1 class="inputFormSubmit" type="submit" name="submit" id="submit" value='<spring:message code="systema.ebooking.submit.save"/>'/>
								
							</td>
						</tr>
						<%-- Validation errors --%>	
						<spring:hasBindErrors name="record"> <%-- name must equal the command object name in the Controller --%>	
						<tr height="5"><td></td></tr>
						<tr>
							<td colspan="20">
				            	<table align="left" border="0" cellspacing="0" cellpadding="0">
				            	<tr>
								<td class="textError">					
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
						<%-- Validation errors on model --%>
						<%-- -------------------------- --%>				
						<c:if test="${not empty model.errorMessage}">
							<tr height="5"><td></td></tr>
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
							
	           		</table>
	           		</form>
	           	</td>
	           	</tr>
	           	<tr height="15"><td></td></tr>
						
	           
     </table>

			
			


	
