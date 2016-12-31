/**
 * 
 */
/*function addTicketOrderEvent(){
	$("#ticket_order_btn").click(function(){
		ticketOrderParams = {
			trainTag  : $("#ticket_order_train_tag").val(),
			startPos  : $("#ticket_order_start_pos").val(),
			endPos    : $("#ticket_order_end_pos").val(),
			trainNo   : $("#ticket_order_train_no").val(),
			queryDate : $("#ticket_order_query_date").val()
		}
		changeAnchorPart({ model: "ticket", active:"ticketOrder"});
	});
}*/

function getTicket(columns){
	var startPos = $.trim($("#query_start_pos").val());
	var endPos   = $.trim($("#query_end_pos").val());
	if(startPos != ""){
		util.setCookie("startPos", startPos);
	}
	if(endPos != ""){
		util.setCookie("endPos", endPos);
	}
	var params = {startPos: startPos, endPos: endPos, startDate:$("#query_start_date").val()};
	
	$.get("/ticket/getTicket", params, function(list){
		var dataArray = [];
		for(var i = 0; i < list.length; i++){
			var ticket = list[i];
			
			var startTime, deptTime;
			if(ticket["startTime"]){
				startTime = new Date(ticket["startTime"]);
			}
			if(ticket["deptTime"]){
				deptTime = new Date(ticket["deptTime"]);
			}
			
			var minute = (ticket["deptTime"] - ticket["startTime"])/(60 * 1000);
			var hour = Math.floor(minute/60);
			minute = minute - hour*60;
			var timeInterval = (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
			
			var startPos = $("#query_start_pos").val();
			var endPos   = $("#query_end_pos").val();
			
			dataArray.push([
			    "<a class='train-detail-link' trainTag='" + ticket["trainTag"] + "'>" + ticket["trainNo"] + "</a>",
			    startPos,
			    endPos,
			    util.formatDate(startTime, 'hh:mm'),
			    util.formatDate(deptTime,  'hh:mm'),
			    timeInterval,
			    ticket["ticketBusiness"],
			    ticket["ticketSpecial"],
			    ticket["ticketFirstClass"],
			    ticket["ticketSecondClass"],
			    ticket["ticketAdvancedSoftSleeper"],
			    ticket["ticketSoftSleeper"],
			    ticket["ticketHardSleeper"],
			    ticket["ticketSoftSit"],
			    ticket["ticketHardSit"],
			    ticket["ticketStand"],
			    '<a class="button button-primary button-tiny button-rounded btn-ticket-order" trainNo="' + ticket["trainNo"] + '" startPos="' + startPos + '" endPos="' + endPos + '" trainTag="' + ticket["trainTag"] + '">预订</a>'
			]);
		}
		util.drawTable({id:"ticket_list_table", columns:columns, datArray:dataArray, rowHeight:'45px'});
		
		//预订车票
		$(".btn-ticket-order").click(function(){
			$("#ticket_order_train_tag").val($(this).attr("trainTag"));
			$("#ticket_order_start_pos").val($(this).attr("startPos"));
			$("#ticket_order_end_pos").val($(this).attr("endPos"));
			$("#ticket_order_train_no").val($(this).attr("trainNo"));
			$("#ticket_order_query_date").val($("#query_start_date").val());
			
			$("#ticket_order_btn").trigger("click");
		});
		
		//车次详情查看
		$(".train-detail-link").click(function(){
			viewTrainDetail($(this).attr("trainTag"), $(this).text());
		});
	});
}

function viewTrainDetail(trainTag, train){
	var params = { trainTag:trainTag };
	$.get("/ticket/getTicketByTrainTag", params, function(list){
		var dataArray= [];
		
		var columns = [];
		if(train.startsWith("D")){
			columns = ["车次", "站点", "开车时间", "到达时间", "一等座", "二等座", "站票"];
		} else if(train.startsWith("G")){
			columns = ["车次", "站点", "开车时间", "到达时间", "商务座", "特等座", "一等座", "二等座", "站票"]
		}
		
		for(var i = 0; i < list.length; i++){
			var ticket = list[i];
			if(ticket["startTime"]){
				startTime = new Date(ticket["startTime"]);
			}else{
				startTime = null;
			}
			if(ticket["deptTime"]){
				deptTime = new Date(ticket["deptTime"]);
			}else{
				deptTime = null;
			}
			
			var arr = [];
			if(train.startsWith("D")){
				arr = [
				    ticket["trainNo"], ticket["stationName"], util.formatDate(startTime, 'hh:mm'), util.formatDate(deptTime, 'hh:mm'), 
				    '¥' + ticket["priceFirstClass"], '¥' + ticket["priceSecondClass"], '¥' + ticket["priceStand"]
				];
			} else if(train.startsWith("G")){
				arr = [
				    ticket["trainNo"], ticket["stationName"], util.formatDate(startTime, 'hh:mm'), util.formatDate(deptTime, 'hh:mm'), 
				    '¥' + ticket["priceBusiness"], '¥' + ticket["priceSpecial"], '¥' + ticket["priceFirstClass"], '¥' + ticket["priceSecondClass"], 
				    '¥' + ticket["priceStand"]
				];
			}
			dataArray.push(arr);
		}
		
		layer.open({
			title: train + '列车时刻详情',
			type: 1,
			area: ['90%', '600px'],
			content: "<table id='train_detail_table' class='display' cellspacing='0' width='100%'></table>"
		});
		
		util.drawTable({ id:"train_detail_table", columns:columns, datArray:dataArray });
	});
}

var columns = ["车次", "出发站", "到达站", "出发时间", "到达时间", "历时", "商务座", "特等座", "一等座", "二等座", "高级软卧", "软卧", "硬卧", "软座", "硬座", "无座", "备注"];

function initViews(){
	var tableHeight = document.body.clientHeight * 0.8;
	$("#ticket_query_result").css("height", tableHeight);
	$("#query_start_date").val(util.formatDate(new Date(), 'yyyy-MM-dd'));
	var datArray = [];
	util.drawTable({id: "ticket_list_table", columns: columns, datArray: datArray, rowHeight:'45px'});
}

function initButtons(){
	$("#query_start_date").click(function() {
		WdatePicker({ isShowClear:false, readOnly:true });
	});
	
	$("#ticket_query_btn").click(function(){
		getTicket(columns);
	});
	
	//addTicketOrderEvent();
}

function setDefaultValues(){
	var startPos = util.getCookie("startPos");
	var endPos   = util.getCookie("endPos");
	if(startPos != null){
		$("#query_start_pos").val(startPos);
	}
	if(endPos   != null){
		$("#query_end_pos").val(endPos);
	}
}
	
$(function(){
	initViews();
	initButtons();
	setDefaultValues();	
});