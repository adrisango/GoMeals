import React, { useState, useEffect, useRef } from "react";
import Nav from "react-bootstrap/Nav";
import {
  Button,
  Card,
  FormGroup,
  Container,
  Navbar,
  Spinner,
  Modal,
  Form,
  DropdownButton,
  Dropdown,
  Table,
} from "react-bootstrap";
import axios from "axios";
import { Label, Input } from "reactstrap";
import CustomerList from "./CustomerList";
import { Cookies } from "react-cookie";
import NavbarComponent from "../components/NavbarComponent";
import swal from "sweetalert";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import { API_HEADER } from "../utils.js";

export default function SupplierDashboard() {
  const [alter, alterstate] = useState("create");
  const [addOnData, setAddOnData] = useState([]);
  const [addOnEdit, setAddOnEdit] = useState(false);
  const [datamon, setdatamon] = useState({});
  const [datatue, setdatatue] = useState({});
  const [datawed, setdatawed] = useState({});
  const [datathu, setdatathu] = useState({});
  const [datafri, setdatafri] = useState({});
  const [datasat, setdatasat] = useState({});
  const [datasun, setdatasun] = useState({});
  const [mealchart, showmealchart] = useState(false);
  const [addOns, showAddOns] = useState(false);
  const [addOnAlter, showAddOnAlter] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [showCustomerList, setShowCustomerList] = useState(false);
  const [customerList, setCustomerList] = useState([]);
  const [subscriptionList, setSubscriptionList] = useState([]);
  const [deliveryData, setDeliveryData] = useState([]);
  const [showDelivery, setshowDelivery] = useState(true);
  const [isCustomerListLoading, setIsCustomerListLoading] = useState(false);
  const [currentSupplier, setCurrentSupplier] = useState({});
  const [editProfile, setEditProfile] = useState(false);
  const [supProfile, showSupProfile] = useState(true);
  const [updateSupplierData, setUpdateSupplierData] = useState(false);
  const [showDeliveryTable, setShowDeliveryTable] = useState(false);
  const [mealChartEdit, showMealChartEdit] = useState(false);
  const [currentChart, showCurrentChart] = useState(false);
  const [editedSupplierDetail, setEditedSupplierDetail] = useState({
    supEmail: "",
    supContactNumber: "",
    supAddress: "",
    paypalLink: "",
  });
  const [value, setValue] = React.useState("1");

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");
  console.log("logged" + loggedInUser);
  const handleca = () => {
    showAddOns(!addOns);
    showAddOnAlter(!addOnAlter);
  };

  const handleClick = (param) => {
    showmealchart(!mealchart);
    showCurrentChart(!currentChart);
    setShowCustomerList(false);
    setshowDelivery(false);
    alterstate(param);
  };

  const handleDelivery = () => {
    setAddOnEdit(false);
    showAddOnAlter(false);
    showAddOns(false);
    showSupProfile(false);
    showmealchart(false);
    setShowDeliveryTable(true);
    setShowCustomerList(false);
    // setshowDelivery(!showDelivery);
    setShowCustomerList(false);
    showmealchart(false);
    setshowDelivery(true);
    setShowCustomerList(false);
    showmealchart(false);
    // axios
    //   .get(`API_HEADERsubscription/get/sup/${loggedInUser.supId}`)
    //   .then((response) => {
    //     response.data.forEach((custId) => {
    //       console.log(custId);
    //       const delivery = {
    //         deliveryId: 8,
    //         deliveryDate: "",
    //         deliveryMeal: "",
    //         orderStatus: "inprogress",
    //         supId: loggedInUser.supId,
    //         custId: custId,
    //       };
    //       console.log(delivery);
    //       axios
    //         .post("API_HEADERdelivery/create", delivery)
    //         .then((res) => {
    //           swal("Deliveries initiated");
    axios
      .get(`${API_HEADER}subscription/get/sup/${loggedInUser.supId}`)
      .then((response) => {
        response.data.forEach((custId) => {
          console.log(custId);
          const delivery = {
            deliveryId: 8,
            deliveryDate: "",
            deliveryMeal: "",
            orderStatus: "inprogress",
            supId: loggedInUser.supId,
            custId: custId,
          };
          console.log(delivery);
          axios
            .post(API_HEADER + "delivery/create", delivery)
            .then((res) => {
              swal("Deliveries initiated");
              const currentDate = new Date();
              currentDate.setDate(currentDate.getDate() + 1);
              const year = currentDate.getFullYear();
              const month = ("0" + (currentDate.getMonth() + 1)).slice(-2);
              const day = ("0" + currentDate.getDate()).slice(-2);

              axios.post(
                `${API_HEADER}customer-notification/create-all/?message=${loggedInUser.supName} has initiated delivery for ${year}-${month}-${day}&type=delivery&supplierId=${loggedInUser.supId}`
              );
              window.location.reload();
            })
            .catch(swal("Deliveries existing"));
        });
      });
    axios
      .get(`${API_HEADER}delivery/get/supplier/${loggedInUser.supId}`)
      .then((response) => {
        setDeliveryData(response.data);
        console.log("getttt");
        console.log(response.data);
      });
  };

  function handleShowCustomers() {
    showCurrentChart(false);
    showMealChartEdit(false);
    showAddOns(false);
    showSupProfile(false);
    showmealchart(false);
    setShowDeliveryTable(false);
    setShowCustomerList(true);
    setshowDelivery(false);
    setIsCustomerListLoading(true);
    console.log("supId" + JSON.stringify(loggedInUser));
    axios
      .get(`${API_HEADER}supplier/get/${loggedInUser.supId}`)
      .then((response) => {
        setCustomerList(() => {
          return response.data.customers;
        });
        setSubscriptionList(response.data.subscriptions);
      })
      .catch((e) => {
        swal("Error getting data" + e);
      })
      .finally(() => {
        setIsCustomerListLoading(false);
      });
    // setShowCustomerList((prevValue) => {
    //     return !prevValue;
    // });
  }

  const handleAddon = () => {
    const addOn1 = {
      addonId: "",
      item: document.getElementById("addon1").value,
      price: document.getElementById("price1").value,
      supplierId: loggedInUser.supId,
    };
    const addOn2 = {
      addonId: "",
      item: document.getElementById("addon2").value,
      price: document.getElementById("price2").value,
      supplierId: loggedInUser.supId,
    };
    const addOn3 = {
      addonId: "",
      item: document.getElementById("addon3").value,
      price: document.getElementById("price3").value,
      supplierId: loggedInUser.supId,
    };
    axios
      .get(`${API_HEADER}Addons/get/all-supplier/${loggedInUser.supId}`)
      .then((response) => {
        console.log(response.data.length);
        if (response.data.length >= 3) {
          swal("You allready have 3 Add-ons");
        }
        if (response.data.length === 0) {
          axios
            .post(API_HEADER + "Addons/create", addOn1)
            .then((response) => {
              console.log(response.data);
              // navigate("/supplierDashboard");
            })
            .catch((error) => {
              console.log(error);
            });
          axios
            .post(API_HEADER + "Addons/create", addOn2)
            .then((response) => {
              console.log(response.data);
              // navigate("/supplierDashboard");
            })
            .catch((error) => {
              console.log(error);
            });
          axios
            .post(API_HEADER + "Addons/create", addOn3)
            .then((response) => {
              console.log(response.data);
              swal("Add ons successfully added!");
              // navigate("/supplierDashboard");
              window.location.reload();
            })
            .catch((error) => {
              console.log(error);
              swal("Error uploading new Add ons");
            });
        }
      });
  };
  const handleCreate = () => {
    const mealChart = [
      {
        id: {
          day: "monday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("monday1").value,
        item2: document.getElementById("monday2").value,
        item3: document.getElementById("monday3").value,
        item4: document.getElementById("monday4").value,
        item5: document.getElementById("monday5").value,
        specialDate: "2022-02-01",
      },
      {
        id: {
          day: "tuesday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("tuesday1").value,
        item2: document.getElementById("tuesday2").value,
        item3: document.getElementById("tuesday3").value,
        item4: document.getElementById("tuesday4").value,
        item5: document.getElementById("tuesday5").value,
        specialDate: "2022-02-01",
      },
      {
        id: {
          day: "wednesday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("wednesday1").value,
        item2: document.getElementById("wednesday2").value,
        item3: document.getElementById("wednesday3").value,
        item4: document.getElementById("wednesday4").value,
        item5: document.getElementById("wednesday5").value,
        specialDate: "2022-02-01",
      },
      {
        id: {
          day: "thursday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("thursday1").value,
        item2: document.getElementById("thursday2").value,
        item3: document.getElementById("thursday3").value,
        item4: document.getElementById("thursday4").value,
        item5: document.getElementById("thursday5").value,
        specialDate: "2022-02-01",
      },
      {
        id: {
          day: "friday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("friday1").value,
        item2: document.getElementById("friday2").value,
        item3: document.getElementById("friday3").value,
        item4: document.getElementById("friday4").value,
        item5: document.getElementById("friday5").value,
        specialDate: "2022-02-01",
      },
      {
        id: {
          day: "saturday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("saturday1").value,
        item2: document.getElementById("saturday2").value,
        item3: document.getElementById("saturday3").value,
        item4: document.getElementById("saturday4").value,
        item5: document.getElementById("saturday5").value,
        specialDate: "2022-02-01",
      },
      {
        id: {
          day: "sunday",
          supId: loggedInUser.supId,
        },
        item1: document.getElementById("sunday1").value,
        item2: document.getElementById("sunday2").value,
        item3: document.getElementById("sunday3").value,
        item4: document.getElementById("sunday4").value,
        item5: document.getElementById("sunday5").value,
        specialDate: "2022-02-01",
      },
    ];
    // item1:
    if (alter === "create") {
      axios
        .post(API_HEADER + "meal_chart/create", mealChart)
        .then((response) => {
          console.log(response.data);
          swal("Meal Chart created.");
          // navigate("/supplierDashboard");
          window.location.reload();
        })
        .catch((error) => {
          console.log(error);
          swal("Data was not sent");
        });
    } else {
      axios
        .put(API_HEADER + "meal_chart/update", mealChart)
        .then((response) => {
          console.log(response.data);
          swal("Meal Chart updated.");
          // navigate("/supplierDashboard");
          window.location.reload();
        })
        .catch((error) => {
          console.log(error);
          swal("Data was not sent");
          console.log(mealChart);
        });
    }
  };

  const handleEditProfile = () => {
    setEditProfile(true);
    setEditedSupplierDetail({
      supEmail: currentSupplier.supEmail,
      supContactNumber: currentSupplier.supContactNumber,
      supAddress: currentSupplier.supAddress,
      paypalLink: currentSupplier.paypalLink,
    });
  };

  const handleEditingProfile = (event) => {
    const { name, value } = event.target;
    setEditedSupplierDetail((prevValue) => ({
      ...prevValue,
      [name]: value,
    }));
  };

  useEffect(() => {
    setIsLoading(true);
    axios
      .get(`${API_HEADER}supplier/get/${loggedInUser.supId}`)
      .then((response) => {
        setCurrentSupplier(response.data);
      })
      .catch((e) => {
        swal("Error getting data" + e);
      })
      .finally(() => {
        setIsLoading(false);
      });
    const data1 = { day: "monday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data1).then((response) => {
      setdatamon(response.data);
    });
    const data2 = { day: "tuesday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data2).then((response) => {
      setdatatue(response.data);
    });
    const data3 = { day: "wednesday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data3).then((response) => {
      setdatawed(response.data);
    });
    const data4 = { day: "thursday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data4).then((response) => {
      setdatathu(response.data);
    });
    const data5 = { day: "friday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data5).then((response) => {
      setdatafri(response.data);
    });
    const data6 = { day: "saturday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data6).then((response) => {
      setdatasat(response.data);
    });
    const data7 = { day: "sunday", supId: loggedInUser.supId };
    axios.post(API_HEADER + "meal_chart/get", data7).then((response) => {
      setdatasun(response.data);
    });
    axios
      .get(`${API_HEADER}Addons/get/all-supplier/${loggedInUser.supId}`)
      .then((response) => {
        setAddOnData(response.data);
      });
  }, [loggedInUser.supId]);

  useEffect(() => {
    if (updateSupplierData) {
      axios
        .put(API_HEADER + "supplier/update", currentSupplier)
        .catch((e) => {
          swal("Error getting data" + e);
        })
        .finally(() => {
          setIsLoading(false);
          setEditProfile(false);
        });
    }
  }, [updateSupplierData]);

  const updateSupplierProfile = () => {
    if (validateProfileInputs()) {
      //setIsLoading(true);
      console.log("before setting", JSON.stringify(editedSupplierDetail));
      setCurrentSupplier((prevValue) => ({
        ...prevValue,
        supEmail: editedSupplierDetail.supEmail,
        supContactNumber: editedSupplierDetail.supContactNumber,
        supAddress: editedSupplierDetail.supAddress,
        paypalLink: editedSupplierDetail.paypalLink,
      }));
      setUpdateSupplierData(true);
    }
  };

  const DeliveryColumns = [
    { header: "Customer ID", accessor: "custId" },
    { header: "Delivery ID", accessor: "deliveryId" },
    { header: "Delivery Date", accessor: "deliveryDate" },
    { header: "Meal", accessor: "deliveryMeal" },
    {
      header: "Order status",
      accessor: "orderStatus",
      Cell: ({ value, deliveryId }) => (
        <DropdownButton
          title={value}
          onSelect={(eventKey) => {
            handleOrderStatusChange(deliveryId, eventKey);
            value = "";
          }}
        >
          <Dropdown.Item eventKey="completed">completed</Dropdown.Item>
        </DropdownButton>
      ),
    },
  ];

  const handleOrderStatusChange = (deliveryId, orderStatus) => {
    axios
      .put(
        `${API_HEADER}delivery/update-status/?deliveryId=${deliveryId}&orderStatus=${orderStatus}`
      )
      .then((response) => {
        console.log(response.data);
        window.location.reload();
      })
      .catch((error) => {
        console.error(error);
      });
  };
  const handleProfile = () => {
    setAddOnEdit(false);
    showAddOnAlter(false);
    showCurrentChart(false);
    showSupProfile(true);
    showmealchart(false);
    setShowDeliveryTable(false);
    showAddOns(false);
    setShowCustomerList(false);
    showMealChartEdit(false);
  };
  const handleMC = () => {
    setAddOnEdit(false);
    showAddOnAlter(false);
    showCurrentChart(true);
    showSupProfile(false);
    showmealchart(false);
    setShowDeliveryTable(false);
    showAddOns(false);
    setShowCustomerList(false);
    showMealChartEdit(true);
  };
  const handleAn = () => {
    setAddOnEdit(true);
    showAddOnAlter(false);
    showCurrentChart(false);
    showAddOns(true);
    showSupProfile(false);
    showmealchart(false);
    setShowDeliveryTable(false);
    setShowCustomerList(false);
    showMealChartEdit(false);
  };
  const handleDel = () => {
    setAddOnEdit(false);
    showAddOnAlter(false);
    showCurrentChart(false);
    showAddOns(false);
    showSupProfile(false);
    showmealchart(false);
    setShowDeliveryTable(true);
    setShowCustomerList(false);
    showMealChartEdit(false);
    axios
      .get(`${API_HEADER}delivery/get/supplier/${loggedInUser.supId}`)
      .then((response) => {
        setDeliveryData(response.data);
        console.log(response.data);
      });
  };

  const validateProfileInputs = () => {
    const regexForNumber = /^[0-9\b]+$/;
    const regexForEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (
      editedSupplierDetail.supEmail === "" ||
      editedSupplierDetail.supContactNumber === "" ||
      editedSupplierDetail.supAddress === "" ||
      editedSupplierDetail.paypalLink === ""
    ) {
      swal("Fields should not be empty.");
      return false;
    } else if (!regexForEmail.test(editedSupplierDetail.supEmail)) {
      swal("Please provide valid email.");
      return false;
    } else if (
      editedSupplierDetail.supContactNumber.length !== 10 ||
      !regexForNumber.test(editedSupplierDetail.supContactNumber)
    ) {
      swal("Please provide a valid contact number.");
      return false;
    } else if (!editedSupplierDetail.paypalLink.includes("paypal.com")) {
      swal("Please provide valid Paypal link.");
      return false;
    }
    return true;
  };

  function DeliveryTable(props) {
    return (
      showDeliveryTable && (
        <Table striped bordered hover>
          <thead>
            <tr>
              {props.columns.map((column) => (
                <th key={column.accessor}>{column.header}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {props.data.map((row) => {
              return (
                <tr key={row.cust_id}>
                  {props.columns.map((column) => (
                    <td key={column.accessor}>
                      {column.Cell ? (
                        <column.Cell
                          value={row[column.accessor]}
                          deliveryId={row.deliveryId}
                        />
                      ) : (
                        row[column.accessor]
                      )}
                    </td>
                  ))}
                </tr>
              );
              return null;
            })}
          </tbody>
        </Table>
      )
    );
  }

  return (
    <div className="supplier-dashboard">
      <Box sx={{ flex: 1, typography: 'body1' }}>
        <TabContext value={value}>
          <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
            <TabList onChange={handleChange} aria-label="lab API tabs example">
              <Tab
                label="Profile"
                value="1"
                onClick={handleProfile}
                sx={{ flexGrow: 1 }}
              />
              <Tab
                label="Meal Chart"
                value="2"
                onClick={handleMC}
                sx={{ flexGrow: 1 }}
              />
              <Tab
                label="Addons"
                value="3"
                onClick={handleAn}
                sx={{ flexGrow: 1 }}
              />
              <Tab
                label="Delivery"
                value="4"
                onClick={handleDel}
                sx={{ flexGrow: 1 }}
              />
              <Tab
                label="View Customers"
                value="5"
                onClick={handleShowCustomers}
                sx={{ flexGrow: 1 }}
              />
            </TabList>
          </Box>
          {/* <TabPanel value="1"></TabPanel>
          <TabPanel value="2"></TabPanel>
          <TabPanel value="3"></TabPanel> */}
        </TabContext>
      </Box>
      <h2>Welcome {loggedInUser.supName}</h2>
      <br />
      {addOnEdit && (
        <div>
          <Button variant="outline-primary" onClick={handleca}>
            Create Add-on
          </Button>{" "}
        </div>
      )}
      {mealChartEdit && (
        <div>
          <Button
            variant="outline-primary"
            onClick={() => handleClick("create")}
          >
            Create Meal Chart
          </Button>{" "}
          <Button
            variant="outline-primary"
            onClick={() => handleClick("update")}
          >
            Update Meal Chart
          </Button>{" "}
        </div>
      )}
      {/*<Button variant="outline-primary" onClick={handleca}>Create Meal Addons</Button>{' '}*/}
      {showDeliveryTable && (
        <div>
          <Button variant="outline-primary" onClick={handleDelivery}>
            Initiate Deliveries
          </Button>{" "}
        </div>
      )}
      {/*<Button variant="outline-primary" onClick={handleShowCustomers}>*/}
      {/*    View Customers*/}
      {/*</Button>*/}
      <Modal show={editProfile} onHide={() => setEditProfile(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Edit your profile</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="email">
              <Form.Label>Email address</Form.Label>
              <Form.Control
                type="email"
                placeholder="name@example.com"
                name="supEmail"
                value={editedSupplierDetail.supEmail}
                onChange={handleEditingProfile}
                autoFocus
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="contactNo">
              <Form.Label>Contact Number</Form.Label>
              <Form.Control
                type="text"
                placeholder="Your contact number"
                name="supContactNumber"
                value={editedSupplierDetail.supContactNumber}
                onChange={handleEditingProfile}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="address">
              <Form.Label>Address</Form.Label>
              <Form.Control
                as="textarea"
                rows={2}
                name="supAddress"
                value={editedSupplierDetail.supAddress}
                onChange={handleEditingProfile}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="paypalId">
              <Form.Label>Paypal Link</Form.Label>
              <Form.Control
                type="text"
                placeholder="Your contact number"
                name="paypalLink"
                value={editedSupplierDetail.paypalLink}
                onChange={handleEditingProfile}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setEditProfile(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={updateSupplierProfile}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
      {supProfile && (
        <div>
          {/* <NavbarComponent /> */}
          {isLoading ? (
            <Spinner />
          ) : (
            <div>
              <div className="container customer-profile my-4">
                <div className="col-lg-12">
                  <div className="d-flex justify-content-between align-items-center">
                    <div>
                      <h2 className="d-inline">Hi, </h2>
                      <h2 className="d-inline">{currentSupplier.supName}</h2>
                    </div>
                    <Button variant="dark" onClick={handleEditProfile}>
                      Edit Profile
                    </Button>
                  </div>
                </div>
                <div className="row mb-2 profile-item">
                  <div className="col-lg-12">
                    <h4 className="d-inline">Email</h4>
                    <hr></hr>
                  </div>
                  <div className="col-lg-12">
                    <h4 className="d-inline">{currentSupplier.supEmail}</h4>
                  </div>
                </div>
                <div className="row mb-2 profile-item">
                  <div className="col-lg-12">
                    <h4 className="d-inline">Contact number</h4>
                    <hr></hr>
                  </div>
                  <div className="col-lg-12">
                    <h4 className="d-inline">
                      {currentSupplier.supContactNumber}
                    </h4>
                  </div>
                </div>
                <div className="row mb-2 profile-item">
                  <div className="col-lg-12">
                    <h4 className="d-inline">Address</h4>
                    <hr></hr>
                  </div>
                  <div className="col-lg-12">
                    <h4 className="d-inline">{currentSupplier.supAddress}</h4>
                  </div>
                </div>
                <div className="row mb-2 profile-item">
                  <div className="col-lg-12">
                    <h4 className="d-inline">Paypal Link</h4>
                    <hr></hr>
                  </div>
                  <div className="col-lg-12">
                    <h4 className="d-inline">{currentSupplier.paypalLink}</h4>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      )}
      {mealchart && (
        <div>
          <Card className="mechco">
            <Card.Body>
              <h3>Meal Plan Details</h3>
              <table>
                <tr>
                  <td>Monday:</td>
                  <td>
                    <input type="text" id="monday1" />
                  </td>
                  <td>
                    <input type="text" id="monday2" />
                  </td>
                  <td>
                    <input type="text" id="monday3" />
                  </td>
                  <td>
                    <input type="text" id="monday4" />
                  </td>
                  <td>
                    <input type="text" id="monday5" />
                  </td>
                </tr>
                <br />
                <tr>
                  <td>Tuesday:</td>
                  <td>
                    <input type="text" id="tuesday1" />
                  </td>
                  <td>
                    <input type="text" id="tuesday2" />
                  </td>
                  <td>
                    <input type="text" id="tuesday3" />
                  </td>
                  <td>
                    <input type="text" id="tuesday4" />
                  </td>
                  <td>
                    <input type="text" id="tuesday5" />
                  </td>
                </tr>
                <br />
                <tr>
                  <td>Wednesday:</td>
                  <td>
                    <input type="text" id="wednesday1" />
                  </td>
                  <td>
                    <input type="text" id="wednesday2" />
                  </td>
                  <td>
                    <input type="text" id="wednesday3" />
                  </td>
                  <td>
                    <input type="text" id="wednesday4" />
                  </td>
                  <td>
                    <input type="text" id="wednesday5" />
                  </td>
                </tr>
                <br />
                <tr>
                  <td>Thursday:</td>
                  <td>
                    <input type="text" id="thursday1" />
                  </td>
                  <td>
                    <input type="text" id="thursday2" />
                  </td>
                  <td>
                    <input type="text" id="thursday3" />
                  </td>
                  <td>
                    <input type="text" id="thursday4" />
                  </td>
                  <td>
                    <input type="text" id="thursday5" />
                  </td>
                </tr>
                <br />
                <tr>
                  <td>Friday:</td>
                  <td>
                    <input type="text" id="friday1" />
                  </td>
                  <td>
                    <input type="text" id="friday2" />
                  </td>
                  <td>
                    <input type="text" id="friday3" />
                  </td>
                  <td>
                    <input type="text" id="friday4" />
                  </td>
                  <td>
                    <input type="text" id="friday5" />
                  </td>
                </tr>
                <br />
                <tr>
                  <td>Saturday:</td>
                  <td>
                    <input type="text" id="saturday1" />
                  </td>
                  <td>
                    <input type="text" id="saturday2" />
                  </td>
                  <td>
                    <input type="text" id="saturday3" />
                  </td>
                  <td>
                    <input type="text" id="saturday4" />
                  </td>
                  <td>
                    <input type="text" id="saturday5" />
                  </td>
                </tr>
                <br />
                <tr>
                  <td>Sunday:</td>
                  <td>
                    <input type="text" id="sunday1" />
                  </td>
                  <td>
                    <input type="text" id="sunday2" />
                  </td>
                  <td>
                    <input type="text" id="sunday3" />
                  </td>
                  <td>
                    <input type="text" id="sunday4" />
                  </td>
                  <td>
                    <input type="text" id="sunday5" />
                  </td>
                </tr>
              </table>
              <br />
              <Button variant="outline-primary" onClick={handleCreate}>
                Upload
              </Button>{" "}
            </Card.Body>
          </Card>
        </div>
      )}
      {addOns && (
        <div className="mx-4">
          <h3>Add-on details</h3>
          <hr />
          <Table striped bordered responsive>
            <tbody>
              {addOnData.map((data) => {
                return (
                  <tr key={data.id}>
                    <td>
                      <h5>Item</h5>
                    </td>
                    <td>
                      <h5>{data.item}</h5>
                    </td>
                    <td>
                      <h5>Price</h5>
                    </td>
                    <td>
                      <h5>{data.price}</h5>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        </div>
      )}

      {addOnAlter && (
        <div>
          <Card className="mechco">
            <Card.Body>
              <h3>Add on Details</h3>
              <table>
                <tr>
                  <td>Addon1:</td>
                  <td>
                    <input type="text" id="addon1" />
                  </td>
                  <td>Price</td>
                  <td>
                    <input type="text" id="price1" />
                  </td>
                </tr>
                <tr>
                  <td>Addon2:</td>
                  <td>
                    <input type="text" id="addon2" />
                  </td>
                  <td>Price</td>
                  <td>
                    <input type="text" id="price2" />
                  </td>
                </tr>
                <tr>
                  <td>Addon3:</td>
                  <td>
                    <input type="text" id="addon3" />
                  </td>
                  <td>Price</td>
                  <td>
                    <input type="text" id="price3" />
                  </td>
                </tr>
              </table>
              <br />
              <Button variant="outline-primary" onClick={handleAddon}>
                Upload
              </Button>{" "}
            </Card.Body>
          </Card>
        </div>
      )}
      {currentChart && (
        <div className="mx-4">
          <br />
          <h2> Current Meal Plan </h2>
          <hr />
          <br />
          <Table striped bordered responsive>
            <tbody>
              <tr>
                <td style={{ width: "150px" }}>
                  <h5>Monday</h5>
                </td>
                <td style={{ width: "150px" }}>
                  <h5>{datamon.item1}</h5>
                </td>
                <td style={{ width: "150px" }}>
                  <h5>{datamon.item2}</h5>
                </td>
                <td style={{ width: "150px" }}>
                  <h5>{datamon.item3}</h5>
                </td>
                <td style={{ width: "150px" }}>
                  <h5>{datamon.item4}</h5>
                </td>
                <td style={{ width: "150px" }}>
                  <h5>{datamon.item5}</h5>
                </td>
              </tr>
              <tr>
                <td>
                  <h5>Tuesday</h5>
                </td>
                <td>
                  <h5>{datatue.item1}</h5>
                </td>
                <td>
                  <h5>{datatue.item2}</h5>
                </td>
                <td>
                  <h5>{datatue.item3}</h5>
                </td>
                <td>
                  <h5>{datatue.item4}</h5>
                </td>
                <td>
                  <h5>{datatue.item5}</h5>
                </td>
              </tr>
              <tr>
                <td>
                  <h5>Wednesday</h5>
                </td>
                <td>
                  <h5>{datawed.item1}</h5>
                </td>
                <td>
                  <h5>{datawed.item2}</h5>
                </td>
                <td>
                  <h5>{datawed.item3}</h5>
                </td>
                <td>
                  <h5>{datawed.item4}</h5>
                </td>
                <td>
                  <h5>{datawed.item5}</h5>
                </td>
              </tr>
              <tr>
                <td>
                  <h5>Thursday</h5>
                </td>
                <td>
                  <h5>{datathu.item1}</h5>
                </td>
                <td>
                  <h5>{datathu.item2}</h5>
                </td>
                <td>
                  <h5>{datathu.item3}</h5>
                </td>
                <td>
                  <h5>{datathu.item4}</h5>
                </td>
                <td>
                  <h5>{datathu.item5}</h5>
                </td>
              </tr>
              <tr>
                <td>
                  <h5>Friday</h5>
                </td>
                <td>
                  <h5>{datafri.item1}</h5>
                </td>
                <td>
                  <h5>{datafri.item2}</h5>
                </td>
                <td>
                  <h5>{datafri.item3}</h5>
                </td>
                <td>
                  <h5>{datafri.item4}</h5>
                </td>
                <td>
                  <h5>{datafri.item5}</h5>
                </td>
              </tr>
              <tr>
                <td>
                  <h5>Saturday</h5>
                </td>
                <td>
                  <h5>{datasat.item1}</h5>
                </td>
                <td>
                  <h5>{datasat.item2}</h5>
                </td>
                <td>
                  <h5>{datasat.item3}</h5>
                </td>
                <td>
                  <h5>{datasat.item4}</h5>
                </td>
                <td>
                  <h5>{datasat.item5}</h5>
                </td>
              </tr>
              <tr>
                <td>
                  <h5>Sunday</h5>
                </td>
                <td>
                  <h5>{datasun.item1}</h5>
                </td>
                <td>
                  <h5>{datasun.item2}</h5>
                </td>
                <td>
                  <h5>{datasun.item3}</h5>
                </td>
                <td>
                  <h5>{datasun.item4}</h5>
                </td>
                <td>
                  <h5>{datasun.item5}</h5>
                </td>
              </tr>
            </tbody>
          </Table>
          <br />

          <br />
        </div>
      )}
      {showCustomerList ? (
        isCustomerListLoading ? (
          <Container className="my-5 mx-auto">
            <Spinner variant="primary" />
          </Container>
        ) : (
          <CustomerList
            customerList={customerList}
            subscriptionList={subscriptionList}
          />
        )
      ) : null}

      {showDeliveryTable && (
        <DeliveryTable columns={DeliveryColumns} data={deliveryData} />
      )}

      <br />
      <br />

      <Navbar
        bg="primary"
        variant="light"
        className="justify-content-center align-items-center"
      >
        <h3>©Go Meals</h3>
      </Navbar>
      
    </div>
  );
}
