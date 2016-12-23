/**
 * 
 */
function adjustTrainStartInfo(){
	for(var i = 0; i < list.length; i++){
		var data = list[i];
		var time1 = "", time2 = "";

		time1 = data['trainTag'].substr(0, 4) + "-" + data['trainTag'].substr(4, 2) + "-" + data['trainTag'].substr(6, 2);
		time2 = " " + data['timeTrainStart'];

		$(".order-unfinished-body div:eq(" + i + ") span:eq(4)").html("乘车日期：<font>" + time1 + time2 + "</font>");
	}
}

function addDivHoverEvent(){
	$(".order-list-div").hover(function(){
		$(this).find(".icon-btn-down").css("background-position", "0 -966px");
		if($(this).find(".icon-btn-down").length > 0){
			$(this).css("color", "#FFF").css("background-color", "#41BAE2");
		}
	}, function(){
		$(this).find(".icon-btn-down").css("background-position", "0 -1000px");
		if($(this).find(".icon-btn-down").length > 0){
			$(this).css("color", "#000").css("background-color", "#FFF");
		}
	});
}

function addDivClickEvent(){
	$(".order-list-div").click(function(){
		var div = $(this);
		var state = $(this).attr("state");
		if(state == "open"){
			$(this).attr("state", "close");
			var orderNo = $(this).attr("orderNo");
			$(this).css("color", "#FFF").css("background-color", "#41BAE2");
			$(this).find("span:eq(0)").removeClass("icon-btn-down").addClass("icon-btn-up");
			$(".icon-btn-up").css("background-position", "0px -950px");
			$.get("/orderUnfinished/get/byOrderNo", {orderNo:$(this).attr("orderNo")}, function(list){
				var spanDetails = "<span class='ticket-order-detail-span' style='margin-left:0px;'><table id='" + orderNo + "_table'></table></span>";
				div.append(spanDetails);
				var dataArray = [];
				var columns = ["序号", "车次信息", "席位信息", "旅客信息", "票款金额", "车票状态"];
				for(var i = 0; i < list.length; i++){
					var data = list[i];
					dataArray.push([
					    i + 1,
					    data["trainNo"],
					    data["seatNo"],
					    data["custName"],
					    data["price"],
					    data["state"] == 'P' ? "已支付":"未支付"
					]);
				}
				
				var height = (83 + 36*(list.length+1));
				div.css("height", height + "px");
				util.drawTable({id:orderNo + "_table", columns:columns, datArray:dataArray });
				$("#" + orderNo + "_table").css("background-color", "#FFF").css("color", "#000");
				$("#" + orderNo + "_table thead").css("background-color", "#EEF1F8").css("color", "#000");
				$("#" + orderNo + "_table thead th").css("font-weight", "normal").css("border-collapse", "collapse").css("border", "1px solid #C0D7E4");
				$("#" + orderNo + "_table tbody td").css("color", "#666666");
				$("#" + orderNo + "_table tbody td").css("border-collapse", "collapse").css("border", "1px solid #C0D7E4");
				
				var bizActionSpan = "<tfoot><tr><td colspan='6'>" +
						"<a class='button button-rounded button-small order-cancel' orderNo='" + orderNo + "' style='margin-right:10px;'>取消订单</a>" +
						"<a class='button button-highlight button-rounded button-small order-payment' orderNo='" + orderNo + "' style='margin-right:10px;'>继续支付</a>" +
						"</td></tr></tfoot>";
				$("#" + orderNo + "_table").append(bizActionSpan);
				$("#" + orderNo + "_table tfoot tr td").css("text-align", "right");
				$("#" + orderNo + "_table tfoot tr").css("border", "1px solid #C0D7E4").css("height", "40px").css("line-height", "40px");
				
				$(".order-cancel").click(function(){
					var orderNo = $(this).attr("orderNo");
					layer.confirm('确认要取消订单么?', {icon: 3, title:'取消订单'}, function(index){
						layer.close(index);
						$.get("/ticketOrder/update/cancelTicketOrder", {orderNo: orderNo}, function(data){
							window.location.reload();
						});
					});
					return false;
				});
				
				$(".order-payment").click(function(){
					var btnOrderNo = $(this).attr("orderno");
					$("#order-no-input").val(btnOrderNo);
					spa.shell.changeAnchorPart({model:"ticket", active:"orderResult"});
					return false;
				});
			});
		} else {
			$(this).attr("state", "open");
			$(this).find("span:eq(0)").removeClass("icon-btn-up").addClass("icon-btn-down");
			$(this).find(".icon-btn-down").css("background-position", "0 -966px");
			$(this).find(".ticket-order-detail-span").remove();
			div.css("height", "36px");
			addDivHoverEvent();
		}
	});
}

function initViews(){
	$("#home_current_position").html("您现在的位置：客运首页 > 我的12306> <font class='position-font'>未完成订单</font>");
	
	$("#orderUnfinished").addClass("model-active").css("padding-left", "26px");

	adjustTrainStartInfo();
	
	addDivHoverEvent();
	
	addDivClickEvent();
}

$(function(){
	initViews();
});