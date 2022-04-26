import axios from 'axios';
import { server } from '../constants/constants'
import { RecipeDetails } from '../models/models';

export async function getIngredientsApi(page: number, limit: number) {
    const key = "" + sessionStorage.getItem("key");
    try {
        const response = await axios.get(`${server}/ingredients?page=${page}&limit=${limit}`, {
            headers: {
              'security_header': key
            }});
            
        return response.data;
    } catch (error) {
        throw error;
    }
}

export async function addIngredientApi(name: string) {
    const key = "" + sessionStorage.getItem("key");
    try {
        await axios.post(`${server}/ingredients`, {
            id: 0,
            name: name
        },{
            headers: {
              'security_header': key
        }})
    } catch (error) {
        throw error;
    }
}


export async function deleteIngredientApi(id: number) {
    const key = "" + sessionStorage.getItem("key");
    try {
        await axios.delete(`${server}/ingredients/${id}`, {
            headers: {
              'security_header': key
            }})
    } catch (error) {
        throw error;
    }
}

export async function editIngredientApi(name: string, id: number) {
    const key = "" + sessionStorage.getItem("key");
    try {
        await axios.put(`${server}/ingredients/${id}`, {
            id: id,
            name: name
        },{
            headers: {
              'security_header': key
        }})
    } catch (error) {
        throw error;
    }
}

export async function deleteRecipeApi(id: number) {
    try {
        await axios.delete(`${server}/recipes/${id}`)
    } catch (error) {
        throw error;
    }
}

export async function getRecipesApi(page: number, limit: number) {
    try {
        const response = await axios.get(`${server}/recipes?page=${page}&limit=${limit}`);
        return response.data;

    } catch (error) {
        throw error;
    }
}

export async function editRecipeApi(recipe: RecipeDetails, id: number) {
    try {
        console.log(recipe);
        const response = await axios.put(`${server}/recipes/${id}`,{
            id:recipe.id,
            name:recipe.name,
            instructions:recipe.instructions,
            ingredients:recipe.ingredients,
            tags:recipe.tags

        })
        return response.data;

    } catch (error) {
        throw error;
    }
}