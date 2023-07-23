import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import Checkbox from '@mui/material/Checkbox';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import FormControlLabel from '@mui/material/FormControlLabel';
import Link from '@mui/material/Link';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useNavigate } from "react-router-dom";
import { useUser } from "../UserProvider";

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://amani.com/">
        amani.com
      </Link>{' '}
      {new Date().getFullYear()}1
      {'.'}
    </Typography>
  );
}


const defaultTheme = createTheme();

export default function Address() {
  const navigate = useNavigate();
  const user = useUser();
  const [selectedGender, setSelectedGender] = React.useState('');
  const [selectedType, setSelectedType] = React.useState('');

  const handleGenderButtonClick = (gender) => {
    setSelectedGender(gender.toUpperCase());
  };

  const handleTypeButtonClick = (type) => {
    setSelectedType(type.toUpperCase());
  };

  const genderButtons = [
    <Button
      key="male"
      onClick={() => handleGenderButtonClick('male')}
      variant={selectedGender === 'MALE' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedGender === 'MALE' ? 'green' : undefined }}
    >
      Male
    </Button>,
    <Button
      key="female"
      onClick={() => handleGenderButtonClick('female')}
      variant={selectedGender === 'FEMALE' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedGender === 'FEMALE' ? 'green' : undefined }}
    >
      Female
    </Button>,
    <Button
      key="other"
      onClick={() => handleGenderButtonClick('other')}
      variant={selectedGender === 'OTHER' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedGender === 'OTHER' ? 'green' : undefined }}
    >
      Other
    </Button>,
  ];


  const typeButtons = [
    <Button
      key="client_visit"
      onClick={() => handleTypeButtonClick('client_visit')}
      variant={selectedGender === 'CLIENT_VISIT' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedType === 'CLIENT_VISIT' ? 'green' : undefined }}
    >
      Client Visit
    </Button>,
    <Button
      key="house_call"
      onClick={() => handleTypeButtonClick('house_call')}
      variant={selectedType === 'HOUSE_CALL' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedType === 'HOUSE_CALL' ? 'green' : undefined }}
    >
      House Call
    </Button>,
  ];
  
  
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const addressReqBody = {
     province: data.get('province'),
      area: data.get('area'),
      street: data.get('street'),
      suburb: data.get('town'),
      phone: data.get('phone')
    };

    if(user.role === 'STYLIST'){
      const operationBody = {
        appointmentType: selectedType,
        gender:selectedGender
      }
      fetch("http://localhost:8080/tazma/api/users/type-edit/" + user.jwt, {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(operationBody),
      })
    }else{
      const operationBody = {
        gender:selectedGender
      }
      fetch("http://localhost:8080/tazma/api/users/type-edit/" + user.jwt, {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(operationBody),
      })
    }


    fetch("http://localhost:8080/tazma/api/users/address/" + user.jwt, {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(addressReqBody),
      })
      .then(
        navigate("/home")
      )


  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Additional Information
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <p>Gender</p>
          <ButtonGroup  fullWidth margin="normal">{genderButtons}</ButtonGroup>
          {user && user.role === 'STYLIST' ? (
            <>
            <br/>
          <br/>
          <p>How do you want to respond to appointments?*</p>
          <ButtonGroup  fullWidth margin="normal">{typeButtons}</ButtonGroup>
            </>
          ):(<></>)}
          
            <TextField
              margin="normal"
              required
              fullWidth
              id="text"
              label="Province"
              name="province"
              autoComplete="text"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              id="area"
              label="Area"
              name="area"
              autoComplete="text"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="town"
              label="Neighborhood"
              id="town"
              autoComplete="current-password"
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="street"
              label="Street Number/Name"
              id="street"
              autoComplete="text"
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="house"
              label="House Number"
              id="house"
              autoComplete="text"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Save Additional Information
            </Button>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}