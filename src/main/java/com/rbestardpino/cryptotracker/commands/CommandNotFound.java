package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CommandNotFound extends Command {

    private static CommandNotFound instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        StringBuilder string = new StringBuilder();
        string.append("Unknown command\n")
                .append("Here you have a list with _all_ available commands and their descriptions:\n\n");

        for (Command cmd : CryptoTrackerBot.commandsMap.values()) {
            string.append("/" + cmd.name + ": _" + cmd.description + "_\n");
        }
        return SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build();
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
