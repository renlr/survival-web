<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<head>
<head>
<title>胎教音乐</title>
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
	$.extend($.fn.datagrid.methods, {
	    editCell: function(jq,param){
	        return jq.each(function(){
	            var opts = $(this).datagrid("options");
	            var fields = $(this).datagrid("getColumnFields",true).concat($(this).datagrid("getColumnFields"));
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor1 = col.editor;
	                if (fields[i] != param.field){
	                    col.editor = null;
	                }
	            }
	            $(this).datagrid("beginEdit", param.index);
	            for(var i=0; i<fields.length; i++){
	                var col = $(this).datagrid("getColumnOption", fields[i]);
	                col.editor = col.editor1;
	            }
	        });
	    }
	});
	
	var values = undefined;
	var fields = undefined;
	var editIndex = undefined;
	function endEditing(){
	    if (editIndex == undefined){return true}
	    if ($("#music_grid").datagrid("validateRow", editIndex)){
			$("#music_grid").datagrid("endEdit", editIndex);
			var row = $("#music_grid").datagrid("getRows");
			var id = row[editIndex].id;
			var $value = row[editIndex][fields];
			if($value != null && values != $value && fields != "image"){
				$.ajax({
					type: "post",
					dataType: "json",
					url: "${pageContext.request.contextPath}/music/saveEditFields.do?t="+new Date().getTime(),
					data: "id="+id+"&field="+fields+"&value="+$value,
					success: function(data){
				    	if("success" == data.resultMessage){}
				    },
					error : function(data){}
				});
			}
			values = undefined;
			fields = undefined;
	        editIndex = undefined;
	        return true;
	    } else {
	        return false;
	    }
	}
	function clickCell(index, field,value){
	    if (endEditing()){
	        $("#music_grid").datagrid("selectRow", index).datagrid("editCell", {index:index,field:field});
	        values = value;
	        fields = field;
	        editIndex = index;
	    }
	}
	$(document).ready(function() {
		var pageSize = Math.round(($("#right", window.parent.document).height() - 160) / 105);
		$("#music_grid").datagrid({
			url : "${pageContext.request.contextPath}/music/readPages.do?gid=${week.id}",
			datatype : "json",
			mtype : "POST",
			nowrap : false,
			striped : true,
			fitColumns : true, 
			fit : true,
			pageSize: pageSize,
			pageList: [pageSize,30,40,50],
			pagination : true,
			sortname : 'id',
			singleSelect : true,
			rownumbers : true,
			onClickCell : function(index,field,value){
				clickCell(index,field,value);
			},
			toolbar : [ '-', {
				id : 'btnClearSelections',
				text : "添加音乐",
				iconCls : 'icon-add',
				handler : function() {
					$("#div_dialog").dialog({
						title:"添加音乐",
						show:"slide",
						resizable:false,
						closed: false,
						resizable:false
					});
				}
			}, '-', {
				id : 'btnDel',
				text : "删除",
				iconCls : 'icon-no',
				handler : function() {
					var row = $("#music_grid").datagrid("getSelections");
		            if($(row).length < 1 || $(row).length > 1){
			            alert("请选择记录，只能选取单行！","提示消息");
		                return ;
		            }
		            var ids = row[0].id;
		            var groupId = $("#groupId").val();
		            var delUrl = "${pageContext.request.contextPath}/music/delete.do?t="+new Date().getTime();
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
							          data: "id="+ids+"&groupId="+groupId,//要发送的数据
							          success: function(data){
							          	$("#music_grid").datagrid('reload'); 
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
			}],
			columns : [ [ {
				field : "image",
				title : "音乐图标",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					return html.replace("[[images1]]", value).replace("imageId", rec.id);
				}
			},{
				field : "name",
				title : "音乐名称",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				editor : "textarea"
			}, {
				field : "file",
				title : "音乐文件",
				width : $(this).width() * 0.2,
				align : "center",
				sortable : true,
				formatter : function(value,rec){
					var html = "  ";
					if(value != null){
						html += "<a href='"+value+"' target='_blank'>"+ rec.name +"</a>";
					}
					return html;
				}
			}, {
				field : "describes",
				title : "音乐介绍",
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				editor : "textarea"
			}, {
				field : "createDT",
				title : "创建时间",
				width : $(this).width() * 0.2,
				align : 'center',
				sortable : true,
				formatter : function(dateValue,rec){
					return dateValue.toString().toDate().format("yyyy-MM-dd hh:mm:ss");
				}
			} ] ]
		});
		$($("#music_grid").datagrid("getPager")).pagination({
			beforePageText : "第",
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		
		$("#musicForm").submit(function(){
			var images=$("#images").val(); 
			var musics=$("#musics").val(); 
			var describes=$("#describes").val(); 
			if($.trim(images) == ""){ 
				alert("必须上传音乐图标！"); 
				$("#images").focus(); 
				return false; 
			}
			if($.trim(musics) == ""){ 
				alert("必须上传音乐文件！"); 
				$("#musics").focus(); 
				return false; 
			}
			if(!musics.endWith(".mp3")){ 
				alert("必须上传mp3格式的音乐文件！"); 
				$("#musics").focus(); 
				return false; 
			}
			if($.trim(describes) == ""){ 
				alert("音乐介绍不能为空！"); 
				$("#describes").focus(); 
				return false; 
			}
		});
		$("#butrs").click(function(){
			$("#id").val("");
			$("#name").val("");
			$("#musics").val(""); 
			$("#images").val("");
			$("#describes").val("");
			$("#div_dialog").dialog("close");
		});
		$("#butrsClose").click(function(){
			$("#id2").val("");
			$("#images2").val("");
			$("#upload_dialog").dialog("close");
		});
		$("#upbuttons").click(function(){
			$("#uploadForm").form("submit",{
				onSubmit:function(){
					var images2 = $("#images2").val(); 
					if($.trim(images2) == ""){ 
						alert("必须选择上传图片！"); 
						$("#images2").focus(); 
						return false; 
					}
				},
				success:function(data){
					$("#id2").val("");
					$("#images2").val("");
					$("#upload_dialog").dialog("close");
					$("#music_grid").datagrid("reload"); 
				}
			});
		});
	});
	var html = "<a onClick=\"upLoadImage(11,'imageId')\" href=\"#\"><img src=\"[[images1]]\" width=\"100\" height=\"100\"/></a>";	
	function upLoadImage(vas,id){
		$("#id2").val(id);
		$("#upload_dialog").dialog({
			title:"上传图片",
			show:"slide",
			resizable:false,
			closed: false,
			resizable:false
		});
	}
</script>
</head>
<body>
	<table id="music_grid" title="第${week.name}周胎教音乐管理"></table>
	<div id="delete_div">确定删除所选内容?</div>
	<div id="div_dialog">
		<form action="${pageContext.request.contextPath}/music/save.do" method="post" id="musicForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="ji">
				<td class="rt" align="right">音乐名称：</td>
				<td><input type="text" name="name" id="name" size="25" maxlength="50" class="input">(默认使用文件名称)</td>
			</tr><tr class="ji">
				<td class="rt" align="right">音乐图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images" id="images" size="25"></td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">音乐文件：<label style="color: red;">*</label></td>
				<td><input type="file" name="musics" id="musics" size="25">(目前只支持mp3格式文件播放)</td>
			</tr>
			<tr class="tr">
				<td class="rt" align="right">音乐介绍：<label style="color: red;">*</label></td>
				<td><textarea id="describes" name="describes" rows="6" cols="50" spellcheck="false" autocomplete="off"></textarea>
					<script>
					(function($) {
						$("#description").textbox({
							maxLength: 300
						});
					})(jQuery);
					</script>（最多只能输入300个字）
				</td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id" id="id"> 
					<input type="hidden" name="gid" id="gid" value="${week.id}"> 
					<input class="bginput" type="submit" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrs" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="upload_dialog" title="上传图片">
		<form action="${pageContext.request.contextPath}/music/upLoadImages.do" method="post" id="uploadForm" enctype="multipart/form-data">
		<table width="98%" border="0" cellpadding="4" cellspacing="1" class="table">
			<tr class="tr">
				<td class="rt" align="right">上传图片：<label style="color: red;">*</label></td>
				<td><input type="file" name="images2" id="images2" size="25"></td>
			</tr>
			<tr class="tr ct">
				<td colspan="2">
					<input type="hidden" name="id2" id="id2">
					<input type="hidden" name="gid" id="gid" value="${week.id}"> 
					<input class="bginput" type="button" id="upbuttons"  value="上传">&nbsp;&nbsp;&nbsp;&nbsp;<input class="bginput" type="reset" name="Input" id="butrsClose" value="关闭">
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>