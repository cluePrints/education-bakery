<%@include file="/WEB-INF/jsp/commonInclude.jsp"%>				
			<div id="localMenu" class="localMenu">
			<table>		
				<tr><td colspan="2">
				<input type="hidden" name="idForDeletion"/>
				<input type="hidden" name="idForRestore"/>
				<input type="hidden" name="mode"/>
				<input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}"/>
				</td></tr>		
				<input type="hidden" name="_eventId" value="stay"/>
				<tr><td colspan="2">
				<input type="button" value="<fmt:message key="forms.common.button.new" bundle="${resourceBundle}"/>" onclick="javascript: showNew()"/>
				<input type="button" value="<fmt:message key="forms.common.button.search" bundle="${resourceBundle}"/>" onclick="javascript: showPaginator()"/></td></tr>
			</table>		
			</div>