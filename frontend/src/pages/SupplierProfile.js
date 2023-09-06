import React, { useEffect, useState } from "react";
import { Button, Card, Container, Nav, Navbar } from "react-bootstrap";
import chef from "../resources/chef.jpg";
import { useLocation } from "react-router-dom";
import axios from "axios";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function Profile(props) {
  const location = useLocation();
  const [supDetails, setSupDetails] = useState("");
  const currentDate = new Date();
  const handleClick = () => {
    console.log(location.state.cusId);
    const notify = {
      message: `${location.state.cname} requested subscription`,
      eventType: "review",
      supplierId: location.state.id,
    };
    axios
      .post(API_HEADER + "supplier-notification/create", notify)
      .then(swal("Notification sent to the supplier"));
    const subscription = {
      meals_remaining: 30,
      sub_date: currentDate,
      status: "Pending",
      activeStatus: 0,
      customerId: location.state.cusId,
      supplierId: location.state.id,
    };

    axios
      .post(API_HEADER + "subscription/add", subscription)
      .then(swal("Subscription requested"));
  };
  useEffect(() => {
    axios
      .get(`${API_HEADER}supplier/get/${location.state.id}`)
      .then((response) => {
        setSupDetails(response.data);
      });
  });
  return (
    <div>
      <h2 className="m-5">Supplier details</h2>
      <Card style={{ width: "18rem" }} className="mx-auto m-5">
        <Card.Img variant="top" src={chef} />
        <Card.Body>
          <Card.Title></Card.Title>
          <Card.Text>
            Name : {supDetails.supName}
            <br />
            Address : {supDetails.supAddress}
            <br />
            Contact Number : {supDetails.supContactNumber}
            <br />
            Email Id : {supDetails.supEmail}
            <br />
            Licence number : {supDetails.govtIssuedId}
            <br />
            Paypal link : {supDetails.paypalLink}
            <br />
          </Card.Text>
          <Button variant="primary" onClick={handleClick}>
            Notify
          </Button>
        </Card.Body>
      </Card>
    </div>
  );
}
export default Profile;
