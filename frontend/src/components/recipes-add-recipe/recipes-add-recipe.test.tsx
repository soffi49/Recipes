import React from "react";
import {render, screen} from "@testing-library/react";
import AddRecipeModal from "./recipes-add-recipe.component";

describe("AddRecipe", () => {
    it("should render add recipe button", () => {
        render(
            <AddRecipeModal 
                visible={false}
                onCancel={() => {}}
                handleOpen={() => {}}
                addRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Add Recipe Button")).toBeTruthy();
    })

    it("should render edit button", () => {
        render(
            <AddRecipeModal 
                visible={true}
                onCancel={() => {}}
                handleOpen={() => {}}
                addRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Edit ingredients button")).toBeTruthy();
    })

    it("should render accept button", () => {
        render(
            <AddRecipeModal 
                visible={true}
                onCancel={() => {}}
                handleOpen={() => {}}
                addRecipe={() => {}}
            />
        )
        expect(screen.getByLabelText("Accept button")).toBeTruthy();
    })
})