import React, { useState, useEffect } from "react";
import { Cookies } from "react-cookie";
import axios from "axios";
import { Spinner, Container, Table, Button } from "react-bootstrap";
import NavbarComponent from "../components/NavbarComponent";
import { addCustomerNotification } from "../utils";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function SubscriptionRequests() {
  const [subscriptionRequestList, setSubscriptionRequestList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");

  useEffect(() => {
    setIsLoading(true);
    axios
      .get(
        `${API_HEADER}subscription/get/pending/${loggedInUser.supId}`
      )
      .then((response) => {
        setSubscriptionRequestList(response.data);
      })
      .catch((e) => {
        swal("Error getting data" + e);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [loggedInUser.supId]);

  const handleRequest = (event, index) => {
    const selectedStatus = event.target.value;
    const currentRequest = subscriptionRequestList[index];
    delete currentRequest["customer"];
    currentRequest.status = selectedStatus;
    if (selectedStatus === "Approved") {
      currentRequest.activeStatus = 1;
    }
    console.log(currentRequest);
    setIsLoading(true);
    axios
      .put(API_HEADER + "subscription/update", currentRequest)
      .then(() => {
        addCustomerNotification({
          message: `${loggedInUser.supName} has appproved your subscription request.`,
          eventType: "Subscription Approved",
          customerId: subscriptionRequestList[index].customerId,
        });
        setSubscriptionRequestList((prev) =>
          prev.filter((_, idx) => idx !== index)
        );
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  return (
    <div>
      {/* <NavbarComponent /> */}
      {isLoading ? <Spinner /> : null}
      {subscriptionRequestList.length === 0 ? (
        <div className="m-auto">
          <h1>No Pending Requests</h1>
        </div>
      ) : (
        <Container className="my-5 mx-xs-2 mx-auto customerlist-table">
          <Table>
            <thead>
              <tr>
                <th></th>
                <th>Customer Email</th>
                <th>Customer Name</th>
                <th>Request Date</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {subscriptionRequestList.map((subRequest, index) => {
                return (
                  <tr key={subRequest.sub_id}>
                    <td>{index + 1}</td>
                    <td>{subRequest.customer?.cust_email}</td>
                    <td>
                      {subRequest.customer?.cust_fname +
                        " " +
                        subRequest.customer?.cust_lname}
                    </td>
                    <td>{subRequest.sub_date}</td>
                    <td>
                      <Button
                        variant="success"
                        value="Approved"
                        className="me-2 py-1"
                        onClick={(event) => handleRequest(event, index)}
                      >
                        Approve
                      </Button>
                      <Button
                        variant="danger"
                        value="Rejected"
                        className="me-2 py-1"
                        onClick={(event) => handleRequest(event, index)}
                      >
                        Reject
                      </Button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        </Container>
      )}
    </div>
  );
}

export default SubscriptionRequests;
