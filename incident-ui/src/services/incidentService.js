import api from "../api/axios";

export const getAllIncidents = async () => {
  const response = await api.get("/api/incidents");
  return response.data;
};

export const getIncidentById = async (id) => {
  const response = await api.get(`/api/incidents/${id}`);
  return response.data;
};

export const triageIncident = async (incidentRequest) => {
  const response = await api.post(
    "/api/incidents/triage",
    incidentRequest
  );
  return response.data;
};

export const matchIncident = async (description) => {
  const response = await api.get("/api/incidents/match", {
    params: {
      description,
    },
  });

  return response.data;
};