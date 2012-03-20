<%@ include file="../taglibs.inc"%>
<tiles:insertDefinition name="default" flush="true">
    <tiles:putAttribute name="title" value="PlaceCruncher" />
    <tiles:putAttribute name="body" type="string">

<h2>${source.name}</h2>

<div width="100%">
  <div id="urlPreview" width="60%">
    <iframe height="720px" src="${source.url}"/>
  </div>
  <div id="places" width="40%">
  	<ul>
        <c:forEach var="place" items="${source.places}">
          <li>${place.name}</li>
        </c:forEach>
    </ul>
  </div>
</div>

    </tiles:putAttribute>
</tiles:insertDefinition>
