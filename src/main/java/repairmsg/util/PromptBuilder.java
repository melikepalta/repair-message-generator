package repairmsg.util;

import repairmsg.model.MessageRequest;
 
public class PromptBuilder {

    public String buildPrompt(MessageRequest request) {

        String deviceType = defaultIfBlank(request.getDeviceType(), "device");
        String messageType = defaultIfBlank(request.getMessageType(), "status update");
        String tone = defaultIfBlank(request.getTone(), "friendly and professional");
    
        return """
                You are an assistant for a phone repair shop.
                Write a short message to a customer based on the repair information below.

                Device Type: %s
                Repair Notes: %s
                Message Type: %s
                Tone: %s

                Rules:
                - Write 2-4 sentences.
                - Do not invent details that are not in the repair notes.
                - Do not include a subject line or signature.
                - Write only the message text, nothing else.
                
                """.formatted(deviceType, request.getRepairNotes(), messageType, tone);
    }

    private String defaultIfBlank(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        } else {
            return value;
        }
    }
}