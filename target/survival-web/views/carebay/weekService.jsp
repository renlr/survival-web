<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>周期功能</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#week_grid").datagrid({
			url : "${pageContext.request.contextPath}/week/readPages.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加周期",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加周期",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#week_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/week/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#week_grid").datagrid('reload'); 
				          },
						  error : function(data){
						  	$("#week_grid").datagrid('reload'); 
						  }
					});
				}
			}],
			columns : [[{
				field : "name",
				title : "周期名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 0)
						return "默认周期";
					return "第"+value+"周";
				}
			},{
				field : "online",
				title : "是否上线",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(null != value){
						if(0 == value){
							return "未上线";
						}else if(1 == value){
							return "<font color='green'>上线中</font>";
						}else{
							return "<font color='red'>已下线</font>";
						}
					}
					return "	";
				}
			},{
				field : "poster",
				title : "首页轮播",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/week/addPoster.do?id='+rec.id+'">首页轮播</a>';
				}
			},{
				field : "tips",
				title : "孕期贴士",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/week/addTips.do?id='+rec.id+'">孕期贴士</a>';
				}
			},{
				field : "music",
				title : "胎教音乐",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var html = '<a href="${pageContext.request.contextPath}/week/addMusicbg.do?id='+rec.id+'">背景图片</a>&nbsp;&nbsp;' 
						+'<a href="${pageContext.request.contextPath}/week/addMusic.do?id='+rec.id+'">胎教音乐</a>';
					return html;
				}
			},{
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			} ] ]
		});
		$($("#week_grid").datagrid("getPager")).pagination({
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#div_dialog").dialog("close");
		});
		function submits(){
			$("#weekForm").form("submit",{
				onSubmit:function(){
					var id = $("#id").val();
					var name = $("#name").val(); 
					if(id == null && $.trim(name) == ""){ 
						alert("按周名称不能为空！"); 
						$("#image").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id").val("");
					$("#name").val("");
					$("#div_dialog").dialog("close");
					$("#week_grid").datagrid('reload'); 
				}
			});
		}
		
		$("#buts").click(function(){
			var name = $("#name").val();
			var isValiurl = "${pageContext.request.contextPath}/week/vaildation.do?t="+new Date().getTime();
			$.ajax({
				type: "get",
				dataType: "json",
				url: isValiurl,
				data: "name="+name,
				success: function(data){
					if(data.resultMessage == "success"){
						alert("第"+name+"周已添加过，请选择其他周!");
					}else{
						submits();
					}
				},
				error : function(data){
					submits();
				}
			});	
		});
	});
</script>
</head>
<body>
	<table id="week_grid" title="周期功能管理"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/week/save.do" method="post" id="weekForm">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt">按周名称：</td>
				<td>
					<select name="name" id="name" style="width:140px;">
						<option value="0">默认</option>
						<%
							for(int i=1;i<=42;i++){
								%><option value="<%=i %>">第<%=i %>周</option><%
							}
						 %>
					</select>
			    </td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="button" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>