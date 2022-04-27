import axios from "axios";
import {
  getIngredientsApi,
  addIngredientApi,
  deleteIngredientApi,
  deleteRecipeApi,
  editIngredientApi,
  editRecipeApi,
  registerApi,
} from "./api.api";
import data from "../constants/ingredient-table.data.json";

describe("Api tests", () => {
  jest.mock("axios");

  it("should get ingredients", async () => {
    const payload = { data: data };
    axios.post = jest
      .fn()
      .mockImplementationOnce(() => Promise.resolve(payload));
    await expect(getIngredientsApi(0, 100)).resolves.toEqual(data);
  });

  it("should thorw error while getting ingredients", async () => {
    const errorMessage = new Error("rejected");
    axios.post = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(getIngredientsApi(0, 100)).rejects.toThrow(errorMessage);
  });

  it("add ingredient", async () => {
    axios.post = jest.fn().mockImplementationOnce(() => Promise.resolve());
    await expect(addIngredientApi("test")).resolves.toBeUndefined();
  });

  it("should throw error while adding ingredients", async () => {
    const errorMessage = new Error("rejected");
    axios.post = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(addIngredientApi("test")).rejects.toThrow(errorMessage);
  });

  it("delete ingredient", async () => {
    axios.delete = jest.fn().mockImplementationOnce(() => Promise.resolve());
    await expect(deleteIngredientApi(0)).resolves.toBeUndefined();
  });

  it("should throw error while deleting ingredients", async () => {
    const errorMessage = new Error("rejected");
    axios.delete = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(deleteIngredientApi(0)).rejects.toThrow(errorMessage);
  });

  it("delete recipe", async () => {
    axios.delete = jest.fn().mockImplementationOnce(() => Promise.resolve());
    await expect(deleteRecipeApi(0)).resolves.toBeUndefined();
  });

  it("should throw error while deleting recipe", async () => {
    const errorMessage = new Error("rejected");
    axios.delete = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(deleteRecipeApi(0)).rejects.toThrow();
  });

  it("edit ingredient", async () => {
    axios.put = jest.fn().mockImplementationOnce(() => Promise.resolve());
    await expect(editIngredientApi("piwo", 0)).resolves.toBeUndefined();
  });

  it("should throw error while editing ingredient", async () => {
    const errorMessage = new Error("rejected");
    axios.put = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(editIngredientApi("piwo", 0)).rejects.toThrow(errorMessage);
  });

  it("should throw error while editing recipe", async () => {
    const errorMessage = new Error("rejected");
    axios.put = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(
      editRecipeApi(
        {
          id: 0,
          name: "super jedzenie",
          instructions: "fajny nowy opis",
          ingredients: [
            {
              ingredient: {
                id: 2,
                name: "piwo2",
              },
              quantity: 1,
            },
          ],
          tags: ["pyszne", "student", "biedny"],
        },
        0
      )
    ).rejects.toThrow(errorMessage);
  });

  it("register", async () => {
    axios.post = jest.fn().mockImplementationOnce(() => Promise.resolve());
    await expect(registerApi("piwo", "piwo")).resolves.toBeUndefined();
  });

  it("should throw error while registering", async () => {
    const errorMessage = new Error("rejected");
    axios.post = jest
      .fn()
      .mockImplementationOnce(() => Promise.reject(errorMessage));
    await expect(registerApi("piwo", "Piwo")).rejects.toThrow(errorMessage);
  });
});
