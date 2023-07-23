import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import Checkbox from "@mui/material/Checkbox";
import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";
import Divider from "@mui/material/Divider";
import FormControlLabel from "@mui/material/FormControlLabel";
import Grid from "@mui/material/Grid";
import Link from "@mui/material/Link";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import * as React from "react";
import { useNavigate } from "react-router-dom";
import { useUser } from "../UserProvider";

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}>
      {"Copyright © "}
      <Link color="inherit" href="https://amani.com/">
        amani.com
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const defaultTheme = createTheme();

export default function Registration() {
  const user = useUser();
  const navigate = useNavigate();
  const [role, setRoleObject] = React.useState("");
  const [email, setEmailObject] = React.useState("");
  const [emailError, setEmailMessageError] = React.useState("");
  const [passwordError, setPasswordMessageError] = React.useState("");
  const [phoneError, setPhoneErrorMessage] = React.useState("");
  const [firstNameError, setFirstNameErrorMessage] = React.useState("");
  const [lastNameError, setLastNameErrorMessage] = React.useState("");

  React.useEffect(() => {
    if (user.jwt) {
      navigate("/home");
    }
  });

  const validateEmailEntry = (emailEntry) => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(emailEntry);
  };

  const handleEmailEntryChange = async (event) => {
    const emailValue = event.target.value;
    setEmailObject(emailValue);
    const isValidEmail = validateEmailEntry(emailValue);
    if (!isValidEmail) {
      setEmailMessageError("Invalid Email!!");
      return;
    }

    try {
      const response = await fetch(
        "http://localhost:8080/tazma/api/users/email-check/" + emailValue,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = await response.json();

      const isEmailExists = data.exists;

      if (isEmailExists) {
        setEmailMessageError("Email Already Exists!!");
      } else {
        setEmailMessageError("");
      }
    } catch (error) {
      console.error("Error Checking Email Existence!!");
      // Handle error scenario
    }
  };

  const handlePasswordChange = (event) => {
    const password = event.target.value;
    if (!password) {
      setPasswordMessageError("Password Field Cannot be Left Empty");
    } else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/.test(password)) {
      setPasswordMessageError(
        "Password must contain at least one lowercase letter, one uppercase letter, and one number"
      );
    } else {
      setPasswordMessageError("");
    }
  };

  const handleFirstNameChange = (event) => {
    const firstNameEntry = event.target.value;

    if (!firstNameEntry) {
      setFirstNameErrorMessage("First Name Is Required!!");
    } else if (!validateName(firstNameEntry)) {
      setFirstNameErrorMessage("Invalid First Name!!");
    } else {
      setFirstNameErrorMessage("");
    }
  };

  const handleLastNameChange = (event) => {
    const lastNameEntry = event.target.value;
    if (!lastNameEntry) {
      setLastNameErrorMessage("Last Name Is Required!!");
    } else if (!validateName(lastNameEntry)) {
      setLastNameErrorMessage("Invalid Last Name!!");
    } else {
      setLastNameErrorMessage("");
    }
  };

  const validateName = (name) => {
    const namePattern = /^[a-zA-Z\s\-']+$/;

    const minLength = 2;
    const maxLength = 50;

    if (!namePattern.test(name)) {
      return false;
    }

    if (name.length < minLength || name.length > maxLength) {
      return false;
    }

    const consecutiveSpaces = /\s{2,}/;
    if (consecutiveSpaces.test(name)) {
      return false;
    }

    const disallowedCharacters = /[!@#$%^&*()_+=[\]{};':"\\|,.<>/?0-9]/;
    if (disallowedCharacters.test(name)) {
      return false;
    }

    const startsWithLetter = /^[a-zA-Z]/;
    const endsWithLetter = /[a-zA-Z]$/;
    if (!startsWithLetter.test(name) || !endsWithLetter.test(name)) {
      return false;
    }

    const words = name.trim().split(/\s+/);
    const maxWordCount = 2;
    if (words.length > maxWordCount) {
      return false;
    }
    const hasValidCharacters = /^[\p{L}\p{M}'\s-]+$/u.test(name);
    const hasNoRepeatedCharacters = !/(.)\1{2}/.test(name);
    if (!hasValidCharacters || !hasNoRepeatedCharacters) {
      return false;
    }

    const minWordCount = 2;

    if (words.length <= minWordCount) {
      return true;
    }
  

    return true;
  };

  const handlePhoneChange = (event) => {
    const phone = event.target.value;
    if (!phone) {
      setPhoneErrorMessage("Phone Number Is Required!!");
    } else if (
      !/^06[03-5]\d{7}$/.test(phone) &&
      !/^06[06-9]\d{7}$/.test(phone) &&
      !/^061[0-3]\d{6}$/.test(phone) &&
      !/^0614\d{6}$/.test(phone) &&
      !/^061[5-9]\d{6}$/.test(phone) &&
      !/^063[0-5]\d{6}$/.test(phone) &&
      !/^063[6-7]\d{6}$/.test(phone) &&
      !/^0640\d{6}$/.test(phone) &&
      !/^064[1-5]\d{6}$/.test(phone) &&
      !/^064[6-9]\d{6}$/.test(phone) &&
      !/^065[0-3]\d{6}$/.test(phone) &&
      !/^0654\d{6}$/.test(phone) &&
      !/^065[5-7]\d{6}$/.test(phone) &&
      !/^0658\d{6}$/.test(phone) &&
      !/^0659\d{6}$/.test(phone) &&
      !/^066[0-5]\d{6}$/.test(phone) &&
      !/^067[0-2]\d{6}$/.test(phone) &&
      !/^067[3-5]\d{6}$/.test(phone) &&
      !/^067[6-9]\d{6}$/.test(phone) &&
      !/^068[0-5]\d{6}$/.test(phone) &&
      !/^069\d{7}$/.test(phone) &&
      !/^07[10]\d{7}$/.test(phone) &&
      !/^07[11-6]\d{7}$/.test(phone) &&
      !/^0717\d{7}$/.test(phone) &&
      !/^071[0-9]\d{7}$/.test(phone) &&
      !/^072\d{7}$/.test(phone) &&
      !/^073\d{7}$/.test(phone) &&
      !/^074\d{7}$/.test(phone) &&
      !/^0741\d{6}$/.test(phone) &&
      !/^076\d{7}$/.test(phone) &&
      !/^078\d{7}$/.test(phone) &&
      !/^079\d{7}$/.test(phone) &&
      !/^0810\d{7}$/.test(phone) &&
      !/^081[1-5]\d{7}$/.test(phone) &&
      !/^0817\d{7}$/.test(phone) &&
      !/^0818\d{7}$/.test(phone) &&
      !/^082\d{7}$/.test(phone) &&
      !/^083\d{7}$/.test(phone) &&
      !/^0839\d{6}$/.test(phone) &&
      !/^084\d{7}$/.test(phone)
    ) {
      setPhoneErrorMessage("Invalid Phone Number!!");
    } else {
      phoneExists(phone);
      setPhoneErrorMessage("");
    }
  };

  const phoneExists = async (phone) => {
    try {
      const response = await fetch(
        "http://localhost:8080/tazma/api/users/phone-check/" + phone,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = await response.json();
      const isPhoneExists = data.exists;

      if (isPhoneExists === true) {
        setPhoneErrorMessage("Contact number already exists");
      } else {
        setPhoneErrorMessage("");
      }
    } catch (error) {
      console.error("Error checking contact number existence:", error);
      // Handle error scenario
    }
  };

  const handleRoleButtonClick = (role) => {
    setRoleObject(role.toUpperCase());
  };

  const roleButtons = [
    <Button
      key="client"
      onClick={() => handleRoleButtonClick("client")}
      variant={role === "CLIENT" ? "contained" : "outlined"}
      sx={{ bgcolor: role === "CLIENT" ? "green" : undefined }}>
      Client
    </Button>,
    <Button
      key="stylist"
      onClick={() => handleRoleButtonClick("stylist")}
      variant={role === "STYLIST" ? "contained" : "outlined"}
      sx={{ bgcolor: role === "STYLIST" ? "green" : undefined }}>
      Stylist
    </Button>,
  ];

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const reqBody = {
      email: data.get("email"),
      password: data.get("password"),
      firstname: data.get("firstname"),
      lastname: data.get("lastname"),
      phone: data.get("phone"),
      role: role,
    };
    fetch("http://localhost:8080/tazma/api/auth/register", {
      headers: {
        "Content-Type": "application/json",
      },
      method: "post",
      body: JSON.stringify(reqBody),
    })
      .then((response) =>
        response.json().then((data) => {
          user.setJwt(data.jwt);
          user.setRole(data.role);
          navigate("/address");
        })
      )
      .catch((message) => {
       // alert(message);
      });
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}>
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign Up
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}>
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
              onChange={handleEmailEntryChange}
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
            <Divider />
            <p style={{ color: "grey" }}>Create account as a*</p>
            <ButtonGroup fullWidth margin="normal">
              {roleButtons}
            </ButtonGroup>
            <Divider />
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
              sx={{ mt: 3, mb: 2 }}>
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
