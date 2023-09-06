import React, { useEffect, useState } from "react";
import { Button, Card, Container, Nav, Navbar } from "react-bootstrap";
import axios from "axios";
import food from "../resources/food.jpg";
import { Modal } from "react-bootstrap";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import Dropdown from "react-bootstrap/Dropdown";
import HeroComponent from "../components/HeroComponent";
import { API_HEADER } from "../utils.js";

export default function CustomerDashboard(children, func) {
  const [data, setdata] = useState({});
  const [datamon, setdatamon] = useState({});
  const [datatue, setdatatue] = useState({});
  const [datawed, setdatawed] = useState({});
  const [datathu, setdatathu] = useState({});
  const [datafri, setdatafri] = useState({});
  const [datasat, setdatasat] = useState({});
  const [datasun, setdatasun] = useState({});
  const [show, setShowModal] = useState(false);
  const [mealchart, setMealChart] = useState({});
  const [supName, setSupName] = useState("");
  const [supId, setSupId] = useState("");
  const [clicked, setClicked] = useState(false);
  const [filter, setFilter] = useState("");
  const [s4list, sets4List] = useState();
  const [s3list, sets3List] = useState();
  const [loggedInUser, setLoggedInUser] = useState(null);
  const fetchdata = (param) => {
    axios
      .get(API_HEADER + "supplierReview/get/4us")
      .then((response) => {
        // console.log(response.data)
        sets4List(response.data);
      });
    axios
      .get(API_HEADER + "supplierReview/get/3us")
      .then((response) => {
        // console.log(response.data)
        sets3List(response.data);
      });

    console.log(param);
    axios.get(API_HEADER + "supplier/get/all").then((response) => {
      if (param === "normal") {
        setdata(response.data);
      }
      if (param === "lth") {
        setdata(response.data.sort((a, b) => a.mealPrice - b.mealPrice));
      }
      if (param === "htl") {
        setdata(response.data.sort((a, b) => b.mealPrice - a.mealPrice));
      }
      if (param === "4ustar") {
        const filteredData = response.data.filter((obj) =>
          s4list.includes(obj.supId)
        );
        setdata(filteredData);
      }
      if (param === "3ustar") {
        const filteredData1 = response.data.filter((obj) =>
          s3list.includes(obj.supId)
        );
        setdata(filteredData1);
      }
    });
  };
  const navigate = useNavigate();
  const handleNavigate = () => {
    navigate("/supplierProfile", {
      state: {
        id: supId,
        cname: loggedInUser.cust_fname,
        cusId: loggedInUser.cust_id,
      },
    });
    console.log(loggedInUser);
  };
  const handleFilter = (param) => {
    console.log(param);
  };

  useEffect(() => {
    fetchdata("normal");
    const cookies = new Cookies();
    const user = cookies.get("loggedInUser");
    setLoggedInUser(user);
  }, []);

  const handleClick = (param, e) => {
    const id = param;
    axios.get(`${API_HEADER}supplier/get/${id}`).then((response) => {
      setSupName(response.data);
      setSupId(id);
    });
    console.log(loggedInUser.cust_id);
    const data1 = { day: "monday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data1)
      .then((response) => {
        setdatamon(response.data);
      });
    const data2 = { day: "tuesday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data2)
      .then((response) => {
        setdatatue(response.data);
      });
    const data3 = { day: "wednesday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data3)
      .then((response) => {
        setdatawed(response.data);
      });
    const data4 = { day: "thursday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data4)
      .then((response) => {
        setdatathu(response.data);
      });
    const data5 = { day: "friday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data5)
      .then((response) => {
        setdatafri(response.data);
      });
    const data6 = { day: "saturday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data6)
      .then((response) => {
        setdatasat(response.data);
      });
    const data7 = { day: "sunday", supId: param };
    axios
      .post(API_HEADER + "meal_chart/get", data7)
      .then((response) => {
        setdatasun(response.data);
      });
    console.log(param);
    setShowModal(!show);
  };
  const handleClose = () => {
    setShowModal(false);
  };
  return (
    <div>
      {/* <Navbar bg="primary" variant="light">
                <Container>
                    <Navbar.Brand href="#home">Go Meals</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                        <Nav.Link href="#features">Profile</Nav.Link>
                        <Nav.Link href="#pricing">Customers</Nav.Link>
                    </Nav>
                </Container>
            </Navbar> */}
      {/* <NavbarComponent /> */}
      <HeroComponent
        header="Welcome to Go Meals"
        body="Delivering deliciousness – one tiffin at a time!"
      />
      <h3 className="titleVendor">List of available Vendors</h3>
      <hr id="heroLine" />

      <Dropdown onClick={() => setClicked(true)}>
        <Dropdown.Toggle
          variant="success"
          className="filterButton"
          id="dropdown-basic"
        >
          Filter
        </Dropdown.Toggle>


        <Dropdown.Menu>
          <Dropdown.Item onClick={() => fetchdata("lth")}>
            <b>Price :</b> Low to high
          </Dropdown.Item>
          <Dropdown.Item onClick={() => fetchdata("htl")}>
            <b>Price :</b> High to low
          </Dropdown.Item>
          <Dropdown.Item onClick={() => fetchdata("4ustar")}>
            <b>Rating Average :</b> Above 4 Stars
          </Dropdown.Item>
          <Dropdown.Item onClick={() => fetchdata("3ustar")}>
            <b>Rating Average :</b> Above 3 Stars
          </Dropdown.Item>
          <Dropdown.Item onClick={() => fetchdata("normal")}>
            <b>All :</b> Suppliers
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
      <div className="containers" id="listSuppliers">
        <div className="card-containers" id="listSuppliersCardContainer">
          {Object.keys(data).map((key) => (
            <Card style={{ width: "18rem" }} className="mx-2 my-2">
              <Card.Img variant="top" src={food} />
              <Card.Body>
                <Card.Title>{data[key].supName}</Card.Title>

                <Card.Text>
                  <b> Meal Price: </b> {data[key].mealPrice} <b>$</b>
                </Card.Text>

                <Card.Text>
                  <b> Address : </b>
                  {data[key].supAddress}
                </Card.Text>
                <Button
                  variant="primary"
                  onClick={handleClick.bind(this, data[key].supId)}
                >
                  Details
                </Button>
              </Card.Body>
            </Card>
          ))}
        </div>
      </div>
      {show && (
        <Modal size="xl" show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>
              {supName.supName} is offering the following Meal plan
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <h3>
              Monday: {datamon.item1}, {datamon.item2}, {datamon.item3},{" "}
              {datamon.item4}, {datamon.item5}
            </h3>
            <br />
            <h3>
              Tuesday: {datatue.item1}, {datatue.item2}, {datatue.item3},{" "}
              {datatue.item4}, {datatue.item5}
            </h3>
            <br />
            <h3>
              Wednesday: {datawed.item1}, {datawed.item2}, {datawed.item3},{" "}
              {datawed.item4}, {datawed.item5},{" "}
            </h3>
            <br />
            <h3>
              Thursday: {datathu.item1}, {datathu.item2}, {datathu.item3},{" "}
              {datathu.item4}, {datathu.item5},{" "}
            </h3>
            <br />
            <h3>
              Friday: {datafri.item1}, {datafri.item2}, {datafri.item3},{" "}
              {datafri.item4}, {datafri.item5},{" "}
            </h3>
            <br />
            <h3>
              Saturday: {datasat.item1}, {datasat.item2}, {datasat.item3},{" "}
              {datasat.item4}, {datasat.item5},{" "}
            </h3>
            <br />
            <h3>
              Sunday: {datasun.item1}, {datasun.item2}, {datasun.item3},{" "}
              {datasun.item4}, {datasun.item5},{" "}
            </h3>
            <br />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleNavigate}>
              Subscribe
            </Button>
            <Button variant="secondary" onClick={handleClick}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      )}
      {/* <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br />
      <br /> */}

      {/*<Navbar bg="primary" variant="light" className="justify-content-center align-items-center" >*/}
      {/*  <h3>©Go Meals</h3>*/}
      {/*</Navbar>*/}
    </div>
  );
}