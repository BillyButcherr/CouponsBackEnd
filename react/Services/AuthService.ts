import axios from "axios";
import LoginData from "../Models/LoginData/LoginData";
import LoginResponse from "../Models/LoginResponse";
class AuthService {
    private async attemptCustomerLogin(loginData: LoginData) {
        return (await axios.post<LoginResponse>("http://localhost:8080/api/login/customer", loginData)).data;
    }
    private async attemptCompanyLogin(loginData: LoginData) {
        return (await axios.post<LoginResponse>("http://localhost:8080/api/login/company", loginData)).data;
    }
    private async attemptAdminLogin(loginData: LoginData) {
        return (await axios.post<LoginResponse>("http://localhost:8080/api/login/admin", loginData)).data;
    }
    public async attemptLogin(loginData: LoginData) {
        return await this.attemptCustomerLogin(loginData)
            .then(loginResponse => {
                this.updateSession('Customer', loginResponse.token, loginResponse.clientId);
                console.log("login customer successfull.");
                console.log(loginResponse);
                // return "customer";
                return;
            })
            .catch(async (err) => {
                console.log("login customer failed.");
                return await this.attemptCompanyLogin(loginData)
                    .then(loginResponse => {
                        this.updateSession('Company', loginResponse.token, loginResponse.clientId);
                        console.log("login company successfull.");
                        console.log(loginResponse);
                        // return "company";
                        return;
                    })
                    .catch(async (err) => {
                        console.log("login company failed.");
                        return await this.attemptAdminLogin(loginData)
                            .then(loginResponse => {
                                this.updateSession('Admin', loginResponse.token, loginResponse.clientId);
                                console.log("login admin successfull");
                                console.log(loginResponse);
                                return;
                                // return "admin";
                            })
                            .catch(() => {
                                console.log("login admin failed");
                                throw new Error("login failed.");
                                // return "fail";
                            });
                    });
            });

    }
    private updateSession(clientType: string, token: string, clientId: string) {
        sessionStorage.setItem('authToken', token);
        sessionStorage.setItem('clientType', clientType);
        sessionStorage.setItem('clientId', clientId);
    }
    public logout() {
        this.updateSession('', '', '');
    }
}
const authService = new AuthService();
export default authService;
