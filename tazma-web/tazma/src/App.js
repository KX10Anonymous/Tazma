/* eslint-disable no-unused-vars */
import jwt_decode from "jwt-decode";
import { useEffect, useState } from "react";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import Appointments from './Appointments/Appointments';
import Homepage from "./Homepage/Homepage";
import Login from "./Login/Login";
import Register from "./Registration/Registration";
import { useUser } from "./UserProvider";
import "./custom.scss";


function App() {
  const [roles, setRoles] = useState([]);
  const user = useUser();

  useEffect(() => {
    setRoles(getRolesFromJWT());
  }, [getRolesFromJWT, user.jwt]);

  // eslint-disable-next-line react-hooks/exhaustive-deps
  function getRolesFromJWT() {
    if (user.jwt) {
      const decodedJwt = jwt_decode(user.jwt);
      return decodedJwt.authorities;
    }
    return [];
  }
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="register" element={<Register/>}/>
      <Route path="home" element={<Homepage />} />
      <Route path="/" element={<Homepage />} />
      <Route path="appointments" element={<Appointments/>}/>
      <Route path="hairdo"/>
    </Routes>
  );
}

export default App;
