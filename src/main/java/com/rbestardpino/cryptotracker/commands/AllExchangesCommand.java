package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AllExchangesCommand extends Command {

    private static AllExchangesCommand instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        StringBuilder string = new StringBuilder();
        string.append("WIP");

        return SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build();
    }

    private AllExchangesCommand() {
        name = "allexchanges";
        description = "Provide info about all exchanges";
    }

    public static AllExchangesCommand getInstance() {
        if (instance == null)
            instance = new AllExchangesCommand();
        return instance;
    }
}