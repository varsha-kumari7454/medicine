
$().ready(function() {
	$("#userForm").validate({
		rules: {
			name: "required",
			email: "required",
			name: {
				required: true,
				minlength: 3
			},
			email: {
				required: true,
			}
		},
		messages: {
			name: {
				required: "Please enter your name",
				minlength: "Your name must consist of at least 3 characters"
			},
			email: {
				required: "Please enter your email"
			}
		},
		submitHandler: function(form) {
			$.confirm({
				title: 'Confirm!',
			    confirmButtonClass: 'btn-info',
			    cancelButtonClass: 'btn-danger',
			    confirm: function(){
			       form.submit();
			    },
			    cancel: function(){
			    }
			});
		}
	});
});

$().ready(function(){
	$('#deleteUser').validate({
		submitHandler: function(form){
			$.confirm({
				title: 'Confirm!',
			    confirmButtonClass: 'btn-info',
			    cancelButtonClass: 'btn-danger',
			    confirm: function(){
			       form.submit();
			    },
			    cancel: function(){}
			});
		}
	});
});