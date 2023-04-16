import axios from "axios";
import Company from "../Models/Company/Company";
import Customer from "../Models/Customer/Customer";

class AdminService {
    public async getAllCompanies() {
        return (await axios.get<Company[]>("http://localhost:8080/api/admin/companies", this.getConfig())).data;
    }
    public async getAllCustomers() {
        return (await axios.get<Customer[]>("http://localhost:8080/api/admin/customers", this.getConfig())).data;
    }
    private getConfig(){
        const config = {
            headers: {
                "Authorization": 'Bearer ' + sessionStorage.getItem('authToken'),
            }
        }
        return config;
    }
}

export default AdminService;
