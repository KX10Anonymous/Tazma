import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import Checkbox from '@mui/material/Checkbox';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import FormControlLabel from '@mui/material/FormControlLabel';
import Grid from '@mui/material/Grid';
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
  const [role, setRole] = React.useState('');
  const [email, setEmail] = React.useState('');
  const [emailError, setEmailError] = React.useState('');
  const [passwordError, setPasswordError] = React.useState('');
  const [phoneError, setPhoneError] = React.useState('');
  const [firstNameError, setFirstNameError] = React.useState('');
  const [lastNameError, setLastNameError] = React.useState('');


  const validateEmail = (email) => {
    // Regular expression pattern to validate email format
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
  };

  const handleEmailChange = async (event) => {
    const emailValue = event.target.value;
    setEmail(emailValue);
    // Validate the email format
    const isValidEmail = validateEmail(emailValue);
    if (!isValidEmail) {
      setEmailError('Invalid Email');
      return;
    }
    
    try {
      // Check if email exists in the database by querying the API
      const response = await fetch('YOUR_API_ENDPOINT', {
        method: 'POST',
        body: JSON.stringify({ email: emailValue }),
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data = await response.json();
      
      // Assuming the API response includes a `exists` property indicating email existence
      const isEmailExists = data.exists;
      
      if (isEmailExists) {
        setEmailError('Email already exists');
      } else {
        setEmailError('');
      }
    } catch (error) {
      console.error('Error checking email existence:', error);
      // Handle error scenario
    }
  };

  const handlePasswordChange = (event) => {
    const password = event.target.value;
    if (!password) {
      setPasswordError('Password is required');
    } else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/.test(password)) {
      setPasswordError(
        'Password must contain at least one lowercase letter, one uppercase letter, and one number'
      );
    } else {
      setPasswordError('');
    }
  };

  const handleFirstNameChange = (event) => {
    const firstName = event.target.value;
    if (!firstName) {
      setFirstNameError('First Name is required');
    } else if (!validateName(firstName)) {
      setFirstNameError('Invalid First Name');
    } else {
      setFirstNameError('');
    }
  };

  const handleLastNameChange = (event) => {
    const lastName = event.target.value;
    if (!lastName) {
      setLastNameError('Last Name is required');
    } else if (!validateName(lastName)) {
      setLastNameError('Invalid Last Name');
    } else {
      setLastNameError('');
    }
  };

  const validateName = (name) => {
    // Regular expression pattern to allow alphabets, spaces, hyphens, and apostrophes
    const namePattern = /^[a-zA-Z\s\-']+$/;
  
    // Minimum and maximum length requirements
    const minLength = 2;
    const maxLength = 50;
  
    // Check if the name matches the pattern
    if (!namePattern.test(name)) {
      return false;
    }
  
    // Check if the name length is within the specified range
    if (name.length < minLength || name.length > maxLength) {
      return false;
    }
  
    // Additional checks to disallow consecutive spaces
    const consecutiveSpaces = /\s{2,}/;
    if (consecutiveSpaces.test(name)) {
      return false;
    }
  
    // Additional check to disallow numbers or special characters
    const disallowedCharacters = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?0-9]/;
    if (disallowedCharacters.test(name)) {
      return false;
    }
  
    // Check if the name starts and ends with an alphabet
    const startsWithLetter = /^[a-zA-Z]/;
    const endsWithLetter = /[a-zA-Z]$/;
    if (!startsWithLetter.test(name) || !endsWithLetter.test(name)) {
      return false;
    }
  
    // Check if the name consists of a minimum number of words
    const minWordCount = 2;
    const words = name.trim().split(/\s+/);
    if (words.length < minWordCount) {
      return false;
    }
  
    // Check if the name does not exceed the maximum word count
    const maxWordCount = 3;
    if (words.length > maxWordCount) {
      return false;
    }
  
    // Check if the name contains valid characters and doesn't have repeated characters
    const hasValidCharacters = /^[\p{L}\p{M}'\s-]+$/u.test(name);
    const hasNoRepeatedCharacters = !/(.)\1{2}/.test(name);
    if (!hasValidCharacters || !hasNoRepeatedCharacters) {
      return false;
    }
  
    // Add more custom validation checks as needed
  
    return true;
  };

  const handlePhoneChange = (event) => {
    const phone = event.target.value;

    if (!phone) {
      setPhoneError('Phone number is required');
    } else if (!/^06[03-5]\d{7}$/.test(phone) && !/^06[06-9]\d{7}$/.test(phone) &&
     !/^061[0-3]\d{6}$/.test(phone) && !/^0614\d{6}$/.test(phone) && !/^061[5-9]\d{6}$/.test(phone) &&
      !/^063[0-5]\d{6}$/.test(phone) && !/^063[6-7]\d{6}$/.test(phone) && !/^0640\d{6}$/.test(phone) && 
      !/^064[1-5]\d{6}$/.test(phone) && !/^064[6-9]\d{6}$/.test(phone) && !/^065[0-3]\d{6}$/.test(phone) &&
       !/^0654\d{6}$/.test(phone) && !/^065[5-7]\d{6}$/.test(phone) && !/^0658\d{6}$/.test(phone) &&
        !/^0659\d{6}$/.test(phone) && !/^066[0-5]\d{6}$/.test(phone) && !/^067[0-2]\d{6}$/.test(phone) &&
         !/^067[3-5]\d{6}$/.test(phone) && !/^067[6-9]\d{6}$/.test(phone) && !/^068[0-5]\d{6}$/.test(phone) && 
         !/^069\d{7}$/.test(phone) && !/^07[10]\d{7}$/.test(phone) && !/^07[11-6]\d{7}$/.test(phone) &&
          !/^0717\d{7}$/.test(phone) && !/^071[0-9]\d{7}$/.test(phone) && !/^072\d{7}$/.test(phone) && 
          !/^073\d{7}$/.test(phone) && !/^074\d{7}$/.test(phone) && !/^0741\d{6}$/.test(phone) && 
          !/^076\d{7}$/.test(phone) && !/^078\d{7}$/.test(phone) && !/^079\d{7}$/.test(phone) && 
          !/^0810\d{7}$/.test(phone) && !/^081[1-5]\d{7}$/.test(phone) && !/^0817\d{7}$/.test(phone) &&
           !/^0818\d{7}$/.test(phone) && !/^082\d{7}$/.test(phone) && !/^083\d{7}$/.test(phone) && 
           !/^0839\d{6}$/.test(phone) && !/^084\d{7}$/.test(phone)) {
      setPhoneError('Invalid phone number');
    } else {
      setPhoneError('');
    }
  };

  const handleRoleButtonClick = (role) => {
    setRole(role.toUpperCase());
  };

  const roleButtons = [
    <Button
      key="client"
      onClick={() => handleRoleButtonClick('client')}
      variant={role === 'CLIENT' ? 'contained' : 'outlined'}
      sx={{ bgcolor: role === 'CLIENT' ? 'green' : undefined }}
    >
      Client
    </Button>,
    <Button
      key="stylist"
      onClick={() => handleRoleButtonClick('stylist')}
      variant={role === 'STYLIST' ? 'contained' : 'outlined'}
      sx={{ bgcolor: role === 'STYLIST' ? 'green' : undefined }}
    >
      Styist
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
            Sign Up
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
        margin="normal"
        required
        fullWidth
        id="firstname"
        label="First Name"
        name="firstname"
        autoComplete="text"
        autoFocus
        error={!!firstNameError}
        helperText={firstNameError}
        onChange={handleFirstNameChange}
      />
      <TextField
        margin="normal"
        required
        fullWidth
        id="lastname"
        label="Last Name"
        name="lastname"
        autoComplete="text"
        autoFocus
        error={!!lastNameError}
        helperText={lastNameError}
        onChange={handleLastNameChange}
      />
            <TextField
        margin="normal"
        required
        fullWidth
        id="email"
        label="Email Address"
        name="email"
        autoComplete="email"
        autoFocus
        value={email}
        error={!!emailError}
        helperText={emailError}
        onChange={handleEmailChange}
      />
            <TextField
        margin="normal"
        required
        fullWidth
        id="phone"
        label="Contact Number"
        name="phone"
        autoComplete="phone"
        autoFocus
        error={!!phoneError}
        helperText={phoneError}
        onChange={handlePhoneChange}
      />
            <Divider/>
           <p style={{ color: 'grey' }}>Create Account As A:</p>
          <ButtonGroup  fullWidth margin="normal">{roleButtons}</ButtonGroup>
          <Divider/>
          <TextField
        margin="normal"
        required
        fullWidth
        name="password"
        label="Password"
        type="password"
        id="password"
        autoComplete="current-password"
        error={!!passwordError}
        helperText={passwordError}
        onChange={handlePasswordChange}
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
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="/login" variant="body2">
                  {"Have an account? Sign in"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}