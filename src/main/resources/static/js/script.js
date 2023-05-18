populateTable();
function populateTable(){
    const myTableBody = document.getElementById("myTableBody");
    let myPromise = fetchCompanies();
    myPromise.then((companies) => {
        console.log(companies);
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

            myTableBody.appendChild(row);
        }
    });
}
async function fetchCompanies(){
    const response = await fetch('http://localhost:8080/admin/companies', {
        mode:"cors"
    });
    return await response.json();
}