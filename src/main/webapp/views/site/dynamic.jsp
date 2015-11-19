<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<title>公司地址</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"  />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/admin_style.css'>
<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/views/css/dialog.css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/js/themes/icon.css">
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/common.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery.easyui.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/easyui-lang-zh_CN.js"></script>
<link href="${pageContext.request.contextPath}/views/js/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/views/js/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/views/js/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/js/umeditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105);
		$("#dynamic_grid").datagrid({
			url : "${pageContext.request.contextPath}/dynamic/readPages.do",
		datatype : "json",
			mtype : "POST",
			fit : true,
			nowrap : false,
			striped : true,
			fitColumns : true, 
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			pageSize: 30,
			pageList: [pageSize,30,40,50],
			sortname : 'id',
			toolbar : [ '-', {
				id : 'btnAdd',
				text : "新增",
				iconCls : 'icon-add',
				handler : function() {
				window.location.href="${pageContext.request.contextPath}/dynamic/add.do";
				}
			}, '-', {
				id : 'btnEdt',
				text : "修改",
				iconCls : 'icon-edit',
				handler : function() {
					var row = $("#dynamic_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
		                alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var id = row[0].id;
		            window.location.href="${pageContext.request.contextPath}/dynamic/edit.do?id="+id;
				}
			} ,'-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#dynamic_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var delUrl = "${pageContext.request.contextPath}/dynamic/delete.do?t="+new Date().getTime();
		            $("#delete_div").dialog({
						title : "删除提示",
						show:"slide",
		            	height : 130,
						width : 200,
						modal : true,
						show:"slide",
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
							          $("#dynamic_grid").datagrid("reload"); 
							          	if("success" == data.resultMessage){
							          		$("#dynamic_grid").datagrid("reload"); 
							          	}
							          },
									  error : function(data){
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
			},'-',{
				id : 'btnsearch',
				text : "高级搜索",
				iconCls : 'icon-search',
				handler : function() {
					$("#search_dialog").dialog({
   						title:"高级搜索",
   						show:"slide",
   						resizable:true,
   						closed: false,
   						buttons:[{
   							text : "搜索",
   							handler:function(){
   								var type = $("#type").combobox("getValue");
   								$("#dynamic_grid").datagrid("load",{type : type});
								$("#type").combobox("setValue","-1");
								$("#search_dialog").dialog("close");
   							}
   						},{
   							text:"关闭",
   							handler:function(){
   								$("#names").val("");
								$("#type").combobox("setValue","-1");
								$("#search_dialog").dialog("close");
   							}
   						}]
   					});
				}
			}],
			columns : [ [ {
				field : "title",
				title : "标题",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "source",
				title : "出处",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "author",
				title : "网站编辑",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true
			},{
				field : "type",
				title : "类别",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					if(value == 0){
						return "暴风资讯";
					}else if(value == 1){
						return "行业资讯";
					}			
					return "";
				}
			},{
				field : "content",
				title : "内容",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(dateValue,rec){
				    if(dateValue.length>10){
					return dateValue.substr(0,10)+'...';
					}
					return dateValue.substr(0,10);
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
			}]]
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#images").val("");
			$("#indexs").val("");
			$("#div_dialog").dialog("close");
		});
	
	});

</script>



</head>
<body>
	<table id="dynamic_grid" title="公司地址"></table>
	<div id="delete_div">确定删除所选内容?</div> 
	<div id="search_dialog">
		<table width="99.5%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" colspan="4">&nbsp;</td>
			</tr>
			<tr class="table_title">
				<td class="rt">资讯类别：</td>
				<td class="ji">
					<select id="type" name="type" class="easyui-combobox" style="width:200px;">
						<option value="-1"></option>
						<option value="0">暴风资讯</option>
					    <option value="1">行业资讯</option>
					</select>
				</td>
			</tr>
			<tr class="ji">
				<td class="rt" colspan="4">&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
	<script type="text/javascript">
    </script>
</html>