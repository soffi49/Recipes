import axios from 'axios';
import { server } from '../constants/constants'

export async function getIngredientsApi(page: number, limit: number) {
    try {
        const response = await axios.get(`http://${server}/ingredients?page=${page}&limit=${limit}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export async function addIngredientApi(name: string) {
    try {
        await axios.post(`http://${server}/ingredients`, {
            id: 0,
            name: name
        })
    } catch (error) {
        throw error;
    }
}