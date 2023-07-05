import {
  MDBBtn,
  MDBCard,
  MDBCardBody,
  MDBContainer,
  MDBInput
} from 'mdb-react-ui-kit';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
  
  const Address = () => {
    const navigate = useNavigate();
    const [province, setProvince] = useState("");
    const [town, setTown] = useState("");
    const [street, setStreet] = useState("");
    const [house, setHouse]=  useState("");
    const [area, setArea] = useState("");
    
  
    
    function createAddress() {
      const reqBody = {
        province: province,
        house: house,
        street: street,
        suburb: town,
        area: area,
    
      };
  
      fetch("http://localhost:8080/tazma/api/users/address/" + localStorage.getItem("jwt"), {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(reqBody),
      })
      .then(
        navigate("/home")
      )
        };
    
  
    return (
      <>
      <MDBContainer fluid className='d-flex align-items-center justify-content-center bg-image' style={{backgroundImage: 'url(https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp)'}}>
        <div className='mask gradient-custom-3'></div>
        <MDBCard className='m-5' style={{maxWidth: '600px'}}>
          <MDBCardBody className='px-5'>
            <h2 className="text-uppercase text-center mb-5">Add Address Information</h2>
            <MDBInput wrapperClass='mb-4' value={province}
                  onChange={(e) => setProvince(e.target.value)} placeholder='Province' size='lg' id='province' type='text'/>
  
            <MDBInput wrapperClass='mb-4' value={area}
                  onChange={(e) => setArea(e.target.value)} placeholder='Area' size='lg' id='province' type='text'/>
  
            <MDBInput wrapperClass='mb-4' value={town}
                  onChange={(e) => setTown(e.target.value)} placeholder='Neighborhood' size='lg' id='town' type='text'/>
            
            <MDBInput wrapperClass='mb-4' value={street}
                  onChange={(e) => setStreet(e.target.value)} placeholder='Street Name/Number' size='lg' id='text' type='email'/>
            
            <MDBInput wrapperClass='mb-4' value={house}
                  onChange={(e) => setHouse(e.target.value)} placeholder='House Number' size='lg' id='phone' type='text'/>
            
            
            <br/>
  
            <MDBBtn style={{ width: '150px', height: '40px' }} className='mb-4 w-100 gradient-custom-4' size='lg' type="button" onClick={() => {
                  createAddress();
                }}>Save Address Information</MDBBtn>
            
          </MDBCardBody>
        </MDBCard>
      </MDBContainer>
      </>
    );
  }
  export default Address;