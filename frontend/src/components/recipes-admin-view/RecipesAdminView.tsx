import { useEffect, useState } from 'react';

import CircularProgress from '@mui/material/CircularProgress';
import { getRecipesApi } from '../../api/api.api';
import Box from '@mui/system/Box';
import { RecipeDetails } from '../../models/models';
import RecipesTable from '../recipes-table/recipes-table';

export default function RecipesAdminView() {
    const [recipes, setRecipes] = useState<RecipeDetails[]>([]);
    const [isFetching, setIsFetching] = useState<boolean>(true);
    const [page, setPage] = useState<number>(0);
    const [limit, setLimit] = useState<number>(100);
    const getAllRecipes = () => {
        getRecipesApi(page, limit).then((response) => {
            if(!!response){
                setRecipes(response)
            };
        setIsFetching(false);
        });
    };


    useEffect(() => {
        getAllRecipes();
    }, [])

    return isFetching ? (
        <Box sx={{ display: 'flex', justifyContent: 'center',}}>
            <CircularProgress />
        </Box>
        ) : (
        <>
            <RecipesTable 
                recipes={recipes}
            />
        </>
      );
}