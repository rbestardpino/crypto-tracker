package com.rbestardpino.model;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.coinapi.rest.Exchange_rate;
import io.coinapi.rest.REST_methods;

public class CryptoTrackerBot extends TelegramLongPollingBot {

    private Logger log = LoggerFactory.getLogger(CryptoTrackerBot.class);
    private REST_methods api;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String chatId = String.valueOf(update.getMessage().getChatId());

            log.info(update.getMessage().getFrom().getUserName() + " in " + chatId + ": "
                    + update.getMessage().getText());

            SendMessage message = new SendMessage();
            message.setChatId(chatId);

            api = new REST_methods("9619CF7F-A991-4149-8527-D8A856BE258F");

            try {
                Exchange_rate ethusd = api.get_exchange_rate("ETH", "USD");
                Exchange_rate btcusd = api.get_exchange_rate("BTC", "USD");
                message.setText("ETH/USD: " + ethusd.get_rate() + ". BTC/UDS: " + btcusd.get_rate() + ". Time: "
                        + ethusd.get_time());

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                execute(message);
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
        return System.getenv("BOT_TOKEN");
    }
}
