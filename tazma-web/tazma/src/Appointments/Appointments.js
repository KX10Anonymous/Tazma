import { MDBBadge, MDBBtn, MDBContainer } from "mdbreact";
import React, { useEffect, useState } from "react";
import { FaHouseUser, FaShopware } from "react-icons/fa";
import { useUser } from "../UserProvider";


const Appointments = () => {
  const [appointments, setAppointments] = useState([]);
  const user = useUser();
  const [jwt, setJwt] = useState("");

  useEffect(() => {
    const fetchAppointments = async () => {
      setJwt(user.jwt);
      console.log("Reading Appointments for User: " + jwt);
      if (user.role !== "ADMIN") {
        await fetch("http://localhost:8080/tazma/api/appointments/all/" + jwt, {
          headers: {
            "Content-Type": "application/json",
          },
          method: "GET",
        })
          .then((response) =>
            response.json().then((data) => {
              setAppointments(data);
            })
          )
          .catch((message) => {
            
          });
      }
    };
    fetchAppointments();
  }, [jwt, user, setAppointments]);

  const renderAppointmentBadge = (status) => {
    let color = "";
    let text = "";

    switch (status) {
      case "OPEN":
        color = "success";
        text = "Open";
        break;
      case "ACCEPTED":
        color = "warning";
        text = "Accepted";
        break;
      case "CLOSED":
        color = "danger";
        text = "Closed";
        break;
      default:
        color = "secondary";
        text = "Unknown";
    }

    return <MDBBadge color={color}>{text}</MDBBadge>;
  };

  const renderAppointmentTypeBadge = (type) => {
    switch (type) {
      case "CLIENT_VISIT":
        return <FaShopware />;
      case "HOUSE_CALL":
        return <FaHouseUser color="success"/>;
      default:
        return null;
    }
  };

  const handleRating = (appointmentId, rating) => {
    
  };

  const renderStylistName = (stylist, id) =>{
    const device = localStorage.getItem("device");
    if(device ==="phone"){
      return null;
    }else{
      <MDBBadge
      color="info rounded-pill"
      onClick={() => handleRating(id, "THREE")}>
      {stylist}
    </MDBBadge>
    }
  };

  const shouldDisplayRating = (appointment) => {
    const currentDate = new Date();
    const appointmentDate = new Date(appointment.time);
    //appointment.status === 'closed' &&
    return appointmentDate < currentDate;
  };

  return (
    <MDBContainer>
      {appointments.map((appointment) => (
        <div key={appointment.id} className="my-4 p-4 border">
          <div className="d-flex justify-content-between align-items-center mb-2">
            {renderAppointmentBadge(appointment.status)}
            <MDBBadge
              color="info rounded-pill"
              onClick={() => handleRating(appointment.id, "THREE")}>
              {appointment.time}
            </MDBBadge>
            <>
              {renderStylistName.bind(null,appointment.stylist, appointment.id)}
            </>
           
            <MDBBadge
              color="info rounded-pill"
              onClick={() => handleRating(appointment.id, "THREE")}>
              Offer: R{appointment.offer}
            </MDBBadge>
          </div>
          <div className="d-flex justify-content-between align-items-center">
            <div>{renderAppointmentTypeBadge(appointment.type)}</div>
            <div>
              {appointment.description}
              {shouldDisplayRating(appointment) && (
                <div>
                  <MDBBtn
                    color="danger rounded-pill"
                    onClick={() => handleRating(appointment.id, "ONE")}>
                    1
                  </MDBBtn>
                  <MDBBtn
                    color="warning rounded-pill"
                    onClick={() => handleRating(appointment.id, "TWO")}>
                    2
                  </MDBBtn>
                  <MDBBtn
                    color="success rounded-pill"
                    onClick={() => handleRating(appointment.id, "THREE")}>
                    3
                  </MDBBtn>
                  <MDBBtn
                    color="primary rounded-pill"
                    onClick={() => handleRating(appointment.id, "FOUR")}>
                    4
                  </MDBBtn>
                  <MDBBtn
                    color="secondary rounded-pill"
                    onClick={() => handleRating(appointment.id, "FIVE")}>
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
