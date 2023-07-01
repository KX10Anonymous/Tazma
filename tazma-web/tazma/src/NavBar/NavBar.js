import jwt_decode from "jwt-decode";
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
import { useLocation, useNavigate } from "react-router-dom";
import { useUser } from "../UserProvider";

function NavBar() {
  const navigate = useNavigate();
  const { pathname } = useLocation();
  const user = useUser();
  const [authorities, setAuthorities] = useState(null);
  const [showNavNoTogglerThird, setShowNavNoTogglerThird] = useState(false);

  useEffect(() => {
    user.setJwt(localStorage.getItem("jwt"));
    if (user && user.jwt) {
      const decodedJwt = jwt_decode(user.jwt);
      setAuthorities(decodedJwt.authorities);
    }
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
                  <MDBNavbarLink disabled href='#' tabIndex={-1} aria-disabled='true'>
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
