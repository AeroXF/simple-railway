/**
 * 
 */
spa.contacts = (function(){
	var drawContactsTable = function(list){
		console.log(list);
		var columns = ["序号", "姓名", "证件类型", "证件号码", "手机/电话", "旅客类型", "操作"];
		var dataArray = [];
		for(var i = 0; i < list.length; i++){
			var data = list[i];
			var isNotSelf = (data["username"] != $("#current_user_input").val());
			dataArray.push([
			    "<input type='checkbox' name='sequence' contactid='" + data["id"] + "' username='" + data["username"] + "' gender='" + data["gender"] + "' " + (isNotSelf? "": "disabled") + ">" + (i+1),
			    data["username"],
			    data["credentialType"] == 1 ? '身份证': '其它',
			    data["credentialNumber"],
			    data["telephone"],
			    data["custType"] == 0? '成人': '其它',
			    isNotSelf ? '<i class="fa fa-times-circle delete-contacts-icon" aria-hidden="true"></i>'
			    	+ '<i class="fa fa-pencil-square-o modify-contacts-icon" aria-hidden="true"></i>' : ""
			]);
		}
		
		util.drawTable({id:"my_contacts_table", columns:columns, datArray:dataArray});
		
		$(".delete-contacts-icon").click(function(){
			var ele = $(this).parent().parent().find("input[type='checkbox']");
			var username = ele.attr("username");
			layer.confirm('确认要删除所选中的联系人么?', {icon: 3, title:'删除联系人'}, function(index){
				layer.close(index);
				$.get(APP_PATH + "/contacts/del/" + username, function(list){
					layer.alert('删除联系人成功', {title:'删除联系人'});
					drawContactsTable(list);
				});
			});   
		});
		
		$(".modify-contacts-icon").click(function(){
			var ele = $(this).parent().parent().find("input[type='checkbox']");
			var username = ele.attr("username");
			var id = ele.attr("contactid");
			var gender = ele.attr("gender");
			var credentialNumber = $(this).parent().parent().find("td:eq(3)").text();
			var telephone = $(this).parent().parent().find("td:eq(4)").text();
			layer.open({
				title: '修改联系人',
				type: 1,
				area: ['800px', '600px'],
				content: template("add_contact_template", {}),
				success: function(layero, index){
					$("#cancel_add_contact_btn").click(function(){
						layer.close(index);
					});
					
					$("#save_add_contact_btn").click(function(){
						var formData = $("#add_contact_form").serialize();
						$.post(APP_PATH + "/contacts/modify/" + id, formData, function(list){
							layer.close(index);
							layer.alert('修改联系人成功', {title:'修改联系人'});
							drawContactsTable(list);
						});
					});
				}
			});
			$(".add-contact-div-title i").text("修改联系人");
			$("input[name='username']").val(username);
			$("input[name='gender']:eq(" + gender + ")").attr("checked",'checked');
			$("input[name='credentialNumber']").val(credentialNumber);
			$("input[name='telephone']").val(telephone);
		});
	}
	
	var initViews = function(){
		$("#home_current_position").html("您现在的位置：客运首页 > 我的12306> <font class='position-font'>常用联系人</font>");		
		
		$.get(APP_PATH + "/contacts/get/contacts", function(list){
			drawContactsTable(list);
		});
	}
	
	var initButtons = function(){
		$("#add_contacts_btn").click(function(){
			layer.open({
				title: '添加常用联系人',
				type: 1,
				area: ['800px', '600px'],
				content: template("add_contact_template", {}),
				success: function(layero, index){
					$("#cancel_add_contact_btn").click(function(){
						layer.close(index);
					});
					
					$("#save_add_contact_btn").click(function(){
						var formData = $("#add_contact_form").serialize();
						$.post(APP_PATH + "/contacts/add/contact", formData, function(list){
							layer.close(index);
							drawContactsTable(list);
						});
					});
				}
			})
		});
		
		$("#del_contacts_btn").click(function(){
			var userArray = [];
			$("#my_contacts_table input:checkbox[name='sequence']:checked").each(function(i){
				userArray.push($(this).attr('username'));
			});
			layer.confirm('确认要删除所选中的联系人么?', {icon: 3, title:'删除联系人'}, function(index){
				layer.close(index);
				$.get(APP_PATH + "/contacts/del/" + userArray.join("-"), function(list){
					layer.alert('删除联系人成功', {title:'删除联系人'});
					drawContactsTable(list);
				});
			});
		});
	}
	
	var initModule = function($container){		
		initViews();
		initButtons();
	};
	
	return {
		initModule: initModule
	};
}());