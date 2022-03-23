import { useEffect, useState } from 'react';

import CircularProgress from '@mui/material/CircularProgress';
import { getIngredientsApi, addIngredientApi } from '../../api/api.api';
import Box from '@mui/system/Box';
import IngredientsTable from '../ingredients-table/ingredients-table.component';
import { IngredientDetails } from '../../models/models';
import AddIngredient from '../ingredients-table-add-ingredient/add-ingredient.component';

export default function IngredientsTableAdminView() {
    const [ingredients, setIngredients] = useState<IngredientDetails[]>([]);
    const [isFetching, setIsFetching] = useState<boolean>(true);
    const [page, setPage] = useState<number>(0);
    const [limit, setLimit] = useState<number>(100);
    const getAllIngredients = () => {
        getIngredientsApi(page, limit).then((response) => {
            if(!!response.ingredients){
                setIngredients(response.ingredients)
            };
        setIsFetching(false);
        });
    };

    const addIngredient = (name: string) => {
        setIsFetching(true);
        addIngredientApi(name).then(() => getAllIngredients());
    }


    useEffect(() => {
        getAllIngredients();
    }, [])

    return isFetching ? (
        <Box sx={{ display: 'flex', justifyContent: 'center',}}>
            <CircularProgress />
        </Box>
        ) : (
        <>
            <AddIngredient addIngredient={addIngredient}/>
            <IngredientsTable 
                ingredients={ingredients}
            />
        </>
      );
}