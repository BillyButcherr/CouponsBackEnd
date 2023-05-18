addEvents();
function addEvents() {
    const myForm = document.getElementById("myForm");
    myForm.addEventListener("submit", e => {
        e.preventDefault();
        createCustomer();
    });
}

function createCustomer(){
    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    let response = postCustomer(firstName, lastName, email, password);
    console.log(response);
    // if(response.ok)
    // console.log(response);
    // else
    // alert("failed");
}
async function postCustomer(firstName, lastName, email, password){
    const payload = JSON.stringify({firstName, lastName, email, password});
    const token = getToken();
    const response = await fetch('/api/admin/customers', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: payload
    });
    return response;
}
function getToken(){
    return sessionStorage.getItem('token');
}