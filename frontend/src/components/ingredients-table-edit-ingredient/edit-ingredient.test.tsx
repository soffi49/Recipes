import React from "react";
import {render, screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event"
import EditIngredient from "./edit-ingredient.component";

describe("AddIngredient", () => {
    it("should render edit ingredient button", () => {
        render(
            <EditIngredient id={0} oldName="Banana" editIngredient={() => {}} />
        )
        expect(screen.getByLabelText("Edit button")).toBeTruthy();
    })

    it("should render Save button", () => {
        render(
            <EditIngredient id={0} oldName="Banana" editIngredient={() => {}} />
        )
        userEvent.click(screen.getByLabelText("Edit button"));
        expect(screen.getByLabelText("Save Button")).toBeTruthy();
    })

    it("should render name input", () => {
        render(
            <EditIngredient id={0} oldName="Banana" editIngredient={() => {}} />
        )
        userEvent.click(screen.getByLabelText("Edit button"));
        expect(screen.getByLabelText("Name input")).toBeTruthy();
    })

    it("should call addIngredient function", () => {
        const editIngredient = jest.fn();
        render(
            <EditIngredient id={0} oldName="Banana" editIngredient={editIngredient} />
        )
        userEvent.click(screen.getByLabelText("Edit button"));
        userEvent.click(screen.getByLabelText("Save Button"));
        expect(editIngredient).toHaveBeenCalled();
    })
})