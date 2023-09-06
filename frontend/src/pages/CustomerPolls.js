import React, { useEffect, useState } from "react";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { Button, Card } from "react-bootstrap";
import meal from "../resources/foodplate.jpg";
import CustomerPolling from "./CustomerPolling";
import NavbarComponent from "../components/NavbarComponent";
import "../styles/CustomerPolls.css";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function CustomerPolls() {
  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");
  const [listOfSuppliers, setListOfSuppliers] = useState([]);
  const [pollingList, setPollingList] = useState([]);
  const navigate = useNavigate();
  const [selectedPoll, setSelectedPoll] = useState(null);
  const [modalShow, setModalShow] = useState(false);

  useEffect(() => {
    console.log("Use Effect called");
    fetch(
      API_HEADER + "subscription/get/customersSubscriptions/" +
        loggedInUser.cust_id
    )
      .then((res) => res.json())
      .then((listOfSuppliersData) => {
        if (listOfSuppliersData.length === 0) {
          swal("No subscriptions available");
          navigate("/dashboard");
        } else {
          setListOfSuppliers(listOfSuppliersData);
          getPollingList(listOfSuppliersData);
        }
      });
  }, []);

  function getPollingList(suppliers) {
    let url = API_HEADER + "polling/get/activePolls/";
    suppliers.forEach((value) => {
      url = url + value + ",";
    });
    console.log("Final URL is " + url.substring(0, url.lastIndexOf(",")));
    fetch(url.substring(0, url.lastIndexOf(",")))
      .then((res) => res.json())
      .then((pollingListData) => {
        setPollingList(pollingListData);
        console.log(pollingListData);
      });
  }

  const handleVoteClick = (poll) => {
    setSelectedPoll(poll);

    fetch(
      API_HEADER + "meal-voting/get/" +
        poll.pollId +
        "/" +
        loggedInUser.cust_id
    )
      .then((res) => {
        console.log(res);
        if (!res.ok) {
          throw new Error("Network response was not ok");
        } else if (res.status === 204) {
          setModalShow(true);
        } else {
          swal("You have already voted for this meal");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const handleModalClose = () => {
    setSelectedPoll(null);
    setModalShow(false);
  };

  return (
    <div>
      {/* <NavbarComponent /> */}
      <h2 id="customerPollsHeader">Special Meal Vote</h2>
      <div id="customerPolls">
        {pollingList.map((poll) => (
          <Card
            style={{ width: "18rem" }}
            key={poll.pollId}
            class="border-1 col-3 m-5"
          >
            <Card.Img variant="top" src={meal} />
            <Card.Body>
              <Card.Text>
                Special Meal rating available for the date {poll.pollDate}
              </Card.Text>
              <Button onClick={() => handleVoteClick(poll)}>Vote</Button>
            </Card.Body>
          </Card>
        ))}
        {selectedPoll && (
          <CustomerPolling
            poll={selectedPoll}
            show={modalShow}
            onHide={handleModalClose}
          />
        )}
      </div>
    </div>
  );
}

export default CustomerPolls;
