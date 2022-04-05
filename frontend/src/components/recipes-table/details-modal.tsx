import React, {ChangeEventHandler, useState} from 'react';
import {
    Button,
    Card,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField,
    Typography
} from "@mui/material";
import { IngredientDetailsQuantity, RecipeDetails } from '../../models/models';
import styled from '@emotion/styled';
interface Props {
    visible: boolean;   
    onCancel: () => void;
    recipe?: RecipeDetails;
}

const DetailsModal: React.FC<Props> = ({visible,onCancel,recipe}) => {
    return (
        <>
            <Dialog fullScreen={false} open={visible} >
                <DialogTitle>{recipe?.name}</DialogTitle>
                <DialogContent>
                <Typography variant="body1">Instructions:</Typography>
                <Typography variant="body2">{recipe?.instructions}</Typography>
                <Typography variant="body1">Ingredients:</Typography>
                <CardsContainer>
                {recipe?.ingredients.map((ingredient: IngredientDetailsQuantity) => 
                    <StyledCard key={ingredient.ingredient.id}>{ingredient.ingredient.name}</StyledCard>
                )}
                </CardsContainer>
                <Typography variant="body1">Tags:</Typography>
                <TagsContainer>
                {recipe?.tags.map((tag: string,index) => 
                    <Tag key={index}>{tag}</Tag>
                )}
                </TagsContainer>
                    
                </DialogContent>
                <DialogActions>
                <Button onClick={onCancel}>Close</Button>
                </DialogActions>
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


export default DetailsModal;