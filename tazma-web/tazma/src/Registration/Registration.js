import {
  MDBBtn,
  MDBBtnGroup,
  MDBCard,
  MDBCardBody,
  MDBCheckbox,
  MDBContainer,
  MDBInput, MDBRow
} from 'mdb-react-ui-kit';
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { useUser } from "../UserProvider";

const Register = () => {
  const user = useUser();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstname, setFirstName] = useState("");
  const [lastname, setLastName]=  useState("");
  const [phone, setPhone] = useState("");
  const [role, setRole] = useState("");
  const [currJwt, setCurrJwt] = useState("");
  const [buttonColor1, setButtonColorA] = useState('info');
  const [buttonColor2, setButtonColorB] = useState('info');

  const handleClickA = (e) => {
    setButtonColorB('info');
    setRole(e.target.value);
    setButtonColorA('success');
  };

  const handleClickB = (e) => {
    setButtonColorA('info');
    setRole(e.target.value);
    setButtonColorB('success');
  };


  useEffect(() => {
    if (user.jwt) navigate("/home");
  }, [navigate, user]);

  function createAndLoginUser() {
    const reqBody = {
      email: email,
      password: password,
      firstname: firstname,
      lastname: lastname,
      phone: phone,
      role: role,
    };

    fetch("http://localhost:8080/tazma/api/auth/register", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => response.json().then(
        (data) => {
          user.setJwt(data.jwt);
        }
      ))
      .catch((message) => {
        alert(message);
      });
  }

  return (
    <>
    <MDBContainer fluid className='d-flex align-items-center justify-content-center bg-image' style={{backgroundImage: 'url(https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp)'}}>
      <div className='mask gradient-custom-3'></div>
      <MDBCard className='m-5' style={{maxWidth: '600px'}}>
        <MDBCardBody className='px-5'>
          <h2 className="text-uppercase text-center mb-5">Create an account</h2>
          <MDBInput wrapperClass='mb-4' value={firstname}
                onChange={(e) => setFirstName(e.target.value)} placeholder='First Name' size='lg' id='firstname' type='text'/>

          <MDBInput wrapperClass='mb-4' value={lastname}
                onChange={(e) => setLastName(e.target.value)} placeholder='Last Name' size='lg' id='firstname' type='text'/>
          
          <MDBInput wrapperClass='mb-4' value={email}
                onChange={(e) => setEmail(e.target.value)} placeholder='Your Email' size='lg' id='email' type='email'/>
          
          <MDBInput wrapperClass='mb-4' value={phone}
                onChange={(e) => setPhone(e.target.value)} placeholder='Phone Number' size='lg' id='phone' type='text'/>
          
          <MDBRow >
            <MDBBtnGroup >
              <MDBBtn style={{ width: '150px', height: '40px' }} onClick={handleClickA} color={buttonColor1} value="CLIENT" name="options" id="role">
                Client
              </MDBBtn>
              <MDBBtn style={{ width: '150px', height: '40px' }} color={buttonColor2} onClick={handleClickB}   value="Sylist" name="options" id="role">
                Stylist
              </MDBBtn>
              
            </MDBBtnGroup>
          </MDBRow>
          <br/>

          <MDBInput wrapperClass='mb-4' placeholder='Password' value={password}
                onChange={(e) => setPassword(e.target.value)} size='lg' id='password' type='password'/>
          
          <div className='d-flex flex-row justify-content-center mb-4'>
            <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I agree all statements in Terms of service' />
          </div>
          <MDBBtn style={{ width: '150px', height: '40px' }} className='mb-4 w-100 gradient-custom-4' size='lg' type="button" onClick={() => {
                createAndLoginUser();
              }}>Register</MDBBtn>
          <MDBBtn style={{ width: '150px', height: '40px' }} className='mb-4 w-100 gradient-custom-4' size='lg' type="button" onClick={() => {
                navigate("/login");
              }}>Already have an account?, Sign in.</MDBBtn>
              
        </MDBCardBody>
      </MDBCard>
    </MDBContainer>
    </>
  );
}
export default Register;