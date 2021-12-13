async function login(){
	const url = 'http://localhost:8079/login';
	let email = document.getElementsByName("email")[0].value;
	let password = document.getElementsByName("password")[0].value;
	let token = btoa(`${email}:${password}`);
	token = `Basic ${token}`;

	//console.log(token);
	const response = await fetch(url, {
    method: 'GET', 
    headers: {
      'Authorization': token,
   	  'Access-Control-Allow-Origin':'*'
    } // body data type must match "Content-Type" header
  }).then((res)=>{
  	if(res.status==200){
    	localStorage.setItem("token", token);
    	return res.json();
    }else if(res.status==401){
      let alertMsg = document.getElementById('myAlert');
      alertMsg.innerHTML='Wrong email or password';
      alertMsg.style.display = 'block';
    }
  }).then(data=>{
    if(data.authorities.includes('ADMIN')){
      localStorage.setItem('Role','ADMIN');
    }else{
      localStorage.setItem('Role','USER');
    }
    localStorage.setItem("firstName", data.firstName);
    localStorage.setItem("lastName", data.lastName);
    localStorage.setItem("email", data.email);
    localStorage.setItem("birthDate", data.birthDate.split('T')[0]);
    localStorage.setItem("mobile", data.mobile);
    localStorage.setItem("country", data.addressUserModel.country);
    localStorage.setItem("city", data.addressUserModel.city);
    localStorage.setItem("street", data.addressUserModel.street);
    localStorage.setItem("houseNumber", data.addressUserModel.houseNumber);
    localStorage.setItem("postalCode", data.addressUserModel.postalCode);
  	//window.location.replace('../Home/home.html');
  });
  const accResponse = await fetch('http://localhost:8079/user/account', {
    method: 'GET', 
    headers: {
      'Authorization': token,
      'Access-Control-Allow-Origin':'*'
    } // body data type must match "Content-Type" header
  }).then((res)=>{
    if(res.status==200){
    return res.json();
  }
  }).then(data=>{
    let accounts = "";
    for(let i = 0;i<data.length;i++){
          accounts+=data[i].number;
          if(i!=data.length-1){
            accounts+=",";
          }
    }
    localStorage.setItem('accounts',accounts);
    window.location.replace('../Home/home.html');
  });
  
}

