<%@page import="com.baofeng.carebay.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>最新活动</title>
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
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/docelltip.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 23.5);
		$("#activity_grid").datagrid({
			url : "${pageContext.request.contextPath}/activity/readPages.do",
			datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : true,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: pageSize > 30 ? pageSize : 30,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加活动",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加活动",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			},'-', {
				id : 'btnDel',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#activity_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许编辑！","提示消息");
		                return ;
		            }
		            var editurl = "${pageContext.request.contextPath}/activity/edit.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	$("#id").val(data.id);
				          	$("#name").val(data.name);
				          	$("#content").val(data.content);
				          	$("#details").val(data.details);
				          	if(data.beginTime != null){
								$("#beginTime").datebox("setValue",data.beginTime.toString().toDate().format("yyyy-MM-dd hh:mm"));
							}
							if(data.endTime != null){			          	
								$("#endTime").datebox("setValue",data.endTime.toString().toDate().format("yyyy-MM-dd hh:mm"));
							}	
				          	$("#div_dialog").dialog();
				          },
						  error : function(data){
						  	$("#activity_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btnRedo',
				text : "上  | 下线",
				iconCls : 'icon-redo',
				handler : function() {
					var row = $("#activity_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            var editurl = "${pageContext.request.contextPath}/activity/online.do?t="+new Date().getTime();
					$.ajax({
				          type: "get",
				          dataType: "json",
				          url: editurl,
				          data: "id="+id,
				          success: function(data){
				          	if("success" == data.resultMessage){
					          	$("#activity_grid").datagrid("reload"); 
				          	}
				          },
						  error : function(data){
						  	$("#activity_grid").datagrid("reload"); 
						  }
					});
				}
			},'-', {
				id : 'btn-tsk',
				text : "定时任务",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#activity_grid").datagrid("getSelections");
		            if($(row).length < 1){
		                alert("请选择记录，至少选取一行！","提示消息");
		                return ;
		            }
		            var ids = "";
		            $.each(row,function(i,item){
		            	if(i)
		            		ids += ",";
		            	ids += item.id;
		            });
					$("#task_dialog").dialog({
     					title:"定时上下线任务",
     					show:"slide",
     					resizable:true,
     					closed: false,
     					buttons:[{
     						text : "确定",
     						handler:function(){
     							var onlineDT = $("#onlineDT").datetimebox("getValue");
								var offlineDT = $("#offlineDT").datetimebox("getValue");
								if(onlineDT == "" && offlineDT == ""){
									alert("定时上下线任务时间不能为空！");
									return;
								}
								$.ajax({
            				          type: "POST",
            				          dataType: "json",
            				          url: "${pageContext.request.contextPath}/activity/saveLineTask.do?t="+new Date().getTime(),
            				          data: "ids="+ids+"&onlineDT="+onlineDT+"&offlineDT="+offlineDT,
            				          success: function(data){
            				        	$("#onlineDT").datetimebox("setValue","");
            				        	$("#offlineDT").datetimebox("setValue","");
            				        	$("#task_dialog").dialog("close");
            				        	$("#activity_grid").datagrid("reload");
            				          },
            						  error : function(data){alert("添加定时任务失败!");}
            					}); 
     						}},{
     						text:"关闭",
     						handler:function(){
     							$("#task_dialog").dialog("close");
     						}
     					}]
     				});
				}
			},'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#activity_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var online = row[0].online;
		            if(online == 1){
		            	alert("上线中不允许删除！","提示消息");
		                return ;
		            }
		            var delUrl = "${pageContext.request.contextPath}/activity/delete.do?t="+new Date().getTime();
					$("#delete_div").dialog({
						title : "删除提示",
						show:"slide",
			            height : 130,
						width : 200,
						modal : true,
						closeOnEscape : true,
						buttons: [{ 
						text: "确定", 
							handler: function() {
							$.ajax({
								type: "get",
								dataType: "json",
								url: delUrl,
								data: "id="+ids,//要发送的数据
								success: function(data){
									if("success" == data.resultMessage){
										$("#activity_grid").datagrid("reload"); 
								    }
								},
								error : function(data){
									$("#activity_grid").datagrid("reload"); 
								}
							});
							$("#delete_div").dialog("close");
						}}, {
							text: "取消", 
							handler: function() { 
								$("#delete_div").dialog("close");
							} 
						}] 
			       	});
				}
			}],
			columns : [[{
				field : "name",
				title : "活动名称",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "content",
				title : "活动摘要",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "details",
				title : "活动介绍",
				width : $(this).width() * 0.2,
				align : "left",
				sortable : true
			},{
				field : "chamber",
				title : "所在会所",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "online",
				title : "是否上线",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var hls = "";
					if(null != value){
						if(0 == value){
							hls = "未上线";
						}else if(1 == value){
							hls = "<font color='green'>上线中</font>";
						}else{
							hls = "<font color='red'>已下线</font>";
						}
					}
					if(rec.taskDT != null){
						var dts = rec.taskDT.split("|");	
						if(dts.length > 0 && dts[0] != null && dts[0] != ""){
							hls += "<br/>上线："+dts[0].toString().toDate().format("MM-dd hh:mm");
						}
						if(dts.length > 1 && dts[1] != null && dts[1] != ""){
							hls += "<br/>下线："+dts[1].toString().toDate().format("MM-dd hh:mm");
						}
					}
					return hls;
				}
			},{
				field : "joinAct",
				title : "参与用户",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return '<a href="${pageContext.request.contextPath}/activity/details.do?gid='+rec.id+'">参与用户</a>';
				}
			},{
				field : "beginTime",
				title : "开始时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return value.toString().toDate().format("yyyy-MM-dd hh:mm");
				}
			},{
				field : "endTime",
				title : "结束时间",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return value.toString().toDate().format("yyyy-MM-dd hh:mm");
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
			}]],
			onLoadSuccess:function(data){
				$(this).datagrid('doCellTip',{'max-width':'300px','delay':500});
			}
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#content").val("");
			$("#details").val(""); 
			$("#div_dialog").dialog("close");
		});
		$("#activityForm").submit(function(){
			var name = $("#name").val();
			var content = $("#content").val();
			var details = $("#details").val();
			var beginTime=$("#beginTime").datebox("getValue");
			var endTime=$("#endTime").datebox("getValue");
			if($.trim(name) == ""){ 
				alert("活动名称不能为空！"); 
				$("#name").focus(); 
				return false; 
			}
			if($.trim(content) == ""){
				alert("活动摘要不能为空！"); 
				$("#content").focus();
				return false;
			}
			if($.trim(details) == ""){
				alert("活动介绍不能为空！"); 
				$("#details").focus();
				return false;
			}
			if($.trim(beginTime) == ""){
				alert("开始时间必须选择！"); 
				$("#beginTime").focus(); 
				return false; 
			}
			if($.trim(endTime) == ""){
				alert("结束时间必须选择！"); 
				$("#endTime").focus(); 
				return false; 
			}
		});
	});
</script>
</head>
<body>
	<table id="activity_grid" title="最新活动"></table>
	<div id="delete_div">确定删除所选内容吗？</div> 
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/activity/save.do" method="post" id="activityForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">活动名称：<label style="color: red;">*</label></td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input"></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">活动摘要：<label style="color: red;">*</label></td>
				<td class="ji" colspan="3"><textarea id="content" name="content" style="height:100px;width:450px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">活动介绍：</td>
				<td class="ji" colspan="3"><textarea id="details" name="details" style="height:150px;width:450px;"></textarea></td>
			</tr>
			<tr class="ji">
				<td class="rt" align="right">活动时间：<label style="color: red;">*</label></td>
				<td class="ji">
					<input type="text" class="easyui-datetimebox input" id="beginTime" name="beginTime" data-options="required:true,showSeconds:false" style="width:150px">
					至<input type="text" class="easyui-datetimebox input" id="endTime" name="endTime" data-options="required:true,showSeconds:false" style="width:150px">
				</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input class="bginput" type="submit" id="buts" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="task_dialog" title="定时上下线任务" style="height:205px;width:445px;">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
			<tr class="tr">
				<td class="rt">上线时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="onlineDT" name="onlineDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
			</tr>
			<tr class="tr">
				<td class="rt">下线时间：</td>
				<td class="ji"><input type="text" class="easyui-datetimebox input" id="offlineDT" name="offlineDT" data-options="required:true,showSeconds:false" style="width:150px"></td>
			</tr>
			<tr class="tr">
				<td class="rt" colspan="2">&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
</html>