async function updateUser(){
 const url = 'http://localhost:8079/update';
 var firstName = document.getElementsByName("firstName")[0].value;
 var lastName = document.getElementsByName("lastName")[0].value;
 var email = document.getElementsByName("email")[0].value;
 //var password = document.getElementsByName("password")[0].value;
 var birthDate = document.getElementsByName("birthDate")[0].value;
 var mobile = document.getElementsByName("mobile")[0].value;
 var country = document.getElementsByName("country")[0].value;
 var city = document.getElementsByName("city")[0].value;
 var street = document.getElementsByName("street")[0].value;
 var houseNumber = document.getElementsByName("houseNumber")[0].value;
 var postalCode = document.getElementsByName("postalCode")[0].value;
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
   mobile: mobile
   //password: password
 };
 let token = localStorage.getItem('token');
 const response = await fetch(url, {
    method: 'PUT', 
    headers: {
      'Authorization' : token,
      'Access-Control-Allow-Origin':'*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user) // body data type must match "Content-Type" header
  }).then(res=>{
    if(res.status == 200){
      localStorage.setItem("firstName", firstName);
      localStorage.setItem("lastName", lastName);
      localStorage.setItem("email", email);
      localStorage.setItem("birthDate", birthDate);
      localStorage.setItem("mobile", mobile);
      localStorage.setItem("country", country);
      localStorage.setItem("city", city);
      localStorage.setItem("street", street);
      localStorage.setItem("houseNumber", houseNumber);
      localStorage.setItem("postalCode", postalCode);
      // let newToken = btoa(`${email}:${password}`);
      // newToken = `Basic ${newToken}`;
      // localStorage.setItem('token',newToken);
      window.location.replace("../Home/home.html");
    }
   }
  );
}