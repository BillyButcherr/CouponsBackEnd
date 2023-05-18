import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";
import { CouponsStore, fetchCoupons } from "../Store/CouponsState";

class ViewerService {
	public async getAllCoupons(){
        if (CouponsStore.getState().coupons.length === 0) {
            const coupons = (await axios.get<CouponItem[]>("https://localhost:443/api/public/coupons")).data;
            CouponsStore.dispatch(fetchCoupons(coupons));
        }
        return CouponsStore.getState().coupons;
    }
    public async getOneCoupon(couponId:string){
        const coupons = (await this.getAllCoupons());
        const index = coupons.findIndex(coupon => coupon.id == couponId);
        return coupons[index];
    }
}
const viewerService = new ViewerService();
export default viewerService;











