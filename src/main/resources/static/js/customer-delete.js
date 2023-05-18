addEvents();
function addEvents() {
    const myForm = document.getElementById("myForm");
    myForm.addEventListener("submit", e => {
        e.preventDefault();
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
async function deleteCustomer(customerID){
    const token = getToken();
    const response = await fetch('/api/admin/customers/' + customerID, {
        method: "DELETE",
        headers: {
            'Authorization': token
        },
    });
    return response;
}
function getToken(){
    return document.cookie.split('=')[1];
}