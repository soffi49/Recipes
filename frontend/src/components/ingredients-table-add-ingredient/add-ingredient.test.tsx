import React from "react";
import {render, screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event"
import AddIngredient from "./add-ingredient.component";

describe("AddIngredient", () => {
    it("should rendet add ingredient button", () => {
        render(
            <AddIngredient addIngredient={() => {}} />
        )
        expect(screen.getByLabelText("Add Ingredient Button")).toBeTruthy();
    })

    it("should rendet add button", () => {
        render(
            <AddIngredient addIngredient={() => {}} />
        )
        userEvent.click(screen.getByLabelText("Add Ingredient Button"));
        expect(screen.getByLabelText("Add Button")).toBeTruthy();
    })

    it("should render name input", () => {
        render(
            <AddIngredient addIngredient={() => {}} />
        )
        userEvent.click(screen.getByLabelText("Add Ingredient Button"));
        expect(screen.getByLabelText("Name input")).toBeTruthy();
    })

    it("should call addIngredient function", () => {
        const addIngredient = jest.fn();
        render(
            <AddIngredient addIngredient={addIngredient} />
        )
        userEvent.click(screen.getByLabelText("Add Ingredient Button"));
        userEvent.click(screen.getByLabelText("Add Button"));
        expect(addIngredient).toHaveBeenCalled();
    })
})