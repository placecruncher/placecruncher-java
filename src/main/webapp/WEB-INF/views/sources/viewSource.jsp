<%@ include file="../../taglibs.inc"%>
<tiles:insertDefinition name="default" flush="true">
    <tiles:putAttribute name="title" value="PlaceCruncher" />
    <tiles:putAttribute name="body" type="string">

<h2>${source.name}</h2>

<div>
  <div id="places" >
      <ul>
        <c:forEach var="place" items="${source.places}">
          <li id="place_${place.id}"_>
              ${place.name}<br/>
              ${place.address}<br/>
              ${place.city}, ${place.state}, ${place.country}<br/>
              ${place.url}<br/>
              ${place.phone}<br/>
          </li>
        </c:forEach>
    </ul>
  </div>
  <div id="url_preview">
    <iframe src="${source.url}"></iframe>
  </div>
</div>

    </tiles:putAttribute>
</tiles:insertDefinition>
