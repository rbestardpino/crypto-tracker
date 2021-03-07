package com.rbestardpino.cryptotracker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rbestardpino.cryptotracker.App;
import com.rbestardpino.cryptotracker.commands.AllExchangesCommand;
import com.rbestardpino.cryptotracker.commands.AssetCommand;
import com.rbestardpino.cryptotracker.commands.Command;
import com.rbestardpino.cryptotracker.commands.CommandNotFound;
import com.rbestardpino.cryptotracker.commands.CustomCommand;
import com.rbestardpino.cryptotracker.commands.ExchangeCommand;
import com.rbestardpino.cryptotracker.commands.ExchangeRateCommand;
import com.rbestardpino.cryptotracker.commands.HelpCommand;
import com.rbestardpino.cryptotracker.commands.SettingsCommand;
import com.rbestardpino.cryptotracker.commands.StartCommand;
import com.rbestardpino.cryptotracker.utils.PropertiesReader;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoTrackerBot extends TelegramLongPollingBot {

    public static Map<String, Command> commandsMap = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {

            String chatId = String.valueOf(update.getMessage().getChatId());
            String messageString = update.getMessage().getText();

            log.info(update.getMessage().getFrom().getUserName() + " in " + chatId + ": " + messageString);

            App.database.createEntityManager();
            App.database.beginTransaction();

            Message message = new Message();
            message.setMessage(messageString);

            Chat chat = App.database.find(Chat.class, chatId);

            if (chat == null) {
                chat = new Chat();
                chat.setId(chatId);
                App.database.persist(chat);
            }

            message.setChat(chat);
            chat.addMessage(message);
            App.database.persist(message);

            App.database.commit();
            App.database.close();

            dispatch(messageString, chat);
        }
    }

    private void dispatch(String messageString, Chat chat) {
        List<String> args = new ArrayList<>(Arrays.asList(messageString.split("\\s+")));
        String commandName = args.get(0).substring(1);
        args.remove(0);

        String sentMessage;
        if (commandsMap.containsKey(commandName)) {
            try {
                sentMessage = commandsMap.get(commandName).execute(args, chat, this);
                log.info("Bot: " + sentMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            try {
                sentMessage = CommandNotFound.getInstance().execute(args, chat, this);
                log.info("Bot: " + sentMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return new PropertiesReader("private.properties").read("test_bot_username");
    }

    @Override
    public String getBotToken() {
        return new PropertiesReader("private.properties").read("test_bot_token");
    }

    public CryptoTrackerBot() {
        List<Command> commandsList = new ArrayList<>();
        commandsList.add(StartCommand.getInstance());
        commandsList.add(HelpCommand.getInstance());
        commandsList.add(AssetCommand.getInstance());
        commandsList.add(ExchangeCommand.getInstance());
        commandsList.add(ExchangeRateCommand.getInstance());
        commandsList.add(SettingsCommand.getInstance());
        commandsList.add(CustomCommand.getInstance());
        commandsList.add(AllExchangesCommand.getInstance());

        for (Command command : commandsList)
            commandsMap.put(command.name, command);
    }
}
