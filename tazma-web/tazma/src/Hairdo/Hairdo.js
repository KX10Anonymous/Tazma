import AspectRatio from "@mui/joy/AspectRatio";
import Card from "@mui/joy/Card";
import CardContent from "@mui/joy/CardContent";
import CardOverflow from "@mui/joy/CardOverflow";
import Divider from "@mui/joy/Divider";
import Link from "@mui/joy/Link";
import Typography from "@mui/joy/Typography";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDateTimePicker } from "@mui/x-date-pickers/StaticDateTimePicker";
import axios from "axios";
import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { useUser } from "../UserProvider";

const Hairdo = () => {
  const currentDate = dayjs();
  const twoWeeksFromNow = dayjs(currentDate.add(2, "week"));
  const user = useUser();
  const [data, setData] = useState([]);
  const [selectedImageId, setSelectedImageId] = useState(null);
  const [open, setOpen] = React.useState(false);
  const [selectedDate, setSelectedDate] = React.useState(dayjs());
  const [appointmentType, setAppointmentType] = useState("");

  const handleDateChange = (date) => {
    setSelectedDate(date);
    createDateTimeObject(selectedDate);
  };

  const createDateTimeObject = (selectedDate) => {
    const dayjsDate = dayjs(selectedDate);

    const year = dayjsDate.year();
    const month = dayjsDate.month() + 1;
    const day = dayjsDate.date();
    const hour = dayjsDate.hour();
    const minute = dayjsDate.minute();
    const second = dayjsDate.second();
    const millisecond = dayjsDate.millisecond();
    const timezoneOffset = dayjsDate.utcOffset();

    const dateTimeObject = {
      year,
      month,
      day,
      hour,
      minute,
      second,
      millisecond,
      timezoneOffset,
    };
    return dateTimeObject;
  };

  const handleClickOpen = (id) => {
    setSelectedImageId(id);
    setOpen(true);
  };

  const handleCloseDialog = () => {
    setOpen(false);
  };

  useEffect(() => {
    const fetchData = async () => {
      console.error("About to read");
      try {
        await fetch("http://localhost:8080/tazma/api/styles/styles", {
          headers: {
            "Content-Type": "application/json",
          },
          method: "GET",
        })
          .then((response) =>
            response.json().then((data) => {
              console.error("Read it all");
              setData(data);
            })
          )
          .catch((message) => {
            console.error("Did not");
            /// alert(message);
          });
      } catch (error) {
        console.error("Error fetching styles:", error);
      }
    };

    fetchData();
  }, [user, setData]);

  const handleImageClick = (imageId) => {
    setSelectedImageId(imageId);
  };

  const handleSendBookingData = () => {
    if (selectedDate && selectedImageId) {
      const dataToSend = {
        time: selectedDate,
        id: selectedImageId,
        type: appointmentType,
      };

      // Send data to API using axios or fetch
      axios
        .post(
          "http://localhost:8080/tazma/api/appointments/create/" + user.jwt,
          dataToSend
        )
        .then((response) => {
          console.log("Data sent successfully:", response.data);
          // Perform any additional actions on successful data submission
        })
        .catch((error) => {
          console.error("Error sending data:", error);
          // Handle error scenario
        });
    }
    handleCloseDialog();
  };

  const handleTypeButtonClick = (type) => {
    setAppointmentType(type);
  };

  const typeButtons = [
    <Button
      key="house_call"
      onClick={() => handleTypeButtonClick("HOUSE_CALL")}
      variant={appointmentType === "HOUSE_CALL" ? "contained" : "outlined"}
      sx={{ bgcolor: appointmentType === "HOUSE_CALL" ? "green" : undefined }}
      maxWidth="bg">
      House Call
    </Button>,
    <Button
      key="stylist"
      onClick={() => handleTypeButtonClick("CLIENT_VISIT")}
      variant={appointmentType === "CLIENT_VISIT" ? "contained" : "outlined"}
      sx={{ bgcolor: appointmentType === "CLIENT_VISIT" ? "green" : undefined }}
      maxWidth="bg">
      Client Visit
    </Button>,
  ];

  return (
    <div
      style={{
        width: "100%",
        height: "100%", // Set the desired height of the scrollable container
        overflow: "auto", // This will make the container scrollable with hidden scrollbars
      }}>
      <ImageList gap={40} sx={{ width: 1, height: 1 }}>
        {data.map((item) => {
          const url = process.env.PUBLIC_URL + "/" + item.url;
          const id = item.id;
          const key = id;
          return (
            <>
              <Dialog
                open={open}
                onClose={handleCloseDialog}
                fullWidth="md"
                maxWidth="md">
                <DialogTitle>Book Appointment</DialogTitle>
                <DialogContent>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <StaticDateTimePicker
                      orientation="landscape"
                      minDate={currentDate}
                      maxDate={twoWeeksFromNow}
                      value={selectedDate}
                      onChange={handleDateChange}
                      slotProps={{
                        actionBar: {
                          hidden: true,
                        },
                      }}
                    />
                  </LocalizationProvider>
                  <Divider />
                  <p style={{ color: "grey" }}>Appointment Type*</p>
                  <ButtonGroup fullWidth margin="normal">
                    {typeButtons}
                  </ButtonGroup>
                  <Divider />
                </DialogContent>

                <DialogActions>
                  <Button onClick={handleCloseDialog}>Cancel</Button>
                  <Button onClick={handleSendBookingData}>Book</Button>
                </DialogActions>
              </Dialog>
              <ImageListItem key={key}>
                <Card
                  variant="outlined"
                  sx={{ height: "65%", width: "75%", mx: "auto" }}>
                  <CardOverflow sx={{ zIndex: 3 }}>
                    <AspectRatio ratio="2">
                      <img
                        src={url}
                        srcSet="https://images.unsplash.com/photo-1532614338840-ab30cf10ed36?auto=format&fit=crop&w=318&dpr=2 2x"
                        loading="lazy"
                        alt=""
                        onClick={handleImageClick}
                      />
                    </AspectRatio>
                  </CardOverflow>
                  <CardContent>
                    <Typography level="h2" fontSize="md">
                      <Link href="#multiple-actions" overlay underline="none">
                        {item.title}
                      </Link>
                    </Typography>
                    <Typography level="body2" sx={{ mt: 0.5 }}>
                      <Link href="#multiple-actions">California</Link>
                    </Typography>
                  </CardContent>
                  <CardOverflow
                    variant="soft"
                    sx={{ bgcolor: "background.level1" }}>
                    <Divider inset="context" />
                    <CardContent orientation="horizontal">
                      <Typography
                        level="body3"
                        sx={{ fontWeight: "md", color: "text.secondary" }}>
                        6.3k views
                      </Typography>
                      <Divider orientation="vertical" />
                      <Typography
                        level="body3"
                        sx={{ fontWeight: "md", color: "text.secondary" }}>
                        <Link onClick={handleClickOpen.bind(null, id)}>
                          Book Appointment
                        </Link>
                      </Typography>
                    </CardContent>
                  </CardOverflow>
                </Card>
              </ImageListItem>
            </>
          );
        })}
      </ImageList>
    </div>
  );
};

export default Hairdo;
