import {LoginInformation} from "../models/models";
import axios from "axios";
import {server} from "../constants/constants";

interface AuthService {
    login: (loginInfo: LoginInformation) => void;
    logout: () => void;
    getCurrentUserToken: () => void;
}

function createAuthService(): AuthService {

    let data = '';

    const login = async (loginInfo: LoginInformation) => {
        await axios.post(`${server}/login`, {
            login: loginInfo.login,
            password: loginInfo.password
        }).then(response => {
            if (response.data) {
                data = response.data.token;
                sessionStorage.setItem('key', response.data.token);
                console.log(response.data.token);
            }
        });
        return data;
    }
    const logout = () => {
        sessionStorage.removeItem('key');
    }
    const getCurrentUserToken = () => {
    }
    return {
        login,
        logout,
        getCurrentUserToken
    }
}

const AuthService = createAuthService();

export default AuthService;
