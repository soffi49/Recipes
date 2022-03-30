import React, {ChangeEventHandler, useState} from "react";
import styled from "@emotion/styled";
import {Box, Button, TextField, Typography} from "@mui/material";
import { LoginInformation } from "../models/models";
import {useNavigate} from "react-router-dom";
import AuthService from "../servies/AuthService";
const LoginPage = () => {

    let navigate = useNavigate();
    const [loginInfo, setLoginInfo] = useState<LoginInformation>({
        login: '',
        password: ''
    });
    const handleChangeLogin: ChangeEventHandler<HTMLInputElement> = event => {
        setLoginInfo(prev => ({...prev, login: event.target.value}));
    }
    const handleChangePassword: ChangeEventHandler<HTMLInputElement> = event => {
        setLoginInfo(prev => ({...prev, password: event.target.value}));
    }

    return (
        <>
            <StyledBox>
                <StyledTypography>
                    You are not logged in
                </StyledTypography>
                <div>
                    <StyledTextField label="Username" style={{ marginBottom: '20px' }} onChange={handleChangeLogin}/>
                </div>
                <div>
                    <StyledTextField label="Password" type="password" style={{ marginBottom: '20px' }} onChange={handleChangePassword}/>
                </div>
                <div>
                    <StyledButton variant="contained" onClick={async () => {
                        await AuthService.login(loginInfo);
                        navigate('/');
                    }}>Login</StyledButton>
                </div>
            </StyledBox>
        </>
    )
}

const StyledBox = styled(Box)`
    background-color: #fff;
    position: absolute;
    max-width: 300;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 16px;
    border: 1px solid;
    border-color: black;
    border-radius: 4px;
`;

const StyledTypography = styled(Typography)`
  margin-bottom: 25px;
  text-align: center;
`

const StyledTextField = styled(TextField)`
    font-size: 12px;
    min-width: 300px;
`

const StyledButton = styled(Button)`
    min-width: 100px;
    margin-left: 100px;
`

export default LoginPage;