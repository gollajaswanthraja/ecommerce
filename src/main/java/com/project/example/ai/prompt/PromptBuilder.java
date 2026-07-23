package com.project.example.ai.prompt;

import com.project.example.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    public String buildShoppingPrompt(String question,
                                      List<Product> products) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an AI Shopping Assistant.
                
                                                     Recommend ONLY from the products listed below.
                
                                                     Rules:
                                                     - Do NOT invent products.
                                                     - Recommend all relevant products.
                                                     - Keep the response concise.
                                                     - Use simple, natural English.
                                                     - Do NOT use markdown symbols like *, #, or **.
                                                     - Do NOT use bullet points.
                                                     - Mention the product name, price, and a short reason.
                                                     - Separate multiple recommendations with a blank line.
                
                                                     Example format:
                
                                                     IQOO Mobile (₹20,000)
                                                     A good gaming phone with excellent performance for this budget.
                
                                                     Samsung M35 (₹19,500)
                                                     A balanced choice with a good display and battery life.
                
                                                     Available Products:
                

                """);

        for (Product product : products) {

            prompt.append("""
                    
                    Product Name : %s
                    Category     : %s
                    Price        : %.2f
                    Description  : %s

                    -------------------------
                    
                    """.formatted(
                    product.getProductName(),
                    product.getCategory().getCategoryName(),
                    product.getPrice(),
                    product.getDescription()
            ));
        }

        prompt.append("""

                Customer Question:

                """);

        prompt.append(question);

        return prompt.toString();

    }

}