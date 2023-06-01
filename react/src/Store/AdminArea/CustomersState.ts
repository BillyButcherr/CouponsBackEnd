import { createStore } from "redux";
import Customer from "../../Models/Customer/Customer";

export class CustomersState {
    public customers: Customer[] = [];
}
export enum CustomersActionType {
    fetchAll, add, update, delete
}
export interface CustomersAction {
    type: CustomersActionType;
    payload: any;
}

export function fetchCustomers(customers: Customer[]) {
    return { type: CustomersActionType.fetchAll, payload: customers };
}
export function addCustomer(customers: Customer) {
    return { type: CustomersActionType.add, payload: customers };
}
export function updateCustomer(customers: Customer) {
    return { type: CustomersActionType.update, payload: customers };
}
export function deleteCustomer(customerId: string) {
    return { type: CustomersActionType.delete, payload: customerId };
}
function customerReducer(currentState = new CustomersState(), action: CustomersAction) {
    const newState = { ...currentState };
    switch (action.type) {
        case CustomersActionType.fetchAll: {
            newState.customers = action.payload;
        } break;
        case CustomersActionType.add: {
            newState.customers.push(action.payload);
        } break;
        case CustomersActionType.update: {
            const customerId = action.payload.id;
            const customerIndex = newState.customers.findIndex(customer => customer.id === customerId);
            if (customerIndex != -1) {
                newState.customers[customerIndex] = action.payload;
            }
        } break;
        case CustomersActionType.delete: {
            const customerId = action.payload;
            newState.customers = newState.customers.filter((item: Customer) => item.id !== customerId);
        } break;
    }
    return newState;
}
export const customersStore = createStore(customerReducer);
