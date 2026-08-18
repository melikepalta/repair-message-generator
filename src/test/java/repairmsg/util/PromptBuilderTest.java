package repairmsg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import repairmsg.model.MessageRequest;

class PromptBuilderTest {

    private final PromptBuilder promptBuilder = new PromptBuilder();

    @Test
    void buildPrompt_usesDefaultsWhenFieldsAreBlank() {
        MessageRequest request = new MessageRequest();
        request.setRepairNotes("Screen cracked");

        String prompt = promptBuilder.buildPrompt(request);

        assertTrue(prompt.contains("Device Type: device"));
        assertTrue(prompt.contains("Repair Notes: Screen cracked"));
        assertTrue(prompt.contains("Message Type: status update"));
        assertTrue(prompt.contains("Tone: friendly and professional"));
        assertTrue(prompt.contains("Write 2-4 sentences."));
    }

    @Test
    void buildPrompt_usesProvidedValues() {
        MessageRequest request = new MessageRequest();
        request.setDeviceType("iPhone 14");
        request.setRepairNotes("Battery replacement completed");
        request.setMessageType("appointment confirmation");
        request.setTone("warm and concise");

        String prompt = promptBuilder.buildPrompt(request);

        assertTrue(prompt.contains("Device Type: iPhone 14"));
        assertTrue(prompt.contains("Repair Notes: Battery replacement completed"));
        assertTrue(prompt.contains("Message Type: appointment confirmation"));
        assertTrue(prompt.contains("Tone: warm and concise"));
    }

    @Test
    void buildPrompt_includesExpectedSectionsAndFormatting() {
        MessageRequest request = new MessageRequest();
        request.setRepairNotes("Back glass replaced and testing passed.");

        String prompt = promptBuilder.buildPrompt(request);

        assertTrue(prompt.startsWith("You are an assistant for a phone repair shop."));
        assertTrue(prompt.contains("Device Type: device"));
        assertTrue(prompt.contains("Repair Notes: Back glass replaced and testing passed."));
        assertTrue(prompt.contains("Message Type: status update"));
        assertTrue(prompt.contains("Tone: friendly and professional"));
        assertTrue(prompt.contains("Rules:"));
        assertTrue(prompt.contains("- Write 2-4 sentences."));
        assertTrue(prompt.contains("- Do not invent details that are not in the repair notes."));
        assertTrue(prompt.contains("- Do not include a subject line or signature."));
        assertTrue(prompt.contains("- Write only the message text, nothing else."));
    }
}
