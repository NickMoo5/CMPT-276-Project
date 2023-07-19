package sfu.cmpt276.project.chatGptApi;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import sfu.cmpt276.project.chatGptApi.dto.ChatRequest;
import sfu.cmpt276.project.chatGptApi.dto.ChatResponse;

@Service
public class ChatController {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-3.5-turbo";
    private static final int DELAY = 6000;              // delay time
    public static final String ERROR = "ERROR: Failed to query ChatGPT";

    public String queryChatGPT(String prompt, String key) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(key);

        ChatRequest request = new ChatRequest(MODEL, prompt);
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<ChatResponse> responseEntity = restTemplate.exchange(API_ENDPOINT, HttpMethod.POST, requestEntity, ChatResponse.class);
        
        Thread.sleep(DELAY);            // delay to limit user from querying chatgpt to quickly (due to our 3 requests per min limit)

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ChatResponse responseBody = responseEntity.getBody();
            if (responseBody != null && responseBody.getChoices().size() > 0) {
                return responseBody.getChoices().get(0).getMessage().getContent();
            }
        }


        return ERROR;
    }
}