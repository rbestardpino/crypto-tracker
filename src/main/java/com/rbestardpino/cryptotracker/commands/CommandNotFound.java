package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.CryptoTrackerBot;
import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CommandNotFound extends Command {

    private static CommandNotFound instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setParseMode("markdown");

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
