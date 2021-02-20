package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.CryptoTrackerBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandNotFound extends Command {

    private static CommandNotFound instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        StringBuilder string = new StringBuilder();
        string.append("Unknown command\n")
                .append("Here you have a list with _all_ available commands and their descriptions:\n\n");

        for (Command cmd : CryptoTrackerBot.commandsMap.values()) {
            string.append("/" + cmd.name + ": _" + cmd.description + "_\n");
        }
        message.setText(string.toString());
        return message;
    }

    private CommandNotFound() {
        name = "commandnotfound";
        description = "List all available commands";
    }

    public static CommandNotFound getInstance() {
        if (instance == null)
            instance = new CommandNotFound();
        return instance;
    }
}
