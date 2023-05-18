import axios from "axios";
import Company from "../Models/Company/Company";
import Customer from "../Models/Customer/Customer";

class AdminService {
    public async getAllCompanies() {
        return (await axios.get<Company[]>("https://localhost:443/api/admin/companies", this.getConfig())).data;
    }
    public async addCompany(company:Company) {
        return (await axios.post<string>("https://localhost:443/api/admin/companies", this.getConfig())).data;
    }
    public async updateCompany(company:Company) {
        return (await axios.put<string>("https://localhost:443/api/admin/companies/", company, this.getConfig())).data;
    }
    public async deleteCompany(companyId:string) {
        return (await axios.delete<string>("https://localhost:443/api/admin/companies/" + companyId, this.getConfig())).data;
    }  
    public async getAllCustomers() {
        return (await axios.get<Customer[]>("https://localhost:443/api/admin/customers", this.getConfig())).data;
    }
    public async addCustomer(customer:Customer) {
        return (await axios.post<string>("https://localhost:443/api/admin/customers", this.getConfig())).data;
    }
    public async updateCustomer(customer:Customer) {
        return (await axios.put<string>("https://localhost:443/api/admin/customers/", customer, this.getConfig())).data;
    }
    public async deleteCustomer(customerId:string) {
        return (await axios.delete<string>("https://localhost:443/api/admin/customers/" + customerId, this.getConfig())).data;
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
const adminService = new AdminService();
export default adminService;
