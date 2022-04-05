import axios from "axios";
import {getIngredientsApi, addIngredientApi, deleteIngredientApi, deleteRecipeApi} from "./api.api";
import data from "../constants/ingredient-table.data.json";

describe("Api tests", () => {
    jest.mock("axios");

    it("should get ingredients", async () => {
        const payload = { data: data };
        axios.get = jest.fn().mockImplementationOnce(() => Promise.resolve(payload));
        await expect(getIngredientsApi(0, 100)).resolves.toEqual(data);
    });

    it("should thorw error while getting ingredients", async () => {
        const errorMessage = new Error("rejected");
        axios.get = jest.fn().mockImplementationOnce(() => Promise.reject(errorMessage));
        await expect(getIngredientsApi(0, 100)).rejects.toThrow(errorMessage);
    });

    it("add ingredient", async () => {
        axios.post = jest.fn().mockImplementationOnce(() => Promise.resolve());
        await expect(addIngredientApi("test")).resolves.toBeUndefined();
    });

    it("should throw error while adding ingredients", async () => {
        const errorMessage = new Error("rejected");
        axios.post = jest.fn().mockImplementationOnce(() => Promise.reject(errorMessage));
        await expect(addIngredientApi("test")).rejects.toThrow(errorMessage);
    });

    it("delete ingredient", async () => {
        axios.delete = jest.fn().mockImplementationOnce(() => Promise.resolve());
        await expect(deleteIngredientApi(0)).resolves.toBeUndefined();
    });

    it("should throw error while deleting ingredients", async () => {
        const errorMessage = new Error("rejected");
        axios.delete = jest.fn().mockImplementationOnce(() => Promise.reject(errorMessage));
        await expect(deleteIngredientApi(0)).rejects.toThrow(errorMessage);
    });

    it("delete recipe", async () => {
        axios.delete = jest.fn().mockImplementationOnce(() => Promise.resolve());
        await expect(deleteRecipeApi(0)).resolves.toBeUndefined();
    });

    it("should throw error while deleting recipe", async () => {
        const errorMessage = new Error("rejected");
        axios.delete = jest.fn().mockImplementationOnce(() => Promise.reject(errorMessage));
        await expect(deleteRecipeApi(0)).rejects.toThrow(errorMessage);
    });
});