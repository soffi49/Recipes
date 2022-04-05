import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import RequireAuth from './auth/RequireAuth';
import LoginPage from './pages/loginPage';
ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
            <Routes>
                <Route path="/*" element={
                    <RequireAuth>
                        <App/>
                    </RequireAuth>
                    }
                />
                <Route path="/not-authorized" element={<LoginPage/>}/>
            </Routes>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);
