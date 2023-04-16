import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";
import viewerService from "./ViewerService";
import Customer from "../Models/Customer/Customer";

class CustomerService {
    private customerId:string = '';
    private token:string = '';
    constructor(customerId:string, token:string){
        this.customerId = customerId;
        this.token = token;
    }
	public async getAllCoupons(){
        return viewerService.getAllCoupons();
        // return (await axios.get<CouponItem[]>("http://localhost:8080/api/customers/{customerId}/coupons")).data;
    }
    public async purchaseCoupon(coupon:CouponItem){
        return (await axios.post<string>("http://localhost:8080/api/customers/" + this.customerId + "/purchases", coupon, this.getConfig())).data;
    }
    public async getAllPurchasedCoupons(){
        return (await axios.get<CouponItem[]>("http://localhost:8080/api/customers/" + this.customerId + "/purchases", this.getConfig())).data;
    }
    public async getCustomerInfo(){
        return (await axios.get<Customer>("http://localhost:8080/api/customers/" + this.customerId + "/info", this.getConfig())).data;
    }
    private getConfig(){
        const config = {
            headers: {
                "Authorization": 'Bearer ' + this.token,
            }
        }
        return config;
    }
}
export default CustomerService;
