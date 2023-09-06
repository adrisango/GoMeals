import { React, useState, useEffect } from "react";
import { Col, Button, Row, Container, Card, Form } from "react-bootstrap";
import { Cookies } from "react-cookie";
import "react-calendar/dist/Calendar.css";
import { useNavigate } from "react-router-dom";
import NavbarComponent from "../components/NavbarComponent";
import "../styles/SupplierPolling.css";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function SupplierPolling() {
  const cookies = new Cookies();
  const [item1, setItem1] = useState({});
  const [item2, setItem2] = useState({});
  const [item3, setItem3] = useState({});
  const [item4, setItem4] = useState({});
  const [item5, setItem5] = useState({});
  const loggedInUser = cookies.get("loggedInUser");
  const [supplierPollingList, setSupplierPollingList] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const navigate = useNavigate();
  const minDate = new Date().toISOString().slice(0, 10); // get current date as yyyy-mm-dd
  const maxDate = new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)
    .toISOString()
    .slice(0, 10);

  useEffect(() => {
    console.log("useEffect");
    fetch(API_HEADER + "polling/get/allPolls/" + loggedInUser.supId)
      .then((res) => res.json())
      .then((supplierPollingList) => {
        setSupplierPollingList(supplierPollingList);
        console.log(supplierPollingList);
      });
  }, []);

  function handleSubmit(event) {
    event.preventDefault();

    try {
      if (Array.isArray(supplierPollingList)) {
        console.log("Requested Poll Date :" + selectedDate);
        supplierPollingList.forEach(function (poll) {
          console.log("pollDate" + poll.pollDate);
          if (poll.pollDate === selectedDate) {
            swal("A poll for this date has already been raised.");
            throw new Error("Complain already raised for this delivery");
          } else {
            console.log(poll.pollDate + " : " + selectedDate);
          }
        });
        // if (validateMealPolling()) {
        submitPolling();
        // }
      }
    } catch (error) {
      console.log(error);
    }
  }
  const validateMealPolling = (pollingObject) => {
    console.log("In here");
    console.log(selectedDate);
    if (selectedDate === null) {
      swal("Please enter the date to raise a poll.");
      return false;
    } else if (
      Object.keys(item1).length === 0 ||
      Object.keys(item2).length === 0 ||
      Object.keys(item3).length === 0 ||
      Object.keys(item4).length === 0 ||
      Object.keys(item5).length === 0
    ) {
      swal("Please provide all the meals to raise a voting");
      return false;
    }
    return true;
  };

  function submitPolling() {
    let pollingObject = {
      pollId: null,
      pollDate: selectedDate,
      item1: item1,
      item2: item2,
      item3: item3,
      item4: item4,
      item5: item5,
      supId: loggedInUser.supId,
      status: 1,
    };
    // console.log();
    if (validateMealPolling(pollingObject)) {
      console.log(pollingObject);
      let polling = JSON.stringify(pollingObject);
      console.log(
        "The final polling data going to the backend is ::" + polling
      );
      console.log("Polling object received is ::" + polling);
      fetch(API_HEADER + "polling/create", {
        method: "POST",
        headers: { "content-type": "application/json" },
        body: polling,
      })
        .then((res) => {
          console.log("Response received is :" + res.json());
          res.json();
        })
        .then((val) => {
          console.log("value" + val);
          swal(`Polling created for the date ${selectedDate} successfully! `);
          navigate("/supplierDashboard");
        });
    }
  }
  const handleDateChange = (e) => {
    setSelectedDate(e.target.value);
  };
  return (
    <div id="supplierPolling">
      {/* <NavbarComponent /> */}
      <div className="d-flex justify-content-center align-items-center">
        <Card className="shadow px-4 polling-card">
          <Card.Body>
            <div className="mb-3 mt-md-4">
              <h2
                className="fw-bold mb-2 text-center text-uppercase "
                id="pollingHeader"
              >
                Meal Polling
              </h2>
              <div className="mb-3">
                <Form onSubmit={handleSubmit}>
                  <input
                    type="date"
                    value={selectedDate}
                    onChange={handleDateChange}
                    min={minDate}
                    max={maxDate}
                    id="pollingDatePicker"
                  />
                  <Form.Group className="mb-3" controlId="Item1">
                    <Form.Label>Meal Option 1 :</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter 1st meal"
                      onChange={(e) => setItem1(e.target.value)}
                    />
                  </Form.Group>

                  <Form.Group className="mb-3" controlId="Item2">
                    <Form.Label>Meal Option 2 :</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter 2nd meal"
                      onChange={(e) => setItem2(e.target.value)}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="Item3">
                    <Form.Label>Meal Option 3 :</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter 3rd meal"
                      onChange={(e) => setItem3(e.target.value)}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="Item4">
                    <Form.Label>Meal Option 4 :</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter 4th meal"
                      onChange={(e) => setItem4(e.target.value)}
                    />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="Item5">
                    <Form.Label>Meal Option 5 :</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Enter 5th meal"
                      onChange={(e) => setItem5(e.target.value)}
                    />
                  </Form.Group>
                  <Button variant="primary" type="Submit">
                    Create Poll
                  </Button>
                </Form>
              </div>
            </div>
          </Card.Body>
        </Card>
      </div>
    </div>
  );
}

export default SupplierPolling;
