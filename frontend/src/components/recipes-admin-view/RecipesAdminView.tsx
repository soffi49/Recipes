import { useEffect, useState } from 'react';

import CircularProgress from '@mui/material/CircularProgress';
import { deleteRecipeApi, editRecipeApi, getRecipesApi } from '../../api/api.api';
import Box from '@mui/system/Box';
import { RecipeDetails } from '../../models/models';
import RecipesTable from '../recipes-table/recipes-table';

export default function RecipesAdminView() {
    const [recipes, setRecipes] = useState<RecipeDetails[]>([]);
    const [isFetching, setIsFetching] = useState<boolean>(true);
    const [page, setPage] = useState<number>(0);
    const [limit, setLimit] = useState<number>(10);
    const [count, setCount] = useState<number>(0);
    const getAllRecipes = () => {
        getRecipesApi(page, limit).then((response) => {
            if(!!response){
                setRecipes(response.recipes)
                setCount(response.total_recipes)
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

    const deleteRecipe = (id: number) => {
        setIsFetching(true);
        deleteRecipeApi(id).then(() => getAllRecipes());
    }
    const editRecipe = (recipe:RecipeDetails,id: number) => {
        setIsFetching(true);
        editRecipeApi(recipe,id).then(() => getAllRecipes());
    }

    useEffect(() => {
        getAllRecipes();
    }, [limit, page])

    return isFetching ? (
        <Box sx={{ display: 'flex', justifyContent: 'center',}}>
            <CircularProgress />
        </Box>
        ) : (
        <>
            <RecipesTable 
                recipes={recipes}
                page={page}
                limit={limit}
                count={count}
                handleChangeRowsPerPage={handleChangeRowsPerPage}
                handleChangePage={handleChangePage}
                deleteRecipe={deleteRecipe}
                editRecipe={editRecipe}
            />
        </>
      );
}