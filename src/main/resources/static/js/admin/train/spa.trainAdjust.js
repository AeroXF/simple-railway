spa.trainAdjust = (function(){
	
	var trainList;
	
	var getAllTrain = function(){
		$.get(APP_PATH + "/admin/train/get/allTrain", function(list){
			trainList = list;
			var trainMap = {};
			
			for(var i = 0; i < list.length; i++){
				var trainNo     = list[i]['trainNo'];
				var stationName = list[i]['stationName'];
				var startTime   = list[i]['startTime'];
				var deptTime    = list[i]['deptTime'];
				var statOrder   = list[i]['statOrder'];
				
				if(trainMap[trainNo] == undefined){
					trainMap[trainNo] = {'statOrderMax':statOrder, 'statOrderMin':statOrder, 'stationMax': stationName, 'stationMin': stationName, 'startTime':startTime, 'deptTime':deptTime};
				} else {
					if(statOrder > trainMap[trainNo]['statOrderMax']){
						trainMap[trainNo]['statOrderMax'] = statOrder;
						trainMap[trainNo]['stationMax'] = stationName;
						trainMap[trainNo]['deptTime'] = deptTime;
					} else if(statOrder < trainMap[trainNo]['statOrderMin']){
						trainMap[trainNo]['statOrderMin'] = statOrder;
						trainMap[trainNo]['stationMin'] = stationName;
						trainMap[trainNo]['startTime'] = deptTime;
					}
				}
			}
			
			var count = 0;
			$("#all_train_overview_div").empty();
			for(var trainNo in trainMap){
				count++;
				var htmlDiv = "<div class='train-list-overview'><input type='checkbox'><span style='margin-left:10px;'>" + count + "</span><span style='margin-left:30px;'>" + trainNo + "</span><span style='margin-left:30px;'>车次：" + trainMap[trainNo]['stationMin'] + "  -->  " + 
					trainMap[trainNo]['stationMax'] + "</span><span style='margin-left:30px;'>出发时间：" + trainMap[trainNo]['startTime'] + "</span><span>到达时间：" + trainMap[trainNo]['deptTime'] + "</span>" +
					"<span><a class='train-detail-view'>查看详情</a></span></div>";
				$("#all_train_overview_div").append(htmlDiv);
			}
			
			$(".train-detail-view").click(function(){
				var trainNo = $(this).parent().parent().find("span:eq(1)").text();
				var dataArray = [];
				for(var i=0; i < list.length; i++){
					if(list[i]['trainNo'] == trainNo){
						if(trainNo.startsWith("D")){
							dataArray.push([
							    list[i]['statOrder'] + 1, 
							    list[i]['trainNo'], 
							    list[i]['stationName'], 
							    list[i]['startTime'], 
							    list[i]['deptTime'],
								list[i]['priceFirstClass'], 
								list[i]['ticketFirstClass'], 
								list[i]['priceSecondClass'], 
								list[i]['ticketSecondClass'], 
								list[i]['priceStand'], 
								list[i]['ticketStand']
							]);
						}else if(trainNo.startsWith("G")){
							dataArray.push([
							    list[i]['statOrder'] + 1, 
							    list[i]['trainNo'], 
							    list[i]['stationName'], 
							    list[i]['startTime'], 
							    list[i]['deptTime'], 
								list[i]['priceBusiness'], 
								list[i]['ticketBusiness'],
								list[i]['priceFirstClass'], 
								list[i]['ticketFirstClass'], 
								list[i]['priceSecondClass'], 
								list[i]['ticketSecondClass']
							]);
						}
					}
				}

				if(trainNo.startsWith("D")){
					//动车
					var columns = ["序号", "车次", "站点", "开车时间", "到达时间", "一等座价格", "一等座票数", "二等座价格", "二等座票数", "站票价格", "站票票数"];
					layer.open({
						title: '增加车次',
						type: 1,
						area: ['1200px', '600px'],
						content: template("view_train_detail_template", {}),
						success: function(layero, index){
							var options = {id:"train_detail_table", columns:columns, datArray:dataArray};
							util.drawTable(options);
						}
					});
				}else if(trainNo.startsWith("G")){
					//高铁
					console.log(dataArray);
					var columns = ["序号", "车次", "站点", "开车时间", "到达时间", "商务座价格", "商务座票数", "一等座价格", "一等座票数", "二等座价格", "二等座票数"];
					layer.open({
						title: '增加车次',
						type: 1,
						area: ['1200px', '600px'],
						content: template("view_train_detail_template", {}),
						success: function(layero, index){
							var options = {id:"train_detail_table", columns:columns, datArray:dataArray};
							util.drawTable(options);
						}
					});
				}
			});
			
		});
	};
	
	var initViews = function(){
		getAllTrain();
	};
	
	var addCancelTrainLinkEvent = function(){
		$(".cancel-train-link").click(function(){
			var length = $(".cancel-train-link").length;
			if(length > 1){
				$(this).parent().remove();
				$(".train-stat-order").each(function(index, ele){
					$(this).text(index + 1);
				});
			}else{
				$(this).parent().find("input").each(function(){
					$(this).val("");
				});
			}
		});
	}
	
	var addCancelStationLinkEvent = function(){
		$(".cancel-station-link").click(function(){
			var length = $(".cancel-station-link").length;
			
			if(length > 1){
				$(this).parent().remove();
				$(".station-order").each(function(index, ele){
					$(this).text(index + 1);
				});
			}else{
				$(this).parent().find("input").each(function(){
					$(this).val("");
				});
			}
		});
	};
	
	var addSeatType = function(checked, category){
		if(category == '0'){
			$(".station-label-business").remove();
			$(".station-price-business").remove();
			if(checked){
				$("<span class='station-label-business'>商务座价格:</span><span class='station-price-business'><input type='text' class='station-price-business-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '1'){
			$(".station-label-special").remove();
			$(".station-price-special").remove();
			if(checked){
				$("<span class='station-label-special'>特等座价格:</span><span class='station-price-special'><input type='text' class='station-price-special-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '2'){
			$(".station-label-first").remove();
			$(".station-price-first").remove();
			if(checked){
				$("<span class='station-label-first'>一等座价格:</span><span class='station-price-first'><input type='text' class='station-price-first-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '3'){
			$(".station-label-second").remove();
			$(".station-price-second").remove();
			if(checked){
				$("<span class='station-label-second'>二等座价格:</span><span class='station-price-second'><input type='text' class='station-price-second-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '4'){
			$(".station-label-advanced").remove();
			$(".station-price-advanced").remove();
			if(checked){
				$("<span class='station-label-advanced'>高级软卧价格:</span><span class='station-price-advanced'><input type='text' class='station-price-advanced-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '5'){
			$(".station-label-softsleeper").remove();
			$(".station-price-softsleeper").remove();
			if(checked){
				$("<span class='station-label-softsleeper'>软卧价格:</span><span class='station-price-softsleeper'><input type='text' class='station-price-softsleeper-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '6'){
			$(".station-label-hardsleeper").remove();
			$(".station-price-hardsleeper").remove();
			if(checked){
				$("<span class='station-label-hardsleeper'>硬卧价格:</span><span class='station-price-hardsleeper'><input type='text' class='station-price-hardsleeper-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '7'){
			$(".station-label-softsit").remove();
			$(".station-price-softsit").remove();
			if(checked){
				$("<span class='station-label-softsit'>软座价格:</span><span class='station-price-softsit'><input type='text' class='station-price-softsit-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '8'){
			$(".station-label-hardsit").remove();
			$(".station-price-hardsit").remove();
			if(checked){
				$("<span class='station-label-hardsit'>硬座价格:</span><span class='station-price-hardsit'><input type='text' class='station-price-hardsit-input'/></span>").insertBefore(".station-order-label");
			}
		}else if(category == '9'){
			$(".station-label-stand").remove();
			$(".station-price-stand").remove();
			if(checked){
				$("<span class='station-label-stand'>站票价格:</span><span class='station-price-stand'><input type='text' class='station-price-stand-input'/></span>").insertBefore(".station-order-label");
			}
		}
	};
	
	var getCheckedTrainNo = function(){
		var trainNo = "";
		
		$("#all_train_overview_div .train-list-overview").each(function(index, ele){
			var checkbox = $(this).find("input:eq(0)");
			var checked = checkbox.is(":checked");
			
			if(checked){
				trainNo = $(this).find("span:eq(1)").text();
				return;
			}
		});
		
		return trainNo;
	};
	
	var initButtons = function(){
		$("#add_train_btn").click(function(){
			layer.open({
				title: '增加车次',
				type: 1,
				area: ['1200px', '600px'],
				content: template("add_train_template", {}),
				success: function(layero, index){
					for(var i=0; i<10; i++){
						$(template("add_station_template", {order: (i+1)})).insertBefore($("#station_control_div"));
					}
					
					$("#add_station_btn").click(function(){
						var length = $(".add-station-div").length;
						$(template("add_station_template", {order: (length+1)})).insertBefore($("#station_control_div"));
						$(".train-seat-type").each(function(index, ele){
							var checked  = $(this).is(":checked");
							var category = $(this).attr("category");
							addSeatType(checked, category);
						});
						addCancelStationLinkEvent();
					});
					
					$("#save_train_btn").click(function(){
						var params = [];
						$(".add-station-div").each(function(){
							var map = {};
							var station = $(this).find("input:eq(0)").val();
							if(station == ""){
								return;
							}
							map['trainNo']     					= $(".train-no-input").val();
							map['stationName']     				= $(this).find("input:eq(0)").val();
							map['startTime']   					= $(this).find("input:eq(1)").val();
							map['deptTime']  					= $(this).find("input:eq(2)").val();
							map['statOrder']       				= $(this).find(".station-order").text();
							map['priceBusiness']    			= $(this).find(".station-price-business-input").val();
							map['priceSpecial']     			= $(this).find(".station-price-special-input").val();
							map['priceFirstClass']       		= $(this).find(".station-price-first-input").val();
							map['priceSecondClass']      		= $(this).find(".station-price-second-input").val();
							map['priceAdvancedSoftSleeper']    	= $(this).find(".station-price-advanced-input").val();
							map['priceSoftSleeper'] 			= $(this).find(".station-price-softsleeper-input").val();
							map['priceHardSleeper'] 			= $(this).find(".station-price-hardsleeper-input").val();
							map['priceSoftSit']     			= $(this).find(".station-price-softsit-input").val();
							map['priceHardSit']     			= $(this).find(".station-price-hardsit-input").val();
							map['priceStand']       			= $(this).find(".station-price-stand-input").val();
							params.push(map);
						});
						
						$.get(APP_PATH + "/admin/train/add/train", {json: JSON.stringify(params)}, function(data){
							console.log(data);
							layer.close(index);
							if(data == "success"){
								layer.open({ title:'添加车次',content:'添加成功', icon:1 });
								getAllTrain();
							}else{
								layer.open({ title:'添加车次',content:'添加失败', icon:2 });
							}
						});
					});
					
					$("#cancel_train_btn").click(function(){
						layer.close(index);
					});
					
					$(".train-seat-type").click(function(){
						console.log($(this).attr("category"));
						var category = $(this).attr("category");
						var checked  = $(this).is(":checked");
						console.log($(this).is(":checked"));
						addSeatType(checked, category);
					});
					
					addCancelStationLinkEvent();
				}
			});
		});
		
		$("#del_train_btn").click(function(){
			var trainNo = getCheckedTrainNo();
			
			if(trainNo == ""){
				layer.open({icon:"2", title:"错误", content:"请选择一条数据"});
			} else {
				layer.confirm('确定要删除' + trainNo + '车次?', {icon: 3, title:'删除'}, function(index){
					layer.close(index);
					$.get(APP_PATH + "/admin/train/del/train", {trainNo: trainNo}, function(data){
						if(data == "success"){
							layer.open({icon:1, title:"删除车次", content:"删除成功, 已经生成的车票不会受到影响"});
							getAllTrain();
						} else{
							layer.open({icon:2, title:"删除车次", content:"删除失败"});
						}
					});
				});
			}
		});
		
		$("#modify_train_btn").click(function(){
			var trainNo = getCheckedTrainNo();
			
			if(trainNo == ""){
				layer.open({icon:"2", title:"错误", content:"请选择一条数据"});
			} else {
				layer.open({
					title: '编辑车次',
					type: 1,
					area: ['1200px', '600px'],
					content: template("modify_train_detail_template", {}),
					success: function(layero, index){
						if(trainNo.startsWith("D")){
							var strHead = 
								  "<div class='modify-station-div'>"
								+ "	   <span>顺序</span><span>车次</span><span>站点</span><span>开车时间</span><span>到达时间</span><span>一等座价格</span><span>一等座票数</span><span>二等座价格</span><span>二等座票数</span><span>站票价格</span><span>站票票数</span>"
								+ "</div>";	
							$(".main-content-train-detail").append(strHead);
						}else if(trainNo.startsWith("G")){
							var strHead = 
								  "<div class='modify-station-div'>"
								+ "	   <span>顺序</span><span>车次</span><span>站点</span><span>开车时间</span><span>到达时间</span><span>商务座价格</span><span>商务座票数</span><span>一等座价格</span><span>一等座票数</span><span>二等座价格</span><span>二等座票数</span>"
								+ "</div>";	
							$(".main-content-train-detail").append(strHead);
						}
						
						for(var i=0; i < trainList.length; i++){
							if(trainList[i]['trainNo'] == trainNo){
								var startTime = trainList[i]['startTime'] == null ? "":trainList[i]['startTime'] ;
								var deptTime  = trainList[i]['deptTime']  == null ? "":trainList[i]['deptTime'];
								if(trainList[i]['trainNo'].startsWith("D")){
									var strHtml = 
										  "<div class='modify-station-div'>"
										+ "<span class='train-stat-order'>" + (trainList[i]['statOrder'] + 1) + "</span>"
										+ "<span>" + trainNo + "</span>"
										+ "<span><input type='text' value='" + trainList[i]['stationName']       + "'/></span>"
										+ "<span><input type='text' value='" + startTime         				 + "'/></span>"
										+ "<span><input type='text' value='" + deptTime          				 + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['priceFirstClass']   + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['ticketFirstClass']  + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['priceSecondClass']  + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['ticketSecondClass'] + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['priceStand']        + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['ticketStand']       + "'/></span>"
										+ "<span class='cancel-train-link' style='margin-left:5px;'><i class='fa fa-times-circle' aria-hidden='true' style='color:#FEAE1B;'></i></span>"
										+ "</div>";
									$(".main-content-train-detail").append(strHtml);
								}else if(trainList[i]['trainNo'].startsWith("G")){
									var strHtml = 
										  "<div class='modify-station-div'>"
										+ "<span class='train-stat-order'>" + (trainList[i]['statOrder'] + 1) + "</span>"
										+ "<span>" + trainNo + "</span>"
										+ "<span><input type='text' value='" + trainList[i]['stationName']       + "'/></span>"
										+ "<span><input type='text' value='" + startTime         				 + "'/></span>"
										+ "<span><input type='text' value='" + deptTime          				 + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['priceBusiness']     + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['ticketBusiness']    + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['priceFirstClass']   + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['ticketFirstClass']  + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['priceSecondClass']  + "'/></span>"
										+ "<span><input type='text' value='" + trainList[i]['ticketSecondClass'] + "'/></span>"
										+ "<span class='cancel-train-link' style='margin-left:5px;'><i class='fa fa-times-circle' aria-hidden='true' style='color:#FEAE1B;'></i></span>"
										+ "</div>";
									$(".main-content-train-detail").append(strHtml);
								}
							}
						}
						var html = $(".main-content-train-detail").html();
						addCancelTrainLinkEvent();
						
						$("#cancel_modify_train_btn").click(function(){
							layer.close(index);
						});
						
						$("#restore_modify_train_btn").click(function(){
							$(".main-content-train-detail").empty().append(html);
							addCancelTrainLinkEvent();
						});
						
						$("#save_modify_train_btn").click(function(){
							var params = [];
							$(".modify-station-div").each(function(index, ele){
								console.log("index: " + index);
								if(index == 0){
									return true;
								}
								var map = {};
								var stationName	= $(this).find("input:eq(0)").val();
								console.log("stationName: " + stationName);
								if(stationName == ""){
									return true;
								}
								map['trainNo']						= $(this).find("span:eq(1)").text();
								map['stationName']     				= stationName;
								map['startTime']   					= $(this).find("input:eq(1)").val();
								map['deptTime']  					= $(this).find("input:eq(2)").val();
								map['statOrder']       				= $(this).find(".train-stat-order").text();
								map['priceFirstClass']       		= $(this).find("input:eq(3)").val();
								map['ticketFirstClass']				= $(this).find("input:eq(4)").val()
								map['priceSecondClass']      		= $(this).find("input:eq(5)").val();
								map['ticketSecondClass']      		= $(this).find("input:eq(6)").val();
								map['priceStand']       			= $(this).find("input:eq(7)").val();
								map['ticketStand']       			= $(this).find("input:eq(8)").val();
								params.push(map);
							});
							
							$.get(APP_PATH + "/admin/train/modify/train", {json: JSON.stringify(params)}, function(data){
								layer.close(index);
								if(data == "success"){
									layer.open({icon:1, title:"修改车次", content:"修改已生效, 已经生成的车票不会受到影响"});
								} else{
									layer.open({icon:2, title:"修改车次", content:"修改失败"});
								}
							});
							
						});
						
						$("#save_add_train_btn").click(function(){
							var length = $(".train-stat-order").length;
							$(".main-content-train-detail").append(
								  "<div class='modify-station-div'>"
								+ "<span class='train-stat-order'>" + (length + 1) + "</span>"
								+ "<span>" + trainNo + "</span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span><input type='text' value=''/></span>"
								+ "<span class='cancel-train-link' style='margin-left:5px;'><i class='fa fa-times-circle' aria-hidden='true' style='color:#FEAE1B;'></i></span>"
								+ "</div>"
							);
							addCancelTrainLinkEvent();
						});
					}
				})
			}
		});
		
		$("#export_template_btn").click(function(){
			window.open(APP_PATH + "/admin/train/download/train/template", '_blank');
		});
		
		$("#import_train_btn").click(function(){
			$("#upload_file_input").trigger("click");
		});
		
		$("#upload_file_input").change(function(){
			$("#upload_file_form").ajaxSubmit({
				success: function(data){
					if(data == "success"){
						getAllTrain();
						layer.open({icon:1, title:"文件上传", content:"上传成功"});
					}else{
						layer.open({icon:2, title:"文件上传", content:"上传失败"});
					}
				}
			});
		});
		
		$("#test_btn").click(function(){
			$.get(APP_PATH + "/admin/train/test", function(data){
				console.log(data);
			});
		});
		
	};
	
	var initModule = function($container){
		initViews();
		initButtons();
	};
	
	return {
		initModule: initModule
	};
}());