const customerID = '1';
populateTable();
function populateTable(){
    const myTableBody = document.getElementById("couponsTableBody");
    let myPromise = fetchCoupons();
    myPromise.then((coupons) => {
        console.log(coupons);
        for (const coupon of coupons) {
            const row = document.createElement('tr');

            const id = document.createElement('td');
            id.textContent = coupon.id;
            row.appendChild(id);

            const title = document.createElement('td');
            title.textContent = coupon.title;
            row.appendChild(title);
            
            const description = document.createElement('td');
            description.textContent = coupon.description;
            row.appendChild(description);

            const category = document.createElement('td');
            category.textContent = coupon.category;
            row.appendChild(category);

            const company = document.createElement('td');
            company.textContent = coupon.company.name;
            row.appendChild(company);

            const startDate = document.createElement('td');
            startDate.textContent = coupon.startDate;
            row.appendChild(startDate);

            const endDate = document.createElement('td');
            endDate.textContent = coupon.endDate;
            row.appendChild(endDate);

            const price = document.createElement('td');
            price.textContent = '₪' + coupon.price;
            row.appendChild(price);

            const image = document.createElement('td');
            image.textContent = coupon.image;
            row.appendChild(image);

            myTableBody.appendChild(row);
        }
    });
}
async function fetchCoupons(){
    const token = getToken();
    const response = await fetch('/api/customers/' + customerID + '/purchases', {
        method: "GET",
        headers: {
            'Authorization': token
        },
    });
    return await response.json();
}
function getToken() {
    return sessionStorage.getItem('token');
}