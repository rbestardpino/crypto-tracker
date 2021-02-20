package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ExchangeRateCommand extends Command {

    private static ExchangeRateCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("WIP");
        return message;
    }

    private ExchangeRateCommand() {
        name = "exchangerate";
        description = "WIP";
    }

    public static ExchangeRateCommand getInstance() {
        if (instance == null)
            instance = new ExchangeRateCommand();
        return instance;
    }
}