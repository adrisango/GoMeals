import axios from "axios";

export const addCustomerNotification = (customerNotification) => {
    console.log(customerNotification);
    axios
        .post(API_HEADER + "customer-notification/create", customerNotification)
}

export const addSupplierNotification = (supplierNotification) => {
    axios
        .post(API_HEADER + "supplier-notification/create", supplierNotification)
}


export const API_HEADER = "http://csci5308vm23.research.cs.dal.ca:8080/";