package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class Command {

    public String name;
    public String description;

    public String execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {
        return null;
    }
}
