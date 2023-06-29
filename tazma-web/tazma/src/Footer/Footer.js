import { MDBContainer, MDBFooter } from 'mdb-react-ui-kit';
import React from 'react';

export default function Footer() {
  return (
    <MDBFooter className='text-center text-white' style={{ backgroundColor: '#21081a' }}>
      <MDBContainer className='p-4'></MDBContainer>

      <div className='text-center p-3' style={{ backgroundColor: 'rgba(0, 234, 0, 0.2)' }}>
        © 2023 Copyright:
        <a className='text-white' href='https://tazma.com/'>
          tazma.com
        </a>
      </div>
    </MDBFooter>
  );
}

