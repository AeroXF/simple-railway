/**
 * 
 */	
function cancelTicket(trainTag){
	var params = { orderNo:$("#order_no_input").val() };
	$.get("/ticketOrder/update/cancelTicketOrder", params, function(data){
		$("#ticket").trigger("click");
	});
}

function initViews(){
	if(typeof spa.timeId != "undefined"){
		clearInterval(spa.timeId);
	}
	
	var state = $("#hidden_ticket_order_state").val();
	
	if(state == 'P'){
		$("#ticket_order_warning_div").empty().append("<span id='time_left_span'>订单支付完成</span>");
	}else if(state == 'Y'){
		var date1 = new Date($("#hidden_time_buy_ticket").val());
		var date2 = new Date();
		
		var time = date1.getTime() - date2.getTime() + 1800 * 1000;
		var count1 = Math.floor(time/(60*1000));
		var count2 = Math.floor((time%(60*1000))/1000);
		
		spa.timeId = setInterval(function(){
			count2--;
			if(count2 < 0){
				count1--;
				count2 = 59;
			}
			if(count1 < 0){
				$("#time_left_span").text("订单已经过期");
				clearInterval(spa.timeId);
				cancelTicket();
				return;
			}
			$("#time_left_span").text((count1 < 10 ? "0" + count1 : count1) + "分" + (count2 < 10 ? "0" + count2 : count2) + "秒");
		}, 1000);
	}
}

function initButtons(){
	$("#cancel_ticket_order_btn").click(function(){
		layer.confirm('确认取消订单?', {icon: 3, title:'取消订单'}, function(index){
			layer.close(index);
			cancelTicket();
		});
	});
	
	$("#pay_online_btn").click(function(){
		layer.open({
			title: '订单支付',
			type: 1,
			area: ['600px', '600px'],
			content: template("order_pay_template", {})
		})
		
		$("#alipay_btn").unbind("click");
		$("#alipay_btn").click(function(){
			$("#alipay_btn").text("支付中, 请稍候...");
			$.get("/ticketOrder/update/payTicketOrder", {orderNo:$("#order_no_input").val()}, function(){
				window.location.reload();
			});
		});
	});
}
	
$(function(){
	initViews();
	initButtons();
});