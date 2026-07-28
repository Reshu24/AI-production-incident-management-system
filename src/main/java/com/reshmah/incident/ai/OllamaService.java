package com.reshmah.incident.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
public class OllamaService {

    private final ObjectMapper objectMapper;

    public OllamaIncidentDTO analyseLogs(String logs) throws Exception {

        String prompt = """
                
                You are an incident analysis engine.
                
                  Return ONLY valid JSON.
                
                  Do not explain.
                  Do not provide notes.
                  Do not provide recommendations.
                  Do not use markdown.
                
                  Output format:
                
                  {
                  "title":"",
                  "description":"",
                  "severity":"LOW|MEDIUM|HIGH|CRITICAL",
                  "affectedService":"",
                  "category":"",
                  "rootCause":"",
                  "aiSummary":""
                  }
                
                Logs:
                
                """ + logs;

        RestTemplate restTemplate = new RestTemplate();

        OllamaRequest request =
                new OllamaRequest(
                        "llama3.2:3b",
                        prompt,
                        false,
                        "json"
                );

        OllamaResponse response =
                restTemplate.postForObject(
                        "http://localhost:11434/api/generate",
                        request,
                        OllamaResponse.class
                );
        System.out.println("=======================");
        assert response != null;
        System.out.println(response.getResponse());
        System.out.println("=======================");

        return objectMapper.readValue(
                response.getResponse(),
                OllamaIncidentDTO.class
        );

    }

}
