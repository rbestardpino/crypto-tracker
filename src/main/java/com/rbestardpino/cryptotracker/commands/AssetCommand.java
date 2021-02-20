package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AssetCommand extends Command {

    private static AssetCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("WIP");
        return message;
    }

    private AssetCommand() {
        name = "asset";
        description = "Provides info about all or specified asset/s";
    }

    public static AssetCommand getInstance() {
        if (instance == null)
            instance = new AssetCommand();
        return instance;
    }
}