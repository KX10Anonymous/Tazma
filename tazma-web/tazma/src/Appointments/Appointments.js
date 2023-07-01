import {
  MDBBtn,
  MDBCard,
  MDBCardBody,
  MDBCardImage,
  MDBCardText,
  MDBCardTitle,
  MDBCol,
  MDBRow
} from 'mdb-react-ui-kit';
import React from 'react';

export default function Appointments() {
  return (
    <MDBRow className="justify-content-center">
      <MDBCol className='mb-4'>
        <MDBCard style={{ maxWidth: '22rem' }} aria-hidden='true'>
          <MDBCardImage
            src='https://mdbcdn.b-cdn.net/img/new/standard/nature/182.webp'
            position='top'
            alt='Sunset Over the Sea'
          />
          <MDBCardBody>
          <MDBCardTitle className='placeholder-glow'>
            <span className='placeholder col-6'></span>
          </MDBCardTitle>
          <MDBCardText className='placeholder-glow'>
            <span className='placeholder col-7'></span>
            <span className='placeholder col-4'></span>
            <span className='placeholder col-4'></span>
            <span className='placeholder col-6'></span>
            <span className='placeholder col-8'></span>
          </MDBCardText>
        <MDBBtn href='#' tabIndex={-1} disabled className='placeholder col-6'></MDBBtn>
      </MDBCardBody>
    </MDBCard>
      </MDBCol>
      <MDBCol className='mb-4'>
        <MDBCard style={{ maxWidth: '22rem' }} aria-hidden='true'>
          <MDBCardImage
            src='https://mdbcdn.b-cdn.net/img/new/standard/nature/182.webp'
            position='top'
            alt='Sunset Over the Sea'
          />
          <MDBCardBody>
          <MDBCardTitle className='placeholder-glow'>
            <span className='placeholder col-6'></span>
          </MDBCardTitle>
          <MDBCardText className='placeholder-glow'>
            <span className='placeholder col-7'></span>
            <span className='placeholder col-4'></span>
            <span className='placeholder col-4'></span>
            <span className='placeholder col-6'></span>
            <span className='placeholder col-8'></span>
          </MDBCardText>
        <MDBBtn href='#' tabIndex={-1} disabled className='placeholder col-6'></MDBBtn>
      </MDBCardBody>
    </MDBCard>
      </MDBCol>
      <MDBCol className='mb-4'>
        <MDBCard style={{ maxWidth: '22rem' }} aria-hidden='true'>
          <MDBCardImage
            src='../../public/Images/hairdos/afro-skin-fade.webp'
            position='top'
            alt='Sunset Over the Sea' 
          />
          <MDBCardBody>
          <MDBCardTitle className='placeholder-glow'>
            <span className='placeholder col-6'></span>
          </MDBCardTitle>
          <MDBCardText className='placeholder-glow'>
            <span className='placeholder col-7'></span>
            <span className='placeholder col-4'></span>
            <span className='placeholder col-4'></span>
            <span className='placeholder col-6'></span>
            <span className='placeholder col-8'></span>
          </MDBCardText>
        <MDBBtn href='#' tabIndex={-1} disabled className='placeholder col-6'></MDBBtn>
      </MDBCardBody>
    </MDBCard>
      </MDBCol>
      <MDBCol className='mb-4'>
        <MDBCard style={{ maxWidth: '22rem' }} aria-hidden='true'>
          <MDBCardImage
            src='https://mdbcdn.b-cdn.net/img/new/standard/nature/182.webp'
            position='top'
            alt='Sunset Over the Sea'
          />
          <MDBCardBody>
          <MDBCardText className='placeholder-glow'>
            <span className='placeholder col-7'>Corn Rows</span>
          </MDBCardText>
        <MDBBtn href='#' tabIndex={-1} disabled className='placeholder col-12 justify-content-center'>Make Appointment</MDBBtn>
      </MDBCardBody>
    </MDBCard>
      </MDBCol>
    </MDBRow>
  );
}
