<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>

<script>
    $(function () {
        $("#date").datepicker({ dateFormat: 'yy/mm/dd'});
    });
</script>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    
    <h2>
        New Memo
    </h2>
    <form:form modelAttribute="memo" method="post" class="form-horizontal" >
        <div class="control-group" id="vet">
            <label class="control-label">Vet</label>

            <c:out value="${memo.vet.firstName} ${memo.vet.lastName}"/>
        </div>
        <petclinic:inputField label="Description" name="description"/>
         <div class="controls">
                <form:input path="date"/>
                <span class="help-inline"><form:errors path="date"/></span>
        </div>
        <div class="form-actions">
           <input type="hidden" name="vetId" value="${memo.vet.id}"/>
           <button type="submit">Add Memo</button> 
        </div>
    </form:form>
</div>
<jsp:include page="../fragments/footer.jsp"/>
</body>

</html>