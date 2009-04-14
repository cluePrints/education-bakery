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
	
	function setFormFields(form, id, active, amount, desc, c_day, c_mon, c_year, dest_id, source_id, pli_id, order_id) {
		form['formBean.id'].value=id;					
				
		form.boxActive.checked=active;		
		boxToField(form.boxActive, 'fldActive');
		
		form['formBean.amount'].value=amount;
		form['formBean.desc'].value=desc;
		
		bindSelectBox('sourceAccount.id', source_id);
		bindSelectBox('destinationAccount.id', dest_id);
		bindSelectBox('price.id', pli_id);
		bindSelectBox('order.id', order_id);
		bindSelectBox('date.date', c_day);
		bindSelectBox('date.month', c_mon);
		bindSelectBox('date.year', c_year);
	}
	
	function showEdit(id, active, amount, desc, c_day, c_month, c_year, dest_id, source_id, pli_id, order_id) {
		hidePaginator();
		var form = document.getElementById("actionForm");
		setFormFields(form, id, active, amount, desc, c_day, c_month, c_year, dest_id, source_id, pli_id, order_id);
		form.mode.value="EDIT";
		
		var popup = document.getElementById("editForm");
		popup.style.display = "block";
			
	}
	
	function showNew() {		
		showEdit(0, 1, 0, '', 0, 0, 0, 0, 0, 0, 0);
		var form = document.getElementById("actionForm");
		form.mode.value="NEW";		
	}
	
	function editMoneyMoves(id){
		var form = document.getElementById("actionForm");
		form.idForRestore.value=id;
		form['_eventId'].value='editMoneyMoves';
		
		form.submit();
	}
	
	function editProductMoves(id){
		var form = document.getElementById("actionForm");
		form.idForRestore.value=id;
		form['_eventId'].value='editProductMoves';
		
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
				<spring:bind path="moneyMoveFormBean">		
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
						<fmt:message key="moneyMoves.date.caption" bundle="${resourceBundle}"/>
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
						
						<fmt:message key="moneyMoves.destinationAccount.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.destinationAccount.id" id="formBean.destinationAccount.id">
							<c:forEach items="${accounts}" var="account">
							<option value="${account.id}">${account}</option>
							</c:forEach>
						</select>
						<BR>
						
						<fmt:message key="moneyMoves.sourceAccount.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.sourceAccount.id" id="formBean.sourceAccount.id">
							<c:forEach items="${accounts}" var="account">
							<option value="${account.id}">${account}</option>
							</c:forEach>
						</select>
						<BR>
												
						<fmt:message key="moneyMoves.order.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.order.id" id="formBean.order.id">
							<c:forEach items="${orders}" var="order">
							<option value="${order.id}">${order}</option>
							</c:forEach>
						</select>
						<BR>
						
						<fmt:message key="moneyMoves.price.caption" bundle="${resourceBundle}"/>
						<select size="1" name="formBean.price.id" id="formBean.price.id">
							<c:forEach items="${prices}" var="price">
							<option value="${price.id}">${price}</option>
							</c:forEach>
						</select>
						<BR>
						
						<fmt:message key="moneyMoves.amount.caption" bundle="${resourceBundle}"/>
						<input type="text" size="8" name="formBean.amount">
						<BR>
						
						<fmt:message key="moneyMoves.desc.caption" bundle="${resourceBundle}"/>
						<input type="text" size="8" name="formBean.desc">
						<BR>
						
						<input id="fldActive" name="formBean.active" type="hidden" value="0"/>
						<input name="boxActive" type="checkbox" onclick="boxToField(this, 'fldActive')"/><fmt:message key="moneyMoves.active.caption" bundle="${resourceBundle}"/>
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
			<spring:nestedPath path="moneyMoveFormBean">
			<%@include file="/WEB-INF/jsp/common/paginator.jsp" %>
			</spring:nestedPath>
			<table>
				<tr class="tableHead">
				<td><u><fmt:message key="moneyMoves.table.id.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.date.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.destinationAccount.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.sourceAccount.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.amount.caption" bundle="${resourceBundle}"/></u></td>				
				<td><u><fmt:message key="moneyMoves.table.price.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.total.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.order.caption" bundle="${resourceBundle}"/></u></td>
				<td><u><fmt:message key="moneyMoves.table.actions.caption" bundle="${resourceBundle}"/></u></td></tr>
				<%-- Iteration over items--%>
				<c:forEach items="${moneyMoves}" var="moneyMove">
						<c:if test="${moneyMove.active>0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_ACTIVE%>'>
						</c:if>
						<c:if test="${moneyMove.active<=0}">		
							<tr style='<%=org.bakery.server.util.Constants.STYLE_INACTIVE%>'>
						</c:if>
								<td>${moneyMove.id}</td>
								<td>${moneyMove.date}</td>
								<td>${moneyMove.destinationAccount}</td>
								<td>${moneyMove.sourceAccount}</td>
								<td>${moneyMove.amount}</td>
								<td>${moneyMove.price.price}</td>
								<td>${moneyMove.price.price*moneyMove.amount}</td>
								<td>${moneyMove.order}</td>
								<td><a href="javascript:<c:if test="${moneyMove.active>0}">de</c:if>activate(<c:out value="${moneyMove.id}"/>)">${moneyMove.active<=0 ? 'Restore' : 'Remove'}</a><BR>
									<a href="javascript:showEdit(${moneyMove.id},${moneyMove.active>0 ? 'true' : 'false'}, ${moneyMove.amount}, '${moneyMove.desc}', '${moneyMove.date.date}', '${moneyMove.date.month}', '${moneyMove.date.year}', ${moneyMove.destinationAccount.id}, ${moneyMove.sourceAccount.id}, ${moneyMove.price.id}, ${moneyMove.order.id})"><fmt:message key="moneyMoves.table.actions.edit.head.caption" bundle="${resourceBundle}"/></a><BR>
									<a href="javascript:editProductMoves(${moneyMove.id})"/><fmt:message key="moneyMoves.table.actions.edit.productmoves.caption" bundle="${resourceBundle}"/></a></td></tr>				
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