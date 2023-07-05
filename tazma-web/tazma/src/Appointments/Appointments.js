import {
  MDBCarousel,
  MDBCarouselItem
} from 'mdb-react-ui-kit';
import React from 'react';

export default function Appointments() {
  return (
    <MDBCarousel showIndicators showControls fade>
      <MDBCarouselItem className='active'>
        <img src='https://mdbootstrap.com/img/Photos/Slides/img%20(15).webp' alt='...' className='img-fluid shadow-4'/>
      </MDBCarouselItem>

      <MDBCarouselItem>
        <img src='https://mdbootstrap.com/img/Photos/Slides/img%20(22).webp' alt='...' className='img-fluid shadow-4'/>
      </MDBCarouselItem>

      <MDBCarouselItem>
        <img src='https://mdbootstrap.com/img/Photos/Slides/img%20(23).webp' alt='...' className='img-fluid shadow-4'/>
      </MDBCarouselItem>
  </MDBCarousel>
  );
}
