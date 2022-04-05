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
    const [limit, setLimit] = useState<number>(10);
    const [count, setCount] = useState<number>(0);
    const getAllIngredients = () => {
        getIngredientsApi(page, limit).then((response) => {
            if(!!response.ingredients){
                setIngredients(response.ingredients);
                setCount(response.total_ingredients);
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


    useEffect(() => {
        getAllIngredients();
    }, [limit, page])

    return isFetching ? (
        <Box sx={{ display: 'flex', justifyContent: 'center',}}>
            <CircularProgress />
        </Box>
        ) : (
        <>
            <AddIngredient addIngredient={addIngredient}/>
            <IngredientsTable 
                ingredients={ingredients}
                page={page}
                limit={limit}
                count={count}
                handleChangeRowsPerPage={handleChangeRowsPerPage}
                handleChangePage={handleChangePage}
            />
        </>
      );
}