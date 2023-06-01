import axios from "axios";
import Company from "../Models/Company/Company";
import Customer from "../Models/Customer/Customer";
import { companiesStore, addCompany, deleteCompany, fetchCompanies, updateCompany } from "../Store/AdminArea/CompaniesState";
import authService from "./AuthService";
import { addCustomer, customersStore, deleteCustomer, updateCustomer } from "../Store/AdminArea/CustomersState";

class AdminService {
    public async getAllCompanies() {
        if (companiesStore.getState().companies.length === 0) {
            const companies = (await axios.get<Company[]>("https://localhost:443/api/admin/companies", this.getConfig())).data;
            companiesStore.dispatch(fetchCompanies(companies));
        }
        return companiesStore.getState().companies;
    }
    public async getOneCompany(companyId: string) {
        const companies = await this.getAllCompanies();
        const companyIndex = companies.findIndex(company => company.id === companyId);
        // if (companyIndex != -1)
        return companies[companyIndex];
    }
    public async addCompany(company: Company) {
        try {
            const generatedId = (await axios.post<string>("https://localhost:443/api/admin/companies", company, this.getConfig())).data;
            company.id = generatedId;
            companiesStore.dispatch(addCompany(company));
            return generatedId;
        } catch (error) {
            alert(error);
        }
        return 'failed';
    }
    public async updateCompany(company: Company) {
        try {
            const response = (await axios.put<string>("https://localhost:443/api/admin/companies/", company, this.getConfig())).data;
            companiesStore.dispatch(updateCompany(company));
            return response;
        } catch (error) {
            return error;
        }
    }
    public async updateCompanyPassword(company: Company) {
        return (await axios.put<string>("https://localhost:443/api/admin/companies/password", company, this.getConfig())).data;
    }
    public async deleteCompany(companyId: string) {
        try {
            const response = (await axios.delete<string>("https://localhost:443/api/admin/companies/" + companyId, this.getConfig())).data;
            companiesStore.dispatch(deleteCompany(companyId));
            return response;
        } catch (error) {
            return error;
        }
    }
    public async getAllCustomers() {
        return (await axios.get<Customer[]>("https://localhost:443/api/admin/customers", this.getConfig())).data;
    }
    public async getOneCustomer(customerId: string) {
        const customers = await this.getAllCustomers();
        const customerIndex = customers.findIndex(customer => customer.id === customerId);
        // if (companyIndex != -1)
        return customers[customerIndex];
    }
    public async addCustomer(customer: Customer) {
        try {
            const generatedId = (await axios.post<string>("https://localhost:443/api/admin/customers", customer, this.getConfig())).data;
            customer.id = generatedId;
            customersStore.dispatch(addCustomer(customer));
            return generatedId;
        } catch (error) {
            return error;
        }
    }
    public async updateCustomer(customer: Customer) {
        try {
            const response = (await axios.put<string>("https://localhost:443/api/admin/customers/", customer, this.getConfig())).data;
            customersStore.dispatch(updateCustomer(customer));
            return response;
        } catch (error) {
            return error;
        }
    }
    public async updateCustomerPassword(customer: Customer) {
        try {
            const response = (await axios.put<string>("https://localhost:443/api/admin/customers/password", customer, this.getConfig())).data;
            return response;
        } catch (error) {
            return error;
        }
    }
    public async deleteCustomer(customerId: string) {
        try {
            const response = (await axios.delete<string>("https://localhost:443/api/admin/customers/" + customerId, this.getConfig())).data;
            customersStore.dispatch(deleteCustomer(customerId));
            return response;
        } catch (error) {
            return error;
        }
    }
    private getConfig() {
        const config = {
            headers: {
                "Authorization": 'Bearer ' + authService.getAuthData().token,
                // "Authorization": 'Bearer ' + sessionStorage.getItem('authToken'),
            }
        }
        return config;
    }
}
const adminService = new AdminService();
export default adminService;
