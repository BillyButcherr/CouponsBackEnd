const companyID = '1';
populateTable();
function populateTable(){
    const myTableBody = document.getElementById("couponsTableBody");
    let myPromise = fetchCoupons();
    myPromise.then((companies) => {
        console.log(companies);
        for (const company of companies) {
            const row = document.createElement('tr');

            const id = document.createElement('td');
            id.textContent = company.id;
            row.appendChild(id);

            const title = document.createElement('td');
            title.textContent = company.title;
            row.appendChild(title);
            
            const description = document.createElement('td');
            description.textContent = company.description;
            row.appendChild(description);

            const category = document.createElement('td');
            category.textContent = company.category;
            row.appendChild(category);

            const startDate = document.createElement('td');
            startDate.textContent = company.startDate;
            row.appendChild(startDate);

            const endDate = document.createElement('td');
            endDate.textContent = company.endDate;
            row.appendChild(endDate);

            const amount = document.createElement('td');
            amount.textContent = company.amount;
            row.appendChild(amount);

            const price = document.createElement('td');
            price.textContent = '₪' + company.price;
            row.appendChild(price);

            const image = document.createElement('td');
            image.textContent = company.image;
            row.appendChild(image);

            myTableBody.appendChild(row);
        }
    });
}
async function fetchCoupons(){
    const token = getToken();
    const response = await fetch('/api/companies/' + companyID + '/coupons', {
        method: "GET",
        headers: {
            'Authorization': token
        },
    });
    return await response.json();
}
function getToken(){
    return sessionStorage.getItem('token');

    // return document.cookie.split('=')[1];
}