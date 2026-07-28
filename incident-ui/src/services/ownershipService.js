import api from "../api/axios";

export const getAllOwnershipMaps = async () => {
  const response = await api.get("/api/ownership-map");
  return response.data;
};