function updateToInput(){
  let typeSelect = document.getElementsByName('type')[0];
  if(typeSelect.value == 'EXCHANGE'){
    let to = localStorage.getItem("to");
    let mainDiv = document.getElementsByClassName('container')[0];
    let btn = document.getElementsByClassName('btn')[0];

    let toDiv = document.createElement('div');
    toDiv.setAttribute('id','toAcc');
    toDiv.setAttribute('class','form-group');

    let toLabel = document.createElement('label');
    toLabel.setAttribute('for','to');
    toLabel.innerHTML = 'To';

    let br1 = document.createElement('br');

    let toInput = document.createElement('input');
    toInput.setAttribute('type','text');
    toInput.setAttribute('class','form-control');
    toInput.setAttribute('placeholder','To Who');
    toInput.setAttribute('name','to');
    toInput.setAttribute('value', to);

    let br2 = document.createElement('br');

    toDiv.appendChild(toLabel);
    toDiv.appendChild(br1);
    toDiv.appendChild(toInput);
    toDiv.appendChild(br2)

    mainDiv.insertBefore(toDiv,btn);
  }else{
    let probToDiv = document.getElementById('toAcc');
    if(probToDiv!=null){
      probToDiv.remove();
    }
  }
}
async function updateTransaction(){
	let transactionId = localStorage.getItem('transactionId');
	const url = `http://localhost:8079/updateTransaction/${transactionId}`;
	let type = document.getElementsByName("type")[0].value;
 	let amount = document.getElementsByName("amount")[0].value;
  let from = document.getElementsByName("accs")[0].value;
  let to = document.getElementsByName("to")[0].value;

 	const transaction = {
 		type:type,
 		amount:amount,
    from:from,
    to:to
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
  		window.location.replace('../Home/home.html');
  	}else if(res.status==400){
      let alertMsg = document.getElementById('myAlert');
      res.json().then(text => alertMsg.innerHTML=text.message);
      alertMsg.style.display = 'block';
    }
  });
}