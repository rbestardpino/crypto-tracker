package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TemplateCommand extends Command {

    private static TemplateCommand instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        StringBuilder string = new StringBuilder();
        string.append("WIP");

        return SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build();
    }

    private TemplateCommand() {
        name = "name";
        description = "description";
    }

    public static TemplateCommand getInstance() {
        if (instance == null)
            instance = new TemplateCommand();
        return instance;
    }
}