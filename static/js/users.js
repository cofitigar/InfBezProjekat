$(document).ready(function() {
	var token = localStorage.getItem("token");
	console.log(token)
	if (token == "undefined" || token == null || token == "null") {
		window.location.href = "login.html";
	} else {
		currentUser();

	}
});


function fillTable(){
	var token = localStorage.getItem("token");
	tableHeader();
	$.ajax({
		url:'https://localhost:8443/api/users/active',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			if(response.length == 0){
				var table =  $('#myTable');
				table.empty();
				return;
			}
			for(var i=0; i<response.length; i++) {
				var table =  $('#myTable');
				user = response[i];
				console.log(user.email);
				table.append('<tr>'+
								'<td>'+user.email+'</td>'+

							'</tr>');
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			alert(textStatus+" "+jqXHR.status)
		}
	});
}


function fillInactiveTable(){
	var token = localStorage.getItem("token");
	tableInactiveHeader();
	$.ajax({
		url:'https://localhost:8443/api/users/inactive',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			if(response.length == 0){
				var table =  $('#myInactiveTable');
				table.empty();
				return;
			}
			for(var i=0; i<response.length; i++) {
				var table =  $('#myInactiveTable');
				user = response[i];
				console.log(user.email);
				table.append('<tr>'+
								'<td>'+user.email+'</td>'+
								'<td><button onclick="activateUser('+user.id+')" >Activate</button></td>'+
							'</tr>');
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			alert(textStatus+" "+jqXHR.status)
		}
	});
}

function tableHeader(){
	var table =  $('#myTable');
	table.empty();
	table.append('<tr>'+
				'<th colspan="2" >Active Users</th>'+
				
				'</tr>'+
				'<tr>'+
					'<th>Email</th>'+
				'</tr>');
}


function tableInactiveHeader(){
	var table =  $('#myInactiveTable');
	table.empty();
	table.append('<tr>'+
				'<th colspan="2" >Inactive Users</th>'+
				
				'</tr>'+

				'<tr>'+
					'<th>Email</th>'+
					'<th>Acivate</th>'+
				'</tr>');
}

function activateUser(id){
	var token = localStorage.getItem("token");
	console.log(id)
	$.ajax({
		type: 'PUT',
		headers:{"Authorization" :"Bearer " + token},
        url: 'https://localhost:8443/api/users/activate/'+id,
        dataType: 'json',
        crossDomain: true,
		cache: false,
		processData: false,
		success:function(response){
			alert("Korisnik aktiviran.");
			fillTable();
			fillInactiveTable();
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			alert(textStatus+" "+jqXHR.status)
		}
	});
}


function currentUser(){
	var token = localStorage.getItem("token");
	$.ajax({
		type: 'PUT',
		headers:{"Authorization" :"Bearer " + token},
        url: 'https://localhost:8443/api/users/whoami',
        dataType: 'json',
        crossDomain: true,
		cache: false,
		processData: false,
		success:function(response){
			console.log(response);
			for ( var i in response.authorities) {
				console.log(i);
				console.log(response.authorities[i].name);
				if(response.authorities[i].name=="ADMIN"){
					fillInactiveTable();
				}
			}
			fillTable();
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			alert(textStatus+" "+jqXHR.status)
		}
	});
}

function logout(){
	localStorage.removeItem("token");
	window.location.replace("https://localhost:8443/login.html");
}

