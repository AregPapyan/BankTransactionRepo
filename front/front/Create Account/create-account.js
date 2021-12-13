async function createAccount(){
 const url = 'http://localhost:8079/account';
 let number = "";
 for(let i = 0;i<16;i++){
  number+=Math.floor(Math.random() * 10);
 }
 let currency = document.getElementsByName('currencies')[0].value;
 const account = {
    number:number,
    currency:currency
 }
 let token = localStorage.getItem('token');
 const response = await fetch(url, {
    method: 'POST', 
    headers: {
      'Authorization' : token,
      'Access-Control-Allow-Origin':'*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(account) // body data type must match "Content-Type" header
  }).then(res=>{
    if(res.status == 200){
      if(localStorage.getItem('accounts')==null){
        localStorage.setItem('accounts',number);
      }
      else{
        let accounts = localStorage.getItem('accounts');
        accounts+=",";
        accounts+=number;
        localStorage.setItem('accounts',accounts);
      }
      window.location.replace("../Home/home.html");
    }
   }
  );
}