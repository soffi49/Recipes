import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton'
import DeleteIcon from '@mui/icons-material/Delete';
import { IngredientDetails } from '../../models/models';
import { TableFooter } from '@mui/material';
import { TablePagination } from '@mui/material';
import EditIngredient from '../ingredients-table-edit-ingredient/edit-ingredient.component';
interface IngredientTableProps {
    ingredients: IngredientDetails[];
    page: number;
    limit: number;
    count: number;
    handleChangeRowsPerPage: ( event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
    handleChangePage: ( event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => void;
    deleteIngredient: (id: number) => void;
    editIngredient: (name: string, id: number) => void;
}

export default function IngredientsTable({ingredients, page, limit, count, handleChangeRowsPerPage, handleChangePage, deleteIngredient, editIngredient}: IngredientTableProps) {
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
                        <EditIngredient oldName={row.name} id={row.id} editIngredient={editIngredient}/>
                        <IconButton aria-label="Delete button" onClick={() => deleteIngredient(row.id)}>
                            <DeleteIcon />
                        </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
              <TableFooter>
                <TablePagination
                  count={count}
                  page={page}
                  onPageChange={handleChangePage}
                  rowsPerPage={limit}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                />
              </TableFooter>
            </Table>
          </TableContainer>
        </>
      );
}