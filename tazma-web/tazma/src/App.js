/* eslint-disable no-unused-vars */

import React from 'react';
import { Route, Routes } from "react-router-dom";
import Address from "./Address/Address";
import "./App.css";
import Appointments from './Appointments/Appointments';
import AppointmentForm from './Appointments/Form';
import CreateHairdo from "./Hairdo/CreateHairdo";
import Homepage from "./Homepage/Homepage";
import Login from "./Login/Login";
import Posts from "./Posts/Posts";
import Register from "./Registration/Registration";
import Test from "./Users/Test";
import "./custom.scss";

function App() {
  

  React.useEffect(() => {
    const checkDeviceType = () => {
      const screenWidth = window.innerWidth;
      if (screenWidth >= 1024) {
        localStorage.setItem("device", "desktop");
        
      } else if (screenWidth >= 768) {
        localStorage.setItem("device", "tablet");
      } else {
        localStorage.setItem("device", "phone");
      }
    
    };

    checkDeviceType();
    //Update When window is resized
    window.addEventListener('resize', checkDeviceType);

    //Unmount the listener
    return () => {
      window.removeEventListener('resize', checkDeviceType);
    };
  }, []);
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register/>}/>
      <Route path="home" element={<Homepage />} />
      <Route path="/" element={<Homepage />} />
      <Route path="appointments" element={<Appointments/>}/>
      <Route path="address" element={<Address/>}/>
      <Route path="hairdo" element={<CreateHairdo/>}/>
      <Route path="test" element={<Test/>}/>
      <Route path="posts" element={<Posts/>}/>
      <Route path="create" element={<AppointmentForm/>}/>
    </Routes>
  );
}

export default App;
