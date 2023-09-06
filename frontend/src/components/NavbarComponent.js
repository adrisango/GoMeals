import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { Cookies } from "react-cookie";
import { useLocation } from "react-router-dom";

import {
  Container,
  Dropdown,
  DropdownButton,
  Nav,
  Navbar,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBell,
  faUserCircle,
  faSignOutAlt,
} from "@fortawesome/free-solid-svg-icons";
import foodcart from "../resources/shopping-cart.png";
import "../styles/Navbar.css";
import Notification from "./Notification";

export default function NavbarComponent() {
  const cookies = new Cookies();
  const navigate = useNavigate();

  const loggedInUser = cookies.get("loggedInUser");
  console.log(loggedInUser);
  const [showNotifications, setShowNotifications] = useState(false);

  const toggleNotifications = () => {
    setShowNotifications(!showNotifications);
  };

  const logout = () => {
    cookies.remove("loggedInUser");
    navigate("/");
  };

  var customerUser = "";
  var supplierUser = "";
  if (loggedInUser) {
    customerUser = loggedInUser.userType === "customer";
    supplierUser = loggedInUser.userType === "supplier";
  }
  // const handleProfile = () => {};
  const getProfileName = () => {
    //boolean to see which type of user has logged in
    if (customerUser) {
      return loggedInUser.cust_fname + " " + loggedInUser.cust_lname;
    } else if (supplierUser) {
      return loggedInUser.supName;
    }
  };

  function handleProfile() {
    if (loggedInUser.userType === "customer") {
      navigate("/customerProfile");
    } else if (loggedInUser.userType === "supplier") {
      navigate("/supplierDashboard");
    }
  }

  function handlePollVote() {
    navigate("/customerPollVote");
  }

  function handleSubscirptionRequests() {
    navigate("/subscriptionRequests");
  }

  function handlePaymentHistory() {
    navigate("/customerPaymentHistory");
  }

  function handleOrders() {
    navigate("/customerOrders");
  }

  function handleComplain() {
    navigate("/complainTracker");
  }

  function handleSupplierComplain() {
    navigate("/supplierComplain");
  }

  function handleSupplierPolling() {
    navigate("/supplierPolling");
  }

  function handleSupplierPollingDetails() {
    navigate("/supplierPollingDetails");
  }

  function handleHomePage() {
    navigate(
      loggedInUser.userType === "supplier" ? "/supplierDashboard" : "/dashboard"
    );
  }

  const location = useLocation();
  const hideNavBar =
    location.pathname === "/login" ||
    location.pathname === "/register" ||
    location.pathname === "/" ||
    location.pathname === "/Supplierlogin" ||
    location.pathname === "/supplierRegister";

  if (hideNavBar) {
    return null;
  } else {
    return (
      <>
        <Navbar bg="primary" variant="light">
          <Container
            style={{ display: "flex", justifyContent: "space-between" }}
          >
            <Navbar.Brand
              // href={
              //   loggedInUser.userType === "supplier"
              //     ? "/supplierDashboard"
              //     : "/dashboard"
              // }
              onClick={handleHomePage}
            >
              <img
                src={foodcart}
                id="foodCart"
                width="30px"
                height="30px"
              ></img>
              Go Meals
            </Navbar.Brand>
            <div>
              <Nav className="me-auto">
                <Nav.Link
                  // href={
                  //   loggedInUser.userType === "supplier"
                  //     ? "/supplierDashboard"
                  //     : "/dashboard"
                  // }
                  onClick={handleHomePage}
                >
                  Home
                </Nav.Link>
                {/* <Nav.Link href="#features">Profile</Nav.Link> */}
                <Nav.Link onClick={toggleNotifications}>
                  <FontAwesomeIcon icon={faBell} />
                  {showNotifications && <Notification {...loggedInUser} />}
                </Nav.Link>

                <div className="navbar-icons">
                  <Dropdown>
                    <Dropdown.Toggle variant="primary" id="dropdown-basic">
                      {getProfileName()}
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                      <Dropdown.Item onClick={handleProfile}>
                        Profile
                      </Dropdown.Item>
                      {loggedInUser.userType === "supplier" ? (
                        <>
                          {/* <Dropdown.Item>Customers</Dropdown.Item> */}
                          <Dropdown.Item onClick={handleSupplierComplain}>
                            Complains
                          </Dropdown.Item>
                          <Dropdown.Item onClick={handleSupplierPolling}>
                            Polling
                          </Dropdown.Item>
                          <Dropdown.Item onClick={handleSupplierPollingDetails}>
                            Polling Details
                          </Dropdown.Item>
                          <Dropdown.Item onClick={handleSubscirptionRequests}>
                            Subscription Requests
                          </Dropdown.Item>
                        </>
                      ) : (
                        <>
                          <Dropdown.Item onClick={handleComplain}>
                            Complain
                          </Dropdown.Item>
                          <Dropdown.Item onClick={handleOrders}>
                            Orders
                          </Dropdown.Item>
                          <Dropdown.Item onClick={handlePaymentHistory}>
                            Payment History
                          </Dropdown.Item>
                          <Dropdown.Item onClick={handlePollVote}>
                            Meal Poll
                          </Dropdown.Item>
                        </>
                      )}
                      <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
                    </Dropdown.Menu>
                  </Dropdown>
                </div>
              </Nav>
            </div>
          </Container>
        </Navbar>
      </>
    );
  }
}
