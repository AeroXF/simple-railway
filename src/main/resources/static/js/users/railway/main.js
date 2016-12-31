/**
 * 
 */
$(function(){
	var hour = new Date().getHours();
	document.getElementById("time_font").innerText = hour > 11 ? "下午": "上午";
});
	