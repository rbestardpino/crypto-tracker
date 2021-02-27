package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TemplateCommand extends Command {

    private static TemplateCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("WIP");
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