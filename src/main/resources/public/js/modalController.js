$(document).on("click", "#userUpdateModal", function () {
     var name = $(this).data('name');
     var surname = $(this).data('surname');
     var login = $(this).data('login');
     var id = $(this).data('id');
     $('#editForm').attr('action', '/user/'+id);
     $(".modal-body #name").val(name);
     $(".modal-body #surname").val(surname);
     $(".modal-body #login").val(login);
});

$(document).on("click", "#categoryUpdateModal", function () {
     var name = $(this).data('name');
     var id = $(this).data('id');
     $('#categoryEditForm').attr('action', '/category/'+id);
     $(".modal-body #name").val(name);
});

$(document).on("click", "#productUpdateModal", function () {
     var id = $(this).data('id');
     var name = $(this).data('name');
     var brand = $(this).data('brand');
     var price = $(this).data('price');
     var category = $(this).data('category');
     console.log(category);
     $('#productEditForm').attr('action', '/product/'+id);
     $(".modal-body #name").val(name);
     $(".modal-body #brand").val(brand);
     $(".modal-body #price").val(price);
     $("#category select").val(category);


});

