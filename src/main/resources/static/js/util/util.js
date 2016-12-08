/**
 * util.js : 本地工具JS文件,依赖于jquery
 * @author : fengyunjie
 * @date   : 2016-04-15
 * @include:
 * 			 addDate      - 日期增加
 * 	         formatDate   - 日期格式化
 *			 drawTable    - 绘制表格
 * 			 showInputTip - 给Input增加下拉提示列表
 */
var util = (function(){
	/**
	 * 日期格式化
	 * @param  {date}   date   原始日期
	 * @param  {number} day    增加天数
	 * @return {string} 格式化之后的字符串
	 */
	var addDay = function(date , day){
		var d = new Date(date.getTime() + 1000*60*60*24*day);
		return formatDate(d, "MM/dd");
	};
	
	/**
	 * 日期格式化
	 * @param  {date}   date   需要格式化的日期
	 * @param  {string} format 格式化字符串
	 * @return {string} 格式化之后的日期字符串
	 */
	var formatDate = function(date, format) { 
		if(typeof date == "undefined" || date == null){
			return "";
		}
		var o = { 
		    "M+" : date.getMonth()+1, //month 
		    "d+" : date.getDate(),    //day 
		    "h+" : date.getHours(),   //hour 
		    "m+" : date.getMinutes(), //minute 
		    "s+" : date.getSeconds(), //second 
		    "q+" : Math.floor((date.getMonth()+3)/3),  //quarter 
		    "S" : date.getMilliseconds() //millisecond 
		} 
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
			(date.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		for(var k in o)if(new RegExp("("+ k +")").test(format)) 
			format = format.replace(RegExp.$1, 
				RegExp.$1.length==1 ? o[k] : 
				("00"+ o[k]).substr((""+ o[k]).length)); 
		return format; 
	};
	
	/**
	 * 绘制表格
	 * @param options
	 *        {string}  id        需要渲染到的表格id
	 * 		  {array}   columns   表格列名
	 * 		  {array}   datArray  表格数据,二维数组
	 *        {boolean} paging    是否开启分页
	 *  	  {object}  pagingOpt 分页参数
	 */
	var drawTable = function(options){
		var id 			= options.id;
		var columns 	= options.columns;
		var datArray 	= options.datArray;
		var paging 		= options.paging;
		var pagingOpt 	= options.pagingOpt;
		var rowHeight   = options.rowHeight ? options.rowHeight: '30px';
		var colWidth    = options.colWidth  ? options.colWidth: null;
		
		$("#" + id).empty();
		var str = "";
		
		var widthPercent = 100 / columns.length;
		
		var width = colWidth ? columns.length * parseInt(colWidth.substr(0, colWidth.length-2)) : '100%';
		
		if(columns.length < 2){
			str += "<thead><tr><th style='width:" + width + "'>没有数据</th></tr></thead><tbody>";
		}else if(datArray == null || datArray.length == 0){
			str += "<thead><tr>";
			for(var i = 0;i < columns.length; i++){
				str += "<th style='width: " + (colWidth ? colWidth : widthPercent + "%") + "' >" + columns[i] + "</th>";
			}
			str += "</tr></thead>";//<tbody><tr><td colspan='" + columns.length + "' width='100%'>数据为空</td></tr><tbody>";
		}else{
			str += "<thead><tr>";
			for(var i = 0; i < columns.length; i++){
				str += "<th style='width: " + (colWidth ? colWidth : widthPercent + "%") + "%' >" + columns[i] + "</th>";
			}
			str += "</tr></thead><tbody>";
			var iCount = 0;
			for(var i = 0; i < datArray.length; i++){
				if(datArray[i][0] == null){
					continue;
				}
				if(iCount % 2 == 0){
					str += "<tr class='odd'>";
				}else{
					str += "<tr class='even'>";
				}
				for(var j = 0; j < datArray[i].length; j++){
					if(datArray[i][j] == null){
						str += "<td></td>";
					}else{
						str += "<td>" + datArray[i][j] + "</td>";
					}
				}
				str += "</tr>";
				iCount++;
			}
			str += "</tbody>";
		}
		$("#" + id).append(str);
		$("#" + id).css("width", width).css("border-collapse", "collapse").css("border-bottom", "1px solid #ffffff").
			css("background-color","#ffffff").css("text-align", "center").css("padding", "6px").css("font-size", "13px").
			css("font-family", "consolas");
		$("#" + id + " tr").css("height", rowHeight);
		$("#" + id + " thead").css("background-color", "#0F82D6").css("color", "#ffffff");
		$("#" + id + " thead a").css("color", "#ffffff");
		$("#" + id + " thead th").css("font-weight", "normal");
		$("#" + id + " thead th").css("border-bottom", "1px solid #0F82D6");
		$("#" + id + " tbody tr.even").css("background-color", "#f1f1f1");
		
		//分页支持
		if(paging == true){
			appendPagingHtml(id, pagingOpt);
			
			var pageRequest = {"draw": 1, "start": 1, "length": parseInt($("#" + id + "_div .paging-count-setting-select").val())};
			pagingOpt["length"] = pageRequest["length"];

			if(pagingOpt != null){
				pageRequest["draw"] = pagingOpt["draw"];
				pageRequest["total"] = pagingOpt["recordsTotal"];
				$("#" + id + "_paging_total").text(pagingOpt["recordsTotal"]);
				pageRequest["start"] = pageRequest["length"] * (pageRequest["draw"] - 1) + 1;
				if(pageRequest["length"] * pageRequest["draw"] > pagingOpt["recordsTotal"]){
					pageRequest["end"] = pagingOpt["recordsTotal"];
				}else{
					pageRequest["end"] = pageRequest["length"] * pageRequest["draw"];
				}
				$("#" + id + "_paging_range").text(pageRequest["start"] + "-" + pageRequest["end"]);
				$("#" + id + "_div .text-current-page").text(pageRequest["draw"] + "/" + Math.ceil(pagingOpt["recordsTotal"]/pageRequest["length"]));
			}
			
			//改变每页大小
			$("#" + id + "_div .paging-count-setting-select").change(function(){
				pageRequest["length"] = $(this).val();
				pageRequest["draw"] = 1;
				pageRequest["start"] = 1;
				pageRequest["end"] = pageRequest["draw"] * pageRequest["length"];
				requestPaging(pageRequest, pagingOpt, id, columns);
			});
			
			//首页
			$("#" + id + "_div .btn-paging-first").click(function(){
				if(pageRequest["draw"] == 1){
					alert("已经是第一页了");
					return;
				}
				pageRequest["draw"] = 1;
				pageRequest["start"] = 1;
				pageRequest["end"] = pageRequest["draw"] * pageRequest["length"];
				requestPaging(pageRequest, pagingOpt, id, columns);
			});
			
			//上页
			$("#" + id + "_div .btn-paging-prev").click(function(){
				if(pageRequest["draw"] == 1){
					alert("已经是第一页了");
					return;
				}
				pageRequest["draw"] -= 1;
				pageRequest["start"] -= pageRequest["length"];
				pageRequest["end"] = pageRequest["draw"] * pageRequest["length"];
				requestPaging(pageRequest, pagingOpt, id, columns);
			});
			
			//下页
			$("#" + id + "_div .btn-paging-next").click(function(){
				if(pagingOpt != null && pageRequest["start"] + pageRequest["length"] <= pagingOpt["recordsTotal"]){
					pageRequest["draw"] += 1;
					pageRequest["start"] += pageRequest["length"];
					pageRequest["end"] += pageRequest["length"];
					if(pageRequest["end"] > pagingOpt["recordsTotal"]){
						pageRequest["end"]= pagingOpt["recordsTotal"];
					}
					requestPaging(pageRequest, pagingOpt, id, columns);
				}else{
					alert("已经是最后一页了");
				}
				
			});
			
			//末页
			$("#" + id + "_div .btn-paging-last").click(function(){
				if(pagingOpt != null){
					var pageCount = Math.ceil(pagingOpt["recordsTotal"]/pageRequest["length"]);
					if(pageCount == pageRequest["draw"]){
						alert("已经是最后一页了");
						return;
					}
					pageRequest["draw"] = pageCount;
					pageRequest["start"] = pageRequest["length"] * (pageRequest["draw"] - 1) + 1;
					pageRequest["end"] = pageRequest["length"] * pageRequest["draw"];
					if(pageRequest["end"] > pagingOpt["recordsTotal"]){
						pageRequest["end"] = pagingOpt["recordsTotal"];
					}
					requestPaging(pageRequest, pagingOpt, id, columns);
				}
			});
			
			//跳转
			$("#" + id + "_div .btn-paging-jump").click(function(){
				if(pagingOpt != null){
					var pageCount = Math.ceil(pagingOpt["recordsTotal"]/pageRequest["length"]);
					var pageJmp = parseInt($("#" + id + "_div .text-paging-jump").val());
					console.log("text val: " + pageJmp);
					if(!isNaN(pageJmp) && pageJmp >= 1 && pageJmp <= pageCount){
						pageRequest["draw"] = pageJmp;
						pageRequest["start"] = pageRequest["length"] * (pageRequest["draw"] - 1) + 1;
						pageRequest["end"] = pageRequest["length"] * pageRequest["draw"];
						if(pageRequest["end"] > pagingOpt["recordsTotal"]){
							pageRequest["end"] = pagingOpt["recordsTotal"];
						}
						requestPaging(pageRequest, pagingOpt, id, columns);
					}else{
						alert("请输入一个在1到" + pageCount + "之间的整数");
					}
				}
			});
		}
	};
	
	var appendPagingHtml = function(id, pagingOpt){
		var pagingHtmlId = id + "_div";
		if(!(typeof($("#" + pagingHtmlId).html()) == "undefined")){
			$("#" + pagingHtmlId).remove();
		}
		
		var pagingHtml = 
			"<div id='" + pagingHtmlId + "'>" +
			"	<span class='paging-count-setting'>每页显示" +
			"	<select class='paging-count-setting-select'>" +
			"		<option val='10'>10</option>" +
			"		<option val='25'>25</option>" +
			"		<option val='50'>50</option>" +
			"		<option val='100'>100</option>" +
			"	</select>" +
			"	条记录</span>" +
			"	<span style='float:right;'>" +
			"		当前显示: <text id='" + id + "_paging_range'>1-10</text>" +
			"		共: <text id='" + id + "_paging_total'>26</text>" +
			"		<a class='button button-small button-square btn-paging-first' style='width:50px;'>首页</a>" +
			"		<a class='button button-small button-square btn-paging-prev' style='width:50px;'>上页</a>" +
			"		<text class='text-current-page'></text>" +
			"		<a class='button button-small button-square btn-paging-next' style='width:50px;'>下页</a>" +
			"		<a class='button button-small button-square btn-paging-last' style='width:50px;'>末页</a>" +
			"		<input type='text' class='text-paging-jump'/>" +
			"		<a class='button button-small button-square btn-paging-jump' style='width:50px;'>跳转</a>" +
			"   </span>" +
			"</div><br>";
		
		$(pagingHtml).insertAfter($("#" + id));
		$("#" + id + "_paging_range").css('margin-right', '15px');
		$("#" + id + "_paging_total").css('margin-right', '15px');
		$("#" + id + "_div .paging-count-setting-select").css("height", "28px").css("line-height", "28px");
		$("#" + id + "_div .text-paging-jump").css("width", "50px").css("height", "22px").css("line-height", "22px");
		$(".paging-count-setting").css("font-size", "14px");
		$("#" + id + "_div .paging-count-setting-select").val(pagingOpt['length']);
	}
	
	var requestPaging = function(pageRequest, pagingOpt, id, columns){	
		$("#" + id + "_paging_range").text(pageRequest["start"] + "-" + pageRequest["end"]);
		
		var params = {
			"start": 		pageRequest["start"], 
			"length": 		pageRequest["length"], 
			"draw": 		pageRequest["draw"], 
		};
		
		for(var key in pagingOpt){
			if(!params.hasOwnProperty(key)){
				params[key] = pagingOpt[key];
			}
		}
		
		var layerIndex = layer.msg('数据加载中..', {icon: 16, shade: [0.4, '#B3B3B3'], time: 300000});
		util.ajaxRequest.requestData(pagingOpt["url"], params, function(recvData){
			layer.close(layerIndex);
			pagingOpt['draw'] = recvData['draw'];
			pagingOpt['length'] = pageRequest['length'];
			drawTable(id, columns, recvData.data, true, pagingOpt);
		});
	}
	
	/**
	 * 给Input增加下拉提示列表
	 * @param {string} id    input的id
	 * @param {array}  list  需要提示的列表数组
	 */
	var showInputTip = function(id, list){
		var prev = "";
		var $autocomplete = $("<ul class='autocomplete'></ul>").hide().insertAfter("#" + id);
		
		$("#" + id).keyup(function(){
			$autocomplete.empty();
			if(list.length == 0){
				$("<li style='background:#fff;color:#bbb;'></li>").text("没有匹配数据").appendTo($autocomplete);
			}
			if($("#" + id).val() == prev){
				return;
			}
			prev = $("#"+id).val();
			
			$(list).each(function(index, ele){
				$("<li style='background:#fff;'></li>").text(ele).appendTo($autocomplete).mouseover(function(){
					$(this).css('font-weight', 'bold').css("background", "#f0f0f0");
				}).mouseout(function(){
					$(this).css('font-weight', 'normal').css("background", "#fff");
				}).click(function(){
					$("#" + id).val(ele);
					$autocomplete.hide();
				});
			});
			
			$autocomplete.css("margin", 0).css("padding", 0).css("border", "1px solid #CCC").css("border-top", "none").css("position", "absolute");
			$autocomplete.find("li").css("height", $("#" + id).height() + 6).css("line-height", $("#" + id).height() + 6 + "px");
			$autocomplete.css("width", $("#" + id).width() + 2);
			$autocomplete.show();
			$("body").click(function(){
				$autocomplete.hide();
			});
		});
	}
	
	var setCookie = function(name,value){
		var days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + days*24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	}
	
	var getCookie = function(name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	}
	
	var delCookie = function(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval=getCookie(name);
		if(cval!=null)
			document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	}
	
	return {
		addDay: addDay,
		formatDate: formatDate,
		drawTable: drawTable,
		showInputTip: showInputTip,
		setCookie: setCookie,
		getCookie: getCookie,
		delCookie: delCookie
	}
}());