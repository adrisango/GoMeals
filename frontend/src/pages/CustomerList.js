import React from "react";
import { Container, Table } from "react-bootstrap";

function CustomerList(props) {
    props.customerList.forEach((customer) => {
        props.subscriptionList.forEach((subscription) => {
            if(subscription.customerId === customer.cust_id) {
                customer.mealCount = subscription.meals_remaining
            }
        });
    })

    return (
        <Container className="my-5 mx-auto">
            <Table striped bordered responsive>
                <thead>
                    <tr>
                        <th></th>
                        <th>Customer Name</th>
                        <th>Contact Number</th>
                        <th>Meal Count</th>
                    </tr>
                </thead>
                <tbody>
                    {props.customerList.map((customer, index) => {
                        return (
                            <tr key={customer.cust_id}>
                                <td>{index + 1}</td>
                                <td>{customer.cust_fname + " " + customer.cust_lname}</td>
                                <td>{customer.cust_contact_number}</td>
                                <td>{customer.mealCount}</td>
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
        </Container>

    );
};

export default CustomerList;