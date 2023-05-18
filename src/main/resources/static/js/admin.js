loadPage();
addEvents();
let lastSelectedCustomerID;
function loadPage() {
    console.log(getToken());
    populateTable();
}
function addEvents() {
    const myForm = document.getElementById("myForm");
    myForm.addEventListener("submit", e => {
        e.preventDefault();
        updateCustomer(lastSelectedCustomerID);
    });
    document.addEventListener("click", e => {
        if (e.target.getAttribute('id') == "modal-background")
            e.target.style.display = "none";
    });
}
function populateTable() {
    const companiesTableBody = document.getElementById("companiesTableBody");
    fetchCompanies().then((companies) => {
        // console.log(companies);
        for (const company of companies) {
            const row = document.createElement('tr');

            const id = document.createElement('td');
            id.textContent = company.id;
            row.appendChild(id);

            const name = document.createElement('td');
            name.textContent = company.name;
            row.appendChild(name);

            const email = document.createElement('td');
            email.textContent = company.email;
            row.appendChild(email);

            const password = document.createElement('td');
            password.textContent = company.password;
            row.appendChild(password);

            companiesTableBody.appendChild(row);
        }
    });
    const customersTableBody = document.getElementById("customersTableBody");
    fetchCustomers().then((customers) => {
        // console.log(customers);
        for (const customer of customers) {
            const row = document.createElement('tr');

            const id = document.createElement('td');
            id.textContent = customer.id;
            row.appendChild(id);

            const firstName = document.createElement('td');
            firstName.textContent = customer.firstName;
            row.appendChild(firstName);

            const lastName = document.createElement('td');
            lastName.textContent = customer.lastName;
            row.appendChild(lastName);


            const email = document.createElement('td');
            email.textContent = customer.email;
            row.appendChild(email);

            const password = document.createElement('td');
            password.textContent = customer.password;
            row.appendChild(password);


            const actions = document.createElement('td');

            const editAction = document.createElement('span');
            editAction.setAttribute("class", "material-symbols-outlined icon-hover edit");
            editAction.setAttribute("customer", customer.id);
            editAction.textContent = "edit";
            editAction.addEventListener("click", e => {
                document.getElementById("modal-background").style.display = "block";
                lastSelectedCustomerID = customer.id;
                loadCustomerInfo(customer.id);
                // editCustomer(customer.id);
            });
            actions.appendChild(editAction);

            const deleteAction = document.createElement('span');
            deleteAction.setAttribute("class", "material-symbols-outlined icon-hover delete");
            deleteAction.setAttribute("customer", customer.id);
            deleteAction.textContent = "delete";
            deleteAction.addEventListener("click", e => {
                deleteCustomer(customer.id);
                updateCustomersTable(customer.id);
            });
            actions.appendChild(deleteAction);

            row.appendChild(actions);

            customersTableBody.appendChild(row);
        }
    });
}
async function fetchCompanies() {
    const response = await fetch('/api/admin/companies');
    if (response.ok)
        return await response.json();
    else
        alert(await response.text());
}
async function fetchCustomers() {
    const response = await fetch('/api/admin/customers');
    if (response.ok)
        return await response.json();
    else
        alert(await response.text());
}
async function deleteCustomer(customerID) {
    const token = getToken();
    const response = await fetch('/api/admin/customers/' + customerID, {
        method: "DELETE",
        headers: {
            'Authorization': token
        },
    });
    return response;
}
function updateCustomersTable(customerID) {
    const table = document.getElementById("customersTableBody");
    for (let i = 0; i < table.rows.length; i++) {
        const row = table.rows[i];
        if (row.cells[0].textContent == customerID) {
            table.removeChild(row);
            return;
        }
    }
    // for (const row in customersTableBody.rows) {
    //     alert(row.cells[0].textContent);
    // }
}
async function editCustomer(customerID) {
    alert("customer update form");
}


function getToken() {
    return sessionStorage.getItem('token');
    // return document.cookie.split('=')[1];
}
function loadCustomerInfo(customerID) {
    fetchCustomer(customerID).then((customerData) => {
        console.log(customerData);
        document.getElementById("first-name").value = customerData.firstName;
        document.getElementById("last-name").value = customerData.lastName;
        document.getElementById("email").value = customerData.email;
    });
}
function updateCustomer(id) {
    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const passwordAgain = document.getElementById("password-again").value;

    if (password == passwordAgain) {
        let response = putCustomer(id, firstName, lastName, email, password);
        console.log(response);

        alert("Customer updated")
    } else {
        alert("both passwords should be equal");
    }

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