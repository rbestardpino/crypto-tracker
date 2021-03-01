package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AssetCommand extends Command {

    private static AssetCommand instance = null;

    @Override
    public SendMessage createMessage(Update update, List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getChatId());
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