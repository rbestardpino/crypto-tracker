package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SettingsCommand extends Command {

    private static SettingsCommand instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        StringBuilder string = new StringBuilder();
        string.append("WIP");

        return SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build();
    }

    private SettingsCommand() {
        name = "settings";
        description = "Allow to view and change current chat settings";
    }

    public static SettingsCommand getInstance() {
        if (instance == null)
            instance = new SettingsCommand();
        return instance;
    }
}