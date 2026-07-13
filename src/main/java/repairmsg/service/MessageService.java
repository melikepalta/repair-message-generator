package repairmsg.service;

import org.springframework.stereotype.Service;

import repairmsg.client.LlmClient;
import repairmsg.model.MessageRequest;
import repairmsg.model.MessageResponse;
import repairmsg.util.PromptBuilder;

@Service
public class MessageService {
    private final PromptBuilder promptBuilder;
    private final LlmClient llmClient;

    public MessageService(PromptBuilder promptBuilder, LlmClient llmClient) {
        this.promptBuilder = promptBuilder;
        this.llmClient = llmClient;
    }

    public MessageResponse generateMessage(MessageRequest request) {
        if (request.getRepairNotes() == null || request.getRepairNotes().isBlank()) {
            return MessageResponse.fail("Repair notes cannot be empty.");
        }
        try {
            String prompt = promptBuilder.buildPrompt(request);
            String generatedText = llmClient.generateText(prompt);
            return MessageResponse.ok(generatedText.trim());
        } catch (Exception e) {
            return MessageResponse.fail("Failed to generate message. Please try again.");
        }
    }
}
