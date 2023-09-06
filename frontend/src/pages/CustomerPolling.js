import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { useEffect, useState } from "react";
import { Cookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";
import swal from "sweetalert";
import { API_HEADER } from "../utils.js";

function CustomerPolling(props) {
  let cookies = new Cookies();
  const loggedInUser = cookies.get("loggedInUser");
  const [selectedOption, setSelectedOption] = useState("");
  const navigate = useNavigate();
  const [items, setItems] = useState([]);
  const handleOptionChange = (e) => {
    setSelectedOption(e.target.value);
  };

  useEffect(() => {
    setItems([
      props.poll.item1,
      props.poll.item2,
      props.poll.item3,
      props.poll.item4,
      props.poll.item5,
    ]);
    setSelectedOption(props.poll.item1);
  }, [props.poll]);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(loggedInUser.cust_Id);
    const mealPollObject = {
      pollingId: props.poll.pollId,
      customerId: loggedInUser.cust_id,
      supplierId: props.poll.supId,
      votedMeal: selectedOption,
    };
    let mealPoll = JSON.stringify(mealPollObject);
    fetch(API_HEADER + "meal-voting/create", {
      method: "POST",
      headers: { "content-type": "application/json" },
      body: mealPoll,
    })
      .then((res) => {
        res.json();
      })
      .then((val) => {
        console.log("value" + val);
        swal("Meal voting completed for the date " + props.poll.pollDate);
        console.log(mealPoll);
        props.onHide();
        navigate("/dashboard");
      });
  };

  if (items.length === 0) {
    return null; // or render a loading spinner or message
  }

  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Special Meal voting date {props.poll.pollDate}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <FormControl>
          <RadioGroup
            aria-labelledby="demo-radio-buttons-group-label"
            defaultValue="Meals"
            name="radio-buttons-group"
          >
            {items.map((item) => (
              <FormControlLabel
                value={item}
                control={<Radio />}
                label={item}
                checked={selectedOption === item}
                onChange={handleOptionChange}
                className="row-5"
              />
            ))}
          </RadioGroup>
        </FormControl>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={handleSubmit} type="submit">
          Submit
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default CustomerPolling;
