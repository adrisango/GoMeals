import React, { useState } from "react";
import axios from "axios";
import { Col, Button, Row, Container, Card, Form } from "react-bootstrap";
import { NavLink } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { Cookies } from "react-cookie";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function Login() {
  const cookies = new Cookies();
  const [cust_email, setEmail] = useState("");
  const [cust_password, setCustPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    const user = {
      cust_fname: "N/A",
      cust_lname: "N/A",
      cust_address: "N/A",
      cust_email: cust_email,
      cust_card_details: "Default fake param",
      cust_contact_number: "N/A",
      cust_password: cust_password,
    };
    axios
      .post(API_HEADER + "customer/login", user)
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data);
          const userData = {
            ...response.data,
            userType: "customer",
          };
          const cookieValue = JSON.stringify(userData);
          cookies.set("loggedInUser", cookieValue, { path: "/" });
          navigate("/dashboard");
          const loggedInUser = cookies.get("loggedInUser");
          console.log("current user's ID: " + loggedInUser.cust_id);
          console.log("current user's type: " + loggedInUser.userType);
        }
      })
      .catch((error) => {
        console.log(error);
        swal("login failed");
      });
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
                      Go Meals - Customer Login
                    </h2>
                    <div className="mb-3">
                      <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                          <Form.Control
                            type="email"
                            placeholder="Enter email"
                            value={cust_email}
                            onChange={(e) => setEmail(e.target.value)}
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
                          controlId="formBasicCheckbox"
                        ></Form.Group>
                        <div className="d-grid">
                          <Button variant="primary" type="submit">
                            Login
                          </Button>
                        </div>
                      </Form>
                      <div className="mt-3">
                        <p className="mb-0  text-center">
                          Don't have an account??{" "}
                          {/* <Link to='/register'>Register</Link> */}
                          <NavLink
                            className=""
                            activeClassName="is-active"
                            to="/"
                            exact
                          >
                            Register
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

export default Login;
