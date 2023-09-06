import React, { useState, useEffect } from "react";
import { Cookies } from "react-cookie";
import axios from "axios";
import {
  Container,
  Table,
  Modal,
  Button,
  Form,
  Spinner,
} from "react-bootstrap";
import { addCustomerNotification } from "../utils";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

export default function SupplierComplain() {
  const cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");
  const [isLoading, setIsLoading] = useState(false);
  const [complains, setComplains] = useState([]);
  const [selectedComplainIndex, setSelectedComplainIndex] = useState(0);
  const [supplierComment, setSupplierComment] = useState("");
  const [showComplainDetail, setShowComplainDetail] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    axios
      .get(
        `${API_HEADER}complain/get/all-supplier/${loggedInUser.supId}`
      )
      .then((response) => {
        setComplains(response.data);
      })
      .catch((e) => {
        swal("Error getting data" + e);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [loggedInUser.supId]);

  function handleComplain(index) {
    setSelectedComplainIndex(index);
    setSupplierComment(complains[index].supplierComment);
    console.log("ss" + supplierComment);
    setShowComplainDetail(true);
  }

  const submitFeedback = () => {
    if (supplierComment.trim().length === 0) {
      swal("Please insert feedback.");
      return;
    }
    setIsLoading(true);
    if (complains.length !== 0) {
      complains[selectedComplainIndex].supplierComment = supplierComment;
      complains[selectedComplainIndex].status = "Resolved";
    }
    axios
      .put(
        API_HEADER + "complain/update",
        complains[selectedComplainIndex]
      )
      .then(() => {
        addCustomerNotification({
          message: `${loggedInUser.supName} has provided feedback to your complain`,
          eventType: "Complain Solved",
          customerId: complains[selectedComplainIndex].customerId,
        });
      })
      .catch((e) => {
        swal("Error submitting feedback" + e);
      })
      .finally(() => {
        setIsLoading(false);
        setShowComplainDetail(false);
      });
  };

  console.log(complains[0]?.supplierId);

  return (
    <div>
      <Modal
        show={showComplainDetail}
        onHide={() => setShowComplainDetail(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title>Complain</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {isLoading ? (
            <Spinner />
          ) : (
            <Form>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Complain Date</Form.Label>
                <Form.Control
                  type="email"
                  value={complains[selectedComplainIndex]?.date}
                  disabled
                  autoFocus
                />
              </Form.Group>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Label>Customer Comment</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={3}
                  value={complains[selectedComplainIndex]?.customerComment}
                  disabled
                />
              </Form.Group>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Complain Date</Form.Label>
                <Form.Control
                  type="Complain Status"
                  value={complains[selectedComplainIndex]?.status}
                  disabled
                  autoFocus
                />
              </Form.Group>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlTextarea1"
              >
                <Form.Label>Your Feedback</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={4}
                  value={supplierComment}
                  onChange={(e) => setSupplierComment(e.target.value)}
                  disabled={
                    complains[selectedComplainIndex]?.status === "Initiated"
                      ? false
                      : true
                  }
                />
              </Form.Group>
            </Form>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setShowComplainDetail(false)}
          >
            Close
          </Button>
          {
            complains[selectedComplainIndex]?.status === "Initiated" &&
              <Button variant="primary" onClick={submitFeedback}>
                Save Changes
              </Button> 
          }
        </Modal.Footer>
      </Modal>

      <Container className="my-5 mx-xs-2 mx-auto customerlist-table">
        {complains.length === 0 ? (
          <div>
            <h4>No Complains</h4>
          </div>
        ) : (
          <Table>
            <thead>
              <tr>
                <th></th>
                <th>Complain Date</th>
                <th>Complain Comment</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {complains?.map((complain, index) => {
                return (
                  <tr
                    key={complain.complainId}
                    onClick={() => handleComplain(index)}
                  >
                    <td>{index + 1}</td>
                    <td>{complain.date}</td>
                    <td>{complain.customerComment}</td>
                    <td>{complain.status}</td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        )}
        {/* {
                    showComplainDetail &&
                    <Modal.Dialog>
                        <Modal.Header closeButton>
                            <Modal.Title>Modal title</Modal.Title>
                        </Modal.Header>

                        <Modal.Body>
                            <p>Modal body text goes here.</p>
                        </Modal.Body>

                        <Modal.Footer>
                            <Button variant="secondary" onClick={() => { setShowComplainDetail(false); }}>Close</Button>
                            <Button variant="primary">Save changes</Button>
                        </Modal.Footer>
                    </Modal.Dialog>
                } */}
      </Container>
    </div>
  );
}
