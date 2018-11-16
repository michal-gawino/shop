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
     console.log(name + id);
     $('#categoryEditForm').attr('action', '/category/'+id);
     $(".modal-body #name").val(name);
});
