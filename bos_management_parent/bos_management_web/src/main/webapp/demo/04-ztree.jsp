<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body>
第一步：在页面中提供dom容器（ ul元素）  设置样式 class="ztree"  --哪个位置展示ztree<br>
第二步：设置配置信息<br>
第三步：设置ztree需要符合规范数据（1、标准json数据格式  2、简单json数据格式）<br>
第四步：调用ztree组件初始化方法创建树<br>
<hr>
<!-- 标准json数据格式：无必须设置的参数:关键点：通过children属性指定子节点数据 -->
<ul class="ztree" id="ztree1"></ul>
<script type="text/javascript">
	var setting1 = {};  //使用默认值
	var nodes1 = [{"id":1,name:"节点一",children:[{id:101,name:"节点二",children:[{id:1001,name:"节点三"}]}]}];
	//参数一：展示ztree dom容器
	$.fn.zTree.init($("#ztree1"), setting1, nodes1);
</script>

<hr>
<!-- 关键点：通过id pId指定父子关系 -->
<ul class="ztree" id="ztree2"></ul>
<script type="text/javascript">
	var setting2 = {
			data: {
				simpleData: {
					enable: true,  //开启简单数据模式
					idKey: "id",   //通过id标识节点数据
					pIdKey: "pId", //通过pId指定父节点
					rootPId: 0,    //顶级节点 pID为0
				}
			}
	};
	var nodes2 = [{id:1,name:'节点一',pId:2},{id:2,name:'节点二',pId:3},{id:3,name:'节点三',pId:0}];
	$.fn.zTree.init($("#ztree2"), setting2, nodes2);
	
	
</script>
</body>
</html>