<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Upload User Image</title>
</head>

<body>
	<h2>Change your avatar</h2>
	<s:actionerror />
	<s:form action="changeAvatar" method="post" enctype="multipart/form-data">
		<s:file name="changeAvatar" label="Change Avtar" />
		<s:submit value="Upload" align="center" />
	</s:form>
</body>
</html>