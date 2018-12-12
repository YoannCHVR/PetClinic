<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">


<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>Pets</h2>

    <datatables:table id="pets" data="${pets.petList}" row="pet" theme="bootstrap2" cssClass="table table-striped" pageable="false" info="false">
        <datatables:column title="Name" cssStyle="width: 150px;" display="html">
            <spring:url value="/pets/{petId}.html" var="petUrl">
                <spring:param name="petId" value="${pet.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.name}"/></a>
        </datatables:column>
        <datatables:column title="Birthdate" cssStyle="width: 150px;" display="html">
            <spring:url value="/pets/{petId}.html" var="petUrl">
                <spring:param name="petId" value="${pet.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.birthDate}"/></a>
        </datatables:column>
        <datatables:column title="Owner" cssStyle="width: 150px;" display="html">
            <spring:url value="/pets/{petId}.html" var="petUrl">
                <spring:param name="petId" value="${pet.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(petUrl)}"><c:out value="${pet.owner.lastName}"/></a>
        </datatables:column>
    </datatables:table>
    
    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/pets.xml" htmlEscape="true" />">View as XML</a>
            </td>
            <td>
                <a href="<spring:url value="/pets.atom" htmlEscape="true" />">Subscribe to Atom feed</a>
            </td>
        </tr>
    </table>

    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
