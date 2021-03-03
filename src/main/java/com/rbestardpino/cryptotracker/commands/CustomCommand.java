package com.rbestardpino.cryptotracker.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rbestardpino.cryptotracker.CryptoTrackerBot;
import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CustomCommand extends Command {

    private static CustomCommand instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {

        if (chat.getCustomCommand() == null) {
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setParseMode("markdown");
            message.setText("You haven't set up your custom command yet. You can configure it in /settings.");
            return message;
        }

        List<String> customArgs = new ArrayList<>(Arrays.asList(chat.getCustomCommand().split("\\s+")));
        String customCommandName = args.get(0).substring(1);
        customArgs.remove(0);

        if (CryptoTrackerBot.commandsMap.get(customCommandName) == null) {
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setParseMode("markdown");
            message.setText(
                    "The command you configured as your custom does not exist, you could have mispelled it. Change it in /settings.");
            return message;
        }

        return CryptoTrackerBot.commandsMap.get(customCommandName).createMessage(customArgs, chat);
    }

    private CustomCommand() {
        name = "custom";
        description = "Your custom command";
    }

    public static CustomCommand getInstance() {
        if (instance == null)
            instance = new CustomCommand();
        return instance;
    }
}