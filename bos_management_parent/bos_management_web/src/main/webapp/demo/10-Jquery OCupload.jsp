<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>
</head>
<body>
//文件上传页面三个要素  :<br>
1、表单提交方式必须post  <br>
2、设置表单属性encty="multipart/form-data"  <br> 
3、在表单中存在input type="file" 必须有name<br>
<iframe name="aaaa" style="display: none;"></iframe>
<form target="aaaa" action="abc.action" method="post" enctype="multipart/form-data">
	<input type="file" name="upload" value="选择文件">
	<input type="submit" value="上传">
</form>




<hr>
使用ocupload实现文件上传:原理调用upload方法动态修改html元素<br>
1、在页面中提供任意元素 给出Id<br>
2、在页面加载完成后调用upload方法<br>
<input type="button" id="myBtn">
<script type="text/javascript">
	$(function(){
		$("#myBtn").upload({
			action:'xyz.action',  //提交url
			name:'upload'         //提交文件参数名
		});
	})
</script>
</body>
</html>