import Company from "../Company/Company";
import CouponItem from "./CouponItem";

class CouponDTO {
    company:CompanyDTO;
    category:Category;
    title:string;
    description:string;
    startDate:string;
    endDate:string;
    amount:number;
    price:number;
    image:string;
    constructor(companyId:string, category:Category, title:string, description:string,
        startDate:string, endDate:string, amount:number, price:number, image:string){
        this.company = new CompanyDTO(companyId);
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public toCouponItem():CouponItem{
        return new CouponItem("", new Company(this.company.id, "", "", ""),
         this.category, this.title, this.description, this.startDate, this.endDate, this.amount, this.price, this.image);
    }
}
export class CompanyDTO{
    id:string;
    constructor(id:string){
        this.id = id;
    }
}
export enum Category{
    Food, Electricity, Restaurant, Vacation
}
export default CouponDTO;