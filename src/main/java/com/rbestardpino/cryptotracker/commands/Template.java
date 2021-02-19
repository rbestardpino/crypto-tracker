package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Template extends Command {

    private static Template instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("text");
        return message;
    }

    private Template() {
        name = "name";
        description = "description";
    }

    public static Template getInstance() {
        if (instance == null)
            instance = new Template();
        return instance;
    }
}