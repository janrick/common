<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<h2>Hello World!</h2>
	<form id="uploadCommonForm" method="post"
		action="http://insurance.zjzmjr.com/uploadServlet"
		enctype="multipart/form-data" target="hidden_frame">
		<table>
			<tr>
				<th style="width: 30%;">项目名称</th>
				<td style="width: 50%;"><select id="projectId" name="projectId" class="select">
				</select></td>
			</tr>
			<tr>
				<th>文件名</th>
				<td><select id="fileId" name="fileId" class="select"></select></td>
			</tr>
			<tr>
				<th>上传文件</th>
				<td><input type="file" name="fileAddress" id="fileAddress" multiple /></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" name="btnSubmit" id="btnSubmit" value="提交" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
