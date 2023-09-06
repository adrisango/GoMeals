import React, { useState, useEffect } from "react";
import { Button } from "react-bootstrap";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function NumberInput(props) {
  const { name, min, max, step, initialValue,id } = props;
  const [value, setValue] = useState(initialValue);

  function handleInputChange(event) {
    let newValue = parseInt(event.target.value);
    if (isNaN(newValue)) {
      newValue = min;
    } else if (newValue < min) {
      newValue = min;
    } else if (newValue > max) {
      newValue = max;
    }
    setValue(newValue);
  }

  function handleDecrement() {
    if (value > min) {
      setValue(value - step);
    }
  }

  function handleIncrement() {
    if (value < max) {
      setValue(value + step);
    }
  }

  return (
    <div className="input-group">
        <div className="d-flex flex-column align-items-start">
      <span className="input-group-addon p-3 ps-0">{name}</span>
      <div className="d-flex">
        <span className="input-group-btn">
          <Button
            type="button"
            className="btn btn-default btn-number bg-danger"
            onClick={handleDecrement}
          >
            <FontAwesomeIcon icon={faMinus} />
          </Button>
        </span>
        <input
          type="text"
          className="form-control input-number"
          id={id}
          name={name}
          value={value}
          min={min}
          max={max}
          step={step}
          onChange={handleInputChange}
        />
        <span className="input-group-btn">
          <Button
            type="button"
            className="btn btn-default btn-number"
            onClick={handleIncrement}
          >
            <FontAwesomeIcon icon={faPlus} />
          </Button>
        </span></div>
      </div>
    </div>
  );
}
