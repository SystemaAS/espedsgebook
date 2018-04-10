<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!-- ======================= header =====================================-->
<jsp:include page="/WEB-INF/views/headerEbookingChildWindows.jsp" />
<!-- =====================end header ====================================-->

	<%-- specific jQuery functions for this JSP (must reside under the resource map since this has been
	specified in servlet.xml as static <mvc:resources mapping="/resources/**" location="WEB-INF/resources/" order="1"/> --%>
	<SCRIPT type="text/javascript" src="resources/js/ebooking_childwindow.js?ver=${user.versionEspedsg}"></SCRIPT>
	
	<table width="90%" height="500px" class="tableBorderWithRoundCorners3D_RoundOnlyOnBottom" cellspacing="0" border="0" cellpadding="0">
		<tr height="5"><td colspan="2"></td></tr>
		<tr>
			<td colspan="3" class="text14Bold">&nbsp;&nbsp;&nbsp;
			<img title="search" valign="bottom" src="resources/images/search.gif" width="24px" height="24px" border="0" alt="search">
			<spring:message code="systema.ebooking.childwindow.customer.label.title"/>
			</td>
		</tr>
		<tr height="20"><td colspan="2"></td></tr>
		<tr>
		<td valign="top">
		<form action="ebooking_childwindow_customer.do?action=doFind" name="searchCustomerForm" id="searchCustomerForm" method="post">
			<input type="hidden" name="ctype" id="ctype" value="${model.container.ctype}">
			<%-- =====================================================  --%>
          	<%-- Here we have the search [Customer] popup window --%>
          	<%-- =====================================================  --%>
          		<%-- this container table is necessary in order to separate the datatables element and the frame above, otherwise
			 	the cosmetic frame will not follow the whole datatable grid including the search field... --%>
				<table id="containerdatatableTable" cellspacing="2" align="left" width="100%">
					
					<%-- NO FILTER since the data set won't be large (less than 50 in all customers)
					<tr height="5"><td></td></tr>
					<tr>
					<td>
						<table>
						<tr>
							<td class="text11">&nbsp;Customer No.</td>
							<td class="text11">&nbsp;<input type="text" class="inputText" name="sokknr" id="sokknr" size="8" maxlength="8" value="${model.container.sokknr}"></td>
						
							<td class="text11">&nbsp;&nbsp;&nbsp;Name</td>
							<td class="text11">&nbsp;<input type="text" class="inputText" name="soknvn" id="soknvn" size="15" maxlength="35" value="${model.container.soknvn}"></td>
						
							<td class="text11">&nbsp;&nbsp;&nbsp;Post.Code/City/Country</td>
							<td class="text11">&nbsp;<input type="text" class="inputText" name="kunpnsted" id="kunpnsted" size="15" maxlength="10" value="${model.container.kunpnsted}"></td>
						
							<td class="text11">&nbsp;</td>
	           				<td align="right">&nbsp;<input class="inputFormSubmit" type="submit" name="submit" value='<spring:message code="systema.transportdisp.search"/>'></td>
	           				<td width="15px" >&nbsp;</td>
	           				<td class="text11" style="color:#9F6000;"><label class="isa_warning" >&nbsp;&nbsp;Adressekunder&nbsp;&nbsp;</label></td>
	           				
		           		</tr>
		           		
		           		</table>
					</td>
					</tr>
					<%-- Validation errors 
					<spring:hasBindErrors name="record"> <%-- name must equal the command object name in the Controller
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
					--%>					
					
					<tr><td><hr size="1" width="100%"/></td></tr>								           		
	           		<tr height="15"><td></td></tr>
					
					<tr class="text12" >
					<td class="ownScrollableSubWindowDynamicWidthHeight" width="100%" style="height:50em;">
					<%-- this is the datatables grid (content)--%>
					<table id="customerList" class="display compact cell-border" width="100%">
						<thead>
						<tr style="background-color:#EEEEEE">
						    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customer.label.customer.nr"/></th>  
						    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customer.label.address.name"/></th>
		                    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customer.label.address.address"/></th>
		                    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customer.label.address.postnrStedLand"/></th>
		                    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customer.label.customer.name"/></th>  
						    <th class="text11">&nbsp;<spring:message code="systema.ebooking.childwindow.customer.label.customer.address"/></th>  
						    
		                </tr> 
		                </thead>
		                
		                <tbody>
		                <c:forEach var="record" items="${model.customerList}" varStatus="counter">    
			               <c:choose>           
			                   <c:when test="${counter.count%2==0}">
			                       <tr class="text11" >
			                   </c:when>
			                   <c:otherwise>   
			                       <tr class="text11" >
			                   </c:otherwise>
			               </c:choose>
			               <td class="text11MediumBlue" style="cursor:pointer;" id="kundnr_${record.kundnr}@navn_${record.navn}@adr1_${record.gateAdr}@adr2_${record.adresse2}@postnrsted_${record.postnrSted}@kundname_${record.kundNavn}@kundaddress_${record.kundPnSt}@counter_${counter.count}">&nbsp;${record.kundnr}</td>
			               <td class="text11" >&nbsp;${record.navn}</td>
			               <td class="text11" >&nbsp;${record.gateAdr}&nbsp;${record.adresse2}</td>
			               <td class="text11" >&nbsp;${record.postnrSted}&nbsp;${record.land}</td>
			               <td class="text11" >&nbsp;${record.kundNavn}</td>
			               <td class="text11" >&nbsp;${record.kundPnSt}</td>
			               
			            </tr> 
			            </c:forEach>
			            </tbody>
		            </table>
		            </td>
	           		</tr>
        			</table>
				
		</form>	
		</td>
		</tr>
	</table> 
