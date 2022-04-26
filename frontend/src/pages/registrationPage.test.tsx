import {fireEvent, render, screen} from "@testing-library/react";
import React from "react";
import {setupServer} from "msw/node";
import {MockedRequest, rest} from "msw";
import {ResponseComposition} from "msw/lib/types/response";
import LoginPage from "./loginPage";
import {createMemoryHistory} from 'history';
import { BrowserRouter, Router } from "react-router-dom";
import RegistrationPage from "./registrationPage";

 const server = setupServer(
     rest.post('*/register', async (request: MockedRequest, response: ResponseComposition, ctx) => {
         await new Promise(resolve => setTimeout(resolve, 150))
         return response(ctx.json([
             {  
             }
         ]));
     })
 );

beforeAll(() => server.listen());
beforeEach(() => {
    
    
    jest.restoreAllMocks();
    jest.spyOn(window.sessionStorage.__proto__, 'setItem');
    
    window.sessionStorage.clear();
});
afterEach(() => {
    server.resetHandlers();
    jest.clearAllMocks();
    
});
afterAll(() => server.close());

describe('Register page tests', () => {
    test('Login page renders a login text input', () => {
        render(<BrowserRouter><RegistrationPage/></BrowserRouter>);
        expect(screen.getByLabelText("Username")).toBeTruthy();
    }),
    test('Login page renders a password text input', () => {
        render(<BrowserRouter><RegistrationPage/></BrowserRouter>);
        expect(screen.getByLabelText("Password")).toBeTruthy();
    })
});