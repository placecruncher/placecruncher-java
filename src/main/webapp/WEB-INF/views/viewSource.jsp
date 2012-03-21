<%@ include file="../taglibs.inc"%>
<tiles:insertDefinition name="default" flush="true">
    <tiles:putAttribute name="title" value="PlaceCruncher" />
    <tiles:putAttribute name="body" type="string">

<h2>${source.name}</h2>

<div width="100%">
  <div style="float:left" id="places" width="20%" >
      <ul>
        <c:forEach var="place" items="${source.places}">
          <li>
              ${place.name}<br/>
              ${place.address}<br/>
              ${place.city}, ${place.state}, ${place.country}<br/>
              ${place.url}<br/>
              ${place.phone}<br/>
          </li>
        </c:forEach>
    </ul>
  </div>
  <div id="urlPreview" width="80%" style="float:right">
    <iframe scrolling="yes" width="100%" height="720px" src="${source.url}"></iframe>
  </div>
</div>

    </tiles:putAttribute>
</tiles:insertDefinition>
