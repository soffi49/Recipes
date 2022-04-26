import {fireEvent, render, screen} from "@testing-library/react";
import data from "../../constants/recipe-table.data.json";
import { RecipeDetails } from "../../models/models";
import RecipesTable from "./recipes-table";

describe("AddIngredient", () => {
    it("should Check ammount of headers", () => {
        render(
            <RecipesTable 
                recipes={[]}
                page={0}
                limit={10}
                count={0}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getAllByRole("columnheader").length).toEqual(3);
    })

    it("should Check ammount of rows", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getAllByRole("row").length).toEqual(2);
    })

    it("should render delete button", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Delete button")).toBeTruthy();
    })

    it("should render edit button", () => {
        render(
            <RecipesTable 
                recipes={data as unknown as RecipeDetails[]} 
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Edit button")).toBeTruthy();
    })
    it("should render details button", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Details button")).toBeTruthy();
    })
    it("should render a modal with details", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        const button =screen.getByLabelText("Details button");
        fireEvent.click(button);
        expect(screen.getByText("piwo")).toBeTruthy();
    })
    it("should render a modal with editing", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        const button =screen.getByLabelText("Edit button");
        fireEvent.click(button);
        expect(screen.getByText("Instructions:")).toBeTruthy();
    })
   
    

    it("should render next page button", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Go to next page")).toBeTruthy();
    })

    it("should render previous page button", () => {
        render(
            <RecipesTable
                recipes={data as unknown as RecipeDetails[]}
                page={0}
                limit={10}
                count={data.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteRecipe={() => {}}
                editRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Go to previous page")).toBeTruthy();
    })
})