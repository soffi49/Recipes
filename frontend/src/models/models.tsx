export interface LoginInformation {
    login: string;
    password: string;
}

export interface IngredientDetails {
    id: number;
    name: string;
    quantity?: number;
}
export interface IngredientDetailsQuantity {
    ingredient: IngredientDetails;
    quantity: number;
    
}
export interface RecipeDetails {
    id: number;
    name: string;
    instructions: string;
    ingredients: Array<IngredientDetailsQuantity>;
    tags: Array<string>;
}