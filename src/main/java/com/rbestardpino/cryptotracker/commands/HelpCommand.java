package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import com.rbestardpino.cryptotracker.CryptoTrackerBot;
import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpCommand extends Command {

    private static HelpCommand instance = null;

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setParseMode("markdown");

        StringBuilder string = new StringBuilder();

        if (args.isEmpty()) {
            string.append("Here you have a list with _all_ available commands and their descriptions:\n\n");

            for (Command cmd : CryptoTrackerBot.commandsMap.values()) {
                string.append("/" + cmd.name + ": _" + cmd.description + "_\n");
            }
        } else {
            for (String cmdName : args) {
                string.append("/" + cmdName + ": ");
                Command cmd = CryptoTrackerBot.commandsMap.get(cmdName);
                if (cmd == null) {
                    string.append("_Unknown command_\n");
                } else
                    string.append("_" + cmd.description + "_\n");
            }
        }

        message.setText(string.toString());

        return message;
    }

    private HelpCommand() {
        name = "help";
        description = "Provides info about all or specified command/s";
    }

    public static HelpCommand getInstance() {
        if (instance == null)
            instance = new HelpCommand();
        return instance;
    }
}