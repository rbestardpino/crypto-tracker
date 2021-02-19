package com.rbestardpino.cryptotracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.commands.Command;
import com.rbestardpino.cryptotracker.commands.CommandNotFound;
import com.rbestardpino.cryptotracker.commands.Start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CryptoTrackerBot extends TelegramLongPollingBot {

    private Map<String, Command> commandsMap = new HashMap<String, Command>();

    private Logger log = LoggerFactory.getLogger(CryptoTrackerBot.class);

    // private APIManager api;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {

            String chatId = String.valueOf(update.getMessage().getChatId());

            log.info(update.getMessage().getFrom().getUserName() + " in " + chatId + ": "
                    + update.getMessage().getText());

            List<String> args = new ArrayList<String>(Arrays.asList(update.getMessage().getText().split("\\s+")));
            String commandName = args.get(0).substring(1);
            args.remove(0);

            SendMessage message;
            if (commandsMap.containsKey(commandName)) {
                message = commandsMap.get(commandName).createMessage(update, args);
            } else
                message = CommandNotFound.getInstance().createMessage(update, args);

            if (message != null) {

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

        // if (update.hasMessage() && update.getMessage().hasText()) {

        // String chatId = String.valueOf(update.getMessage().getChatId());

        // log.info(update.getMessage().getFrom().getUserName() + " in " + chatId + ": "
        // + update.getMessage().getText());

        // SendMessage message = new SendMessage();
        // message.setChatId(chatId);

        // api = new APIManager("9619CF7F-A991-4149-8527-D8A856BE258F");

        // try {
        // ExchangeRate ethusd = api.getExchangeRate("ETH", "USD");
        // ExchangeRate btcusd = api.getExchangeRate("BTC", "USD");
        // message.setText("ETH/USD: " + ethusd.getRate() + ". BTC/UDS: " +
        // btcusd.getRate() + ". Time: "
        // + ethusd.getTime());

        // } catch (IOException e1) {
        // e1.printStackTrace();
        // }

        // try {
        // execute(message);
        // } catch (TelegramApiException e) {
        // e.printStackTrace();
        // }
        // }
    }

    @Override
    public String getBotUsername() {
        return "Crypto_TrackerBot";
    }

    @Override
    public String getBotToken() {
        // return System.getenv("BOT_TOKEN");
        return "1569622449:AAE1WVZtOUVLvCekquT-1C7vL9vaU9Nc6LQ";
    }

    public CryptoTrackerBot() {
        commandsMap.put(Start.getInstance().name, Start.getInstance());
    }
}
