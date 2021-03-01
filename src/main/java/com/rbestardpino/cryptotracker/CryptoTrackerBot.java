package com.rbestardpino.cryptotracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rbestardpino.cryptotracker.commands.AssetCommand;
import com.rbestardpino.cryptotracker.commands.Command;
import com.rbestardpino.cryptotracker.commands.CommandNotFound;
import com.rbestardpino.cryptotracker.commands.ExchangeCommand;
import com.rbestardpino.cryptotracker.commands.ExchangeRateCommand;
import com.rbestardpino.cryptotracker.commands.HelpCommand;
import com.rbestardpino.cryptotracker.commands.MioCommand;
import com.rbestardpino.cryptotracker.commands.SettingsCommand;
import com.rbestardpino.cryptotracker.commands.StartCommand;
import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.model.Message;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoTrackerBot extends TelegramLongPollingBot {

    public static Map<String, Command> commandsMap = new HashMap<String, Command>();

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
                chat.setChatId(chatId);
                App.database.persist(chat);
            }

            message.setChat(chat);
            chat.addMessage(message);
            App.database.persist(message);

            App.database.commit();
            App.database.close();

            List<String> args = new ArrayList<String>(Arrays.asList(messageString.split("\\s+")));
            String commandName = args.get(0).substring(1);
            args.remove(0);

            SendMessage sendMessage;
            if (commandsMap.containsKey(commandName)) {
                sendMessage = commandsMap.get(commandName).createMessage(update, args, chat);
            } else
                sendMessage = CommandNotFound.getInstance().createMessage(update, args, chat);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Crypto_TrackerBot";
    }

    @Override
    public String getBotToken() {
        // return System.getenv("BOT_TOKEN");
        return "1633380461:AAEHiIgrKerxFp-kzee6JaYqizKpeFFF8Pc";
    }

    public CryptoTrackerBot() {
        List<Command> commandsList = new ArrayList<>();
        commandsList.add(StartCommand.getInstance());
        commandsList.add(HelpCommand.getInstance());
        commandsList.add(AssetCommand.getInstance());
        commandsList.add(ExchangeCommand.getInstance());
        commandsList.add(ExchangeRateCommand.getInstance());
        commandsList.add(SettingsCommand.getInstance());
        commandsList.add(MioCommand.getInstance());

        for (Command command : commandsList)
            commandsMap.put(command.name, command);
    }
}
