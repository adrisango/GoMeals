import React from "react";
import "../styles/FooterComponent.css";
class FooterComponent extends React.Component {
  render() {
    return (
      <footer className="footer">
        <div className="footer-content">
          <h3>Contact Us</h3>
          <p>17 - 4 Sunset Avenues</p>
          <p>Halifax, Nova Scotia, Canada, B3H 4R2</p>
          <p>Phone: (902) 494-2211</p>
          <p>Email: help@gomeals.com</p>
        </div>
        <div className="footer-content">
          <h3>Follow Us</h3>
          <ul className="social-media">
            <li>
              <a href="#">
                <i className="fab fa-facebook"></i>
              </a>
            </li>
            <li>
              <a href="#">
                <i className="fab fa-twitter"></i>
              </a>
            </li>
            <li>
              <a href="#">
                <i className="fab fa-instagram"></i>
              </a>
            </li>
          </ul>
        </div>
      </footer>
    );
  }
}

export default FooterComponent;
