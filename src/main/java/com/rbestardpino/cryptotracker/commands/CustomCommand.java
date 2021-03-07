package com.rbestardpino.cryptotracker.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CustomCommand extends Command {

    private static CustomCommand instance = null;

    @Override
    public String execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {

        if (chat.getCustomCommand() == null) {
            SendMessage message = new SendMessage();
            message.setChatId(chat.getId());
            message.setParseMode("markdown");
            message.setText("You haven't set up your custom command yet. You can configure it in /settings.");
            bot.execute(message);
            return message.getText();
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
            bot.execute(message);
            return message.getText();
        }

        return CryptoTrackerBot.commandsMap.get(customCommandName).execute(customArgs, chat, bot);
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