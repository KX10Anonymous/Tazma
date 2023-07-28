import AccountCircle from '@mui/icons-material/AccountCircle';
import CloseIcon from '@mui/icons-material/Close';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import MailIcon from '@mui/icons-material/Mail';
import MenuIcon from '@mui/icons-material/Menu';
import MinimizeIcon from '@mui/icons-material/Minimize';
import MoreIcon from '@mui/icons-material/MoreVert';
import NotificationsIcon from '@mui/icons-material/Notifications';
import SearchIcon from '@mui/icons-material/Search';
import { ClickAwayListener, Divider, Paper, Popper } from '@mui/material';
import AppBar from '@mui/material/AppBar';
import Avatar from '@mui/material/Avatar';
import Badge from '@mui/material/Badge';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import InputBase from '@mui/material/InputBase';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { alpha, styled } from '@mui/material/styles';
import * as React from 'react';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../UserProvider';

const Search = styled('div')(({ theme }) => ({
  position: 'relative',
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha(theme.palette.common.white, 0.15),
  '&:hover': {
    backgroundColor: alpha(theme.palette.common.white, 0.25),
  },
  marginRight: theme.spacing(2),
  marginLeft: 0,
  width: '100%',
  [theme.breakpoints.up('sm')]: {
    marginLeft: theme.spacing(3),
    width: 'auto',
  },
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: '100%',
  position: 'absolute',
  pointerEvents: 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  color: 'inherit',
  '& .MuiInputBase-input': {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: '20ch',
    },
  },
}));

export default function NavBar() {
  const user = useUser();
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [conversations, setConversations] = useState([]);
  const [notificationsCount, setNotificationsCount] = useState(0);
  const [showConversations, setShowConversations] = useState(false);
  const [selectedChat, setSelectedChat] = useState(null);
  const [showChat, setShowChat] = useState(false);
  const conversationsRef = React.useRef(null);

  const isMenuOpen = Boolean(anchorEl);
  const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

  const handleShowConversations = () => {
    setShowConversations((prev) => !prev);
  };

  const handleShowChat = (chat) => {
    setSelectedChat(chat);
    setShowChat(true);
  };

  const handleMinimizeChat = () => {
    setShowChat(false);
  };

  const handleCloseChat = () => {
    setSelectedChat(null);
    setShowChat(false);
  };

  
  const fetchConversations = React.useCallback(async () => {
    try {
      const response = await fetch('YOUR_API_ENDPOINT', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${user.jwt}`
        },
      });
      const data = await response.json();
      return data.conversations; 
    } catch (error) {
      console.error('Failed to fetch conversations', error);
      return [];
    }
  }, [user.jwt]);

  useEffect(() => {
    const checkLogin = () => {
      if (user.jwt.length < 1) {
        setIsLoggedIn(false);
      } else {
        setIsLoggedIn(true);
      }
    };

    checkLogin();
  }, [user, navigate]);

  useEffect(() => {
    if (isLoggedIn) {
      fetchConversations().then((conversations) => {
        setConversations(conversations);
      });
    }
  }, [isLoggedIn, fetchConversations]);


  useEffect(() => {
    const checkLogin = () => {
      if (user.jwt.length < 1) {
        setIsLoggedIn(false);
      } else {
        setIsLoggedIn(true);
      }
    };

    checkLogin();
  }, [user, navigate]);

  // Fetch notifications count from API
  useEffect(() => {
    const fetchNotificationsCount = async () => {
      try {
        const response = await fetch('http://localhost:8080/tazma/api/notifications/count', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.jwt}`
          },
        });
        const data = await response.json();
        setNotificationsCount(data.count);
      } catch (error) {
        console.error('Failed to fetch notifications count', error);
      }
    };

    if (isLoggedIn) {
      fetchNotificationsCount();
    }
  }, [isLoggedIn, user.jwt]);

  

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMobileMenuClose = () => {
    setMobileMoreAnchorEl(null);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
    handleMobileMenuClose();
  };

  const handleMobileMenuOpen = (event) => {
    setMobileMoreAnchorEl(event.currentTarget);
  };

  const handleLogout = async () => {
    try {
      console.log("MY JWT:" + user.jwt);

      await fetch('http://localhost:8080/tazma/api/auth/logout/' + user.jwt, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },

      });
      user.setJwt("");
      user.setRole("");

    } catch (error) {
      console.error('Logout Request Failed!!');
    }
  };

  const menuId = 'primary-search-account-menu';
  const renderMenu = (
    <Menu
      anchorEl={anchorEl}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      id={menuId}
      keepMounted
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={isMenuOpen}
      onClose={handleMenuClose}
    >
      <MenuItem onClick={handleMenuClose}>Profile</MenuItem>
      <MenuItem onClick={handleMenuClose}>My account</MenuItem>
    </Menu>
  );

  const mobileMenuId = 'primary-search-account-menu-mobile';
  const renderMobileMenu = (
    <Menu
      anchorEl={mobileMoreAnchorEl}
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      id={mobileMenuId}
      keepMounted
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      open={isMobileMenuOpen}
      onClose={handleMobileMenuClose}
    >
      <MenuItem>
        <IconButton size="large"
        aria-label="show conversations"
        color="inherit"
        onClick={handleShowConversations}>
          <Badge Badge badgeContent={showConversations.length} color="error">
            <MailIcon />
          </Badge>
        </IconButton>
        <p>Messages</p>

        <Popper open={showConversations} anchorEl={conversationsRef.current} placement="bottom">
        <ClickAwayListener onClickAway={() => setShowConversations(false)}>
          <Paper sx={{ maxHeight: '300px', overflowY: 'auto' }}>
            {conversations.map((chat) => (
              <div key={chat.id} onClick={() => handleShowChat(chat)}>
                <ListItem button>
                  <ListItemAvatar>
                    <Avatar alt={chat.recipientName} src={chat.recipientAvatar} />
                  </ListItemAvatar>
                  <ListItemText primary={chat.recipientName} secondary={chat.lastMessage} />
                </ListItem>
                <Divider />
              </div>
            ))}
          </Paper>
        </ClickAwayListener>
      </Popper>

      {/* Chat display */}
      {showChat && selectedChat && (
        <Box sx={{ position: 'fixed', bottom: '20px', right: '20px', zIndex: 1000 }}>
          <Paper>
            {/* Render your chat component here using selectedChat */}
            <Box p={2}>
              <Typography variant="h6">{selectedChat.recipientName}</Typography>
              <Typography variant="body1">{selectedChat.lastMessage}</Typography>
            </Box>
            <Box p={1} display="flex" justifyContent="flex-end">
              <IconButton onClick={handleMinimizeChat}>
                <MinimizeIcon />
              </IconButton>
              <IconButton onClick={handleCloseChat}>
                <CloseIcon />
              </IconButton>
            </Box>
          </Paper>
        </Box>
      )}
      </MenuItem>
      <MenuItem>
        <IconButton
          size="large"
          aria-label="show 17 new notifications"
          color="inherit"
        >
          <Badge badgeContent={notificationsCount} color="error">
            <NotificationsIcon />
          </Badge>
        </IconButton>
        <p>Notifications</p>
      </MenuItem>
      <MenuItem onClick={handleProfileMenuOpen}>
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="primary-search-account-menu"
          aria-haspopup="true"
          color="inherit"
        >
          <AccountCircle />
        </IconButton>
        <p>Profile</p>
      </MenuItem>
      <MenuItem>
        <IconButton size="large"
          color="inherit">
          <LoginIcon />
        </IconButton>
      </MenuItem>
    </Menu>
  );

  if (!isLoggedIn) {
    return null;
  }

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="open drawer"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            noWrap
            component="div"
            sx={{ display: { xs: 'none', sm: 'block' } }}
          >
            amani
          </Typography>
          <Search>
            <SearchIconWrapper>
              <SearchIcon />
            </SearchIconWrapper>
            <StyledInputBase
              placeholder="Search…"
              inputProps={{ 'aria-label': 'search' }}
            />
          </Search>
          <Box sx={{ flexGrow: 1 }} />
          <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
            <IconButton size="large" aria-label="show 4 new mails" color="inherit">
              <Badge badgeContent={4} color="error">
                <MailIcon />
              </Badge>
            </IconButton>
            <IconButton
              size="large"
              aria-label="show 17 new notifications"
              color="inherit"
            >
              <Badge badgeContent={notificationsCount} color="error">
                <NotificationsIcon />
              </Badge>
            </IconButton>
            <IconButton
              size="large"
              edge="end"
              aria-label="account of current user"
              aria-controls={menuId}
              aria-haspopup="true"
              onClick={handleProfileMenuOpen}
              color="inherit"
            >
              <AccountCircle />
            </IconButton>
            <IconButton size="large"
              aria-controls={menuId}
              aria-haspopup="true"
              onClick={handleLogout}
              color="inherit">
              <LogoutIcon />
            </IconButton>
          </Box>
          <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="show more"
              aria-controls={mobileMenuId}
              aria-haspopup="true"
              onClick={handleMobileMenuOpen}
              color="inherit"
            >
              <MoreIcon />
            </IconButton>
          </Box>
        </Toolbar>
      </AppBar>
      {renderMobileMenu}
      {renderMenu}
    </Box>
  );
}
