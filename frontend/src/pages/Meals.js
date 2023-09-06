import React, { useCallback, useEffect, useMemo, useState } from "react";
import {
  Accordion,
  Button,
  Col,
  Container,
  Row,
  Form,
  InputGroup,
} from "react-bootstrap";

import NumberInput from "../components/NumberInput";
import Swal from "sweetalert2";
import { Cookies } from "react-cookie";
import axios from "axios";
import { faSpinner } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { API_HEADER } from "../utils.js";

const MealAccordion = () => {
  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");
  const [deliveries, setDeliveries] = useState([]);
  const [activeKey, setActiveKey] = useState(null); // Declare and define the activeKey state variable and the setActiveKey function
  const [showAddOnsForm, setShowAddOnsForm] = useState(false);
  const [updatingAddons, setUpdatingAddons] = useState(false);

  const customerId = loggedInUser.cust_id;
  useEffect(() => {
    async function fetchData() {
      try {
        const response = await axios.get(
          `${API_HEADER}delivery/get/customer/${customerId}`
        );
        const sortedDeliveries = [...response.data].sort((a, b) => {
          if (a.deliveryDate < b.deliveryDate) {
            return -1;
          } else if (a.deliveryDate > b.deliveryDate) {
            return 1;
          } else {
            return 0;
          }
        });
        setDeliveries(sortedDeliveries);
      } catch (error) {
        console.log(error);
      }
    }

    if (customerId) {
      fetchData();
    }
  }, [customerId]);
  useEffect(() => {
    return () => {};
  }, [deliveries]);

  const handleAddOnsFormSubmit = (event) => {
    setUpdatingAddons(true);
    event.preventDefault();
    const formData = new FormData(event.target);
    const data = {};
    for (let [key, value] of formData.entries()) {
      const field = document.querySelector(`[name="${key}"]`);
      if (field) {
        data[key] = { value, id: field.id };
      } else {
        data[key] = value;
      }
    }
    const custId = data.custId.value;
    const deliveryId = data.deliveryId.value;
    for (let [key, value] of Object.entries(data)) {
      if (key !== "custId" && key !== "deliveryId") {
        const addonId = value.id;
        const quantity = value.value;
        const payload = {
          customerId: custId,
          addonId: addonId,
          deliveryId: deliveryId,
          quantity: quantity,
        };
        if (quantity > 0) {
          fetch(API_HEADER + "deliveryAddons/update", {
            method: "PUT",
            body: JSON.stringify(payload),
            headers: {
              "Content-Type": "application/json",
            },
          })
            .then((response) => response.json())
            .then(() => setUpdatingAddons(false))
            .catch((error) => console.error(error));
        } else {
          continue;
        }
      }
    }
  };

  const handleAddOnsClick = useCallback(
    async (deliveryId, sup_id) => {
     await getDeliveryAddons(deliveryId, sup_id);
      setShowAddOnsForm((showAddOnsForm) => !showAddOnsForm);
    },
    [getDeliveryAddons]
  );

  const groupedMeals = useMemo(() => {
    return deliveries.reduce((acc, meal) => {
      const key =
        meal.deliveryDate === new Date().toISOString().slice(0, 10)
          ? "Today"
          : meal.deliveryDate;
      if (!acc[key]) {
        acc[key] = [];
      }
      acc[key].push(meal);
      return acc;
    }, {});
  }, [deliveries]);

  function deleteMealById(deliveryId) {
    const endpoint = `${API_HEADER}delivery/delete/${deliveryId}`;
    axios
      .delete(endpoint)
      .then((response) => {
        console.log(
          `Meal with delivery ID ${deliveryId} deleted successfully.`
        );
        // Remove deleted meal from deliveries state variable
        const updatedDeliveries = deliveries.filter(
          (delivery) => delivery.deliveryId !== deliveryId
        );
        // Update deliveries state variable
        setDeliveries(updatedDeliveries);
      })

      .catch((error) => {
        console.error(
          `Error deleting meal with delivery ID ${deliveryId}: ${error}`
        );

        // handle error response here
      });
  }
  async function getDeliveryAddons(deliveryId, sup_id) {
    try {
      const endpoint = `${API_HEADER}deliveryAddons/get/${deliveryId}`;
      const response1 = await axios.get(endpoint);

      const updatedDeliveries1 = deliveries.map((delivery) => {
        const matchingAddons = response1.data.filter(
          (addon) => addon.deliveryId === delivery.deliveryId
        );

        return {
          ...delivery,
          addons: matchingAddons,
        };
      });

      const response2 = await axios.get(
        `${API_HEADER}Addons/get/all-supplier/${sup_id}`
      );

      const updatedDeliveries2 = updatedDeliveries1.map((delivery) => {
        const matchingAvailableAddons = response2.data.filter(
          (addon) => addon.supplierId === delivery.supId
        );
        return {
          ...delivery,
          availableAddons: matchingAvailableAddons,
        };
      });

      await setDeliveries(updatedDeliveries2);
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  }

  const handleDeleteClick = (deliveryId) => {
    Swal.fire({
      title: "Are you sure?",
      text: `Do you want to cancel this meal?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes, Cancel it!",
      confirmButtonColor: "#dc3545",
      cancelButtonText: "No, keep it",
    }).then((result) => {
      if (result.isConfirmed) {
        // Calling API to delete the meal here
        console.log("Delivery ID in handleDeleteClick" + deliveryId);
        deleteMealById(deliveryId);
      }
    });
  };
  return (
    <div>
      <Container>
        <Row className="pt-5">
          <Col>
            <h1 className="text-start">Meals</h1>
          </Col>
        </Row>
        {Object.keys(groupedMeals).map((date) => {
          return (
            <div key={date}>
              <Row>
                <Col className="text-start mt-3">
                  <h3>{date}</h3>
                </Col>
              </Row>
              <Row>
                <Col>
                  <Accordion activeKey={activeKey}>
                    {groupedMeals[date].map((meal) => (
                      <Accordion.Item
                        key={meal.deliveryId}
                        eventKey={meal.deliveryId.toString()}
                        onClick={async() => {
                          setActiveKey(meal.deliveryId.toString());
                          await getDeliveryAddons(meal.deliveryId, meal.supId);
                        }}
                        style={{ backgroundColor: "#f5f5f5" }}
                      >
                        <Accordion.Header>
                          <div>
                            <h5>{meal.title}</h5>
                            <p>Delivery Status: {meal.orderStatus}</p>
                          </div>
                        </Accordion.Header>
                        <Accordion.Body>
                          <div className="d-flex">
                            <p>Items: </p>
                            <div className="ms-1 d-flex">
                              {meal.deliveryMeal}
                            </div>
                          </div>

                          <div className="d-flex">
                            <Button
                              variant="primary"
                              onClick={() => {
                                handleAddOnsClick(meal.deliveryId, meal.supId);
                              }}
                            >
                              Add ons
                            </Button>

                            <Button
                              variant="danger"
                              onClick={() => handleDeleteClick(meal.deliveryId)}
                              className="ms-4"
                            >
                              Cancel Meals
                            </Button>
                          </div>
                          {/* {showAddOnsForm && renderAddOnsForm()} */}

                          {showAddOnsForm && activeKey == meal.deliveryId && (
                            <>
                              <div className="d-flex">
                                {console.log(meal.addons)}
                                {meal.addons && <p>Addons: </p>}
                                <div className="ms-1 d-flex">
                                  {meal.addons &&
                                    meal.addons.length > 0 &&
                                    meal.addons.map((addon, index) => {
                                      const availableAddon =
                                        meal.availableAddons.find(
                                          (a) => a.addonId === addon.addonId
                                        );
                                      console.log(availableAddon);
                                      if (addon.quantity > 0) {
                                        return (
                                          <React.Fragment key={addon.addonId}>
                                            {index !== 0 && ", "}
                                            <p>
                                              {availableAddon
                                                ? availableAddon.item
                                                : ""}
                                            </p>
                                          </React.Fragment>
                                        );
                                      }
                                    })}
                                </div>
                              </div>
                              <Form onSubmit={handleAddOnsFormSubmit}>
                                <InputGroup className="mb-3">
                                  {!meal.availableAddons
                                    ? null
                                    : meal.availableAddons.map(
                                        (availableAddon) => {
                                          const addon = meal.addons.find(
                                            (a) =>
                                              a.addonId ===
                                              availableAddon.addonId
                                          );
                                          // // Set the initial value to the quantity of the matching addon or an empty string if not found
                                          const initialValue = addon
                                            ? addon.quantity.toString()
                                            : "";
                                          return (
                                            <NumberInput
                                              name={availableAddon.item}
                                              id={availableAddon.addonId}
                                              key={availableAddon.addonId}
                                              min={0}
                                              max={100}
                                              step={1}
                                              initialValue={
                                                initialValue
                                                  ? parseInt(initialValue)
                                                  : 0
                                              }
                                            />
                                          );
                                        }
                                      )}
                                  <input
                                    type="hidden"
                                    name="deliveryId"
                                    value={meal.deliveryId}
                                  />
                                  <input
                                    type="hidden"
                                    name="custId"
                                    value={customerId}
                                  />

                                  <Button
                                    variant="outline-primary"
                                    type="submit"
                                    className="mt-3"
                                  >
                                    {updatingAddons ? (
                                      <FontAwesomeIcon icon={faSpinner} spin />
                                    ) : (
                                      "Submit"
                                    )}
                                  </Button>
                                </InputGroup>
                              </Form>
                            </>
                          )}
                        </Accordion.Body>
                      </Accordion.Item>
                    ))}
                  </Accordion>
                </Col>
              </Row>
            </div>
          );
        })}
      </Container>
    </div>
  );
};

export default MealAccordion;
