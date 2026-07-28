import api from "../api/axios";

export const getAllKnowledge = async () => {
  const response = await api.get("/api/knowledge");
  return response.data;
};