import { MDBBtn, MDBBtnGroup, MDBCard, MDBCardBody, MDBContainer, MDBFile, MDBInput, MDBRow } from 'mdb-react-ui-kit';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CreateHairdo() {
  const navigate = useNavigate();
  const [styleName, setStyleName] = useState("");
  const [id, setId] = useState();
  const [files, setFiles] = useState([]);
  const [buttonColor1, setButtonColorA] = useState('info');
  const [buttonColor2, setButtonColorB] = useState('info');
  const [buttonColor3, setButtonColorC] = useState('info');
  const [gender, setGender] = useState("");

  const handleClickA = (e) => {
    setButtonColorB('info');
    setButtonColorC('info');
    setGender(e.target.value);
    setButtonColorA('success');
  };

  const handleClickB = (e) => {
    setButtonColorA('info');
    setButtonColorC('info');
    setGender(e.target.value);
    setButtonColorB('success');
  };

  const handleClickC = (e)=>{
    setButtonColorA('info');
    setButtonColorB('info');
    setButtonColorC('success');
    setGender(e.target.value);
  };

  const handleChange = (e)=>{
    setFiles(e.target.files);
  };

  const saveHairdo = ()=>{
    
    const reqBody = {
        styleName: styleName,
        gender: gender,
      };

    fetch("http://localhost:8080/tazma/api/styles/create", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => response.json().then(
        (data) => {
          setId(data.id);
        }
      ))
      .catch((message) => {
        alert(message);
      });

      const formData = new FormData();
    for(let i = 0; i < files.length; i++){
        formData.append(`images[${i}]`);
        fetch("http://localhost:8080/tazma/api/styles/upload/"+id, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(formData),
    })
      .then((response) => response.json())
      .catch((message) => {
        alert(message);
      });
      formData.delete(`images[${i}]`)
    }
    navigate("/hairdo")
  };
  return (
    <>
    <MDBContainer fluid className='d-flex align-items-center justify-content-center bg-image' style={{backgroundImage: 'url(https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp)'}}>
      <div className='mask gradient-custom-3'></div>
      <MDBCard className='m-5' style={{maxWidth: '600px'}}>
        <MDBCardBody className='px-5'>
          <h2 className="text-uppercase text-center mb-5">Create New Hairdo</h2>
          <MDBInput wrapperClass='mb-4' value={styleName}
                onChange={(e) => setStyleName(e.target.value)} label='Hairdo Name'  id='styleName' type='text'/>

          <MDBRow >
            <MDBBtnGroup >
              <MDBBtn style={{ width: '150px', height: '40px' }} onClick={handleClickA} color={buttonColor1} value="MALE" name="options" id="role">
                Male
              </MDBBtn>
              <MDBBtn style={{ width: '150px', height: '40px' }} color={buttonColor2} onClick={handleClickB}   value="FEMALE" name="options" id="role">
                Female
              </MDBBtn>
              <MDBBtn style={{ width: '150px', height: '40px' }} color={buttonColor3} onClick={handleClickC}   value="UNISEX" name="options" id="role">
                Unisex
              </MDBBtn>
            </MDBBtnGroup>
          </MDBRow>
          <br/>
          
          <MDBRow>
          
            <MDBFile label='Add Images' onClick={handleChange} size='sm' id="formFileMultiple" multiple accept='image/jpg, image/png, image/jpeg, image/webp' />
          </MDBRow>
          <br/>

          <MDBBtn style={{ width: '150px', height: '40px' }} className='mb-4 w-100 gradient-custom-4' size='lg' type="button" onClick={() => {
                saveHairdo();
              }}>Save Hairdo</MDBBtn>
        </MDBCardBody>
      </MDBCard>
    </MDBContainer>
    </>
  );
}

