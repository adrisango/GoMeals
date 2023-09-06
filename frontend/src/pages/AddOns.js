import React, { useState } from 'react';
import { Form, Button, ListGroup, Card, Col, Row, Modal } from 'react-bootstrap';
import Meals from './Meals';

export default function AddOns(){
  const [selectedAddOns, setSelectedAddOns] = useState([]);
  const [availableAddOns, setAvailableAddOns] = useState([
    { id: 1, name: 'Pepsi', price: 1.99 },
    { id: 2, name: 'Fries', price: 2.99 },
    { id: 3, name: 'Salad', price: 3.99 },
    { id: 4, name: 'Garlic Bread', price: 4.99 },
  ]);
  const [newAddOnName, setNewAddOnName] = useState('');
  const [newAddOnPrice, setNewAddOnPrice] = useState('');
  const [showAddOnModal, setShowAddOnModal] = useState(false);

  const handleAddOnSelection = (id) => {
    const addOn = availableAddOns.find((addOn) => addOn.id === id);

    setSelectedAddOns([...selectedAddOns, addOn]);
  };

  const handleRemoveAddOn = (id) => {
    const index = selectedAddOns.findIndex((addOn) => addOn.id === id);
    if (index !== -1) {
      const newSelectedAddOns = [...selectedAddOns];
      newSelectedAddOns.splice(index, 1);
      setSelectedAddOns(newSelectedAddOns);
    }
  };
  

  const handleAddOnSubmit = (event) => {
    event.preventDefault();

    const newAddOn = {
      id: availableAddOns.length + 1,
      name: newAddOnName,
      price: parseFloat(newAddOnPrice),
    };

    setAvailableAddOns([...availableAddOns, newAddOn]);
    setNewAddOnName('');
    setNewAddOnPrice('');
    setShowAddOnModal(false);
  };

  const renderAddOns = () => {
    return availableAddOns.map((addOn) => {
      return (
        <Col key={addOn.id} className="mb-3">
          <Card>
            <Card.Body>
              <Card.Title>{addOn.name}</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">${addOn.price.toFixed(2)}</Card.Subtitle>
              <Button variant="primary" onClick={() => handleAddOnSelection(addOn.id)}>
                Add
              </Button>
            </Card.Body>
          </Card>
        </Col>
      );
    });
  };

  const renderSelectedAddOns = () => {
    return selectedAddOns.map((addOn, index) => {
      return (
        <ListGroup.Item key={index}>
          <Row>
            <Col>{addOn.name}</Col>
            <Col>${addOn.price.toFixed(2)}</Col>
            <Col>
              <Button variant="danger" onClick={() => handleRemoveAddOn(addOn.id)}>
                Remove
              </Button>
            </Col>
          </Row>
        </ListGroup.Item>
      );
    });
  };
  

  return (
    <>
    <Meals />
    <Form>
      <h3>Add-Ons</h3>
      <Row>{renderAddOns()}</Row>
      <br />
      <h4>Selected Add-Ons:</h4>
      <ListGroup>{renderSelectedAddOns()}</ListGroup>
      <br />
      <Button variant="primary" onClick={() => setShowAddOnModal(true)}>
        Add New Add-On
      </Button>
      <Modal show={showAddOnModal} onHide={() => setShowAddOnModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add New Add-On</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleAddOnSubmit}>
            <Form.Group controlId="newAddOnName">
              <Form.Label>Name:</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter name"
                value={newAddOnName}
                onChange={(e) => setNewAddOnName(e.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="newAddOnPrice">
              <Form.Label>Price:</Form.Label>
              <Form.Control
                type="number"
                step="0.01"
                placeholder="Enter price"
                value={newAddOnPrice}
                onChange={(e) => setNewAddOnPrice(e.target.value)}
              />
            </Form.Group>
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </Form>
    </>
  );}
  