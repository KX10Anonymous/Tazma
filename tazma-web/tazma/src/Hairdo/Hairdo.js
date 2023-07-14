import axios from 'axios';
import { MDBBtn, MDBCard, MDBCardBody, MDBCardImage, MDBCardText, MDBCardTitle, MDBCol, MDBContainer, MDBRow } from 'mdbreact';
import React, { useEffect, useState } from 'react';
import DatePicker from "react-datepicker";
const Hairdo = () => {
  const [data, setData] = useState([]);
  const [selectedImageId, setSelectedImageId] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8080/tazma/api/styles/styles", {
        headers: {
          "Content-Type": "application/json",
        },
        method: "GET",
      });
  
      if (!response.ok) {
        throw new Error("Failed to fetch styles");
      }
  
      const data = await response.json();
      setData(data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const handleImageClick = (imageId) => {
    setSelectedImageId(imageId);
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const handleSendData = () => {
    if (selectedDate && selectedImageId) {
      const dataToSend = {
        time: selectedDate,
        id: selectedImageId,
      };

      // Send data to API using axios or fetch
      axios.post('http://localhost:8080/tazma/api/appointments/create/'+ localStorage.getItem("jwt"), dataToSend)
        .then(response => {
          console.log('Data sent successfully:', response.data);
          // Perform any additional actions on successful data submission
        })
        .catch(error => {
          console.error('Error sending data:', error);
          // Handle error scenario
        });
    }
  };

  return (
    <MDBContainer>
      {data.map((item, index) => {
        const url = process.env.PUBLIC_URL + "/" +item.url
        return(
          <MDBCard key={index} className="mb-4">
          <MDBRow className="g-0">
            <MDBCol md="4">
              <MDBCardImage
                  src={url}
                alt={item.title}
                className="img-fluid"
                onClick={() => handleImageClick(item.id)}
                style={{ cursor: 'pointer' }}
              />
            </MDBCol>
            <MDBCol md="8">
              <MDBCardBody>
                <MDBCardTitle>{item.title}</MDBCardTitle>
                <MDBCardText>{item.description}</MDBCardText>
                {selectedImageId === item.id && (
                  <DatePicker getValue={handleDateChange} />
                )}
                <MDBBtn color="primary" onClick={handleSendData}>
                  Make Appointment
                </MDBBtn>
              </MDBCardBody>
            </MDBCol>
          </MDBRow>
        </MDBCard>
        );
        
      })}
    </MDBContainer>
  );
};

export default Hairdo;
