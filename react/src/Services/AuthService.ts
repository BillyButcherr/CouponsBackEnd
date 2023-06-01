import axios from "axios";
import LoginData from "../Models/LoginData/LoginData";
import LoginResponse from "../Models/LoginResponse";
import { authStore, loginAction, logoutAction } from "../Store/AuthState";
import AuthData from "../Models/AuthData";
import { companiesStore, fetchCompanies } from "../Store/AdminArea/CompaniesState";
import { CompanyCouponsStore, fetchCompanyCoupons } from "../Store/CompanyArea/CompanyCouponsState";
import { PurchasesStore, fetchPurchases } from "../Store/CustomerArea/PurchasesState";
import { CouponsStore, fetchCoupons } from "../Store/ViewerArea/CouponsState";
class AuthService {
    private async attemptCustomerLogin(loginData: LoginData) {
        return (await axios.post<LoginResponse>("https://localhost:443/api/auth/customer", loginData)).data;
    }
    private async attemptCompanyLogin(loginData: LoginData) {
        return (await axios.post<LoginResponse>("https://localhost:443/api/auth/company", loginData)).data;
    }
    private async attemptAdminLogin(loginData: LoginData) {
        return (await axios.post<LoginResponse>("https://localhost:443/api/auth/admin", loginData)).data;
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
        authStore.dispatch(loginAction(new AuthData(clientId, clientType, token)));
    }
    public logout() {
        this.updateSession('', '', '');
        //Empty stores
        this.clearStores();
    }
    public clearStores() {
        authStore.dispatch(logoutAction());
        companiesStore.dispatch(fetchCompanies([]));
        CompanyCouponsStore.dispatch(fetchCompanyCoupons([]));
        CouponsStore.dispatch(fetchCoupons([]));
        PurchasesStore.dispatch(fetchPurchases([]));
    }
    public getAuthData() {
        const authData = authStore.getState().authData;
        // if (!authData.token)
        //     throw new Error("Empty token.");
        return authData;
    }
    public isLogged() {
        const authData = authStore.getState().authData;
        return !authData.token === false;
    }
}
const authService = new AuthService();
export default authService;
