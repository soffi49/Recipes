import {fireEvent, render, screen} from "@testing-library/react";
import IngredientsTable from "./ingredients-table.component";
import data from "../../constants/ingredient-table.data.json";
import { IngredientDetails } from "../../models/models";
import userEvent from "@testing-library/user-event";
import * as moduleApi from "../../api/api.api";

describe("AddIngredient", () => {
    it("should Check ammount of headers", () => {
        render(
            <IngredientsTable 
                ingredients={[]}
                page={0}
                limit={10}
                count={0}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        expect(screen.getAllByRole("columnheader").length).toEqual(3);
    })

    it("should Check ammount of rows", () => {
        render(
            <IngredientsTable 
                ingredients={data.ingredients as IngredientDetails[]} 
                page={0} 
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        expect(screen.getAllByRole("row").length).toEqual(2);
    })

    it("should render delete button", () => {
        render(
            <IngredientsTable 
                ingredients={data.ingredients as IngredientDetails[]}
                page={0}
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        expect(screen.getByLabelText("Delete button")).toBeTruthy();
    })

    it("should render edit button", () => {
        render(
            <IngredientsTable
                ingredients={data.ingredients as IngredientDetails[]}
                page={0}
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        expect(screen.getByLabelText("Edit button")).toBeTruthy();
    })

    it("should render next page button", () => {
        render(
            <IngredientsTable
                ingredients={data.ingredients as IngredientDetails[]}
                page={0}
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        expect(screen.getByLabelText("Go to next page")).toBeTruthy();
    })

    it("should render previous page button", () => {
        render(
            <IngredientsTable
                ingredients={data.ingredients as IngredientDetails[]}
                page={0}
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        expect(screen.getByLabelText("Go to previous page")).toBeTruthy();
    })
    
    it("should call delete api function", () => {
        jest.spyOn(moduleApi ,"deleteIngredientApi");
        render(
            <IngredientsTable
                ingredients={data.ingredients as IngredientDetails[]}
                page={0}
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                getAllIngredients={() => {}}
            />
        )
        fireEvent.click(screen.getByLabelText("Delete button"));
        expect(moduleApi.deleteIngredientApi).toHaveBeenCalled();
    })
})