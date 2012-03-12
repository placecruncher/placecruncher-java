<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- exposes tiles attributes as '${}' accessible beans --%>
<tiles:importAttribute />

<html>
<head>
<title>${title}</title>

<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<style type="text/css">
.error, .notice, .success {margin-bottom:1em;border:2px solid #ddd;}
.error {background:#FBE3E4;color:#8a1f11;border-color:#FBC2C4;}
</style>


</head>

<body>

    <h3>${title}</h3>

    ${body}

</body>
</html>
