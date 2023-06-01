import { createStore } from "redux";
import CouponItem from "../../Models/Coupon/CouponItem";

export class PurchasesState{
    public coupons:CouponItem[] = [];
}
export enum PurchasesActionType{
    fetchAll, add, update, delete
}
export interface PurchasesAction{
    type:PurchasesActionType;
    payload:any;
}

export function fetchPurchases(coupons:CouponItem[]){
    return {type:PurchasesActionType.fetchAll, payload:coupons};
}
export function addPurchase(coupon:CouponItem){
    return {type:PurchasesActionType.add, payload:coupon};
}
export function updatePurchase(coupon:CouponItem){
    return {type:PurchasesActionType.update, payload:coupon};
}
export function deletePurchase(coupon:CouponItem){
    return {type:PurchasesActionType.delete, payload:coupon};
}
function PurchasesReducer(currentState = new PurchasesState(), action:PurchasesAction){
    const newState = {...currentState};
    switch(action.type){
        case PurchasesActionType.fetchAll:{
            newState.coupons = action.payload;
        }break;
        case PurchasesActionType.add:{
            newState.coupons.push(action.payload);
        }break;
        case PurchasesActionType.update:{
            const couponId = action.payload.id;
            const couponIndex = newState.coupons.findIndex(index => index === couponId);
            newState.coupons[couponIndex] = action.payload;
        }break;
        case PurchasesActionType.delete:{
            const couponId = action.payload;
            newState.coupons = newState.coupons.filter((item: CouponItem) => item.id !== couponId);
        }break;
    }
    return newState;
}
export const PurchasesStore = createStore(PurchasesReducer);
