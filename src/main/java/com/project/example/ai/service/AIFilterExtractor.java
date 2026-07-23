package com.project.example.ai.service;

import com.project.example.ai.dto.ProductFilter;
import com.project.example.ai.util.JsonUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIFilterExtractor {

    private final ChatClient chatClient;
    private final JsonUtil jsonUtil;
    private final CategoryAIService categoryService;

    public AIFilterExtractor(ChatClient chatClient,
                             JsonUtil jsonUtil, CategoryAIService categoryService) {

        this.chatClient = chatClient;
        this.jsonUtil = jsonUtil;
        this.categoryService = categoryService;
    }

    public ProductFilter extract(String question) {

        List<String> categories = categoryService.getAllCategories();

        String prompt = """
                You are an AI Shopping Assistant.
                
                Your task is to extract shopping filters from the customer's question.
                
                IMPORTANT RULES
                
                1. Return ONLY valid JSON.
                2. Do NOT return markdown.
                3. Do NOT explain anything.
                4. The category MUST be selected ONLY from the Available Categories list.
                5. If no category matches, return null for category.
                6. If brand is not mentioned, return an empty string.
                7. If price is not mentioned, return 0.
                8. If keywords are not mentioned, return an empty string.
                
                Available Categories:
                
                %s
                
                Return JSON in this format:
                
                {
                  "category":"",
                  "brand":"",
                  "maxPrice":0,
                  "keywords":""
                }
                
                Examples
                
                Question:
                Recommend Dell laptops under 50000
                
                Response:
                {
                  "category":"laptops",
                  "brand":"Dell",
                  "maxPrice":50000,
                  "keywords":""
                }
                
                Question:
                Best gaming laptop
                
                Response:
                {
                  "category":"laptops",
                  "brand":"",
                  "maxPrice":0,
                  "keywords":"gaming"
                }
                
                Question:
                Show phones
                
                Response:
                {
                  "category":"mobile",
                  "brand":"",
                  "maxPrice":0,
                  "keywords":""
                }
                
                Question:
                Suggest televisions
                
                Response:
                {
                  "category":"tv",
                  "brand":"",
                  "maxPrice":0,
                  "keywords":""
                }
                
                Customer Question:
                
                %s
                """.formatted(
                String.join(", ", categories),
                question
        );

        String json = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        System.out.println("AI JSON:");
        System.out.println(json);

        return jsonUtil.fromJson(json, ProductFilter.class);
    }
}