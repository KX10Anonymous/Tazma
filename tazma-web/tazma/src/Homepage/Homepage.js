import { MDBTabs, MDBTabsContent, MDBTabsItem, MDBTabsLink, MDBTabsPane } from 'mdb-react-ui-kit';
import React, { useEffect, useState } from 'react';
import CreateHairdo from '../Hairdo/CreateHairdo';
import { useUser } from "../UserProvider";
const Homepage = () => {
  const [stylists, setStylists] = useState([]);
  const user = useUser();
  const [selectedStylist, setSelectedStylist] = useState(null);

  const [fillActive, setFillActive] = useState('tab1');

  const handleFillClick = (value) => {
    if (value === fillActive) {
      return;
    }

    setFillActive(value);
  };

  useEffect(() => {
    fetchStylists();
  }, []);

  const fetchStylists = async () => {
    try {
      // Make an API request to fetch the list of high-rated stylists
      const response = await fetch('https://your-api-endpoint.com/stylists');
      const data = await response.json();

      // Update the stylists state with the fetched data
      setStylists(data);
    } catch (error) {
      console.error('Error fetching stylists:', error);
    }
  };

  const handleStylistSelect = (stylist) => {
    // Set the selected stylist when the user selects a marker on the map
    setSelectedStylist(stylist);
  };

  const handleAppointmentSet = async (event) => {
    try {
      // Validate if a stylist is selected
      if (!selectedStylist) {
        console.log('No stylist selected');
        return;
      }
  
      // Retrieve the user object using JWT authentication
      const token = 'YOUR_JWT_TOKEN'; // Replace with the actual JWT token
      const userResponse = await fetch('https://your-api-endpoint.com/user', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      if (!userResponse.ok) {
        console.error('Error fetching user:', userResponse.status);
        // Handle the error and show an error message to the user
        return;
      }
  
      const user = await userResponse.json();
  
      // Retrieve the location information from the map click event
      const { latitude, longitude } = event.nativeEvent.coordinate;
      const address = event.nativeEvent.address; // Replace with the actual address based on the coordinates
  
      // Create the location object
      const location = {
        latitude,
        longitude,
        address,
      };
  
      // Create the appointment object using the selected stylist, user, and location data
      const appointment = {
        stylist: selectedStylist,
        user,
        location,
        datetime: new Date().toISOString(), // Replace with the actual appointment datetime
        // Additional appointment details can be included here
      };
  
      // Send the appointment object to the API
      const appointmentResponse = await fetch('https://localhost:8080/appointments/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(appointment),
      });
  
      if (appointmentResponse.ok) {
        console.log('Appointment set:', selectedStylist);
        // Show success message or perform any additional actions
      } else {
        console.error('Error setting appointment:', appointmentResponse.status);
        // Handle the error and show an error message to the user
      }
    } catch (error) {
      console.error('Error setting appointment:', error);
      // Handle the error and show an error message to the user
    }
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
        <MDBTabsPane show={fillActive === 'tab1'}>Tab 1 content</MDBTabsPane>
        <MDBTabsPane show={fillActive === 'tab2'}><CreateHairdo/></MDBTabsPane>
        <MDBTabsPane show={fillActive === 'tab3'}>Tab 3 content</MDBTabsPane>
      </MDBTabsContent>
    </>
  );
};

export default Homepage;
