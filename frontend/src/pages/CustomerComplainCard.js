import { React, useState } from "react";
import { Button, Card } from "react-bootstrap";
import ComplainModalComponent from "../components/ComplainModalComponent";

function ComplainCard(props) {
  const [show, setShow] = useState(false);
  return (
    <Card border="dark" style={{ width: "18rem" }}>
      <Card.Header>Complain raised on : {props.complain.date} </Card.Header>
      <Card.Body>
        <Card.Title>Complain Status : {props.complain.status}</Card.Title>
        <Card.Text>Meal : {props.delivery.deliveryMeal}</Card.Text>
        <Card.Text>
          Meal Delivery date : {props.delivery.deliveryDate}
        </Card.Text>
      </Card.Body>
      <Button
        variant="info"
        className="m-lg-4"
        onClick={() => {
          setShow(true);
          console.log(show);
          console.log("Button was clicked");
        }}
      >
        View Complain
      </Button>
      <ComplainModalComponent
        complain={props.complain}
        delivery={props.delivery}
        onClose={() => setShow(false)}
        show={show}
      />
    </Card>
  );
}

export default ComplainCard;
