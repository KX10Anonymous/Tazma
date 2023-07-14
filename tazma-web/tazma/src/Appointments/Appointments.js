import { MDBBadge, MDBBtn, MDBContainer } from 'mdbreact';
import React, { useEffect, useState } from 'react';
import { FaHouseUser, FaShopware } from 'react-icons/fa';

const Appointments = () => {
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {

      const response = await fetch('http://localhost:8080/tazma/api/appointments/all/'+localStorage.getItem('jwt'), {
        headers: {
          "Content-Type": "application/json",
        },
        method: "GET",
      });
  
      const data = await response.json();
      setAppointments(data);
    
  };

  const renderAppointmentBadge = (status) => {
    let color = '';
    let text = '';

    switch (status) {
      case 'OPEN':
        color = 'success';
        text = 'Open';
        break;
      case 'ACCEPTED':
        color = 'warning';
        text = 'Accepted';
        break;
      case 'CLOSED':
        color = 'danger';
        text = 'Closed';
        break;
      default:
        color = 'secondary';
        text = 'Unknown';
    }

    return <MDBBadge color={color}>{text}</MDBBadge>;
  };

  const renderAppointmentTypeBadge = (type) => {
    switch (type) {
      case 'client-visit':
        return <FaShopware />;
      case 'house-call':
        return <FaHouseUser />;
      default:
        return null;
    }
  };

  const handleRating = (appointmentId, rating) => {
    // Send rating to API or perform rating logic here
    console.log(`Rating ${rating} for appointment with ID ${appointmentId}`);
  };

  const shouldDisplayRating = (appointment) => {
    const currentDate = new Date();
    const appointmentDate = new Date(appointment.time);
    //appointment.status === 'closed' &&
    return  appointmentDate < currentDate;
  };

  return (
    <MDBContainer>
      {appointments.map((appointment) => (
        <div key={appointment.id} className="my-4 p-4 border">
          <div className="d-flex justify-content-between align-items-center mb-2">
            {renderAppointmentBadge(appointment.status)}
            <span>{appointment.time}</span>
          </div>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              {renderAppointmentTypeBadge(appointment.type)}
            </div>
            <div>
              {appointment.description}
              {shouldDisplayRating(appointment) && (
                <div>
                  <MDBBtn color="danger rounded-pill" onClick={() => handleRating(appointment.id, 'ONE')}>
                    1
                  </MDBBtn>
                  <MDBBtn color="warning rounded-pill" onClick={() => handleRating(appointment.id, 'TWO')}>
                    2
                  </MDBBtn>
                  <MDBBtn color="success rounded-pill" onClick={() => handleRating(appointment.id, 'THREE')}>
                    3
                  </MDBBtn>
                  <MDBBtn color="primary rounded-pill" onClick={() => handleRating(appointment.id, 'FOUR')}>
                    4
                  </MDBBtn>
                  <MDBBtn color="secondary rounded-pill" onClick={() => handleRating(appointment.id, 'FIVE')}>
                    5
                  </MDBBtn>
                </div>
              )}
            </div>
          </div>
        </div>
      ))}
    </MDBContainer>
  );
};



export default Appointments;

