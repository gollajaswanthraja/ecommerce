package com.project.example.ai.controller;

import com.project.example.ai.dto.ChatRequest;
import com.project.example.ai.dto.ChatResponse;
import com.project.example.ai.service.AIShoppingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    private final AIShoppingService shoppingService;

    public AIController(AIShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String answer = shoppingService.searchProducts(request.question());

        return new ChatResponse(answer);
    }
}