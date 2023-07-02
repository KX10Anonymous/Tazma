import {
  MDBCollapse,
  MDBContainer,
  MDBIcon,
  MDBNavbar,
  MDBNavbarBrand,
  MDBNavbarItem,
  MDBNavbarLink,
  MDBNavbarNav,
  MDBNavbarToggler
} from 'mdb-react-ui-kit';
import React, { useEffect, useState } from "react";
import { useUser } from "../UserProvider";

function NavBar() { 
  const user = useUser();
  const [showNavNoTogglerThird, setShowNavNoTogglerThird] = useState(false);

  const handleLogout = async () => {
   // try {
      // Send a logout request to the API endpoint
      
      //const response = await fetch('http://localhost:8080/tazma/api/auth/logout/'+localStorage.getItem('jwt'), {
        //method: 'GET',
        //headers: {
          //'Content-Type': 'application/json',
          //Authorization: localStorage.getItem('jwt'),
        //},
        
      //});
      //console.log(response);
      // Clear the JWT from local storage
      localStorage.clear();
      user.setJwt("");
      // Perform any additional cleanup or redirect to the login page
      // ...
    //} catch (error) {
      // Handle any network or other errors
      //console.error('Logout request failed:', error);
    //}
  };

  useEffect(() => {
    user.setJwt(localStorage.getItem("jwt"));
  }, [user, user.jwt]);

  return (
    <>
      <MDBNavbar expand='lg' light bgColor='light'>
        <MDBContainer fluid>
          <MDBNavbarToggler
            type='button'
            data-target='#navbarTogglerDemo03'
            aria-controls='navbarTogglerDemo03'
            aria-expanded='false'
            aria-label='Toggle navigation'
            onClick={() => setShowNavNoTogglerThird(!showNavNoTogglerThird)}
          >
            <MDBIcon icon='bars' fas />
          </MDBNavbarToggler>
          <MDBNavbarBrand href='#'>tazma</MDBNavbarBrand>
          <MDBCollapse navbar show={showNavNoTogglerThird}>
            <MDBNavbarNav className='mr-auto mb-2 mb-lg-0'>
              <MDBNavbarItem>
                <MDBNavbarLink active aria-current='page' href='/home'>
                  Home
                </MDBNavbarLink>
              </MDBNavbarItem>
              <MDBNavbarItem>
                <MDBNavbarLink href='#'>Stylists</MDBNavbarLink>
              </MDBNavbarItem>
              <MDBNavbarItem>
                <MDBNavbarLink href='/appointments'>Hairdo</MDBNavbarLink>
              </MDBNavbarItem>
              <MDBNavbarItem>
                <MDBNavbarLink href='#'>About</MDBNavbarLink>
              </MDBNavbarItem>
              <MDBNavbarItem>
                <MDBNavbarLink href='#'>Contact</MDBNavbarLink>
              </MDBNavbarItem>
              {user && user.jwt ? (
                <>
                  <MDBNavbarItem>
                    <MDBNavbarLink href='#'>Appointments</MDBNavbarLink>
                  </MDBNavbarItem>
                  <MDBNavbarItem>
                  <MDBNavbarLink  onClick={handleLogout} tabIndex={0} aria-disabled='false'>
                    Logout
                  </MDBNavbarLink>
                  </MDBNavbarItem>
                </>
                ) : (
                <>
                  <MDBNavbarItem>
                    <MDBNavbarLink href='/login'>Login</MDBNavbarLink>
                  </MDBNavbarItem>
                  <MDBNavbarItem></MDBNavbarItem>
                </>
              )}
            </MDBNavbarNav>
          </MDBCollapse>
        </MDBContainer>
      </MDBNavbar>
    </>
  );
}

export default NavBar;
