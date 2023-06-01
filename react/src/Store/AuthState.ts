import { createStore } from "redux";
import LoginResponse from "../Models/LoginResponse";
import AuthData from "../Models/AuthData";

export class AuthState{
    authData:AuthData = new AuthData('','','');
}
export enum AuthActionType{
    Signup, Login, Logout
}
export interface AuthAction{
    type:AuthActionType;
    payload:any;
}

export function signupAction(LoginResponse:AuthData){
    return {type:AuthActionType.Signup, payload:LoginResponse};
}
export function loginAction(LoginResponse:AuthData){
    return {type:AuthActionType.Login, payload:LoginResponse};
}
export function logoutAction(){
    return {type:AuthActionType.Logout, payload:LoginResponse};
}
function authReducer(currentState = new AuthState(), action:AuthAction){
    const newState = {...currentState};
    switch(action.type){
        case AuthActionType.Signup:
        case AuthActionType.Login:{
            newState.authData = action.payload;
        }break;
        case AuthActionType.Logout:{
            newState.authData = new AuthData('','', '');
        }break;
    }
    return newState;
}
export const authStore = createStore(authReducer);
