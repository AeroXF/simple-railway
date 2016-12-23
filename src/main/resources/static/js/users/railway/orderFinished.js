/**
 * 
 */
function initViews(){
	$("#home_current_position").html("您现在的位置：客运首页 > 我的12306> <font class='position-font'>已完成订单</font>");
	$("#orderFinished").addClass("model-active").css("padding-left", "26px");
	$("#query_start_date").val(util.formatDate(new Date(), "yyyy-MM-dd"));
	$("#query_end_date").val(util.formatDate(new Date(), "yyyy-MM-dd"));
}

function initButtons(){
	$("#query_start_date").click(function() {
		WdatePicker({ isShowClear:false, readOnly:true });
	});
	
	$("#query_end_date").click(function() {
		WdatePicker({ isShowClear:false, readOnly:true });
	});
	
	$("#ticket_order_query_btn").click(function(){
		var params = { startDate:$("#query_start_date").val(), endDate:$("#query_end_date").val() };
		$.get("/orderFinished/get/orderFinished", params, function(list){
			var columns = ["序号", "车次信息", "席位信息", "旅客信息", "票款金额", "状态", "操作"];
			var dataArray = [];
			for(var i = 0; i < list.length; i++){
				var data = list[i];
				var state = "";
				if(data["state"] == 'P'){
					state = "已支付";
				} else if(data["state"] == 'Y'){
					state = "已提交";
				} else if(data["state"] == 'N'){
					state = "已失效";
				}
				var time = data['trainTag'].substr(0, 4) + "-" + data['trainTag'].substr(4, 2) + "-" + data['trainTag'].substr(6, 2) + " " + data["timeTrainStart"] + "开";
				dataArray.push([
				    i+1,
				    time + "<br/>" + data['trainNo'] + " " + data['startPos'] + "-" + data['endPos'],
				    data['seatNo'],
				    data['custName'],
				    data['price'],
				    state,
				    data["state"] == 'P' ? '<a ticket-id="' + data['id'] + '" class="button button-primary button-rounded button-small ticket-refund">退票</a>' : ''
				]);
			}
			util.drawTable({id:'order_finished_table', columns:columns, datArray:dataArray, rowHeight:'45px'});
			//调整样式
			$("#order_finished_table").css("width", "98%").css("margin", "0 auto");
			$("#order_finished_table thead").css("background-color", "#EEF1F8").css("color", "#000");
			$("#order_finished_table thead th").css("font-weight", "normal").css("border-collapse", "collapse").css("border", "1px solid #C0D7E4");
			$("#order_finished_table tbody td").css("color", "#666666");
			$("#order_finished_table tbody td").css("border-collapse", "collapse").css("border", "1px solid #C0D7E4");
			
			$(".ticket-refund").click(function(){
				var orderId = $(this).attr("ticket-id");
				layer.confirm('确认要退票么?', {icon: 3, title:'退票'}, function(index){
					layer.close(index);
					$.get("/orderFinished/update/refundOrder", {orderId:orderId}, function(){
						window.location.reload();
					});
				});
			});
		});
	});
}

$(function(){
	initViews();
	initButtons();
});