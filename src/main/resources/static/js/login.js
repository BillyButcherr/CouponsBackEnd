addEvents();
let token = "";
function addEvents() {
    const myForm = document.getElementById("myForm");
    myForm.addEventListener("submit", e => {
        e.preventDefault();
        postLogin();
        // promise.then(json => {
        //     console.log(json.data);
        //     // document.cookie = "token=" + json.data;
        //     window.location.href = "/admin";
        // });
    });
}


async function postLogin() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // const email = "admin@admin.com"
    // const password = "admin";

    const payload = JSON.stringify({ email, password });

    let response = await attempLogin("admin", payload);
    if(response.ok){
        await loginSuccessfull(response);
        window.location.href = "/admin";
        return;
    }
    response = await attempLogin("company", payload);
    if(response.ok){
        await loginSuccessfull(response);
        window.location.href = "/companies/1";
        return;
    }
    response = await attempLogin("customer", payload);
    if(response.ok){
        await loginSuccessfull(response);
        window.location.href = "/customers/1";
        
        return;
    }
    alert("login failed!");
}
async function attempLogin(client, payload){
    response = await fetch("/api/login/" + client, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: payload
    });
    return response;
}
async function loginSuccessfull(response){
    const json = await response.json();
    sessionStorage.setItem('token', json.data);
}