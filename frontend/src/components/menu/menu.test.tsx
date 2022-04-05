import {render, screen} from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import Menu from "./menu.component";

describe("Menu", () => {
    it("should render ingredients button", () => {
        render(
            <BrowserRouter>
              <Menu />
            </BrowserRouter>
        )
        expect(screen.getByLabelText("Ingredients")).toBeTruthy();
    })

    it("should render recipes button", () => {
        render(
            <BrowserRouter>
              <Menu />
            </BrowserRouter>
        )
        expect(screen.getByLabelText("Recipes")).toBeTruthy();
    })

    it("should render logout button", () => {
        render(
            <BrowserRouter>
              <Menu />
            </BrowserRouter>
        )
        expect(screen.getByLabelText("Logout")).toBeTruthy();
    })
})