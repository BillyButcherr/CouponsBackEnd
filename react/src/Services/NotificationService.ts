import axios from "axios";
import CouponItem from "../Models/Coupon/CouponItem";
import { CouponsStore, fetchCoupons } from "../Store/ViewerArea/CouponsState";
import { notificationStore, updateMessage } from "../Store/NotificationState";
import NotificationData from "../Models/NotificationData";

class NotificationService {
    public push(message:string){
        const data = new NotificationData(true, message);
        notificationStore.dispatch(updateMessage(data));
    }
}
const notificationService = new NotificationService();
export default notificationService;











