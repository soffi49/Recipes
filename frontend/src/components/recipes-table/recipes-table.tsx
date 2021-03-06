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
import SearchIcon from '@mui/icons-material/Search';
import { RecipeDetails } from '../../models/models';
import { useState } from 'react';
import DetailsModal from './details-modal';
import { TableFooter, TablePagination } from '@mui/material';
import EditModal from './edit-modal';
interface RecipeTableProps {
    recipes: RecipeDetails[];
    page: number;
    limit: number;
    count: number;
    handleChangeRowsPerPage: ( event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
    handleChangePage: ( event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => void;
    deleteRecipe: (id: number) => void;
    editRecipe: (recipe: RecipeDetails, id:number) => void;
}
interface propsDetailsModal {
  isVisible: boolean;
  recipe: RecipeDetails;
}

export default function RecipesTable({recipes, page, limit, count, handleChangeRowsPerPage, handleChangePage, deleteRecipe,editRecipe}: RecipeTableProps) {
    const placeholder = {
      id:-1,
      name:"placeholder",
      instructions:" ",
      ingredients: [],
      tags: []
    }
    const [detailsState,setDetailsState] = useState<propsDetailsModal>({isVisible: false,recipe: placeholder});
    const [editState,setEditState] = useState<propsDetailsModal>({isVisible: false,recipe: placeholder});
    return (
        <>
          <TableContainer id={'recipe-table-container'} component={Paper}>
            <Table>
              <TableHead id={'recipe-table-header'}>
                <TableRow>
                  <TableCell align="center">Id</TableCell>
                  <TableCell align="center">Name</TableCell>
                  <TableCell align="center">Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody id={'recipe-table-body'}>
                {recipes.map((row) => (
                  <TableRow
                    key={row.id}
                  >
                    <TableCell align="center">{row.id}</TableCell>
                    <TableCell align="center">{row.name}</TableCell>
                    <TableCell align="center">
                        <IconButton aria-label="Edit button" onClick = {() => setEditState({isVisible: true, recipe: row})}>
                          <EditIcon/>
                        </IconButton>
                        <IconButton aria-label="Delete button">
                            <DeleteIcon onClick={() => deleteRecipe(row.id)}/>
                        </IconButton>
                        <IconButton aria-label="Details button" onClick = {() => setDetailsState({isVisible: true, recipe: row})}>
                            <SearchIcon />
                        </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
              <TableFooter id={'recipe-table-footer'}>
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
          <DetailsModal visible={detailsState.isVisible} onCancel={() =>setDetailsState({isVisible: false,recipe: detailsState.recipe})} recipe = {detailsState.recipe}/>
          <EditModal  visible={editState.isVisible} onCancel={() =>setEditState({isVisible: false,recipe: editState.recipe})} recipe = {editState.recipe} editRecipe = {editRecipe}/>
        </>
      );
}
