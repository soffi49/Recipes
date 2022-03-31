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
interface RecipeTableProps {
    recipes: RecipeDetails[];
}
interface propsDetailsModal{
  isVisible: boolean;
  recipe?: RecipeDetails;
}

export default function RecipesTable({recipes}: RecipeTableProps) {
    const [detailsState,setDetailsState] = useState<propsDetailsModal>({isVisible: false});
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
                {recipes.map((row) => (
                  <TableRow
                    key={row.id}
                  >
                    <TableCell align="center">{row.id}</TableCell>
                    <TableCell align="center">{row.name}</TableCell>
                    <TableCell align="center">
                        <IconButton aria-label="Edit button">
                            <EditIcon />
                        </IconButton>
                        <IconButton aria-label="Delete button">
                            <DeleteIcon />
                        </IconButton>
                        <IconButton aria-label="Details button" onClick = {() => setDetailsState({isVisible: true, recipe: row})}>
                            <SearchIcon />
                        </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <DetailsModal visible={detailsState.isVisible} onCancel={() =>setDetailsState({isVisible: false,recipe: detailsState.recipe})} recipe = {detailsState.recipe}/>
        </>
      );
}