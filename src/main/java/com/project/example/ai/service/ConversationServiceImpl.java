package com.project.example.ai.service;

import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl
        implements ConversationService {

    @Override
    public String getConversationId() {

        return "demo-user";

    }

}