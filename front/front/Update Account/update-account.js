async function updateAccount(){
	let accNumber = localStorage.getItem('accNumber');
	const url = `http://localhost:8079/update-account/${accNumber}`;
	let number = document.getElementsByName("number")[0].value;
 	let currencies = document.getElementsByName("currencies")[0].value;

 	const account = {
 		number:number,
 		currency:currencies
 	};
 	let token = localStorage.getItem('token');
 	const response = await fetch(url, {
    method: 'PUT', 
    headers: {
      'Authorization' : token,
      'Access-Control-Allow-Origin':'*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(account) // body data type must match "Content-Type" header
  }).then(res=>{
  	if(res.status==200){
  		window.location.replace('../Home/home.html');
  	}
  })
}