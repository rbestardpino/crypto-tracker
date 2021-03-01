package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TemplateCommand extends Command {

    private static TemplateCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getChatId());
        message.setParseMode("markdown");

        StringBuilder string = new StringBuilder();
        string.append("WIP");

        message.setText(string.toString());
        return message;
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