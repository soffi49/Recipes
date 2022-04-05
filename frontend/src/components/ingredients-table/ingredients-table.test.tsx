import { render, screen} from "@testing-library/react";
import IngredientsTable from "./ingredients-table.component";
import data from "../../constants/ingredient-table.data.json";
import { IngredientDetails } from "../../models/models";
import userEvent from "@testing-library/user-event";

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
                deleteIngredient={() => {}}
                editIngredient={() => {}}
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
                deleteIngredient={() => {}}
                editIngredient={() => {}}
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
                deleteIngredient={() => {}}
                editIngredient={() => {}}
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
                deleteIngredient={() => {}}
                editIngredient={() => {}}
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
                deleteIngredient={() => {}}
                editIngredient={() => {}}
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
                deleteIngredient={() => {}}
                editIngredient={() => {}}
            />
        )
        expect(screen.getByLabelText("Go to previous page")).toBeTruthy();
    })
    
    it("should call delete api function", () => {
        const deleteIngredient = jest.fn();
        render(
            <IngredientsTable
                ingredients={data.ingredients as IngredientDetails[]}
                page={0}
                limit={10}
                count={data.ingredients.length}
                handleChangeRowsPerPage={() => {}}
                handleChangePage={() => {}}
                deleteIngredient={deleteIngredient}
                editIngredient={() => {}}
            />
        )
        userEvent.click(screen.getByLabelText("Delete button"));
        expect(deleteIngredient).toHaveBeenCalled();
    })
})