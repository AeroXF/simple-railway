/**
 * 
 */
var gender, birthday, email;

function initViews(){
	$("#home_current_position").html("您现在的位置：客运首页 > 我的12306> <font class='position-font'>查看个人信息</font>");
	
	$("#selfInfo").addClass("model-active").css("padding-left", "26px");
	
	$.get("/selfInfo/get/selfInfo", function(data){
		console.log(data);
		$("#self_info_form p").each(function(){
			var id = $(this).find("span:eq(1)").attr("id");
			$("#" + id).html(data[id]);
		});
		$("#birthday").html(util.formatDate(new Date(data["birthday"]), "yyyy-MM-dd"));
		$("#credentialType").html(data["credentialType"] == 1 ? "二代身份证": "其他");
		$("#country").html(data["country"] == 0 ? "中国CHINA": "其他");
		$("#gender").html(data["gender"] == 0 ? "男": "女");
		$("#custType").html(data["custType"] == 0 ? "成人": "其他");
		
		gender = $("#gender").text();
		birthday = $("#birthday").text();
		email = $("#email").text();
	});
}

function initButtons(){
	$("#self_info_edit_btn").click(function(){
		var text = $(this).text();
		
		if(text == '修改'){
			$(this).text('取消');
			var genderVal = gender == '男' ? 0 : 1;
			
			$("#gender").empty().append("<input type='radio' name='gender' value='0'>男<input type='radio' name='gender' value='1'>女");
			$("#birthday").empty().append("<input type='text' name='birthday' readonly='readonly' value='" + birthday + "' class='Wdate'/>");
			$("#email").empty().append("<input type='text' name='email' value='" + email + "'/>");
			
			$("input[name='birthday']").click(function() {
				WdatePicker({ isShowClear:false, readOnly:true });
			});
			
			$("input[type='radio'][value='" + genderVal + "']").attr("checked", "true");
			$(".self-info-body input[type='text']").css("width", "140px").css("height", "26px").css("line-height", "26px")
				.css("font-family", "Courier New");
			$(".self-info-body input[type='radio']").css("width", "30px");
		} else {
			$(this).text('修改');
			$("#gender").empty().append(gender);
			$("#birthday").empty().append(birthday);
			$("#email").empty().append(email);
		}
	});
	
	$("#self_info_save_btn").click(function(){
		if($("#self_info_edit_btn").text() == "修改"){
			return;
		}
		
		var params = {
			gender:   $("input[name='gender']:checked").val(),
			birthday: $("input[name='birthday']").val(),
			email:    $("input[name='email']").val()
		}
		
		console.log(params);
		
		$.get("/selfInfo/modify/selfInfo", params, function(data){
			if(data == "success"){
				layer.alert('修改成功', {title:'修改个人信息'}, function(){
					history.go(0);
				});
			}
		});
	})
}
	

$(function(){
	initViews();
	initButtons();
});