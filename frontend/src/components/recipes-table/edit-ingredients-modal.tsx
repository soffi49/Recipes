import React, { useEffect, useState} from 'react';
import {
    Button,
    Card,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
} from "@mui/material";
import { IngredientDetails, IngredientDetailsQuantity, RecipeDetails } from '../../models/models';
import { getIngredientsApi } from '../../api/api.api';

import styled from '@emotion/styled';
import IngredientItem from './ingredient-item';
interface Props {
    visible: boolean;   
    onCancel: () => void;
    onAccept: (newIngredients : IngredientDetailsQuantity[]) => void;
    ingredients: IngredientDetailsQuantity[];
}

const IngredientsModal: React.FC<Props> = ({visible,onCancel,ingredients,onAccept}) => {
    const [isFetching, setIsFetching] = useState<boolean>(false);
    const [allIngredients,setAllIngredients] = useState<IngredientDetails[]>();
    const [newIngredients,setNewIngredients] = useState<IngredientDetailsQuantity[]>(ingredients);
    
    const getAllIngredients = () => {
        getIngredientsApi(0, 100).then((response) => {
            setIsFetching(true);
            if(!!response){
                setAllIngredients(response.ingredients);
            }
        setIsFetching(false);
    })};
    const addIngredient = (ingredient: IngredientDetailsQuantity) =>{
        setIsFetching( true);
        setNewIngredients([...newIngredients, ingredient]);
    }
    const deleteIngredient = (ingredient: IngredientDetailsQuantity) =>{
        setNewIngredients(newIngredients => newIngredients?.filter(x => x.ingredient.id!==ingredient.ingredient.id));
    }
    useEffect(() => {
        getAllIngredients();
        setNewIngredients(ingredients);
    }, [ingredients])
    useEffect(() => {
        setIsFetching(false);
    }, [newIngredients])
    return (
        <>
           
        <Dialog fullScreen={true} open={visible} >
        <>
                <DialogTitle>Edit your ingredients</DialogTitle>
                <DialogContent>
                    {isFetching ? <Button>Saving...</Button> : null}
                <IngredientsContainer>
                { 
                    allIngredients?.map((x: IngredientDetails) =>
                    <IngredientItem key={x.id} ingredient={x} checked= {ingredients.find( (y) => y.ingredient.id === x.id) ? true: false}
                                    addIngredient={addIngredient} deleteIngredient={deleteIngredient}/>
                    )
                }
                </IngredientsContainer>        
                </DialogContent>

                <DialogActions>
                <Button onClick={() => onAccept(newIngredients!)}>Accept</Button>
                <Button onClick={onCancel}>Close</Button>
                </DialogActions>
                </>
                
        </Dialog> 
        
        </>
    )
}
const IngredientsContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;  
  justify-content: space-around;
  max-width:20vw;
`;




export default IngredientsModal;