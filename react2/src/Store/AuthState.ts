import { createStore } from "redux";
import LoginResponse from "../Models/LoginResponse";

export class AuthState{
    loginResponse:LoginResponse = new LoginResponse('','');
}
export enum AuthActionType{
    Signup, Login, Logout
}
export interface AuthAction{
    type:AuthActionType;
    payload:any;
}

export function signupAction(LoginResponse:LoginResponse){
    return {type:AuthActionType.Signup, payload:LoginResponse};
}
export function loginAction(LoginResponse:LoginResponse){
    return {type:AuthActionType.Login, payload:LoginResponse};
}
export function logoutAction(LoginResponse:LoginResponse){
    return {type:AuthActionType.Logout, payload:LoginResponse};
}
function authReducer(currentState = new AuthState(), action:AuthAction){
    const newState = {...currentState};
    switch(action.type){
        case AuthActionType.Signup:
        case AuthActionType.Login:{
            newState.loginResponse = action.payload;
        }break;
        case AuthActionType.Logout:{
            newState.loginResponse = new LoginResponse('','');
        }break;
    }
    return newState;
}
export const authStore = createStore(authReducer);
