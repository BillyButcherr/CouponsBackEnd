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
function CouponReducer(currentState = new CouponsState(), action:CouponsAction){
    const newState = {...currentState};
    switch(action.type){
        case CouponsActionType.fetchAll:{
            newState.coupons = action.payload;
        }break;
    }
    return newState;
}
export const CouponsStore = createStore(CouponReducer);
