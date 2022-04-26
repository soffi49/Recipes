import { useEffect, useState } from 'react';

import CircularProgress from '@mui/material/CircularProgress';
import { getIngredientsApi, addIngredientApi, deleteIngredientApi, editIngredientApi, getFilteredIngredientsApi } from '../../api/api.api';
import Box from '@mui/system/Box';
import IngredientsTable from '../ingredients-table/ingredients-table.component';
import { IngredientDetails } from '../../models/models';
import AddIngredient from '../ingredients-table-add-ingredient/add-ingredient.component';
import { Button, MenuItem, TextField } from '@mui/material';
import { CollectionsBookmarkRounded } from '@mui/icons-material';
import styled from '@emotion/styled';
export default function IngredientsTableAdminView() {
    const [ingredients, setIngredients] = useState<IngredientDetails[]>([]);
    const [isFetching, setIsFetching] = useState<boolean>(true);
    const [page, setPage] = useState<number>(0);
    const [limit, setLimit] = useState<number>(10);
    const [count, setCount] = useState<number>(0);
    const [filter, setFilter] = useState('Name');
    const [filterContent, setFilterContent] = useState('');
    const filters = [
        {value: "Name"},
        {value: "?"}
    ] 
   
    
    const handleChangeFilter = (event: React.ChangeEvent<HTMLInputElement>) => {
         setFilter(event.target.value);
    };
    const handleFilterContentChange= (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilterContent(event.target.value);
   };
   const handleFilterClick= (event:any) => {
        getFilteredIngredients();
};
    const getAllIngredients = () => {
        getIngredientsApi(page, limit).then((response) => {
            setIsFetching(true);
            if(!!response.ingredients){
                setIngredients(response.ingredients);
                setCount(10); //change total_ingredients
            };
        setIsFetching(false);
        
        });
    };
    const getFilteredIngredients = () => {
        if(filter === "?")return;
        getFilteredIngredientsApi(page, limit,filterContent).then((response) => {
            setIsFetching(true);
            if(!!response.ingredients){
                setIngredients(response.ingredients);
                setCount(10); //change total_ingredients
            };
        setIsFetching(false);
        
        });
    };

    const handleChangePage = (
      event: React.MouseEvent<HTMLButtonElement> | null,
      newPage: number,
    ) => {
      setIsFetching(true);
      setPage(newPage);
    };

    const handleChangeRowsPerPage = (
      event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => {
      setIsFetching(true);
      setPage(0);
      setLimit(parseInt(event.target.value));
    };

    const addIngredient = (name: string) => {
        setIsFetching(true);
        addIngredientApi(name).then(() => getAllIngredients());
    }

    const editIngredient = (name: string, id: number) => {
        setIsFetching(true);
        editIngredientApi(name, id).then(() => getAllIngredients());
    }

    const deleteIngredient = (id: number) => {
        setIsFetching(true);
        deleteIngredientApi(id).then(() => getAllIngredients());
    }

    useEffect(() => {
        getFilteredIngredients();
    }, [limit, page])
    
    
  
    return isFetching ? (
        <Box sx={{ display: 'flex', justifyContent: 'center',}}>
            <CircularProgress />
        </Box>
        ) : (
        <>
        <SearchBar>
          <TextField id="outlined-basic" variant="standard" value={filterContent} onChange={handleFilterContentChange}></TextField>
          <TextField select value={filter} onChange={handleChangeFilter} variant = "standard">
          {filters.map((option: any) => (
            <MenuItem key={option.value} value={option.value} autoFocus={false}>
              {option.value}
            </MenuItem>
          ))}
          </TextField>
          <Button variant="contained" onClick = {handleFilterClick}>Search</Button>
          </SearchBar>
            <AddIngredient addIngredient={addIngredient}/>
            <IngredientsTable 
                ingredients={ingredients}
                page={page}
                limit={limit}
                count={count}
                handleChangeRowsPerPage={handleChangeRowsPerPage}
                handleChangePage={handleChangePage}
                deleteIngredient={deleteIngredient}
                editIngredient={editIngredient}
            />
        </>
        
      );

}
const SearchBar = styled.div`
width: 30vw;
margin: 10px 10px 10px 0px;
display: flex;
flex-direction: row;
justify-content: space-between;
`;

