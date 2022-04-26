import React, { useEffect, useState} from 'react';
import {
    Button,
    Card,
    Dialog,
    DialogActions,
    DialogContent,
    TextField,
    Typography
} from "@mui/material";
import { IngredientDetailsQuantity, RecipeDetails } from '../../models/models';
import styled from '@emotion/styled';
import IngredientsModal from './edit-ingredients-modal';
interface Props {
    visible: boolean;   
    onCancel: () => void;
    recipe: RecipeDetails;
    editRecipe: (recipe: RecipeDetails, id:number) => void;
}
const EditModal: React.FC<Props> = ({visible,onCancel,recipe,editRecipe}) => {
  const HandleNameEdit = (e:any) => {
    setRecipeName(e.target.value);
  }
  const HandleInstructionsEdit = (e:any) => {
    setRecipeInstructions(e.target.value);
  }
  const [ingredientsModal,setIngredientsModal] = useState({isVisible: false,ingredients: recipe.ingredients});
  const[ recipeName,setRecipeName] = useState(recipe.name);
  const[ recipeInstructions,setRecipeInstructions] = useState(recipe.instructions);
  const[ recipeIngredients,setRecipeIngredients] = useState(recipe.ingredients);
  const[ recipeTags,setRecipeTags] = useState(recipe.tags);
  useEffect( () =>{
    setRecipeName(recipe.name);
    setRecipeInstructions(recipe.instructions);
    setRecipeIngredients(recipe.ingredients);
    setRecipeTags(recipe.tags);
  }
  ,[recipe])
    return (
        <>
            <Dialog fullScreen={false} open={visible} >
                <TextField defaultValue={recipe?.name} onChange={HandleNameEdit}></TextField>
                <DialogContent>
                <Typography >Instructions:</Typography>
                <Instructions  defaultValue={recipe?.instructions} onChange={HandleInstructionsEdit}></Instructions>
                <br/>
                <Button aria-label="Edit ingredients button" variant ="outlined" onClick ={() => setIngredientsModal({isVisible:true,ingredients: recipeIngredients})}>Edit Ingredients</Button>
                
                
                    
                </DialogContent>
                <DialogActions>
                <Button onClick={onCancel}>Close</Button>
                <Button onClick={() =>{ editRecipe({
                  id: recipe.id,
                  name: recipeName,
                  instructions:recipeInstructions,
                  ingredients: recipeIngredients,
                  tags: recipeTags
                },recipe.id);}
              
              }>Accept</Button>
                </DialogActions>
                <IngredientsModal visible = {ingredientsModal.isVisible} ingredients = {recipeIngredients}
                onCancel={() =>setIngredientsModal({isVisible: false,ingredients: recipeIngredients})}
                onAccept={(newIngredients : IngredientDetailsQuantity[]) =>{setIngredientsModal({isVisible: false,ingredients: recipeIngredients});
                            setRecipeIngredients(newIngredients); console.log(recipeIngredients)}}
                ></IngredientsModal>
            </Dialog>
        </>
    )
}
const CardsContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;  
  justify-content: space-around;
  max-width:30vw;
  min-width:20vw;
`;
const TagsContainer = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;  
  justify-content: space-around;
  max-width:30vw;
  min-width:20vw;
`;
const StyledCard = styled(Card)`
  padding: 10px;
  background-color: LavenderBlush;
  margin:5px;
  display:inline;
`;
const Tag = styled(Card)`
  padding: 10px;
  background-color: AliceBlue;
  margin:5px;
  display:inline;
`;
const Instructions = styled.textarea`
  min-width:20vw;
  min-heigth:30vw;
`;


export default EditModal;