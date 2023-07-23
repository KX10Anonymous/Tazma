import Favorite from "@mui/icons-material/Favorite";
import AspectRatio from "@mui/joy/AspectRatio";
import Card from "@mui/joy/Card";
import CardContent from "@mui/joy/CardContent";
import CardOverflow from "@mui/joy/CardOverflow";
import Divider from "@mui/joy/Divider";
import Link from "@mui/joy/Link";
import Typography from "@mui/joy/Typography";
import IconButton from "@mui/material/IconButton";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import * as React from "react";

export default function Posts() {
  return (
    <div
      style={{
        width: "100%",
        height: "100%", // Set the desired height of the scrollable container
        overflow: "auto", // This will make the container scrollable with hidden scrollbars
      }}>
      <ImageList gap={30} sx={{ width: 1, height: 1 }}>
        {itemData.map((item) => (
          <ImageListItem key={item.img}>
            <Card
              variant="outlined"
              sx={{ height: "85%", width: "75%", mx: "auto" }}>
              <CardOverflow sx={{ zIndex: 3 }}>
                <AspectRatio ratio="2">
                  <img
                    src="https://images.unsplash.com/photo-1532614338840-ab30cf10ed36?auto=format&fit=crop&w=318"
                    srcSet="https://images.unsplash.com/photo-1532614338840-ab30cf10ed36?auto=format&fit=crop&w=318&dpr=2 2x"
                    loading="lazy"
                    alt=""
                  />
                </AspectRatio>
                <IconButton
                  aria-label="Like minimal photography"
                  size="md"
                  variant="solid"
                  color="danger"
                  sx={{
                    position: "absolute",
                    zIndex: 1,
                    borderRadius: "50%",
                    right: "1rem",
                    bottom: "20rem",
                    transform: "translateY(50%)",
                  }}>
                  <Favorite />
                </IconButton>
              </CardOverflow>
              <CardContent>
                <Typography level="h2" fontSize="md">
                  <Link href="#multiple-actions" overlay underline="none">
                    Yosemite National Park
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
                    1 hour ago
                  </Typography>
                </CardContent>
              </CardOverflow>
            </Card>
          </ImageListItem>
        ))}
      </ImageList>
    </div>
  );
}

const itemData = [
  {
    img: "https://images.unsplash.com/photo-1551963831-b3b1ca40c98e",
    title: "Breakfast",
    author: "@bkristastucchio",
    rows: 2,
    cols: 2,
    featured: true,
  },
  {
    img: "https://images.unsplash.com/photo-1551782450-a2132b4ba21d",
    title: "Burger",
    author: "@rollelflex_graphy726",
  },
  {
    img: "https://images.unsplash.com/photo-1522770179533-24471fcdba45",
    title: "Camera",
    author: "@helloimnik",
  },
  {
    img: "https://images.unsplash.com/photo-1444418776041-9c7e33cc5a9c",
    title: "Coffee",
    author: "@nolanissac",
    cols: 2,
  },
  {
    img: "https://images.unsplash.com/photo-1533827432537-70133748f5c8",
    title: "Hats",
    author: "@hjrc33",
    cols: 2,
  },
  {
    img: "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62",
    title: "Honey",
    author: "@arwinneil",
    rows: 2,
    cols: 2,
    featured: true,
  },
  {
    img: "https://images.unsplash.com/photo-1516802273409-68526ee1bdd6",
    title: "Basketball",
    author: "@tjdragotta",
  },
  {
    img: "https://images.unsplash.com/photo-1518756131217-31eb79b20e8f",
    title: "Fern",
    author: "@katie_wasserman",
  },
  {
    img: "https://images.unsplash.com/photo-1597645587822-e99fa5d45d25",
    title: "Mushrooms",
    author: "@silverdalex",
    rows: 2,
    cols: 2,
  },
  {
    img: "https://images.unsplash.com/photo-1567306301408-9b74779a11af",
    title: "Tomato basil",
    author: "@shelleypauls",
  },
  {
    img: "https://images.unsplash.com/photo-1471357674240-e1a485acb3e1",
    title: "Sea star",
    author: "@peterlaster",
  },
  {
    img: "https://images.unsplash.com/photo-1589118949245-7d38baf380d6",
    title: "Bike",
    author: "@southside_customs",
    cols: 2,
  },
];
