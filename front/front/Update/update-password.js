async function updatePassword(){
	let email = localStorage.getItem('email');
	const url = `http://localhost:8079/user/password`;
	let oldPass = document.getElementsByName("oldPass")[0].value;
 	let newPass = document.getElementsByName("newPass")[0].value;
  	let confirmPass = document.getElementsByName("confirmPass")[0].value;

 	const transaction = {
 		oldPassword:oldPass,
 		newPassword:newPass,
    	newPasswordConfirmation:confirmPass
 	};
 	let token = localStorage.getItem('token');
 	const response = await fetch(url, {
    method: 'PUT', 
    headers: {
      'Authorization' : token,
      'Access-Control-Allow-Origin':'*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(transaction) // body data type must match "Content-Type" header
  }).then(res=>{
  	if(res.status==200){
	  	let newToken = btoa(`${email}:${newPass}`);
	    newToken = `Basic ${newToken}`;
	    localStorage.setItem('token',newToken);
  		window.location.replace('../Home/home.html');
  	}else if(res.status==400){
      let alertMsg = document.getElementById('myAlert');
      res.json().then(text => alertMsg.innerHTML=text.message);
      alertMsg.style.display = 'block';
    }
  });
}