import React, { useState, useEffect } from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    TextField,
    Typography
} from "@mui/material";
import styled from '@emotion/styled';
import { IngredientDetailsQuantity, RecipeDetails } from '../../models/models';
import IngredientsModal from '../recipes-table/edit-ingredients-modal';
interface Props {
    visible: boolean;   
    onCancel: () => void;
    addRecipe: (recipe: RecipeDetails) => void;
    handleOpen: () => void;
}
const AddRecipeModal: React.FC<Props> = ({visible,onCancel,addRecipe, handleOpen}) => {
  const HandleNameEdit = (e:any) => {
    setRecipeName(e.target.value);
  }
  const HandleInstructionsEdit = (e:any) => {
    setRecipeInstructions(e.target.value);
  }
  const [ingredientsModal,setIngredientsModal] = useState<any>({isVisible: false,ingredients: []});
  const[ recipeName,setRecipeName] = useState("");
  const[ recipeInstructions,setRecipeInstructions] = useState("");
  const[ recipeIngredients,setRecipeIngredients] = useState<IngredientDetailsQuantity[]>([]);
  const[ recipeTags,setRecipeTags] = useState([]);

  useEffect( () =>{
  }
  ,[recipeIngredients]);

  const AcceptIngredients = (newIngredients : IngredientDetailsQuantity[]) => {
    setIngredientsModal({isVisible: false,ingredients: recipeIngredients});
    setRecipeIngredients(newIngredients);
  };

    return (
        <div>
          <Button
              id={'add-recipe-button'}
            variant="contained"
            sx={{mb: 1}}
            onClick={handleOpen}
            aria-label="Add Recipe Button"
          >
            Add Recipe
          </Button>
            <Dialog fullScreen={false} open={visible} >
                <TextField defaultValue={recipeName} onChange={HandleNameEdit}></TextField>
                <DialogContent>
                    <Typography >Instructions:</Typography>
                    <Instructions  defaultValue={recipeInstructions} onChange={HandleInstructionsEdit}></Instructions>
                    <br/>
                    <Button aria-label="Edit ingredients button" variant ="outlined" onClick ={() => setIngredientsModal({isVisible:true,ingredients: recipeIngredients})}>Edit Ingredients</Button>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onCancel}>Close</Button>
                    <Button onClick={() =>{ addRecipe({
                      id: 0,
                      name: recipeName,
                      instructions:recipeInstructions,
                      ingredients: recipeIngredients,
                      tags: recipeTags
                    });}
                    }
                    aria-label="Accept button"
                    >Accept</Button>
                </DialogActions>
                <IngredientsModal visible = {ingredientsModal.isVisible} ingredients = {[]}
                onCancel={() =>setIngredientsModal({isVisible: false,ingredients: recipeIngredients})}
                onAccept={AcceptIngredients}
                />
            </Dialog>
        </div>
    )
}

const Instructions = styled.textarea`
  min-width:20vw;
  min-heigth:30vw;
`;

export default AddRecipeModal;