package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CommandNotFound extends Command {

    private static CommandNotFound instance = null;

    @Override
    public String execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {
        StringBuilder string = new StringBuilder();
        string.append("Unknown command\n")
                .append("Here you have a list with _all_ available commands and their descriptions:\n\n");

        for (Command cmd : CryptoTrackerBot.commandsMap.values()) {
            string.append("/" + cmd.name + ": _" + cmd.description + "_\n");
        }

        bot.execute(SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build());

        return string.toString();
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
