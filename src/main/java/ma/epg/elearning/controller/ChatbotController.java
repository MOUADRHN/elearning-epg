package ma.epg.elearning.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Controller
@RequestMapping("/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final RestTemplate restTemplate;

    // Spring va automatiquement injecter la valeur depuis application.properties
    @Value("${groq.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL   = "llama-3.1-8b-instant";

    @PostMapping("/message")
    @ResponseBody
    public Map<String, String> message(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");

        // On force la clé ici juste pour ce test
        String testApiKey = "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";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(testApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.1-8b-instant");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "Tu es un assistant de test."),
                Map.of("role", "user", "content", userMessage != null ? userMessage : "Bonjour")
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://api.groq.com/openai/v1/chat/completions",
                    entity,
                    Map.class
            );
            List<Map> choices = (List<Map>) response.getBody().get("choices");
            Map msg = (Map) choices.get(0).get("message");
            return Map.of("reply", (String) msg.get("content"));

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            // S'il y a un refus de Groq (ex: 401 Unauthorized), ça s'affichera dans le chat !
            return Map.of("reply", "🚨 Erreur Groq (" + e.getStatusCode() + ") : " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // S'il y a une erreur Java (ex: RestTemplate manquant, problème de JSON)
            return Map.of("reply", "🚨 Erreur Interne Java : " + e.toString());
        }
    }
}