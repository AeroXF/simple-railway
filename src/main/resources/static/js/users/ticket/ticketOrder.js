/**
 * 
 */
	
var mapWeekDay = { "0":"周日", "1":"周一", "2":"周二", "3":"周三", "4":"周四", "5":"周五", "6":"周六" };
var ticketOrderIndex = 1;

var queryDate, trainNo, startPos, endPos, startTime, arriveTime,
	priceFirstClass, priceSecondClass, priceStand,
	ticketFirstClass, ticketSecondClass, ticketStand;

function initViews(){
	console.log("AAAA");
	queryDate = new Date($("#hidden_queryDate").val());
	
	trainNo    = $("#hidden_trainNo").val();
	startPos   = $("#hidden_startPos").val();
	endPos     = $("#hidden_endPos").val();
	startTime  = $("#hidden_startTime").val();
	arriveTime = $("#hidden_arriveTime").val();
	
	priceFirstClass   = $("#hidden_priceFirstClass").val();
	priceSecondClass  = $("#hidden_priceSecondClass").val();
	priceStand        = $("#hidden_priceStand").val();
	ticketFirstClass  = $("#hidden_ticketFirstClass").val();
	ticketSecondClass = $("#hidden_ticketSecondClass").val();
	ticketStand       = $("#hidden_ticketStand").val();
	
	var ticketOrderColumns = ["序号", "席别", "票种", "姓名", "证件类型", "证件号码", "手机号码", ""];
	var html = "";
	
	console.log("trainNo: " + trainNo);
	
	if(trainNo.startsWith("D")){
		var str1 = "<span>" + $("#hidden_queryDate").val() + "(" + mapWeekDay[queryDate.getDay()] + ")</span><span>" + trainNo + "次</span><span>" + 
			startPos + "站(" + startTime + ")开 - " + endPos + "站(" + arriveTime + "到)</span>";
		$("#ticket_order_train_ticket_title").append(str1);
		
		var str2 = "<span>一等座(<font class='money'>¥" + priceFirstClass + "</font>)" + ticketFirstClass + "张</span>"
			+ "<span>二等座(<font class='money'>¥" + priceSecondClass + "</font>)" + ticketSecondClass + "张</span>"
			+ "<span>站票(<font class='money'>¥" + priceStand + "</font>)" + ticketStand + "张</span>";
		$("#ticket_order_train_ticket_info").append(str2);
	}
	util.drawTable({id:"ticket_order_table", columns:ticketOrderColumns, datArray:null, rowHeight:'40px'});
	appendBlankRow();
}

function appendBlankRow(){
	var html = template("ticket_order_table_tr_template", { oddEven:"odd", count:1, priceFirstClass:priceFirstClass, priceSecondClass:priceSecondClass });
	$("#ticket_order_table").append(html);
}

function appendTicketTableRow(data){
	var credentialNumber = $.trim($("#ticket_order_table tbody tr:eq(0) td:eq(5) input").val());
	if(credentialNumber == ""){
		$("#ticket_order_table tbody tr:eq(0)").remove();
	}
	
	var count = $("#ticket_order_table tbody").children("tr").length;
	data.oddEven = count%2==0?"odd":"even";
	data.count   = count+1;
	var html = template("ticket_order_table_tr_template", data);
	$("#ticket_order_table tbody").append(html);
	
	$("#ticket_order_table tbody tr.even").css("background-color", "#f1f1f1");
}

function addDeleteEvent(){
	$(".ticket-delete-link").unbind("click");
	$(".ticket-delete-link").click(function(){
		var count = $("#ticket_order_table tbody").children("tr").length;
		var credentialNumber = $(this).parent().parent().find("td:eq(5) input").val();
		$(".passengers-info-div1 input[type='checkbox']").each(function(){
			if($(this).prop('checked') && credentialNumber == $(this).attr("credentialNumber")){
				$(this).attr("checked",false);
			}
		});
		$(this).parent().parent().remove();
		var trCount = 1;
		$("#ticket_order_table tbody tr").each(function(){
			if(trCount%2 != 0){
				$(this).removeClass("even").addClass("odd");
			}else{
				$(this).removeClass("odd").addClass("even");
			}
			trCount++;
		});
		if(count == 1){
			appendBlankRow();
		}
		$("#ticket_order_table tbody tr.even").css("background-color", "#f1f1f1");
	});
}

function initButtons(){
	$("#add_passengers_btn").click(function(){
		var layerIndex = layer.open({
			type: 1,
			title: '新增乘客',
		    area: ['800px','200px'], //宽高
		    content: '<table id="add_passengers_table"></table>'
		    	+ '<div>'
		    	+ '<p style="text-align:center;margin-top:20px;">'
		    	+ '     <a class="button button-rounded button-small" style="margin-right:20px;" id="cancel_add_passengers_btn">取消</a>'
		    	+ '     <a class="button button-rounded button-small button-highlight" id="confirm_add_passengers_btn">确定</a>'
		    	+ '</p>'
		    	+ '</div>'
		});
		
		var addPassengersColumns = ["票种", "姓名", "证件类型", "证件号码", "国家/地区"];
		
		var dataArray = [];
		var arr = [
		    "<select><option>成人票</option></select>",
		    "<input type='text' id='add_passengers_username'>",
		    "<select><option>二代身份证</option></select>",
		    "<input type='text' id='add_passengers_credential_number'>",
		    "<select><option>中国CHINA</option></select>"
		];
		dataArray.push(arr);
		util.drawTable({id:"add_passengers_table", columns:addPassengersColumns, datArray:dataArray, rowHeight:'45px'});
		
		$("#cancel_add_passengers_btn").click(function(){
			layer.close(layerIndex);
		});
		
		$("#confirm_add_passengers_btn").click(function(){		
			var data = {
				priceFirstClass:priceFirstClass, 
				priceSecondClass:priceSecondClass,
				credentialNumber:$("#add_passengers_credential_number").val(),
				username:$("#add_passengers_username").val()
			}
			
			layer.close(layerIndex);
			appendTicketTableRow(data);
			addDeleteEvent();
		});
	});
	
	$(".passengers-info-div1 input[type='checkbox']").click(function(){
		var thisCredentialNumber = $(this).attr("credentialNumber");

		if($(this).prop('checked')){
			var data = { 
				priceFirstClass:priceFirstClass, 
				priceSecondClass:priceSecondClass,
				credentialNumber:$(this).attr("credentialNumber"),
				telephone:$(this).attr("telephone"),
				username:$(this).parent().text()
			}
			appendTicketTableRow(data);
			addDeleteEvent();
		}else{
			var trCount = $("#ticket_order_table tbody").children("tr").length;
			var count = 1;
			$("#ticket_order_table tbody tr").each(function(){
				var credentialNumber = $(this).find("td:eq(5) input").val();
				if(credentialNumber == thisCredentialNumber){
					$("#ticket_order_table tbody tr:eq(" + (count-1) + ")").remove();
				} else {
					$(this).find("td:eq(0)").text(count);
					if(count%2 == 0){
						$(this).removeClass("odd").addClass("even");
					}else{
						$(this).removeClass("even").addClass("odd");
					}
					count++;
				}
			});
			if(trCount == 1){
				appendBlankRow();
			}
		}
		$("#ticket_order_table tbody tr.even").css("background-color", "#f1f1f1");
	});
	
	$("#ticket_submit_btn").click(function(){
		layer.confirm('确认提交订单?', {icon: 3, title:'提交订单'}, function(index){
			layer.close(index);
			var ticketOrderArray = [];
			$("#ticket_order_table tbody tr").each(function(index){
				var data = {
					trainNo: trainNo,
					trainTag: $("#hidden_queryDate").val().split("-").join("") + trainNo,
					seatType: $(this).find("td:eq(1) select option:selected").val(),
					credentialNumber: $(this).find("td:eq(5) input").val(),
					custName: $(this).find("td:eq(3) input").val(),
					telephone: $(this).find("td:eq(6) input").val(),
					startPos: startPos,
					endPos: endPos,
					price: $(this).find("td:eq(1) select option:selected").attr("price"),
					timeTrainStart: startTime
				}
				ticketOrderArray.push(data);
			});
			
			console.log(ticketOrderArray);
			
			var form = template("ticket_order_form_template", {ticketOrderList: JSON.stringify(ticketOrderArray)});

			$.post("/ticketOrder/add/ticketOrder", $(form).serialize(), function(data){
				$("#order-no-input").val(data);
				spa.shell.changeAnchorPart({model:"ticket", active:"orderResult"});
			});
		}); 
	})
}

$(function(){
	initViews();
	initButtons();
})