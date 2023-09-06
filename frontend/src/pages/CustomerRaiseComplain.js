import React, { useState, useEffect, useRef } from "react";
import { Container } from "react-bootstrap";
import { Col, Button, Row } from "react-bootstrap";
import { useParams } from "react-router";
import Form from "react-bootstrap/Form";
import { useNavigate } from "react-router-dom";
import { Cookies } from "react-cookie";
import "../styles/CustomerRaiseComplain.css";
import axios from "axios";
import { addSupplierNotification } from "../utils.js";
import { faUtensilSpoon } from "@fortawesome/free-solid-svg-icons";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function CustomerRaiseComplain() {
  const cookies = new Cookies();
  const [delivery, setDelivery] = useState({});
  const [cust_comment, setCustomerComment] = useState("");
  const [checkComplain, setCheckComplain] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();
  const loggedInUser = cookies.get("loggedInUser");
  useEffect(() => {
    console.log("useEffect");
    fetch(API_HEADER + "delivery/get/" + id)
      .then((res) => res.json())
      .then((deliveryData) => {
        setDelivery(deliveryData);
        console.log(deliveryData);
      });
  }, []);

  let getCurrentDate = () => {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, "0");
    const month = String(today.getMonth() + 1).padStart(2, "0"); // January is 0!
    const year = today.getFullYear();
    const currentDate = `${year}-${month}-${day}`;

    return currentDate;
  };

  let raiseComplain = (event) => {
    event.preventDefault();
    try {
      fetch(
        API_HEADER + "complain/get/all-customer/" +
          loggedInUser.cust_id
      )
        .then((res) => res.json())
        .then((complainsData) => {
          if (Array.isArray(complainsData)) {
            complainsData.forEach(function (complain) {
              console.log(complain.deliveryId + " : " + delivery.deliveryId);
              if (complain.deliveryId === delivery.deliveryId) {
                swal("Complain already raised for this delivery");
                throw new Error("Complain already raised for this delivery");
              }
            });
            if (cust_comment === "") {
              swal("Please provide some details in the comments");
            } else {
              callComplainApi();
            }
          }
        });
    } catch (error) {}
  };

  let callComplainApi = () => {
    let complainObject = {
      complainId: null,
      date: getCurrentDate(),
      delivery_id: delivery.delivery_id,
      customerComment: cust_comment,
      supplierComment: "",
      status: "Initiated",
      customerId: loggedInUser.cust_id,
      supplierId: delivery.supId,
      deliveryId: delivery.deliveryId,
    };
    let complain = JSON.stringify(complainObject);
    fetch(API_HEADER + "complain/create", {
      method: "POST",
      headers: { "content-type": "application/json" },
      body: complain,
    })
      .then((res) => {
        res.json();
      })
      .then((val) => {
        console.log(val);
        // alert("Complain raised and complain id is :" + val.complainId);
        notifySupplier();
      });
    swal("Complain raised successfully! ");
    navigate("/dashboard");
  };

  const notifySupplier = () => {
    const notify = {
      message: `${
        loggedInUser.cust_fname + " " + loggedInUser.cust_lname
      } has raised a Complain`,
      eventType: "Complaint",
      supplierId: delivery.supId,
    };
    axios
      .post(API_HEADER + "supplier-notification/create", notify)
      .then(console.log("Notified supplier"));
  };

  return (
    <div>
      {/* <NavbarComponent /> */}
      <Container>
        <h2 id="customerComplainPageHeader">Complain Portal</h2>
        <Row className="vh-100 d-flex justify-content-center align-items-center">
          <Col md={8} lg={6} xs={12}>
            <Form onSubmit={raiseComplain} className="form-container">
              <Form.Label className="form-label">Date</Form.Label>
              <Form.Control
                type="text"
                placeholder={delivery.deliveryDate}
                aria-label="Order"
                disabled
                className="form-control"
                readOnly
              />
              <Form.Group
                className="mb-3 form-group"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label className="form-label">Meal</Form.Label>
                <Form.Control
                  type="text"
                  placeholder={delivery.deliveryMeal}
                  disabled
                  className="form-control"
                  readOnly
                />
              </Form.Group>
              <Form.Group
                className="mb-3 form-group"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Label className="form-label">Enter Complain</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={3}
                  value={cust_comment}
                  onChange={(e) => setCustomerComment(e.target.value)}
                  className="form-control"
                />
              </Form.Group>
              <Button variant="primary" type="submit" className="btn-primary">
                Complain
              </Button>
            </Form>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default CustomerRaiseComplain;
