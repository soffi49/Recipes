import React, {ChangeEventHandler, useState} from "react";
import styled from "@emotion/styled";
import {Box, Button, TextField, Typography} from "@mui/material";
import { LoginInformation } from "../models/models";
import {useNavigate} from "react-router-dom";
import { registerApi } from "../api/api.api";
const RegistrationPage = () => {

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
                <StyledTypography id={'registration-page-label'}>
                    Registration
                </StyledTypography>
                <div>
                    <StyledTextField id={'register-username-field'} label="Username" style={{ marginBottom: '20px' }} onChange={handleChangeLogin}/>
                </div>
                <div>
                    <StyledTextField id={'register-password-field'} label="Password" type="password" style={{ marginBottom: '20px' }} onChange={handleChangePassword}/>
                </div>
                <div>
                    <Button id={'cancel-registration-button'} variant="contained" onClick={async () => {
                        navigate('/not-authorized')
                    }}>Cancel</Button>
                    <StyledButton id={'register-button'} variant="contained" onClick={async () => {
                        const msgBuffer = new TextEncoder().encode(loginInfo.password); 
                        const passwordBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
                        const hashArray = Array.from(new Uint8Array(passwordBuffer));         
                        const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
                        await registerApi(loginInfo.login, hashHex).then((response) => { console.log(response); navigate('/not-authorized');});
                    }}>Register</StyledButton>
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

export default RegistrationPage;