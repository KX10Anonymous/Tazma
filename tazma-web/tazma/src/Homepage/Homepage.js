import { MDBBadge, MDBBtn, MDBTable, MDBTableBody, MDBTableHead } from 'mdb-react-ui-kit';
import React, { useEffect, useState } from 'react';
const Homepage = () => {
  const [stylists, setStylists] = useState([]);
  const [selectedStylist, setSelectedStylist] = useState(null);

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
    <MDBTable align='middle'>
      <MDBTableHead>
        <tr>
          <th scope='col'>Name</th>
          <th scope='col'>Title</th>
          <th scope='col'>Status</th>
          <th scope='col'>Position</th>
          <th scope='col'>Actions</th>
        </tr>
      </MDBTableHead>
      <MDBTableBody>
        <tr>
          <td>
            <div className='d-flex align-items-center'>
              <img
                src='https://mdbootstrap.com/img/new/avatars/8.jpg'
                alt=''
                style={{ width: '45px', height: '45px' }}
                className='rounded-circle'
              />
              <div className='ms-3'>
                <p className='fw-bold mb-1'>John Doe</p>
                <p className='text-muted mb-0'>john.doe@gmail.com</p>
              </div>
            </div>
          </td>
          <td>
            <p className='fw-normal mb-1'>Software engineer</p>
            <p className='text-muted mb-0'>IT department</p>
          </td>
          <td>
            <MDBBadge color='success' pill>
              Active
            </MDBBadge>
          </td>
          <td>Senior</td>
          <td>
            <MDBBtn color='link' rounded size='sm'>
              Edit
            </MDBBtn>
          </td>
        </tr>
        <tr>
          <td>
            <div className='d-flex align-items-center'>
              <img
                src='https://mdbootstrap.com/img/new/avatars/6.jpg'
                alt=''
                style={{ width: '45px', height: '45px' }}
                className='rounded-circle'
              />
              <div className='ms-3'>
                <p className='fw-bold mb-1'>Alex Ray</p>
                <p className='text-muted mb-0'>alex.ray@gmail.com</p>
              </div>
            </div>
          </td>
          <td>
            <p className='fw-normal mb-1'>Consultant</p>
            <p className='text-muted mb-0'>Finance</p>
          </td>
          <td>
            <MDBBadge color='primary' pill>
              Onboarding
            </MDBBadge>
          </td>
          <td>Junior</td>
          <td>
            <MDBBtn color='link' rounded size='sm'>
              Edit
            </MDBBtn>
          </td>
        </tr>
        <tr>
          <td>
            <div className='d-flex align-items-center'>
              <img
                src='https://mdbootstrap.com/img/new/avatars/7.jpg'
                alt=''
                style={{ width: '45px', height: '45px' }}
                className='rounded-circle'
              />
              <div className='ms-3'>
                <p className='fw-bold mb-1'>Kate Hunington</p>
                <p className='text-muted mb-0'>kate.hunington@gmail.com</p>
              </div>
            </div>
          </td>
          <td>
            <p className='fw-normal mb-1'>Designer</p>
            <p className='text-muted mb-0'>UI/UX</p>
          </td>
          <td>
            <MDBBadge color='warning' pill>
              Awaiting
            </MDBBadge>
          </td>
          <td>Senior</td>
          <td>
            <MDBBtn color='link' rounded size='sm'>
              Edit
            </MDBBtn>
          </td>
        </tr>
      </MDBTableBody>
    </MDBTable>
    </>
  );
};

export default Homepage;
