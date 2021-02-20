package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.CryptoTrackerBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand extends Command {

    private static StartCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode("markdown");

        StringBuilder string = new StringBuilder();
        string.append("*Welcome to Crypto Tracker!*\n")
                .append("Here you have a list with _all_ available commands and their descriptions:\n\n");

        for (Command cmd : CryptoTrackerBot.commandsMap.values()) {
            string.append("/" + cmd.name + ": _" + cmd.description + "_\n");
        }

        message.setText(string.toString());

        return message;
    }

    private StartCommand() {
        name = "start";
        description = "Welcome the user and list all available commands";
    }

    public static StartCommand getInstance() {
        if (instance == null)
            instance = new StartCommand();
        return instance;
    }
}
