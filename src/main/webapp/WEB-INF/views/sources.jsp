<%@ include file="../taglibs.inc"%>
<tiles:insertDefinition name="default" flush="true">
    <tiles:putAttribute name="title" value="PlaceCruncher" />
    <tiles:putAttribute name="body" type="string">

<table width="100%" style="border: 1px solid black;">
  <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Status</th>
        <th>URL</th>
    </tr>
  </thead>
  <tbody>
      <c:forEach var="source" items="${sources}">
        <tr >
          <td>${source.id}</td>
          <td><a href="sources/${source.id}/edit.html">${source.name}</a></td>
          <td>${source.status}</td>
          <td>${source.url}</td>
        </tr>
      </c:forEach>
  </tbody>
</table>
    </tiles:putAttribute>
</tiles:insertDefinition>
