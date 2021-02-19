package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Start extends Command {

    private static Start instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hi!");
        return message;
    }

    private Start() {
        name = "start";
        description = "Start the bot";
    }

    public static Start getInstance() {
        if (instance == null)
            instance = new Start();
        return instance;
    }
}
