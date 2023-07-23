import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Unstable_Grid2';
import { experimentalStyled as styled } from '@mui/material/styles';
import * as React from 'react';

import AspectRatio from '@mui/joy/AspectRatio';
import Avatar from '@mui/joy/Avatar';

import FavoriteBorderRoundedIcon from '@mui/icons-material/FavoriteBorderRounded';
import Card from '@mui/joy/Card';
import IconButton from '@mui/joy/IconButton';
import Link from '@mui/joy/Link';
import Typography from '@mui/joy/Typography';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function Test() {
  return (
    <Box sx={{ flexGrow: 1 }}>
  <Grid container spacing={{ xs: 5, md: 6 }} columns={{ xs: 12, sm: 12, md: 12 }}>
    {Array.from(Array(6)).map((_, index) => (
      <Grid item xs={12} sm={6} md={4} key={index}>
        <Item>
            <Card
        variant="outlined"
        sx={(theme) => ({
          width: 350,
          gridColumn: 'span 2',
          flexDirection: 'row',
          flexWrap: 'wrap',
          resize: 'horizontal',
          overflow: 'hidden',
          gap: 'clamp(0px, (100% - 360px + 32px) * 999, 16px)',
          transition: 'transform 0.3s, border 0.3s',
          '&:hover': {
            borderColor: theme.vars.palette.primary.outlinedHoverBorder,
            transform: 'translateY(-2px)',
          },
          '& > *': { minWidth: 'clamp(0px, (360px - 100%) * 999,100%)' },
        })}
      >
        <AspectRatio
          variant="soft"
          sx={{
            flexGrow: 1,
            display: 'contents',
            '--AspectRatio-paddingBottom':
              'clamp(0px, (100% - 360px) * 999, min(calc(100% / (16 / 9)), 300px))',
          }}
        >
          <img
            src="https://images.unsplash.com/photo-1492305175278-3b3afaa2f31f?auto=format&fit=crop&w=2000"
            loading="lazy"
            alt=""
            style={{width: 300}}
          />
        </AspectRatio>
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            gap: 2,
            maxWidth: 300,
          }}
        >
          <Box sx={{ display: 'flex' }}>
            <div>
              <Typography level="h2" sx={{ fontSize: 'md' }} mb={0.5}>
                <Link
                  href="#container-responsive"
                  overlay
                  underline="none"
                  sx={{
                    color: 'text.primary',
                    '&.Mui-focusVisible:after': { outlineOffset: '-4px' },
                  }}
                >
                  Yosemite National Park
                </Link>
              </Typography>
              <Typography level="body2">California, USA</Typography>
            </div>
            <IconButton
              size="sm"
              variant="plain"
              color="neutral"
              sx={{ ml: 'auto', alignSelf: 'flex-start' }}
            >
              <FavoriteBorderRoundedIcon color="danger" />
            </IconButton>
          </Box>
          <AspectRatio
            variant="soft"
            sx={{
              '--AspectRatio-paddingBottom':
                'clamp(0px, (100% - 200px) * 999, 200px)',
              pointerEvents: 'none',
            }}
          >
            <img
              alt=""
              src="https://images.unsplash.com/photo-1492305175278-3b3afaa2f31f?auto=format&fit=crop&w=2262"
            />
          </AspectRatio>
          <Box sx={{ display: 'flex', gap: 1.5, mt: 'auto' }}>
            <Avatar variant="soft" color="neutral">
              Y
            </Avatar>
            <div>
              <Typography level="body2">Designed by</Typography>
              <Typography fontWeight="lg" level="body2">
                Nature itself
              </Typography>
            </div>
          </Box>
        </Box>
      </Card>
            </Item>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
