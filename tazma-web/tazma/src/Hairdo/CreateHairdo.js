import { MDBBtn, MDBBtnGroup, MDBCard, MDBCardBody, MDBContainer, MDBFile, MDBInput, MDBRow } from 'mdb-react-ui-kit';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CreateHairdo() {
  const navigate = useNavigate();
  const [styleName, setStyleName] = useState("");
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
    const selectedFiles = e.target.files;
    const updatedFiles = Array.from(selectedFiles).map((file) => {
      return { file, originalName: file.name };
    });
    setFiles(updatedFiles);
   // console.log(e.target.files);
    ///console.log("Files added");
    //const selectedFiles = Array.prototype.slice.call(e.target.files);
    //setFiles(selectedFiles);
  };

  const saveHairdo = () => {
    const reqBody = {
      styleName: styleName,
      gender: gender,
    };
  
    fetch("http://localhost:8080/tazma/api/styles/create/" + localStorage.getItem("jwt"), {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) => response.json())
      .then((data) => {
        const id = data.id;
        const uploadPromises = [];
       /// console.log("New ID: " + id);
        //console.log("About to LOOP");
        for (let i = 0; i < files.length; i+=1) {
          //console.log("LOOPING!!!!!!!!!!!!!!!!");
          const { file, originalName } = files[i];
          const formData = new FormData();
          formData.append("file", file, originalName);
          console.log(formData);
          const uploadPromise = fetch("http://localhost:8080/tazma/api/styles/upload/" + id, {
            
            method: "post",
            body: formData,
          })
            .then((response) => response.json())
            .catch((error) => {
              throw new Error(error);
            });
          uploadPromises.push(uploadPromise);
          formData.delete("file");
        }
        return Promise.all(uploadPromises);
      })
      .then((results) => {
        // Handle the results of file uploads if needed
        console.log(results);
        navigate("/hairdo");
      })
      .catch((error) => {
        alert(error.message);
      });
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
              <MDBBtn style={{ width: '150px', height: '40px' }}  onClick={handleClickA} color={buttonColor1} value="MALE" name="options" id="role">
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
          
            <MDBFile label='Add Images' onChange={handleChange} size='sm' name="images" id="formFileMultiple" multiple accept='image/jpg, image/png, image/jpeg, image/webp' />
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

