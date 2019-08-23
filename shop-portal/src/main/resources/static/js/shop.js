var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("SHOP_TOKEN");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8085/user/info/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(ret){
				if(ret.code == 1){
					var username = ret.data.username;
					var html = username + "，欢迎来到淘淘！<a href=\"http://loclhost:8082/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});