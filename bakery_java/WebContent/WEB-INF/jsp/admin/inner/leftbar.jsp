<%@include file="/WEB-INF/jsp/commonInclude.jsp"%>
<script type="text/javascript">
	function goto(a){
		var form = document.getElementById("actionForm");
		form['_eventId'].value=a;
		form.submit();
	}
</script>
<div style="background-color: #DDDDFF; height:100%">
<a href="javascript: goto('manageUnits')"><fmt:message key="menu.manage.units.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('manageAddresses')"><fmt:message key="menu.manage.addresses.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('manageProductTypes')"><fmt:message key="menu.manage.producttypes.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('manageContragents')"><fmt:message key="menu.manage.contragents.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('manageAccounts')"><fmt:message key="menu.manage.accounts.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('manageWarehouses')"><fmt:message key="menu.manage.warehouses.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('managePriceLists')"><fmt:message key="menu.manage.pricelists.caption" bundle="${resourceBundle}"/></a><BR>
<a href="javascript: goto('manageOrders')"><fmt:message key="menu.manage.orders.caption" bundle="${resourceBundle}"/></a><BR>
</div>