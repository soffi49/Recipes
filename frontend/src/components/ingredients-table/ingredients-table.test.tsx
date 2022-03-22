import {render, screen} from "@testing-library/react";
import IngredientsTable from "./ingredients-table.component";
import data from "../../constants/ingredient-table.data.json";
import { IngredientDetails } from "../../models/models";

describe("AddIngredient", () => {
    it("should Check ammount of headers", () => {
        render(
            <IngredientsTable ingredients={[]} />
        )
        expect(screen.getAllByRole("columnheader").length).toEqual(3);
    })

    it("should Check ammount of rows", () => {
        render(
            <IngredientsTable ingredients={data.ingredients as IngredientDetails[]} />
        )
        expect(screen.getAllByRole("row").length).toEqual(2);
    })

    it("should render delete button", () => {
        render(
            <IngredientsTable ingredients={data.ingredients as IngredientDetails[]} />
        )
        expect(screen.getByLabelText("Delete button")).toBeTruthy();
    })

    it("should render edit button", () => {
        render(
            <IngredientsTable ingredients={data.ingredients as IngredientDetails[]} />
        )
        expect(screen.getByLabelText("Edit button")).toBeTruthy();
    })
})