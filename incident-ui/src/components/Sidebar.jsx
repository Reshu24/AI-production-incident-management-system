import { Drawer, List, ListItemButton, ListItemIcon, ListItemText, Toolbar } from "@mui/material";
import DashboardIcon from "@mui/icons-material/Dashboard";
import ReportProblemIcon from "@mui/icons-material/ReportProblem";
import MenuBookIcon from "@mui/icons-material/MenuBook";
import GroupsIcon from "@mui/icons-material/Groups";
import { useNavigate } from "react-router-dom";
import SmartToyIcon from "@mui/icons-material/SmartToy";

const drawerWidth = 240;

export default function Sidebar() {

    const navigate = useNavigate();

    return (
        <Drawer
            variant="permanent"
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                "& .MuiDrawer-paper": {
                    width: drawerWidth,
                    boxSizing: "border-box"
                }
            }}
        >

            <Toolbar />

            <List>

                <ListItemButton onClick={() => navigate("/dashboard")}>
                    <ListItemIcon>
                        <DashboardIcon />
                    </ListItemIcon>
                    <ListItemText primary="Dashboard" />
                </ListItemButton>

                <ListItemButton onClick={() => navigate("/incidents")}>
                    <ListItemIcon>
                        <ReportProblemIcon />
                    </ListItemIcon>
                    <ListItemText primary="Incidents" />
                </ListItemButton>

                <ListItemButton onClick={() => navigate("/api/ai/analyse")}>
                    <ListItemIcon>
                        <SmartToyIcon />
                    </ListItemIcon>

                    <ListItemText primary="AI Log Analyzer" />
                </ListItemButton>

                <ListItemButton onClick={() => navigate("/knowledge")}>
                    <ListItemIcon>
                        <MenuBookIcon />
                    </ListItemIcon>
                    <ListItemText primary="Knowledge" />
                </ListItemButton>

                <ListItemButton onClick={() => navigate("/ownership")}>
                    <ListItemIcon>
                        <GroupsIcon />
                    </ListItemIcon>
                    <ListItemText primary="Ownership" />
                </ListItemButton>

            </List>

        </Drawer>
    );
}