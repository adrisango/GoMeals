import React, { useState, useEffect } from "react";
import axios from "axios";
import "../styles/Navbar.css";
import { faCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { API_HEADER } from "../utils.js";

function Notification(props) {
  const [notifications, setNotifications] = useState([]);
  const [newNotificationCount, setNewNotificationCount] = useState(0);
  console.log("In notification: "+props.userType)
  console.log(props)
  useEffect(() => {
    var response = ''
    const fetchNotifications = async () => {
      try {
        if(props.userType==='customer'){
           response = await axios.get(
          `${API_HEADER}customer-notification/get/all-customer/${props.cust_id}`
        );
        }else if(props.userType==='supplier'){
           response = await axios.get(
            `${API_HEADER}supplier-notification/get/all-supplier/${props.supId}`
          );
        }
        
        const { data } = response;
        const newNotifications = data.filter(
          (notification) =>
            !notifications.some(
              (prevNotification) => prevNotification.id === notification.id
            )
        );
        setNotifications([...notifications, ...newNotifications]);
        setNewNotificationCount(newNotifications.length);
      } catch (error) {
        console.error(error);
      }
    };

    fetchNotifications();

    const interval = setInterval(fetchNotifications, 5000);

    return () => clearInterval(interval);
  }, [notifications]);

  return (
    <div>
      <div className="notifications">
        <div>
          {" "}
          <h3>Notifications</h3>
          <div style={{marginBottom: '10px'}}>
            <FontAwesomeIcon icon={faCircle} style={{ color: "red" }} />{" "}
            Complaint{" "}
            <FontAwesomeIcon icon={faCircle} style={{ color: "yellow" }} />{" "}
            Review
          </div>
        </div>
        {notifications.map((notification) => (
          <div key={notification.notificationId}>
            <div>
              {" "}
              <p className="notification-text">
                {notification.eventType === "review" ? (
                  <FontAwesomeIcon
                    icon={faCircle}
                    style={{ color: "yellow" }}
                  />
                ) : (
                  <FontAwesomeIcon icon={faCircle} style={{ color: "red" }} />
                )}{" "}
                {notification.message}
              </p>
              <p></p>{" "}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Notification;
