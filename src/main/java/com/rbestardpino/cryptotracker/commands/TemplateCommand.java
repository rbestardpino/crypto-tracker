package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TemplateCommand extends Command {

    private static TemplateCommand instance = null;

    @Override
    public String execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {
        StringBuilder string = new StringBuilder();
        string.append("WIP");

        bot.execute(SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build());

        return string.toString();
    }

    private TemplateCommand() {
        name = "name";
        description = "description";
    }

    public static TemplateCommand getInstance() {
        if (instance == null)
            instance = new TemplateCommand();
        return instance;
    }
}