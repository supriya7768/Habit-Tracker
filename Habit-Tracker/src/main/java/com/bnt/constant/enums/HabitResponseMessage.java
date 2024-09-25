package com.bnt.constant.enums;

public enum HabitResponseMessage {

    FAILED_TO_SAVE_HABIT("Failed to save habit"),
    HABIT_NOT_FOUND("Habit with id %s not found"),
    SUCCESSFULLY_UPDATED("Habit with id %s is successfully updated"),
    SUCCESSFULLY_DELETED("Habit with id %s is successfully deleted");

    private final String messageFormat;

    HabitResponseMessage(String messageFormat){
        this.messageFormat = messageFormat;
    }

    public String getMessage() {
        return messageFormat;
    }

    public String getMessage(int userId) {
        return String.format(messageFormat, userId);
    }
    
}
