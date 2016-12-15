$(function(){
	if($("#register_complete").val() == "complete"){
		$("#login_form").submit();
	}
	
	var validator = $("#register_form").validate({
		errorPlacement: function(error, ele){
			$(ele).closest("form").find("label[id='label_" +ele.attr("id") + "']").append(error);
		},
		errorElement: "span"
	});
	
	$("#nickname").blur(function(){
		$.get(APP_PATH + "/rest/getUserByName", {nickname: $("#nickname").val()}, function(data){
			if(data == "found"){
				$("#register_err_msg").text("该用户名已被注册");
			}else{
				$("#register_err_msg").text("");
			}
		});
	});
});