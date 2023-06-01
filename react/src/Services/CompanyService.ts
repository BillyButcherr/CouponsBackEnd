import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";
import authService from "./AuthService";
import CouponDTO from "../Models/Coupon/CouponDTO";
import { CompanyCouponsStore, fetchCompanyCoupons, addCompanyCoupon, updateCompanyCoupon, deleteCompanyCoupon } from "../Store/CompanyArea/CompanyCouponsState";

class CompanyService {
    companyId:string;
    constructor(companyId:string){
        this.companyId = companyId;
    }
    public async getAllCoupons() {
        if (CompanyCouponsStore.getState().coupons.length === 0) {
            const coupons = (await axios.get<CouponItem[]>("https://localhost:443/api/companies/" + this.companyId + "/coupons", this.getConfig())).data;
            CompanyCouponsStore.dispatch(fetchCompanyCoupons(coupons));
        }
        return CompanyCouponsStore.getState().coupons;
    }
    public async getOneCoupon(couponId:string){
        const coupons = await this.getAllCoupons();
        const couponIndex = coupons.findIndex(coupon => coupon.id === couponId);
        // if (couponIndex != -1)
        return coupons[couponIndex];
    }
    public async addCoupon(coupon:CouponDTO) {
        try {
            const generatedId = (await axios.post<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons", coupon, this.getConfig())).data;
            const couponItem = coupon.toCouponItem();
            couponItem.id = generatedId;
            CompanyCouponsStore.dispatch(addCompanyCoupon(couponItem));
            return generatedId;
        } catch (error) {
            alert(error);
        }
        // const generatedId = (await axios.post<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons", coupon, this.getConfig())).data;
        // const couponItem = coupon.toCouponItem();
        // couponItem.id = generatedId;
        // CompanyCouponsStore.dispatch(addCompanyCoupon(couponItem));
        return "failed";
    }
    public async updateCoupon(coupon:CouponItem) {
        const response = (await axios.put<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons/" + coupon.id, coupon, this.getConfig()));
        if(response.status === 200)
            CompanyCouponsStore.dispatch(updateCompanyCoupon(coupon));
        return response.data;
    }
    // public async updateCouponImage(couponId:string, imageUrl:string) {
    //     return await this.getOneCoupon(couponId).then(async (coupon) => {
    //         coupon.image = imageUrl;
    //         const response = (await axios.put<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons", coupon, this.getConfig()));
    //         if(response.status === 200)
    //             CompanyCouponsStore.dispatch(updateCompanyCoupon(coupon));
    //         return response.data;
    //     }).catch(err => {return err;});
    // }
    public async deleteCoupon(couponId:string) {
        const response = (await axios.delete<string>("https://localhost:443/api/companies/" + this.companyId + "/coupons/" + couponId, this.getConfig()));
        if(response.status === 200)
            CompanyCouponsStore.dispatch(deleteCompanyCoupon(couponId));
        return response.data;
    }
    private getConfig(){
        const config = {
            headers: {
                "Authorization": 'Bearer ' + authService.getAuthData().token,
            }
        }
        return config;
    }
}
export default CompanyService;
