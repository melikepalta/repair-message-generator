package repairmsg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import repairmsg.client.LlmClient;
import repairmsg.model.MessageRequest;
import repairmsg.model.MessageResponse;
import repairmsg.util.PromptBuilder;

class MessageServiceTest {

    @Test
    void generateMessage_returnsFailureWhenRepairNotesAreBlank() {
        PromptBuilder promptBuilder = mock(PromptBuilder.class);
        LlmClient llmClient = mock(LlmClient.class);
        MessageService service = new MessageService(promptBuilder, llmClient);

        MessageRequest request = new MessageRequest();
        request.setRepairNotes("   ");

        MessageResponse response = service.generateMessage(request);

        assertFalse(response.isSuccess());
        assertEquals("Repair notes cannot be empty.", response.getError());
    }

    @Test
    void generateMessage_returnsSuccessWhenLlmReturnsText() {
        PromptBuilder promptBuilder = mock(PromptBuilder.class);
        LlmClient llmClient = mock(LlmClient.class);
        MessageService service = new MessageService(promptBuilder, llmClient);

        MessageRequest request = new MessageRequest();
        request.setRepairNotes("Screen replaced");
        when(promptBuilder.buildPrompt(request)).thenReturn("Prompt text");
        when(llmClient.generateText("Prompt text")).thenReturn("  Thanks for your patience!  ");

        MessageResponse response = service.generateMessage(request);

        assertTrue(response.isSuccess());
        assertEquals("Thanks for your patience!", response.getGeneratedMessage());
        assertEquals(null, response.getError());
    }

    @Test
    void generateMessage_returnsFailureWhenLlmClientThrows() {
        PromptBuilder promptBuilder = mock(PromptBuilder.class);
        LlmClient llmClient = mock(LlmClient.class);
        MessageService service = new MessageService(promptBuilder, llmClient);

        MessageRequest request = new MessageRequest();
        request.setRepairNotes("Back camera replacement");
        when(promptBuilder.buildPrompt(request)).thenReturn("Prompt text");
        when(llmClient.generateText("Prompt text")).thenThrow(new RuntimeException("boom"));

        MessageResponse response = service.generateMessage(request);

        assertFalse(response.isSuccess());
        assertEquals("Failed to generate message. Please try again.", response.getError());
        assertEquals(null, response.getGeneratedMessage());
    }

    @Test
    void generateMessage_acceptsNullMessageFieldsByUsingDefaults() {
        PromptBuilder promptBuilder = mock(PromptBuilder.class);
        LlmClient llmClient = mock(LlmClient.class);
        MessageService service = new MessageService(promptBuilder, llmClient);

        MessageRequest request = new MessageRequest();
        request.setRepairNotes("Charging port fixed");
        when(promptBuilder.buildPrompt(request)).thenReturn("Prompt text");
        when(llmClient.generateText("Prompt text")).thenReturn("We fixed the charging port.");

        MessageResponse response = service.generateMessage(request);

        assertTrue(response.isSuccess());
        assertNotNull(response.getGeneratedMessage());
        assertEquals("We fixed the charging port.", response.getGeneratedMessage());
    }
}
