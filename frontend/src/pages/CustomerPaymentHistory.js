import { React, useState, useEffect } from "react";
import Table from "react-bootstrap/Table";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
import NavbarComponent from "../components/NavbarComponent";
import "../styles/CustomerPaymentHistory.css";
import { API_HEADER } from "../utils.js";

function CustomerPaymentHistory() {
  const [userDetails, setUserDetails] = useState({});
  const cookies = new Cookies();
  const navigate = useNavigate();
  const loggedInUser = cookies.get("loggedInUser");

  useEffect(() => {
    fetch(API_HEADER + "customer/get/" + loggedInUser.cust_id)
      .then((res) => res.json())
      .then((val) => setUserDetails(val));
  }, []);

  useEffect(() => {
    console.log(userDetails);
  }, [userDetails]);

  return (
    <div>
      {/* <NavbarComponent /> */}
      <h2 id="paymentHistoryHeader"> Payment History </h2>
      <div id="paymentHistoryTable">
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Subscription Id</th>
              <th>Subscription Date</th>
              <th>Meals Remaining</th>
              <th>Subscription Status</th>
              <th>Payment Done (CAD)</th>
              <th>Supplier Name</th>
              <th>Supplier Email</th>
            </tr>
          </thead>
          <tbody>
            {userDetails.subscriptions &&
              userDetails.subscriptions.map((sub) => (
                <tr key={sub.sub_id}>
                  <td>{sub.sub_id}</td>
                  <td>{sub.sub_date}</td>
                  <td>{sub.meals_remaining}</td>
                  <td style={{ color: sub.status === 0 ? "red" : "green" }}>
                    {sub.status === 0 ? "Inactive" : "Active"}
                  </td>
                  {userDetails.suppliers.map((sup) => {
                    if (sup.supId === sub.supplierId) {
                      return (
                        <>
                          <td key={sup.supId}>{sup.mealPrice}</td>
                          <td>{sup.supName}</td>
                          <td>{sup.supEmail}</td>
                        </>
                      );
                    }
                  })}
                </tr>
              ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}

export default CustomerPaymentHistory;
