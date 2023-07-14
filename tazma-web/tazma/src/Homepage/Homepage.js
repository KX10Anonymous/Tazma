import { MDBTabs, MDBTabsContent, MDBTabsItem, MDBTabsLink, MDBTabsPane } from 'mdb-react-ui-kit';
import React, { useState } from 'react';
import Appointments from "../Appointments/Appointments";
import Hairdo from '../Hairdo/Hairdo';
import { useUser } from "../UserProvider";
const Homepage = () => {
  const user = useUser();

  const [fillActive, setFillActive] = useState('tab1');

  const handleFillClick = (value) => {
    if (value === fillActive) {
      return;
    }
    setFillActive(value);
  };

  return (
    <>
          <MDBTabs fill className='mb-3'>
          {user && user.jwt ? (
          <>
            <MDBTabsItem>
              <MDBTabsLink onClick={() => handleFillClick('tab1')} active={fillActive === 'tab1'}>
                Appointments
              </MDBTabsLink>
            </MDBTabsItem>
            <MDBTabsItem>
              <MDBTabsLink onClick={() => handleFillClick('tab2')} active={fillActive === 'tab2'}>
                Hairdo
              </MDBTabsLink>
            </MDBTabsItem>
            <MDBTabsItem>
              <MDBTabsLink onClick={() => handleFillClick('tab3')} active={fillActive === 'tab3'}>
                Stylists
              </MDBTabsLink>
            </MDBTabsItem>
            </>
          ) : (
            <>
            <MDBTabsItem>
              <MDBTabsLink onClick={() => handleFillClick('tab2')} active={fillActive === 'tab2'}>
                Hairdo
              </MDBTabsLink>
            </MDBTabsItem>
            <MDBTabsItem>
              <MDBTabsLink onClick={() => handleFillClick('tab3')} active={fillActive === 'tab3'}>
                Stylists
              </MDBTabsLink>
            </MDBTabsItem>
            </>
          )}
          </MDBTabs>

      <MDBTabsContent>
        <MDBTabsPane show={fillActive === 'tab1'}><Appointments/></MDBTabsPane>
        <MDBTabsPane show={fillActive === 'tab2'}><Hairdo/></MDBTabsPane>
        <MDBTabsPane show={fillActive === 'tab3'}></MDBTabsPane>
      </MDBTabsContent>
    </>
  );
};

export default Homepage;
