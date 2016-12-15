$(function(){
	var validator = $("#login_form").validate({
		errorPlacement: function(error, ele){
			$(ele).closest("form").find("label[for='" +ele.attr("id") + "']").append(error);
		},
		errorElement: "span"
	});
});