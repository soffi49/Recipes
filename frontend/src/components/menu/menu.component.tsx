import { Divider, List, ListItem, ListItemIcon, ListItemText, Toolbar } from '@mui/material';
import Drawer from '@mui/material/Drawer';
import { Link, useNavigate } from "react-router-dom";
import { menuList } from '../../constants/menu.constants';
import LogoutIcon from '@mui/icons-material/Logout';
import styled from "@emotion/styled";
import AuthService from '../../servies/AuthService';

export default function Menu() {
    let navigate = useNavigate();
    const logoutOnClick = () => {
        AuthService.logout();
        navigate("/not-authorized");
    }

    return (
      <Drawer
        variant="permanent"
        sx={{
          display: { xs: "none", sm: "block" },
          "& .MuiDrawer-paper": { boxSizing: "border-box", width: 240, backgroundColor: "#1976d2" },
        }}
        open
      >
        <div>
            <Toolbar />
            <Divider />
            <List>
                {menuList.map((item, index) => {
                const {text, icon, to} = item;
                return(
                <ListItem key={text} component={Link} to={to} aria-label={text} >
                    <ListItemIcon>{icon}</ListItemIcon>
                    <ListItemText sx={{color: "white"}} primary={text} />
                </ListItem>
                )
                })}
                <StyledDiv>
                    <Divider />
                    <ListItem button key="Logout" onClick={logoutOnClick} aria-label="Logout" >
                        <ListItemIcon><LogoutIcon /></ListItemIcon>
                        <ListItemText sx={{color: "white"}} primary="Logout" />
                    </ListItem>
                </StyledDiv>
            </List>
        </div>
      </Drawer>
    )
}

const StyledDiv = styled.div`
    bottom: 0;
    position: fixed;
    width: 239px;
`