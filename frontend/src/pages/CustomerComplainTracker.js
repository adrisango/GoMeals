import { React, useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import ComplainCard from "./CustomerComplainCard";
import NavbarComponent from "../components/NavbarComponent";
import "../styles/CustomerComplainTracker.css";
import { API_HEADER } from "../utils.js";

function CustomerComplainTracker() {
  const cookies = new Cookies();
  const [complainList, setComplainList] = useState([]);
  const loggedInUser = cookies.get("loggedInUser");
  const [deliveries, setDeliveries] = useState([]);

  const navigate = useNavigate();
  useEffect(() => {
    fetch(
      API_HEADER + "complain/get/all-customer/" + loggedInUser.cust_id
    )
      .then((res) => res.json())
      .then((listOfComplains) => {
        setComplainList(listOfComplains);
        console.log(listOfComplains);
        fetch(
          API_HEADER + "delivery/get/customer/" + loggedInUser.cust_id
        )
          .then((res) => res.json())
          .then((deliveryData) => {
            setDeliveries(deliveryData);
            console.log(deliveryData);
          });
      });
  }, []);

  return (
    <div>
      {/* <NavbarComponent /> */}
      <h2 id="customerComplainHeader"> Complain Portal </h2>
      <div id="pollingDetails">
        {complainList.map((complain) =>
          deliveries.map((delivery) =>
            complain.deliveryId === delivery.deliveryId ? (
              <ComplainCard key={complain.complainId} complain={complain} delivery={delivery} />
            ) : (
              <></>
            )
          )
        )}
      </div>
    </div>
  );
}

export default CustomerComplainTracker;
