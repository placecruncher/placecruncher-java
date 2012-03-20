<%@ include file="../taglibs.inc"%>
<tiles:insertDefinition name="default" flush="true">
    <tiles:putAttribute name="title" value="PlaceCruncher" />
    <tiles:putAttribute name="body" type="string">

<h2>${source.name}</h2>

<div>
  <div id="urlPreview" width="60%">
  </div>
  <div id="places" width="40%">
        <c:forEach var="place" items="${source.places}" var="cyyt">
          <c:import url="/config/terms/termRow.ajax?rowId=${cyyt.id}"/>
        </c:forEach>
  </div>
</div>

    </tiles:putAttribute>
</tiles:insertDefinition>
