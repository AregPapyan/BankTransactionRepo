async function register(){
	const url = 'http://localhost:8079/user';

	let firstName = document.getElementsByName("firstName")[0].value;
	let lastName = document.getElementsByName("lastName")[0].value;
	let email = document.getElementsByName("email")[0].value;
	let password = document.getElementsByName("password")[0].value;
	let birthDate = document.getElementsByName("birthDate")[0].value;
	let mobile = document.getElementsByName("mobile")[0].value;
	let country = document.getElementsByName("country")[0].value;
	let city = document.getElementsByName("city")[0].value;
	let street = document.getElementsByName("street")[0].value;
	let houseNumber = document.getElementsByName("houseNumber")[0].value;
	let postalCode = document.getElementsByName("postalCode")[0].value;

	const user = {
	  addressRequest: {
	    city: city,
	    country: country,
	    houseNumber: houseNumber,
	    postalCode: postalCode,
	    street: street
	  },
	  birthDate: birthDate,
	  email: email,
	  firstName: firstName,
	  lastName: lastName,
	  mobile: mobile,
	  password: password
	};
	const response = await fetch(url, {
    method: 'POST', 
    headers: {
      'Access-Control-Allow-Origin':'*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user) // body data type must match "Content-Type" header
  }).then(res=>{
  		if(res.status == 200){
  			let token = btoa(`${email}:${password}`);
				token = `Basic ${token}`;
				localStorage.setItem('token', token);
				return res.json();
  		}else if(res.status==400){
	      let alertMsg = document.getElementById('myAlert');
	      res.json().then(text => alertMsg.innerHTML=text.message);
	      alertMsg.style.display = 'block';
	    }
  	}
  ).then(data=>{
		  	localStorage.setItem('Role','USER');
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
  			window.location.replace("../Login/login.html");
  });
	//console.log(response.json());

}