package com.bnt.constant.enums;

public enum UserResponseMessage {
    
    FAILED_TO_SAVE_ACCOUNT("Failed to save account"),
    ACCOUNT_NOT_FOUND("User with id %s not found"),
    SUCCESSFULLY_UPDATED("User with id %s is successfully updated"),
    SUCCESSFULLY_DELETED("User with id %s is successfully deleted");

    private final String messageFormat;

    UserResponseMessage(String messageFormat){
        this.messageFormat = messageFormat;
    }

    public String getMessage() {
        return messageFormat;
    }

    public String getMessage(int userId) {
        return String.format(messageFormat, userId);
    }
}
