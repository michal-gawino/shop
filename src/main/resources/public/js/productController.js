$(document).on("click", "#addToCartButton", function () {
	var id = $(this).data('id');
	$.ajax({
        type: "POST",
        url: "/cart/"+id,
        success: function(){
			var x = document.getElementById("snackbar");
            x.className = "show";
            setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
        }
    });
});