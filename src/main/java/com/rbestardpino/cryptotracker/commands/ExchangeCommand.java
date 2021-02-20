package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ExchangeCommand extends Command {

    private static ExchangeCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
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