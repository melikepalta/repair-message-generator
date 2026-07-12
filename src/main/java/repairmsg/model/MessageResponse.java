package repairmsg.model;

public class MessageResponse {
    
    private boolean success;
    private String generatedMessage;
    private String error;

    public MessageResponse() {
    }

    public MessageResponse(boolean success, String generatedMessage, String error) {
        this.success = success;
        this.generatedMessage = generatedMessage;
        this.error = error;
    }

    public static MessageResponse ok(String generatedMessage) {
        return new MessageResponse(true, generatedMessage, null);
    }

    public static MessageResponse fail(String error) {
        return new MessageResponse(false, null, error);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGeneratedMessage() {
        return generatedMessage;
    }

    public void setGeneratedMessage(String generatedMessage) {
        this.generatedMessage = generatedMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
