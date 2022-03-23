import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton'
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { IngredientDetails } from '../../models/models';
import { deleteIngredientApi } from '../../api/api.api';
interface IngredientTableProps {
    ingredients: IngredientDetails[];
    getAllIngredients: () => void;
}

export default function IngredientsTable({ingredients,getAllIngredients}: IngredientTableProps) {
  const clickDelete = async (id: number) => {
    await deleteIngredientApi(id).then(() => getAllIngredients()).catch((e) => console.log(e));
  }
    return (
        <>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell align="center">Id</TableCell>
                  <TableCell align="center">Name</TableCell>
                  <TableCell align="center">Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {ingredients.map((row) => (
                  <TableRow
                    key={row.id}
                  >
                    <TableCell align="center">{row.id}</TableCell>
                    <TableCell align="center">{row.name}</TableCell>
                    <TableCell align="center">
                        <IconButton aria-label="Edit button">
                            <EditIcon />
                        </IconButton>
                        <IconButton aria-label="Delete button" onClick={() => clickDelete(row.id)}>
                            <DeleteIcon />
                        </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </>
      );
}