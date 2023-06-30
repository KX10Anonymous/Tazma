import {
  MDBBtn,
  MDBCard,
  MDBCardBody,
  MDBContainer,
  MDBInput
} from 'mdb-react-ui-kit';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useUser } from "../UserProvider";

const Login = () => {
  const user = useUser();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState(null);

  // useEffect(() => {
  //   if (user.jwt) navigate("/dashboard");
  // }, [user]);

  function sendLoginRequest() {
    setErrorMsg("");
    const reqBody = {
      email: email,
      password: password,
    };

    fetch("api/auth/login", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => {
        if (response.status === 200) return response.text();
        else if (response.status === 401 || response.status === 403) {
          setErrorMsg("Invalid username or password");
        } else {
          setErrorMsg(
            "Something went wrong, try again later or reach out to trevor@coderscampus.com"
          );
        }
      })
      .then((data) => {
        if (data) {
          user.setJwt(data);
          navigate("/dashboard");
        }
      });
  }
  return (
    <>
    <MDBContainer fluid className='d-flex align-items-center justify-content-center bg-image' style={{backgroundImage: 'url(https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp)'}}>
      <div className='mask gradient-custom-3'></div>
      <MDBCard className='m-5' style={{maxWidth: '600px'}}>
        <MDBCardBody className='px-5'>
          <h2 className="text-uppercase text-center mb-5">Sign in.</h2>
          
          <MDBInput wrapperClass='mb-4' value={email}
                onChange={(e) => setEmail(e.target.value)} placeholder='Your Email' size='lg' id='email' type='email'/>
          
          
          <MDBInput wrapperClass='mb-4' placeholder='Password' value={password}
                onChange={(e) => setPassword(e.target.value)} size='lg' id='password' type='password'/>
          
          <MDBBtn style={{ width: '150px', height: '40px' }} className='mb-4 w-100 gradient-custom-4' size='lg' type="button" onClick={() => {
                sendLoginRequest();
              }}>Login</MDBBtn>
          <MDBBtn style={{ width: '150px', height: '40px' }} className='mb-4 w-100 gradient-custom-4' size='lg' type="button" onClick={() => {
                navigate("/register");
              }}>Do not have an account?, Sign up.</MDBBtn>
              
        </MDBCardBody>
      </MDBCard>
    </MDBContainer>
    </>
  );
};

export default Login;



