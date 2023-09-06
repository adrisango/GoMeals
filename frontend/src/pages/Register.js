import React, { useState } from "react";
import axios from "axios";
import { Col, Button, Row, Container, Card, Form } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function Register() {
  const [cust_fname, setFname] = useState("");
  const [cust_lname, setLname] = useState("");
  const [cust_address, setAddress] = useState("");
  const [cust_email, setEmail] = useState("");
  const [cust_contact_number, setContactNumber] = useState("");
  const [cust_password, setCustPassword] = useState("");
  const [cust_confirm_password, setCustConfirmPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    const user = {
      cust_fname: cust_fname,
      cust_lname: cust_lname,
      cust_address: cust_address,
      cust_email: cust_email,
      cust_card_details: "Default fake param",
      cust_contact_number: cust_contact_number,
      cust_password: cust_password,
      cust_confirm_password: cust_confirm_password,
    };
    if (validateInputs()) {
      axios
        .post(API_HEADER + "customer/create", user)
        .then((response) => {
          console.log(response.data);
          navigate("/login");
          swal("User registration was succesful");
        })
        .catch((error) => {
          console.log(error);
          if (error.response) {
            const { data } = error.response;
            swal(`Registration failed: ${data.message}`);
          } else {
            swal("Registration failed. Please try again later.");
          }
        });
    }
  };

  const validateInputs = () => {
    const regexForNumber = /^[0-9\b]+$/;
    const regexForEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (cust_fname === "" || cust_lname === "" || cust_address === ""
      || cust_email === "" || cust_contact_number === "" || cust_password === ""
      || cust_confirm_password === "") {
      swal("Please provide inout for all fields.");
      return false;
    } else if (!regexForEmail.test(cust_email)) {
      swal("Please provide valid email.");
      return false;
    } else if (cust_contact_number.length !== 10 || !regexForNumber.test(cust_contact_number)) {
      swal("Please provide valid contact number");
      return false;
    } else if (cust_password !== cust_confirm_password) {
      swal("Password must match.");
      return false;
    }
    return true;
  };

  return (
    <>
      <div>
        <Container>
          <Row className="vh-100 d-flex justify-content-center align-items-center">
            <Col md={8} lg={6} xs={12}>
              <div className="border border-2 border-primary"></div>
              <Card className="shadow px-4">
                <Card.Body>
                  <div className="mb-3 mt-md-4">
                    <h2 className="fw-bold mb-2 text-center text-uppercase ">
                      Go Meals
                    </h2>
                    <div className="mb-3">
                      <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="FName">
                          <Form.Control
                            type="text"
                            placeholder="Enter Name"
                            value={cust_fname}
                            onChange={(e) => setFname(e.target.value)}
                          />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="LName">
                          <Form.Control
                            type="text"
                            placeholder="Enter last name"
                            value={cust_lname}
                            onChange={(e) => setLname(e.target.value)}
                          />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                          <Form.Control
                            type="email"
                            placeholder="Enter email"
                            value={cust_email}
                            onChange={(e) => setEmail(e.target.value)}
                          />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formAddress">
                          <Form.Control
                            type="text"
                            placeholder="Enter your Address"
                            value={cust_address}
                            onChange={(e) => setAddress(e.target.value)}
                          />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formContact">
                          <Form.Control
                            type="text"
                            placeholder="Enter your Phone"
                            value={cust_contact_number}
                            onChange={(e) => setContactNumber(e.target.value)}
                          />
                        </Form.Group>

                        <Form.Group
                          className="mb-3"
                          controlId="formBasicPassword"
                        >
                          <Form.Control
                            type="password"
                            placeholder="Password"
                            value={cust_password}
                            onChange={(e) => setCustPassword(e.target.value)}
                          />
                        </Form.Group>
                        <Form.Group
                          className="mb-3"
                          controlId="formConfirmBasicPassword"
                        >
                          <Form.Control
                            type="password"
                            placeholder="Confirm password"
                            value={cust_confirm_password}
                            onChange={(e) =>
                              setCustConfirmPassword(e.target.value)
                            }
                          />
                        </Form.Group>
                        <Form.Group
                          className="mb-3"
                          controlId="formBasicCheckbox"
                        ></Form.Group>
                        <div className="d-grid">
                          <Button variant="primary" type="submit">
                            Create Account
                          </Button>
                        </div>
                      </Form>
                      <div className="mt-3">
                        <p className="mb-0  text-center">
                          Already have an account??{" "}
                          {/* <Link to="/login">login</Link> */}
                          <NavLink
                            className=""
                            activeClassName="is-active"
                            to="/login"
                            exact
                          >
                            Login
                          </NavLink>
                        </p>
                        <p className="mb-0  text-center">
                          Have a supplier account??{" "}
                          {/* <Link to="/login">login</Link> */}
                          <NavLink
                            className=""
                            activeClassName="is-active"
                            to="/Supplierlogin"
                            exact
                          >
                            Login
                          </NavLink>
                        </p>
                      </div>
                    </div>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    </>
  );
}

export default Register;
