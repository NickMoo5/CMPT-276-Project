package sfu.cmpt276.project.chatGptApi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Configuration
public class OpenAiConfig {
    //@Value("${openai.api.key}")
    //private String openaiApiKey;
    private static final String OPENAI_API_ENV_KEY = "OPENAIKEY";
    private Map<String, String> envVars = System.getenv();
    //private String openaiApiKey = envVars.get(OPENAI_API_ENV_KEY);
    private String openaiApiKey = "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";

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
