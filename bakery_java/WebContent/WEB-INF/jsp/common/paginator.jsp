<%@include file="/WEB-INF/jsp/commonInclude.jsp"%>							
			<div id="paginatorForm" class="optionalForm">
				<table>
				<tr>
					<td><fmt:message key="forms.search.startfrom.caption" bundle="${resourceBundle}"/></td>
					<td><spring:bind path="searchStartFrom">
						<input type="text" name="searchStartFrom" value="${status.value}"/>
						</spring:bind>
					</td>
				</tr>
						<tr>
					<td><fmt:message key="forms.search.maxresults.caption" bundle="${resourceBundle}"/></td>
					<td><spring:bind path="searchMaxResults">
						<input type="text" name="searchMaxResults" value="${status.value}"/>
						</spring:bind>
					</td>
				</tr>
				<tr><input type="submit" value="<fmt:message key="forms.search.button.startsearch" bundle="${resourceBundle}"/>"/>
				<input type="button" value="<fmt:message key="forms.common.button.cancel" bundle="${resourceBundle}"/>" onclick="hidePaginator()"/></tr>
				</table>
			</div>