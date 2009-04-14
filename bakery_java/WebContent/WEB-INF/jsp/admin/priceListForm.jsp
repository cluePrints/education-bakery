<%@include file="/WEB-INF/jsp/commonInclude.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
</head>
<body>
<link rel = "stylesheet" href="default.css" type="text/css">
<script type="text/javascript">
	<%@include file="/WEB-INF/jsp/common/jsCommons.jsp"%>
	
	function bindSelectBox(name, option_value){
		var options = document.getElementById("formBean."+name).options;
		for (i=0; i<options.length; i++) {
			if (options[i].value == option_value) {
				options[i].selected = true;
			}
		}
	}
	
	function setFormFields(form, id, day, mon, year, active, owner_id, desc) {
		form['formBean.id'].value=id;			
		form['formBean.date.date'].value=day;
		form['formBean.date.year'].value=year;
		form['formBean.comment'].value=desc;
		
		form.boxActive.checked=active;		
		boxToField(form.boxActive, 'fldActive'); 
			
		bindSelectBox('owner.id', owner_id);
		bindSelectBox('date.month', mon);		
	}
	
	function showEdit(id, d, m, y, active, owner_id, desc) {
		hidePaginator();
		var form = document.getElementById("actionForm");
		setFormFields(form, id, d,m,y, active, owner_id, desc);
		form.mode.value="EDIT";
		
		var popup = document.getElementById("editForm");
		popup.style.display = "block";
			
	}
	
	function showNew() {		
		showEdit(0, 1, 1, 09, true, 0, '');
		var form = document.getElementById("actionForm");
		form.mode.value="NEW";		
	}
	
	function editContents(id){
		var form = document.getElementById("actionForm");
		form.idForRestore.value=id;
		form['_eventId'].value='editPrice';
		
		form.submit();
	}
	
</script>
	<c:set var="dayStr">
		<fmt:message key="common.date.days.short" bundle="${resourceBundle}"/>
	</c:set>
	<c:set var="monthStr">
		<fmt:message key="common.date.months.short" bundle="${resourceBundle}"/>
	</c:set>
	<c:set var="yearStr">
		<fmt:message key="common.date.years.short" bundle="${resourceBundle}"/>
	</c:set>
<form id="actionForm" method="post">
	<table class="content">
		<tr><td colspan="10"><%@include file="/WEB-INF/jsp/admin/inner/head.jsp"%></td></tr>
		<tr width="90%"><td><%@include file="/WEB-INF/jsp/admin/inner/leftbar.jsp"%></td><td colspan="8">
		<%-- Main content --%>
				<spring:bind path="priceListFormBean">		
				<c:if test="${status.error}">
					<div id="localErrors" class="localError">
						<B>Some error</B><BR>
						<center><input type="button" value="<fmt:message key="forms.common.button.ok" bundle="${resourceBundle}"/>" onclick="document.getElementById('localErrors').style.display='none'"/></center>
					</div>
				</c:if>
				
						
			<%@include file="/WEB-INF/jsp/common/localmenu.jsp" %>
			
			<div id="editForm" class="optionalForm">
				<table>
				<tr>
					<td colspan="2">
						<BR>
						<input type="hidden" name="formBean.id" value="0"/>
						<fmt:message key="pricelists.date.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.date.date" id="formBean.date.date">
							<option value="0"/>
							<c:forTokens items="${dayStr}" delims="," var="token" varStatus="status">
							<option value="${status.count}">${token}</option>
							</c:forTokens>
						</select>
																									
						<select size="1" name="formBean.date.month" id="formBean.date.month">
							<option value="0"/>
							<c:forTokens items="${monthStr}" delims="," var="token" varStatus="status">
							<option value="${status.count-1}">${token}</option>
							</c:forTokens>
						</select>
						
						<select size="1" name="formBean.date.year" id="formBean.date.year">
							<option value="0"/>
							<c:forTokens items="${yearStr}" delims="," var="token" varStatus="status">
							<option value="${token}">${token}</option>
							</c:forTokens>
						</select>
						<BR>
						
						<fmt:message key="pricelists.owner.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.owner.id" id="formBean.owner.id">
							<c:forEach items="${contragents}" var="contragent">
							<option value="${contragent.id}">${contragent}</option>
							</c:forEach>
						</select>
						<BR>
						
						<fmt:message key="pricelists.comment.caption" bundle="${resourceBundle}"/>
						<input type="text" name="formBean.comment" value=""/>
						<BR>
						
						<input id="fldActive" name="formBean.active" type="hidden" value="0"/>
						<input name="boxActive" type="checkbox" onclick="boxToField(this, 'fldActive')"/><fmt:message key="pricelists.active.caption" bundle="${resourceBundle}"/>
						<BR>					
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="<fmt:message key="forms.common.button.ok" bundle="${resourceBundle}"/>"/>
						<input type="button" value="<fmt:message key="forms.common.button.cancel" bundle="${resourceBundle}"/>" onclick="hideEdit()"/>
						<BR>
					</td>
				</tr>
				</table>			
			</div>	
			</spring:bind>
			<spring:nestedPath path="priceListFormBean">
			<%@include file="/WEB-INF/jsp/common/paginator.jsp" %>
			</spring:nestedPath>
			<table>
				<tr class="tableHead">
				<td><u><fmt:message key="pricelists.table.id.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="pricelists.table.date.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="pricelists.table.owner.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="pricelists.table.comment.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="pricelists.table.actions.caption" bundle="${resourceBundle}"/></u></td></tr>
				<%-- Iteration over items--%>
				<c:forEach items="${priceLists}" var="priceList">
						<c:if test="${priceList.active>0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_ACTIVE%>'>
						</c:if>
						<c:if test="${priceList.active<=0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_INACTIVE%>'>
						</c:if>
								<td><c:out value="${priceList.id}"/></td>
								<td><c:out value="${priceList.date}" escapeXml="false"/></td>
								<td><c:out value="${priceList.owner}" escapeXml="false"/></td>
								<td><c:out value="${priceList.comment}" escapeXml="false"/></td>
								<td><a href="javascript:<c:if test="${priceList.active>0}">de</c:if>activate(<c:out value="${priceList.id}"/>)">${priceList.active<=0 ? 'Restore' : 'Remove'}</a><BR>
									<a href="javascript:showEdit(${priceList.id},${priceList.date.date},${priceList.date.month},${priceList.date.year},${priceList.active>0 ? 'true' : 'false'}, ${priceList.owner.id}, '${priceList.comment}')"><fmt:message key="pricelists.table.actions.edit.head.caption" bundle="${resourceBundle}"/></a><BR>
									<a href="javascript:editContents(${priceList.id})"/><fmt:message key="pricelists.table.actions.edit.content.caption" bundle="${resourceBundle}"/></a></td></tr>				
				</c:forEach>
				<%-- /Iteration --%>
			</table>			
		<%-- /main content--%>
		</td><td><%@include file="/WEB-INF/jsp/admin/inner/rightbar.jsp"%></td></tr>
		<tr><td colspan="10"><%@include file="/WEB-INF/jsp/admin/inner/footer.jsp"%></td></tr>
	</table>	
</form>
</body>
</html>