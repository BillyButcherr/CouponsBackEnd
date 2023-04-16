class LoginResponse {
    token:string;
    clientId:string;
    
    constructor(token:string, clientId:string){
        this.token = token;
        this.clientId = clientId;
    }
}
export default LoginResponse;