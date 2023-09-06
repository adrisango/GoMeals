import React, { useEffect, useState } from "react";
import axios from "axios";
import { Spinner, Modal, Form, Button } from "react-bootstrap";
import { Cookies } from "react-cookie";
import ReactStars from "react-stars";
import NavbarComponent from "../components/NavbarComponent";
import { addSupplierNotification } from "../utils";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function CustomerProfile() {
  const [customer, setCustomer] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [editProfile, setEditProfile] = useState(false);
  const [editedCustomerDetail, setEditedCustomerDetail] = useState({
    email: "",
    contactNumber: "",
    address: "",
  });
  const [addReview, setAddReview] = useState(false);
  const [review, setReview] = useState({
    "customerId": 0,
    "supplierId": 0,
    "comment": "",
    "supplier_rating": 0,
    "supplier_reviewcol": ""
  });
  const [currentSupplierReview, setCurrentSupplierReview] = useState({});
  var currentSupplierIndex = 0;
  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");

  useEffect(() => {
    setIsLoading(true);
    axios
      .get(`${API_HEADER}customer/get/${loggedInUser.cust_id}`)
      .then((response) => {
        response.data.subscriptions.forEach((subscription) => {
          response.data.suppliers.forEach((supplier) => {
            if (supplier.supId === subscription.supplierId) {
              subscription.supplierName = supplier.supName;
              subscription.supplierContactNo = supplier.supContactNumber;
            }
          });
        });
        setCustomer(response.data);
      })
      .catch((e) => {
        swal("Error getting data" + e);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [loggedInUser.cust_id]);

  useEffect(() => {
    axios
      .put(API_HEADER + "customer/update", customer)
      .catch((e) => {
        swal("Error getting data" + e);
      })
      .finally(() => {
        setIsLoading(false);
        setEditProfile(false);
      });
  }, [customer]);

  const handleEditProfile = () => {
    setEditProfile(true);
    setEditedCustomerDetail({
      email: customer.cust_email,
      contactNumber: customer.cust_contact_number,
      address: customer.cust_address,
    });
  };

  const handleEditingProfile = (event) => {
    const { name, value } = event.target;
    setEditedCustomerDetail((prevValue) => ({
      ...prevValue,
      [name]: value,
    }));
  };

  const updateCustomerProfile = () => {
    const regexForNumber = /^[0-9\b]+$/;
    const regexForEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (
      editedCustomerDetail.email === "" ||
      editedCustomerDetail.contactNumber === "" ||
      editedCustomerDetail.address === ""
    ) {
      swal("Fields should not be empty");
      return;
    } else if (
      !regexForEmail.test(editedCustomerDetail.email)
    ) {
      swal("Please provide a valid email.");
      return;
    } else if (
      editedCustomerDetail.contactNumber.length !== 10 ||
      !regexForNumber.test(editedCustomerDetail.contactNumber)
    ) {
      swal("Please provide a valid contact number.");
      return;
    }
    setIsLoading(true);
    setCustomer((prevValue) => ({
      ...prevValue,
      cust_email: editedCustomerDetail.email,
      cust_contact_number: editedCustomerDetail.contactNumber,
      cust_address: editedCustomerDetail.address,
    }));
  };

  const handleAddReview = (index) => {
    setAddReview(true);
    currentSupplierIndex = index;
    getCurrentSupplierReview(
      loggedInUser.cust_id,
      customer.subscriptions[currentSupplierIndex].supplierId
    );
  };

  const handleRating = (number) => {
    setReview((prevValue) => ({
      ...prevValue,
      supplier_rating: number,
    }));
  };

  const handleComment = (event) => {
    setReview((prevValue) => ({
      ...prevValue,
      comment: event.target.value,
    }));
  };

  const saveReview = () => {
    if (review.supplier_rating === 0) {
      swal("Please provide rating.");
      return;
    }
    setIsLoading(true);
    axios
      .post(API_HEADER + "supplierReview/create", review)
      .catch((e) => {
        swal("Error posting review");
      })
      .finally(() => {
        setIsLoading(false);
        setAddReview(false);
      });
    addSupplierNotification({
      message: `${customer.cust_fname + " " + customer.cust_lname
        } has provided review.`,
      eventType: "New Review",
      customerId: customer.cust_id,
      supplierId: customer.subscriptions[currentSupplierIndex].supplierId,
    });
  };

  const getCurrentSupplierReview = (customerId, supplierId) => {
    setIsLoading(true);
    axios
      .get(API_HEADER + "supplierReview/getById", {
        params: { customerId: customerId, supplierId: supplierId },
      })
      .then((response) => {
        setCurrentSupplierReview(response.data);
        if (response.data.length !== 0) {
          setReview(response.data);
        } else {
          setReview({
            "customerId": customerId,
            "supplierId": supplierId,
            "comment": "",
            "supplier_rating": 0,
            "supplier_reviewcol": ""
          });
        }
      })
      .finally(() => {
        setIsLoading(false);
        console.log(addReview);
      });
  };

  return (
    <div>
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
                name="email"
                value={editedCustomerDetail.email}
                onChange={handleEditingProfile}
                autoFocus
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="contactNo">
              <Form.Label>Contact Number</Form.Label>
              <Form.Control
                type="text"
                placeholder="Your contact number"
                name="contactNumber"
                value={editedCustomerDetail.contactNumber}
                onChange={handleEditingProfile}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="address">
              <Form.Label>Address</Form.Label>
              <Form.Control
                as="textarea"
                rows={2}
                name="address"
                value={editedCustomerDetail.address}
                onChange={handleEditingProfile}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setEditProfile(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={updateCustomerProfile}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={addReview} onHide={() => setAddReview(false)} centered>
        <Modal.Header closeButton>
          <Modal.Title>Supplier Review</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="email">
              <Form.Label>Rating</Form.Label>
              <ReactStars
                count={5}
                value={review.supplier_rating}
                onChange={handleRating}
                size={24}
                color2={"#ffd700"}
                half={false}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="comment">
              <Form.Label>Comment</Form.Label>
              <Form.Control
                as="textarea"
                rows={2}
                name="comment"
                value={review.comment}
                onChange={handleComment}
                disabled={currentSupplierReview === "" ? false : true}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setAddReview(false)}>
            Close
          </Button>
          {
            currentSupplierReview === "" &&
            <Button
              variant="primary"
              onClick={saveReview}
            >
              Save Changes
            </Button>
          }
        </Modal.Footer>
      </Modal>
      {isLoading ? (
        <Spinner />
      ) : (
        // <div className="customer-profile-page">
        <div className="container customer-profile my-4">
          <div className="col-lg-12">
            <div className="d-flex justify-content-between align-items-center">
              <div>
                <h2 className="d-inline">Hi, </h2>
                <h2 className="d-inline">
                  {customer.cust_fname + " " + customer.cust_lname}
                </h2>
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
              <h4 className="d-inline">{customer.cust_email}</h4>
            </div>
          </div>
          <div className="row mb-2 profile-item">
            <div className="col-lg-12">
              <h4 className="d-inline">Contact number</h4>
              <hr></hr>
            </div>
            <div className="col-lg-12">
              <h4 className="d-inline">{customer.cust_contact_number}</h4>
            </div>
          </div>
          <div className="row mb-2 profile-item">
            <div className="col-lg-12">
              <h4 className="d-inline">Address</h4>
              <hr></hr>
            </div>
            <div className="col-lg-12">
              <h4 className="d-inline">{customer.cust_address}</h4>
            </div>
          </div>
          <div className="row mb-2 profile-item">
            <div className="col-lg-12">
              <h4 className="d-inline">Subscription</h4>
              <hr></hr>
            </div>
            <div className="col-lg-12">
              {customer.subscriptions?.map((subscription, index) => {
                return (
                  <div key={subscription.sub_id} className="row">
                    <div className="col-lg-10 col-md-10">
                      <div className="row mb-2 profile-item">
                        <div className="row">
                          <div className="col-lg-4 col-sm-6">
                            {/* <h5>Subscription {index + 1}</h5> */}
                            <h5 className="d-inline">Supplier's name:</h5>
                          </div>
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">
                              {subscription.supplierName}
                            </h5>
                          </div>
                        </div>
                        <div className="row">
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">Subscription date:</h5>
                          </div>
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">
                              {subscription.sub_date}
                            </h5>
                          </div>
                        </div>
                        <div className="row">
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">Meals remaining:</h5>
                          </div>
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">
                              {subscription.meals_remaining}
                            </h5>
                          </div>
                        </div>
                        <div className="row">
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">Status:</h5>
                          </div>
                          <div className="col-lg-4 col-sm-6">
                            <h5 className="d-inline">
                              {subscription.activeStatus === 1
                                ? "Active"
                                : "Expired"}
                            </h5>
                          </div>
                        </div>
                        <div className="row">
                          <div className="col-lg-4 col-sm-6 py-1">
                            <Button
                              variant="dark"
                              onClick={() => handleAddReview(index)}
                            >
                              Review
                            </Button>
                          </div>
                        </div>
                      </div>
                    </div>
                    {/* <div className="col-lg-4 col-md-2">
                                                
                                            </div> */}
                  </div>
                );
              })}
            </div>
          </div>
        </div>
        // </div>
      )}
    </div>
  );
}

export default CustomerProfile;
