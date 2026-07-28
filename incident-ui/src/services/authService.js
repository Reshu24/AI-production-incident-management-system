import api from "../api/axios";

export const login = async(username, password) => {
    try{
        const response = await api.post("/auth/login", { 
            userName: username,
            password: password });
        return response.data;
    } catch (error) {
        console.error("Error logging in:", error);
        throw error;
    }
}