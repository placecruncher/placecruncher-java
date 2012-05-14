<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tiles:insertDefinition name="default" flush="true">
    <tiles:putAttribute name="title" value="PlaceCruncher" />
    <tiles:putAttribute name="body" type="string">

<script type="text/javascript" src="../../../resources/js/prototype.js"></script>
<script>


  function doNothing() {
  }

  function cancelNewPlace() {
    var html = "<li id='addNewPlace'><a href='javascript:doNothing();' onclick='addNewPlace()'>Add New Place</a></li>"
    $('addNewPlace').replace(html);
  }

  function addNewPlace() {
    var rowName = "addNewPlace";
    new Ajax.Request('places/create.html', {
      asyncronous : true,
      method : 'get',
      parameters : {
    	  index: 99
      },
      onSuccess : function(transport) {
        $(rowName).replace(transport.responseText);
      }
    });
  }

</script>


<div>
  <div>
  <form:form id="source_form" modelAttribute="source" >
  	  <form:input path="name"/>
  	  <form:input path="url"/>
      <ul id="places">
        <c:forEach var="place"  varStatus="row" items="${source.places}">
			<c:set var="place" value="${place}" scope="request" />
			<c:set var="index" value="${row.index}" scope="request"/>
	        <jsp:include page="placeForm.jsp"/>
        </c:forEach>
        <li id="addNewPlace">
		    <a href="javascript:doNothing();" onclick="addNewPlace()">Add New Place</a>
		</li>
    </ul>
    <input type="submit" value="Save"/>
  </form:form>
  </div>
<%--   <div id="url_preview">
    <iframe src="${source.url}"></iframe>
  </div>
 --%>

 </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
