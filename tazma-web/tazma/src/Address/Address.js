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

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://amani.com/">
        amani.com
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

// TODO remove, this demo shouldn't need to reset the theme.

const defaultTheme = createTheme();

export default function Registration() {

  const [selectedGender, setSelectedGender] = React.useState('');

  const handleButtonClick = (gender) => {
    setSelectedGender(gender.toUpperCase());
  };
  const buttons = [
    <Button
      key="male"
      onClick={() => handleButtonClick('male')}
      variant={selectedGender === 'MALE' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedGender === 'MALE' ? 'green' : undefined }}
    >
      Male
    </Button>,
    <Button
      key="female"
      onClick={() => handleButtonClick('female')}
      variant={selectedGender === 'FEMALE' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedGender === 'FEMALE' ? 'green' : undefined }}
    >
      Female
    </Button>,
    <Button
      key="other"
      onClick={() => handleButtonClick('other')}
      variant={selectedGender === 'OTHER' ? 'contained' : 'outlined'}
      sx={{ bgcolor: selectedGender === 'OTHER' ? 'green' : undefined }}
    >
      Other
    </Button>,
  ];
  
  
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get('email'),
      password: data.get('password'),
      firstname: data.get('firstname'),
      lastname: data.get('lastname'),
      phone: data.get('phone')
    });
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
          <ButtonGroup  fullWidth margin="normal">{buttons}</ButtonGroup>
            <TextField
              margin="normal"
              required
              fullWidth
              id="text"
              label="Role"
              name="lastname"
              autoComplete="text"
              autoFocus
            />
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