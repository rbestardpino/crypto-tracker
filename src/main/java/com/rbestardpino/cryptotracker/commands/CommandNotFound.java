package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandNotFound extends Command {

    private static CommandNotFound instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Unknown command. Here is a list with all available commands:");
        // dar lista
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
