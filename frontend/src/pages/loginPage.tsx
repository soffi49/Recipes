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
                    You are not authorized. Please login before proceeding.
                </StyledTypography>
                <StyledTextField label="Username" style={{ marginBottom: '20px' }} onChange={handleChangeLogin}/>
                <FormContainer>
                    <StyledTextField label="Password" type="password" onChange={handleChangePassword}/>
                    <StyledButton variant="outlined" onClick={async () => {
                        await AuthService.login(loginInfo);
                        navigate('/');
                    }}>Login</StyledButton>
                </FormContainer>
            </StyledBox>
        </>
    )
}

const StyledBox = styled(Box)`
    width: 100%;
    max-width: 500px;
    margin: 50px;
`;

const StyledTypography = styled(Typography)`
  margin-bottom: 25px;
`

const StyledTextField = styled(TextField)`
    font-size: 12px;
    min-width: 300px;
    margin-right: 30px;
`

const StyledButton = styled(Button)`
    min-width: 100px;
`

const FormContainer = styled.div`
    display: flex;
`;

export default LoginPage;