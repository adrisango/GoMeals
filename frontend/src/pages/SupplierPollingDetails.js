import React, { useState, useEffect } from "react";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import ListGroup from "react-bootstrap/ListGroup";
import NavbarComponent from "../components/NavbarComponent";
import "../styles/SupplierPollingDetails.css";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function SupplierPollingDetails() {
  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");
  const [polls, setPolls] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetch(API_HEADER + "polling/get/allPolls/" + loggedInUser.supId)
      .then((res) => res.json())
      .then((value) => {
        setPolls(value);
        console.log(value);
      });
  }, [refresh]); // Add refresh as a dependency of useEffect

  function handlePollClose(pollId, pollDate) {
    try {
      fetch(
        API_HEADER + "meal-voting/get/most-voted-meal/" +
          pollId +
          "/" +
          loggedInUser.supId
      ).then((res) => {
        console.log();
        swal(
          "Polling for the date " +
            pollDate +
            " closed. The most voted meal would be highlighted."
        );
        setRefresh(!refresh); // Toggle refresh state to trigger a re-render
      });
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div className="row">
      {/* <NavbarComponent /> */}
      <h2 id="pollingDetailsHeader">Polling Details</h2>
      <div id="pollingDetails">
        {polls.map((poll) => (
          <Card style={{ width: "18rem" }}>
            {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
            <Card.Body>
              <Card.Title>Polling for date: {poll.pollDate}</Card.Title>
              <Card.Text>
                <ListGroup>
                  <ListGroup.Item
                    variant={poll.item1 === poll.vote ? "primary" : ""}
                  >
                    {poll.item1}
                  </ListGroup.Item>
                  <ListGroup.Item
                    variant={poll.item2 === poll.vote ? "primary" : ""}
                  >
                    {poll.item2}
                  </ListGroup.Item>
                  <ListGroup.Item
                    variant={poll.item3 === poll.vote ? "primary" : ""}
                  >
                    {poll.item3}
                  </ListGroup.Item>
                  <ListGroup.Item
                    variant={poll.item4 === poll.vote ? "primary" : ""}
                  >
                    {poll.item4}
                  </ListGroup.Item>
                  <ListGroup.Item
                    variant={poll.item5 === poll.vote ? "primary" : ""}
                  >
                    {poll.item5}
                  </ListGroup.Item>
                </ListGroup>
              </Card.Text>
              {poll.status === true ? (
                <Button
                  variant="primary"
                  onClick={() => handlePollClose(poll.pollId, poll.pollDate)}
                >
                  Close
                </Button>
              ) : (
                <></>
              )}
            </Card.Body>
          </Card>
        ))}
      </div>
    </div>
  );
}

export default SupplierPollingDetails;
