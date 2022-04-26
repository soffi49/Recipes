import styled from "@emotion/styled";
import { Card, CardContent, Checkbox, Typography } from "@mui/material";
import React, { useState } from "react";
import { IngredientDetails, IngredientDetailsQuantity } from "../../models/models";
import AddIngredient from "../ingredients-table-add-ingredient/add-ingredient.component";
interface Props{
    ingredient: IngredientDetails;
    checked: boolean;
    addIngredient: (ingredient: IngredientDetailsQuantity) => void;
    deleteIngredient: (IngredientDetailsQuality: IngredientDetailsQuantity) => void;
}
const IngredientItem : React.FC<Props> = ({ingredient,checked,addIngredient,deleteIngredient}) =>{
    const [isChecked,setIsChecked] = useState<boolean>(checked);
    const handleChange = () =>{
        if(isChecked === true){
            setIsChecked(prevData => false);
            deleteIngredient({ingredient,quantity: 1});
        }
        else{
            setIsChecked(prevData => true);
            addIngredient({ingredient,quantity: 1});
        } 
    }
return(
    <StyledCard>
        <CardContent>
            <Typography>{ingredient.name}</Typography>
            <Checkbox checked={isChecked} onChange={handleChange}></Checkbox>
        </CardContent>
        
    </StyledCard>
)
}
const StyledCard = styled(Card)`
  padding: 5px;
  background-color: LavenderBlush;
  margin:5px;
`;
export default IngredientItem;