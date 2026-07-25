package com.project.example.ai.service;


import com.project.example.ai.dto.ProductFilter;
import com.project.example.ai.prompt.PromptBuilder;
import com.project.example.model.Product;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIShoppingServiceImpl implements AIShoppingService {

    private final ChatService chatService;
    private final AIFilterExtractor filterExtractor;
    private final ConversationService conversationService;
    private final CategoryResolver categoryResolver;
    private final ProductSearchService productSearchService;
    private final PromptBuilder promptBuilder;

    public AIShoppingServiceImpl(ChatService chatService,
                                 AIFilterExtractor filterExtractor, ConversationService conversationService, CategoryResolver categoryResolver, ProductSearchService productSearchService, PromptBuilder promptBuilder) {
        this.chatService = chatService;
        this.filterExtractor = filterExtractor;
        this.conversationService = conversationService;
        this.categoryResolver = categoryResolver;
        this.productSearchService = productSearchService;
        this.promptBuilder = promptBuilder;
    }

    @Override
    public String searchProducts(String question) {

        ProductFilter filter = filterExtractor.extract(question);

        List<Product> products =
                productSearchService.search(filter);



        if (products.isEmpty()) {

            return "No matching products found.";

        }

        String prompt = promptBuilder.buildShoppingPrompt(
                question,
                products
        );

        return chatService.ask(
                prompt,
                conversationService.getConversationId()
        );
    }
}