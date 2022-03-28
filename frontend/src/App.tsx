import IngredientsTableAdminView from "./components/ingredients-table-admin-view/ingredients-table-admin-view.component";
import Menu from "./components/menu/menu.component"
import { Box } from "@mui/system";
import { Routes, Route } from "react-router-dom";

export default function App() {
  return (
    <div>
      <Menu />
      <Box 
        component="main"
        sx={{ flexGrow: 1, p: 3, width: { sm: "calc(100% - 280px)" }, ml: 29.5 }}
      >
        <Routes>
          <Route path="/" element={<IngredientsTableAdminView />} />
          <Route path="/Recipes" element={<div>Recipes</div>} />
        </Routes>
      </Box>
    </div>
  );
}
