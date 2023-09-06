import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import mealBox from "../resources/meal-box.jpg";
import { NavLink } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { Container } from "react-bootstrap";
import { Cookies } from "react-cookie";
import NavbarComponent from "../components/NavbarComponent";
import HeroComponent from "../components/HeroComponent";
import { API_HEADER } from "../utils.js";

const CustomerDeliveries = ({ history }) => {
  const cookies = new Cookies();
  const [customerOrders, setCustomerOrders] = useState([]);
  const [complainDeliveryId, setComplainDeliveryId] = useState(0);
  const navigate = useNavigate();
  const loggedInUser = cookies.get("loggedInUser");
  const images = [
    "https://example.com/image1.jpg",
    "https://example.com/image2.jpg",
    "https://example.com/image3.jpg",
    "https://example.com/image4.jpg",
    "https://example.com/image5.jpg",
  ];

  useEffect(() => {
    fetch(API_HEADER + "delivery/get/customer/" + loggedInUser.cust_id)
      .then((res) => res.json())
      .then((val) => setCustomerOrders(val))
      .then(() => {
        console.log(customerOrders);
      });
  }, []);

  const handleClick = (deliveryId) => {
    setComplainDeliveryId(deliveryId);
    navigate(`/customerRaiseComplain/${deliveryId}`);
  };

  return (
    <div>
      {/* <NavbarComponent /> */}
      <h2 id="customerPollsHeader">Your Orders</h2>
      <div className="orderCard ">
        <Container>
          {customerOrders.length === 0 ? (
            <div>No Orders available</div>
          ) : (
            <div class="m-3 row">
              {customerOrders.map((customerOrder) => (
                <Card
                  style={{ width: "18rem" }}
                  key={customerOrder.deliveryId}
                  class="border-1 col-4 p-3 m-3"
                >
                  <Card.Img variant="top" src={mealBox} />
                  <Card.Body>
                    <Card.Title>{customerOrder.deliveryMeal}</Card.Title>
                    <Card.Text>
                      Delivery Id : {customerOrder.deliveryId}
                      <br />
                      Delivery Date : {customerOrder.deliveryDate}
                      <br />
                      Delivery Status : {customerOrder.orderStatus}
                    </Card.Text>
                    <Button
                      onClick={() => handleClick(customerOrder.deliveryId)}
                    >
                      Raise Complain
                    </Button>
                  </Card.Body>
                </Card>
              ))}
            </div>
          )}
        </Container>
      </div>
    </div>
  );
};

export default CustomerDeliveries;
