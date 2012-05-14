<%@ include file="../../taglibs.inc"%>


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
          <td><a href="${source.url}">${source.name}</a></td>
          <td>${source.status}</td>
          <td>
            <a href="${source.id}/view.html">View</a> |
            <a href="${source.id}/edit.html">Edit</a>
          </td>
        </tr>
      </c:forEach>
  </tbody>
</table>
    </tiles:putAttribute>
</tiles:insertDefinition>
