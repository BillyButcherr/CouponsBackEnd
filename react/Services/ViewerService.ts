import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";

class ViewerService {
	public async getAllCoupons(){
        return (await axios.get<CouponItem[]>("http://localhost:8080/api/public/coupons")).data;
    }
}
const viewerService = new ViewerService();
export default viewerService;
