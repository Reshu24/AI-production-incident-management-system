import api from "../api/axios";

export const analyseLogs = async (request) => {
    const response = await api.post(
        "/api/ai/analyse",
        request
    );

    return response.data;
};