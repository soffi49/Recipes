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
            />
        )
        const button =screen.getByLabelText("Details button");
        fireEvent.click(button);
        expect(screen.getByText("piwo")).toBeTruthy();
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
            />
        )
        expect(screen.getByLabelText("Go to previous page")).toBeTruthy();
    })
})