import { createStore } from "redux";
import NotificationData from "../Models/NotificationData";


export class NotificationState{
    notificationData:NotificationData = new NotificationData(false,'');
}
export enum NotificationActionType{
    UpdateMessage, Login, Logout
}
export interface NotificationAction{
    type:NotificationActionType;
    payload:any;
}

export function updateMessage(notificationData:NotificationData){
    return {type:NotificationActionType.UpdateMessage, payload:notificationData};
}

function notificationReducer(currentState = new NotificationState(), action:NotificationAction){
    const newState = {...currentState};
    switch(action.type){
        case NotificationActionType.UpdateMessage:{
            newState.notificationData = action.payload;
        }break;
    }
    return newState;
}
export const notificationStore = createStore(notificationReducer);
