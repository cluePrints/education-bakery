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
	
	function setFormFields(form, id, active, price, parent_id, product_id) {
		form['formBean.id'].value=id;			
		form['formBean.price'].value=price;
		form['formBean.parent.id'].value=parent_id;
		form['formBean.product.id'].value=product_id;
		
		form.boxActive.checked=active;		
		boxToField(form.boxActive, 'fldActive'); 
					
		var products = document.getElementById("formBean.product.id").options;
		for (i=0; i<products.length; i++) {
			if (products[i].value == product_id) {
				products[i].selected = true;
			}
		}
		
		var parents = document.getElementById("formBean.parent.id").options;
		for (i=0; i<parents.length; i++) {
			if (parents[i].value == parent_id) {
				parents[i].selected = true;
			}
		}	
	}
	
	function showEdit(id, active, price, parent_id, product_id) {
		hidePaginator();
		var form = document.getElementById("actionForm");
		setFormFields(form, id, active, price, parent_id, product_id);
		form.mode.value="EDIT";
		
		var popup = document.getElementById("editForm");
		popup.style.display = "block";
			
	}
	
	function showNew() {		
		showEdit(0, 1, 0, 0, 0);
		var form = document.getElementById("actionForm");
		form.mode.value="NEW";		
	}
</script>
<form id="actionForm" method="post">
	<table class="content">
		<tr><td colspan="10"><%@include file="/WEB-INF/jsp/admin/inner/head.jsp"%></td></tr>
		<tr width="90%"><td><%@include file="/WEB-INF/jsp/admin/inner/leftbar.jsp"%></td><td colspan="8">
		<%-- Main content --%>
				<spring:bind path="priceFormBean">		
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
						
						<fmt:message key="prices.parent.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.parent.id" id="formBean.parent.id">
							<c:forEach items="${priceLists}" var="priceList">
							<option value="${priceList.id}">${priceList}</option>
							</c:forEach>
						</select>
						<BR>
						
						<fmt:message key="prices.product.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.product.id" id="formBean.product.id">
							<c:forEach items="${productTypes}" var="productType">
							<option value="${productType.id}">${productType}</option>
							</c:forEach>
						</select>
						<BR>
						
						<fmt:message key="prices.price.caption" bundle="${resourceBundle}"/>
						<input type="text" name="formBean.price" value="0.0"/>
						<BR>
						
						<input id="fldActive" name="formBean.active" type="hidden" value="0"/>
						<input name="boxActive" type="checkbox" onclick="boxToField(this, 'fldActive')"/><fmt:message key="prices.active.caption" bundle="${resourceBundle}"/>
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
			<spring:nestedPath path="priceFormBean">
			<%@include file="/WEB-INF/jsp/common/paginator.jsp" %>
			</spring:nestedPath>
			<table>
				<tr class="tableHead">
				<td><u><fmt:message key="prices.table.id.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="prices.table.parent.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="prices.table.product.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="prices.table.price.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="prices.table.actions.caption" bundle="${resourceBundle}"/></u></td></tr>
				
				<%-- Iteration over items --%>
				<c:forEach items="${prices}" var="priceItem">
						<c:if test="${priceItem.active>0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_ACTIVE%>'>
						</c:if>
						<c:if test="${priceItem.active<=0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_INACTIVE%>'>
						</c:if>
								<td>${priceItem.id}</td>
								<td>${priceItem.parent}</td>
								<td>${priceItem.product}</td>
								<td>${priceItem.price}</td>
								<td><a href="javascript:<c:if test="${priceItem.active>0}">de</c:if>activate(<c:out value="${priceItem.id}"/>)">${priceItem.active<=0 ? 'Restore' : 'Remove'}</a><BR>
									<a href="javascript:showEdit(${priceItem.id},${priceItem.active>0 ? 'true' : 'false'},${priceItem.price},${priceItem.parent.id},${priceItem.product.id})"><fmt:message key="prices.table.actions.edit.caption" bundle="${resourceBundle}"/></a><BR></tr>				
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