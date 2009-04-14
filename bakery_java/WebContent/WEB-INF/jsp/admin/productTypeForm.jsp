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
	
	function setFormFields(form, id, name, active, unit_id) {
		form['formBean.id'].value=id;			
		form['formBean.name'].value=name;
		form.boxActive.checked=active;
		boxToField(form.boxActive, 'fldActive'); 
			
		var units = document.getElementById("formBean.unit.id").options;
		for (i=0; i<units.length; i++) {
			if (units[i].value == unit_id) {
				units[i].selected = true;
			}
		}
	}
	
	function showEdit(id, name, active, unit_id) {
		hidePaginator();
		var form = document.getElementById("actionForm");
		setFormFields(form, id, name, active, unit_id);
		form.mode.value="EDIT";
		
		var popup = document.getElementById("editForm");
		popup.style.display = "block";
			
	}
	
	function showNew() {		
		showEdit(0, '', true, 0);
		var form = document.getElementById("actionForm");
		form.mode.value="NEW";		
	}
	
	
</script>
<form id="actionForm" method="post">
	<table class="content">
		<tr><td colspan="10"><%@include file="/WEB-INF/jsp/admin/inner/head.jsp"%></td></tr>
		<tr width="90%"><td><%@include file="/WEB-INF/jsp/admin/inner/leftbar.jsp"%></td><td colspan="8">
		<%-- Main content --%>
				<spring:bind path="productTypeFormBean">		
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
						<fmt:message key="forms.search.caption.name" bundle="${resourceBundle}"/>			
						<input type="text" name="formBean.name" value=""/>
						<select size="1" name="formBean.unit.id" id="formBean.unit.id">
							<c:forEach items="${units}" var="unit">
							<option value="${unit.id}">${unit.name}</option>
							</c:forEach>
						</select>
						<BR>
						
						<input id="fldActive" name="formBean.active" type="hidden" value="0"/>
						<input name="boxActive" type="checkbox" onclick="boxToField(this, 'fldActive')"/><fmt:message key="forms.search.caption.active" bundle="${resourceBundle}"/>
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
			<spring:nestedPath path="productTypeFormBean">
			<%@include file="/WEB-INF/jsp/common/paginator.jsp" %>
			</spring:nestedPath>
			<table>
				<tr class="tableHead">
				<td><u><fmt:message key="productTypes.table.id.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="productTypes.table.name.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="productTypes.table.unit.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="productTypes.table.actions.caption" bundle="${resourceBundle}"/></u></td></tr>
				<%-- Iteration over items--%>
				<c:forEach items="${productTypes}" var="productType">
					<%-- if is active--%>
						<c:if test="${productType.active>0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_ACTIVE%>'>
						</c:if>
						<c:if test="${productType.active<=0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_INACTIVE%>'>
						</c:if>
								<td><c:out value="${productType.id}"/></td>
								<td><c:out value="${productType.name}" escapeXml="false"/></td>
								<td><c:out value="${productType.unit}" escapeXml="false"/></td>
								<td><a href="javascript:<c:if test="${productType.active>0}">de</c:if>activate(<c:out value="${productType.id}"/>)">${productType.active<=0 ? 'Restore' : 'Remove'}</a><BR>
									<a href="javascript:showEdit(${productType.id},'${productType.name}', ${productType.active>0 ? 'true' : 'false'}, ${productType.unit.id})">Edit</a><BR></td></tr>				
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