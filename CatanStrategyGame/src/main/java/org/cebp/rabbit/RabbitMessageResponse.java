package org.cebp.rabbit;

public class RabbitMessageResponse {
    private String uuid;
    private String actionName;
    private boolean success;
    private String message;

    public RabbitMessageResponse() {}

    private RabbitMessageResponse(String uuid, String actionName, boolean success, String message) {
        this.uuid = uuid;
        this.actionName = actionName;
        this.success = success;
        this.message = message;
    }

    public static RabbitMessageResponse fromRabbitMessage(RabbitMessage message, boolean success, String textMessage) {
        return new RabbitMessageResponse(message.getUuid(), message.getActionName(), success, textMessage);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RabbitMessageResponse{" +
                "uuid='" + uuid + '\'' +
                ", actionName='" + actionName + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
