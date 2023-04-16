import { createStore } from "redux";
import CouponItem from "../Models/Coupon/CouponItem";

export class CouponsState{
    public coupons:CouponItem[] = [];
}
export enum CouponsActionType{
    fetchAll, add, update, delete
}
export interface CouponsAction{
    type:CouponsActionType;
    payload:any;
}

export function fetchCoupons(coupons:CouponItem[]){
    return {type:CouponsActionType.fetchAll, payload:coupons};
}
export function addCoupon(coupon:CouponItem){
    return {type:CouponsActionType.add, payload:coupon};
}
export function updateCoupon(coupon:CouponItem){
    return {type:CouponsActionType.update, payload:coupon};
}
export function deleteCoupon(coupon:CouponItem){
    return {type:CouponsActionType.delete, payload:coupon};
}
function CouponReducer(currentState = new CouponsState(), action:CouponsAction){
    const newState = {...currentState};
    switch(action.type){
        case CouponsActionType.fetchAll:{
            newState.coupons = action.payload;
        }break;
        case CouponsActionType.add:{
            newState.coupons.push(action.payload);
        }break;
        case CouponsActionType.update:{
            const couponId = action.payload.id;
            const couponIndex = newState.coupons.findIndex(index => index === couponId);
            newState.coupons[couponIndex] = action.payload;
        }break;
        case CouponsActionType.delete:{
            const couponId = action.payload.id;
            const couponIndex = newState.coupons.findIndex(index => index === couponId);
            newState.coupons.splice(couponIndex, 1);
        }break;
    }
    return newState;
}
export const CouponsStore = createStore(CouponReducer);
