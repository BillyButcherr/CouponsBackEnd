class CouponItem {
    id:string;
    company:CompanyItem;
    category:Category;
    title:string;
    description:string;
    startDate:string;
    endDate:string;
    amount:number;
    price:number;
    image:string;
    constructor(id:string, company:CompanyItem, category:Category, title:string, description:string,
        startDate:string, endDate:string, amount:number, price:number, image:string){
        this.id = id;
        this.company = company;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
}
enum Category{
    Food, Electricity, Restaurant, Vacation
}
class CompanyItem{
    id:string;
    name:string;
    email:string;
    constructor(id:string, name:string, email:string){
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
export default CouponItem;