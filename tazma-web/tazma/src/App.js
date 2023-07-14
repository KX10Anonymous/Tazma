/* eslint-disable no-unused-vars */

import { Route, Routes } from "react-router-dom";
import Address from "./Address/Address";
import "./App.css";
import Appointments from './Appointments/Appointments';
import Homepage from "./Homepage/Homepage";
import Login from "./Login/Login";
import Register from "./Registration/Registration";
import { useUser } from "./UserProvider";
import "./custom.scss";


function App() {
  const user = useUser();
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register/>}/>
      <Route path="home" element={<Homepage />} />
      <Route path="/" element={<Homepage />} />
      <Route path="appointments" element={<Appointments/>}/>
      <Route path="address" element={<Address/>}/>
      <Route path="hairdo"/>
    </Routes>
  );
}

export default App;
