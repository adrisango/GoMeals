import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import Register from "./pages/Register";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import SupplierRegister from "./pages/SupplierRegister";
import CustomerDashboard from "./pages/CustomerDashboard";
import SupplierLogin from "./pages/SupplierLogin";
import SupplierDashboard from "./pages/SupplierDashboard";
import React, { useState } from "react";
import CustomerDeliveries from "./pages/CustomerDeliveries";
import CustomerRaiseComplain from "./pages/CustomerRaiseComplain";
import CustomerComplainTracker from "./pages/CustomerComplainTracker";
import SupplierPolling from "./pages/SupplierPolling";
import CustomerProfile from "./pages/CustomerProfile";
import SupplierComplain from "./pages/SupplierComplain";
import SubscriptionRequests from "./pages/SubscriptionRequests";
import CustomerPolls from "./pages/CustomerPolls";
import CustomerPaymentHistory from "./pages/CustomerPaymentHistory";
import FooterComponent from "./components/FooterComponent";
import SupplierPollingDetails from "./pages/SupplierPollingDetails";
import SupplierProfile from "./pages/SupplierProfile";
import NavbarComponent from "./components/NavbarComponent";
import HeroComponent from "./components/HeroComponent";
//import { useNavigate } from "react-router-dom";

function App() {
  //const navigate = useNavigate();
  const [showNavbar, setShowNavbar] = useState(true);

  // Redirect to login page if user is not authenticated
  //  const isAuthenticated = true; // Replace with your authentication logic
  //  if (!isAuthenticated && location.pathname !== "/login") {
  //    navigate("/login");
  //  }
  return (
    <div className="App">
      <Router>
        <NavbarComponent />
        <div className="appc">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<Register />} />
            <Route path="/supplierRegister" element={<SupplierRegister />} />
            {/* <Route path="/" element={<HeroComponent />} /> */}
            <Route path="/dashboard" element={<CustomerDashboard />} />
            <Route path="/supplierLogin" element={<SupplierLogin />} />
            <Route path="/supplierDashboard" element={<SupplierDashboard />} />
            <Route path="/customerOrders" element={<CustomerDeliveries />} />
            <Route path="/customerPollVote" element={<CustomerPolls />} />
            <Route path="/customerProfile" element={<CustomerProfile />} />
            <Route
              path="/customerRaiseComplain/:id"
              element={<CustomerRaiseComplain />}
            />
            <Route
              path="/complainTracker"
              element={<CustomerComplainTracker />}
            />
            <Route
              path="/customerPaymentHistory"
              element={<CustomerPaymentHistory />}
            />
            <Route path="/supplierPolling" element={<SupplierPolling />} />
            <Route
              path="/supplierComplain"
              element={<SupplierComplain />}
            ></Route>
            <Route
              path="subscriptionRequests"
              element={<SubscriptionRequests />}
            ></Route>
            <Route
              path="/supplierPollingDetails"
              element={<SupplierPollingDetails />}
            />
            <Route path="/supplierProfile" element={<SupplierProfile />} />
          </Routes>
        </div>
      </Router>

      <FooterComponent />
    </div>
  );
}

export default App;
