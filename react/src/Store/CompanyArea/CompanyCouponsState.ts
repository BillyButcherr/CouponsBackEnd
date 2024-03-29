import { createStore } from "redux";
import CouponItem from "../../Models/Coupon/CouponItem";

export class CompanyCouponsState {
    public coupons: CouponItem[] = [];
}
export enum CouponsActionType {
    fetchAll, add, update, delete
}
export interface CouponsAction {
    type: CouponsActionType;
    payload: any;
}

export function fetchCompanyCoupons(coupons: CouponItem[]) {
    return { type: CouponsActionType.fetchAll, payload: coupons };
}
export function addCompanyCoupon(coupon: CouponItem) {
    return { type: CouponsActionType.add, payload: coupon };
}
export function updateCompanyCoupon(coupon: CouponItem) {
    return { type: CouponsActionType.update, payload: coupon };
}
export function deleteCompanyCoupon(couponId: string) {
    return { type: CouponsActionType.delete, payload: couponId };
}
function CompanyCouponReducer(currentState = new CompanyCouponsState(), action: CouponsAction) {
    const newState = { ...currentState };
    switch (action.type) {
        case CouponsActionType.fetchAll: {
            newState.coupons = action.payload;
        } break;
        case CouponsActionType.add: {
            newState.coupons.push(action.payload);
        } break;
        case CouponsActionType.update: {
            const couponId = action.payload.id;
            const couponIndex = newState.coupons.findIndex(coupon => coupon.id === couponId);
            if (couponIndex != -1) {
                newState.coupons[couponIndex] = action.payload;
            }
        } break;
        case CouponsActionType.delete: {
            const couponId = action.payload;
            newState.coupons = newState.coupons.filter((item: CouponItem) => item.id !== couponId);
        } break;
    }
    return newState;
}
export const CompanyCouponsStore = createStore(CompanyCouponReducer);
