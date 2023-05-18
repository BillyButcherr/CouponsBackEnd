addEvents();
loadCustomerInfo(1);
function addEvents() {
    const myForm = document.getElementById("myForm");
    myForm.addEventListener("submit", e => {
        e.preventDefault();
        updateCustomer();
    });
}
function loadCustomerInfo(customerID) {
    fetchCustomer(customerID).then((customerData) => {
        console.log(customerData);
        document.getElementById("first-name").value = customerData.firstName;
        document.getElementById("last-name").value = customerData.lastName;
        document.getElementById("email").value = customerData.email;
    });
}

function updateCustomer() {
    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    let response = putCustomer(1, firstName, lastName, email, password);
    console.log(response);
    // if(response.ok)
    // console.log(response);
    // else
    // alert("failed");
}
async function putCustomer(id, firstName, lastName, email, password) {
    const payload = JSON.stringify({ id, firstName, lastName, email, password });
    const token = getToken();
    const response = await fetch('/api/admin/customers', {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: payload
    });
    return response;
}
async function fetchCustomer(id) {
    const response = await fetch('/api/admin/customers/' + id);
    if (response.ok)
        return await response.json();
    else
        alert(await response.text());
}
function getToken() {
    return sessionStorage.getItem('token');
}