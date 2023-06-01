import { createStore } from "redux";
import Company from "../../Models/Company/Company";

export class CompaniesState {
    public companies: Company[] = [];
}
export enum CompaniesActionType {
    fetchAll, add, update, delete
}
export interface CompaniesAction {
    type: CompaniesActionType;
    payload: any;
}

export function fetchCompanies(companies: Company[]) {
    return { type: CompaniesActionType.fetchAll, payload: companies };
}
export function addCompany(company: Company) {
    return { type: CompaniesActionType.add, payload: company };
}
export function updateCompany(company: Company) {
    return { type: CompaniesActionType.update, payload: company };
}
export function deleteCompany(companyId: string) {
    return { type: CompaniesActionType.delete, payload: companyId };
}
function companyReducer(currentState = new CompaniesState(), action: CompaniesAction) {
    const newState = { ...currentState };
    switch (action.type) {
        case CompaniesActionType.fetchAll: {
            newState.companies = action.payload;
        } break;
        case CompaniesActionType.add: {
            newState.companies.push(action.payload);
        } break;
        case CompaniesActionType.update: {
            const companyId = action.payload.id;
            const companyIndex = newState.companies.findIndex(company => company.id === companyId);
            if (companyIndex != -1) {
                newState.companies[companyIndex] = action.payload;
            }
        } break;
        case CompaniesActionType.delete: {

            const companyId = action.payload;
            newState.companies = newState.companies.filter((item: Company) => item.id !== companyId);
            
            // newState.companies = updatedCompanies;

            // const companyIndex = newState.companies.findIndex(company => company.id === companyId);
            // if (companyIndex != -1) {
            //     newState.companies.splice(companyIndex, 1);
            // }
        } break;
    }
    return newState;
}
export const companiesStore = createStore(companyReducer);
