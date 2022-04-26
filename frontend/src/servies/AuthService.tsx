import {LoginInformation} from "../models/models";
import axios from "axios";
import {server} from "../constants/constants";
import { toast } from "react-toastify";

interface AuthService {
    login: (loginInfo: LoginInformation) => void;
    logout: () => void;
    getCurrentUserToken: () => void;
}

function createAuthService(): AuthService {

    let data = '';

    const login = async (loginInfo: LoginInformation) => {
        const msgBuffer = new TextEncoder().encode(loginInfo.password); 
        const passwordBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
        const hashArray = Array.from(new Uint8Array(passwordBuffer));         
        const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
        await axios({method: "post", url: `${server}/login`, data: {username: loginInfo.login, password: hashHex}}).then(response => {
            if (response.data) {
                data = response.data.token;
                sessionStorage.setItem('key', response.data.token);
                toast.success("Successfully logged in!")
            }
        }).catch((error) => toast.error("Somethintg went wrong while logging in!"));
        return data;
    }
    const logout = () => {
        sessionStorage.removeItem('key');
        toast.success("Successfully logged out!")
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
