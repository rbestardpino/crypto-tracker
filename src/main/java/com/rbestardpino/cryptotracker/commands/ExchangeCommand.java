package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ExchangeCommand extends Command {

    private static ExchangeCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getChatId());
        message.setText("WIP");
        return message;
    }

    private ExchangeCommand() {
        name = "exchange";
        description = "Provides info about all or specified exchange/s";
    }

    public static ExchangeCommand getInstance() {
        if (instance == null)
            instance = new ExchangeCommand();
        return instance;
    }
}