package com.bnt.constant.enums;

public enum TargetResponseMessage {

    FAILED_TO_SAVE_TARGET("Failed to save target"),
    TARGET_NOT_FOUND("Target with id %s not found"),
    SUCCESSFULLY_UPDATED("Target with id %s is successfully updated"),
    SUCCESSFULLY_DELETED("Target with id %s is successfully deleted");

    private final String messageFormat;

    TargetResponseMessage(String messageFormat){
        this.messageFormat = messageFormat;
    }

    public String getMessage() {
        return messageFormat;
    }

    public String getMessage(int userId) {
        return String.format(messageFormat, userId);
    }

    
}
