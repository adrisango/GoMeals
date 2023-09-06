import { React } from "react";
import "../styles/HeroComponent.css";
function HeroComponent(props) {
  return (
    <div>
      <section class="hero">
        <div class="hero-content">
          <h1>{props.header}</h1>
          <p>{props.body}</p>
          {/* <a href="#" class="cta-button">
            
          </a> */}
        </div>
      </section>
    </div>
  );
}

export default HeroComponent;
