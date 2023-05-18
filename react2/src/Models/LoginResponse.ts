class LoginResponse {
    clientId:string;
    token:string;
    
    constructor(clientId:string, token:string){
        this.clientId = clientId;
        this.token = token;
    }
}
export default LoginResponse;