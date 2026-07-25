package com.project.example.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String ask(String prompt, String conversationId) {

        return chatClient.prompt()
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .user(prompt)
                .call()
                .content();

    }
}