import InfoOutlined from "@mui/icons-material/InfoOutlined";
import SaveIcon from "@mui/icons-material/Save";
import Button from "@mui/joy/Button";
import Card from "@mui/joy/Card";
import CardActions from "@mui/joy/CardActions";
import CardContent from "@mui/joy/CardContent";
import Divider from "@mui/joy/Divider";
import FormControl from "@mui/joy/FormControl";
import Input from "@mui/joy/Input";
import Typography from "@mui/joy/Typography";
import Box from "@mui/material/Box";
import ButtonBase from "@mui/material/ButtonBase";
import { styled } from "@mui/material/styles";
import { MDBFile } from "mdb-react-ui-kit";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CreateHairdo({ handleCloseDialog }) {
  const navigate = useNavigate();
  const [styleName, setStyleName] = useState("");
  const [files, setFiles] = useState([]);
  const [gender, setGender] = useState("");

  const handleGenderButtonClick = (gender) => {
    setGender(gender);
  };

  const images = [
    {
      url: process.env.PUBLIC_URL +'/components/male.png',
      title: 'Male',
      width: '33%',
    },
    {
      url: process.env.PUBLIC_URL +'/components/female.png',
      title: 'Female',
      width: '33%',
    },
    {
      url: process.env.PUBLIC_URL +'/components/unisex.png',
      title: 'Unisex',
      width: '33%',
      
    },
  ];

  const ImageButton = styled(ButtonBase)(({ theme }) => ({
    position: 'relative',
    height: 200,
    [theme.breakpoints.down('sm')]: {
      width: '100%', 
      height: 100,
    },
    '&:hover, &.Mui-focusVisible': {
      zIndex: 1,
      '& .MuiImageBackdrop-root': {
        opacity: 0.15,
      },
      '& .MuiImageMarked-root': {
        opacity: 0.9,
      },
      '& .MuiTypography-root': {
        border: '4px solid currentColor',
      },
    },
  }));
  
  const ImageSrc = styled('span')({
    position: 'absolute',
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    backgroundSize: '100%',
    backgroundPosition: 'center 100%',
  });
  
  const Image = styled('span')(({ theme }) => ({
    position: 'absolute',
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: theme.palette.common.white,
  }));
  
  const ImageBackdrop = styled('span')(({ theme }) => ({
    position: 'absolute',
    left: 0,
    right: 0,
    top: 0,
    bottom: 0,
    backgroundColor: theme.palette.common.black,
    opacity: 0.4,
    transition: theme.transitions.create('opacity'),
  }));
  
  const ImageMarked = styled('span')(({ theme }) => ({
    height: 3,
    width: 18,
    backgroundColor: theme.palette.common.white,
    position: 'absolute',
    bottom: -2,
    left: 'calc(50% - 9px)',
    transition: theme.transitions.create('opacity'),
  }));

  const genderButtons = [
    <Button
      key="male"
      onClick={() => handleGenderButtonClick("MALE")}
      variant={gender === "MALE" ? "contained" : "outlined"}
      sx={{width: "100%", bgcolor: gender === "MALE" ? "green" : undefined }}>
      Client
    </Button>,
    <Button
      key="female"
      onClick={() => handleGenderButtonClick("FEMALE")}
      variant={gender === "FEMALE" ? "contained" : "outlined"}
      sx={{ width: "100%", bgcolor: gender === "FEMALE" ? "green" : undefined }}>
      Stylist
    </Button>,
  ];

  const handleChange = (e) => {
    const selectedFiles = e.target.files;
    const updatedFiles = Array.from(selectedFiles).map((file) => {
      return { file, originalName: file.name };
    });
    setFiles(updatedFiles);
  };

  const saveHairdo = () => {
    const reqBody = {
      title: styleName,
      gender: gender.toUpperCase(),
    };

    fetch(
      "http://localhost:8080/tazma/api/styles/create/" +
        localStorage.getItem("jwt"),
      {
        headers: {
          "Content-Type": "application/json",
        },
        method: "post",
        body: JSON.stringify(reqBody),
      }
    )
      .then((response) => response.json())
      .then((data) => {
        const id = data.id;
        const uploadPromises = [];
        for (const element of files) {
          const { file, originalName } = element;
          const formData = new FormData();
          formData.append("file", file, originalName);
          console.log(formData);
          const uploadPromise = fetch(
            "http://localhost:8080/tazma/api/styles/upload/" + id,
            {
              method: "post",
              body: formData,
            }
          )
            .then((response) => response.json())
            .catch((error) => {
              throw new Error(error);
            });
          uploadPromises.push(uploadPromise);
          formData.delete("file");
        }
        return Promise.all(uploadPromises);
      })
      .then((results) => {
        console.log(results);
        navigate("/hairdo");
      })
      .catch((error) => {
       
      });
      handleCloseDialog();
  };
  return (
    <Card
      variant="outlined"
      sx={{
        maxHeight: "max-content",
        maxWidth: "75%",
        mx: "auto",
        overflow: "auto",
        resize: "horizontal",
        backgroundImage: `url(${process.env.PUBLIC_URL + "/bg.jpg"})`,
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <Typography level="h2" fontSize="xl" startDecorator={<InfoOutlined />}>
        Create New Hair Style
      </Typography>
      <Divider inset="none" />
      <CardContent
        sx={{
          gridTemplateColumns: "repeat(2, minmax(80px, 1fr))",
          gap: 1.5,
        }}
      >
        <FormControl fullWidth margin="normal">
          <Input
            color="primary"
            size="md"
            placeholder="Style Name"
            variant="soft"
            onChange={(e) => setStyleName(e.target.value)}
            label="Hairdo Name"
            id="styleName"
            endDecorator={<InfoOutlined />}
          />
        </FormControl>

        <FormControl sx={{ width: "100%", mx: "auto" }}>
          <Box
            sx={{ display: "flex", flexWrap: "wrap", minWidth: 300, width: "100%" }}
          >
            {images.map((image) => (
              <ImageButton
                focusRipple
                key={image.title}
                style={{
                  width: image.width, bgcolor: gender === image.title ? "green" : undefined
                }}
                onClick={() => handleGenderButtonClick(image.title)}
              >
                <ImageSrc style={{ backgroundImage: `url(${image.url})` }} />
                <ImageBackdrop className="MuiImageBackdrop-root" />
                <Image>
                  <Typography
                    component="span"
                    variant="subtitle1"
                    color="inherit"
                    sx={{
                      position: "relative",
                      p: 4,
                      pt: 2,
                      pb: (theme) => `calc(${theme.spacing(1)} + 6px)`,
                    }}
                  >
                    {image.title}
                    <ImageMarked className="MuiImageMarked-root" />
                  </Typography>
                </Image>
              </ImageButton>
            ))}
          </Box>
        </FormControl>

        <FormControl sx={{ width: "100%", mx: "auto" }}>
          <MDBFile
            sx={{ width: "100%", mx: "auto", height: "200px" }}
            label="Add Images"
            onChange={handleChange}
            fullWidth
            margin="normal"
            name="images"
            id="formFileMultiple"
            multiple
            accept="image/jpg, image/png, image/jpeg, image/webp"
          />
        </FormControl>

        <CardActions sx={{ width: "100%", mx: "auto" }}>
          <Button
            sx={{ width: "100%", mx: "auto" }}
            variant="solid"
            color="primary"
            className="mb-4 w-100 gradient-custom-4"
            fullWidth
            margin="normal"
            type="button"
            startIcon={<SaveIcon />}
            onClick={() => {
              saveHairdo();
            }}
          >
            Save Hairdo
          </Button>
        </CardActions>
      </CardContent>
    </Card>
  );
}