import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";
import { CompanyCouponsStore, fetchCoupons } from "../Store/CompanyCouponsState";

class CompanyService {
    companyId:string;
    constructor(companyId:string){
        this.companyId = companyId;
    }
    public async getAllCoupons() {
        if (CompanyCouponsStore.getState().coupons.length === 0) {
            const coupons = (await axios.get<CouponItem[]>("https://localhost:443/api/companies/" + this.companyId + "/coupons", this.getConfig())).data;
            CompanyCouponsStore.dispatch(fetchCoupons(coupons));
        }
        return CompanyCouponsStore.getState().coupons;
    }
    public async getOneCoupon(couponId:string){
        const coupons = await this.getAllCoupons();
        const couponIndex = coupons.findIndex(coupon => coupon.id === couponId);
        // if (couponIndex != -1)
        return coupons[couponIndex];
    }
    public async addCoupon(coupon:CouponItem) {
        return (await axios.post<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons", this.getConfig())).data;
    }
    public async updateCoupon(coupon:CouponItem) {
        return (await axios.put<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons", coupon, this.getConfig())).data;
    }
    public async deleteCoupon(couponId:string) {
        return (await axios.delete<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons" + couponId, this.getConfig())).data;
    }  
    private getConfig(){
        const config = {
            headers: {
                "Authorization": 'Bearer ' + sessionStorage.getItem('authToken'),
            }
        }
        return config;
    }
}
export default CompanyService;
