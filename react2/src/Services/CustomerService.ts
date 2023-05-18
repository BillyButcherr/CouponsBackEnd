import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";
import viewerService from "./ViewerService";
import Customer from "../Models/Customer/Customer";
import { PurchasesStore, fetchPurchases } from "../Store/PurchasesState";

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
        return (await axios.post<string>("https://localhost:443/api/customers/" + this.customerId + "/purchases", coupon, this.getConfig())).data;
    }
    public async getAllPurchasedCoupons(){
        if (PurchasesStore.getState().coupons.length === 0) {
            const purchases =  (await axios.get<CouponItem[]>("https://localhost:443/api/customers/" + this.customerId + "/purchases", this.getConfig())).data;
            PurchasesStore.dispatch(fetchPurchases(purchases));
        }
        return PurchasesStore.getState().coupons;
    }
    public async getOnePurchasedCoupon(purchaseId:string){
        const coupons = (await this.getAllPurchasedCoupons());
        const index = coupons.findIndex(coupon => coupon.id == purchaseId);
        return coupons[index];
    }
    public async getCustomerInfo(){
        return (await axios.get<Customer>("https://localhost:443/api/customers/" + this.customerId + "/info", this.getConfig())).data;
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
