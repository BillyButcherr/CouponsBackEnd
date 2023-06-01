class AuthData {
    clientId:string;
    clientType:string;
    token:string;
    
    constructor(clientId:string, clientType:string, token:string){
        this.clientId = clientId;
        this.clientType = clientType;
        this.token = token;
    }
}
export default AuthData;