<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>frames.jsp</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10,chrome=1"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/bootstrap.css" />
<%--<link rel="stylesheet" href="http://files.cnblogs.com/rubylouvre/bootstrap.css" />--%>
<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<%--<script src="http://files.cnblogs.com/rubylouvre/jquery1.83.js"> </script>--%>
<%--<script src="http://files.cnblogs.com/rubylouvre/bootstrap-transition.js"></script>--%>
<%--<script src="http://files.cnblogs.com/rubylouvre/bootstrap-modal.js"></script>--%>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/bootstrap-modal.js"></script>
<script language="JavaScript" charset="utf-8" type="text/javascript" src="${pageContext.request.contextPath}/views/js/bootstrap-transition.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		if("${sessionScope.session_user.callService}" == 1){
			var modals = "";
			var butlerId = null;
			setInterval(function(){
				$.get("${pageContext.request.contextPath}/call/readServiceCall.do?"+new Date().toString(),function(data){
					if(data != null && data.id != null){
						modals = "call";
						butlerId = data.id;
						$("#labelService").html(data.user.room+"&nbsp;&nbsp;<br/>呼叫服务!");
						$("#myModal").modal({show:true});
					}				
				});
			},15000);
			setInterval(function(){
				$.get("${pageContext.request.contextPath}/call/readServiceMeal.do?"+new Date().toString(),function(data){
					if(data != null && data.id != null){
						modals = "meal";
						butlerId = data.id;
						$("#labelService").html(data.user.room+"&nbsp;&nbsp;<br/>点餐服务!");
						$("#myModal").modal({show:true});
					}				
				});
			},28000);
			$("#butSubmit").click(function(){
				var callUrl = "${pageContext.request.contextPath}/call/serviceCall.do?t="+new Date().getTime();
				var mealUrl = "${pageContext.request.contextPath}/call/serviceMeal.do?t="+new Date().getTime();
				if(butlerId != null && modals != null){
					$.ajax({
						type: "get",
					    dataType: "json",
					    url: modals == "call" ? callUrl : mealUrl,
					    data: "id="+butlerId,
					    success: function(data){
					    	$("#myModal").modal("hide");
					    },
						error : function(data){
							$("#myModal").modal("hide"); 
						}
					});					
				}
			});
		}
		//var transports = [];
		//var url = "http://10.1.1.137:8088/core-websocket/sockjs/echo";
		//var ws = null;//new SockJS(url,undefined,{protocols_whitelist : transports});
		//if(ws != null){
		//	ws.onopen = function() {
		//		ws.send("test");
		//	};
		//	ws.onmessage = function(event) {
		///		 $("#myModal").modal({show:true});
		//	};
		//	ws.onclose = function(event) {
		//		alert("Info: connection closed.");
		//	};
		//}
	});
	function autoSize(iframes){
		iframes.height = $(document).height() - 5;
	}
</script>
<body>
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">管家服务</h3>
		</div>
		<div class="modal-body">
			<p><label id="labelService"></label></p>
		</div>
		<div class="modal-footer">
			<!--<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>-->
			<button class="btn btn-primary" name="butSubmit" id="butSubmit">现在去服务</button>
		</div>
	</div>
	<iframe src="${pageContext.request.contextPath}/views/page/mainindex.jsp" id="frames" name="frames" onload="javascript:autoSize(this);"  frameborder="0" marginwidth="0" marginheight="0" width="100%" height="100%">
</body>
</html>
