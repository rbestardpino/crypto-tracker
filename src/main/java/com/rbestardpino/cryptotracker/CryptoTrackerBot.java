package com.rbestardpino.model;

import java.io.IOException;

import com.rbestardpino.model.api.APIManager;
import com.rbestardpino.model.api.domain.ExchangeRate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CryptoTrackerBot extends TelegramLongPollingBot {

    private Logger log = LoggerFactory.getLogger(CryptoTrackerBot.class);

    private APIManager api;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String chatId = String.valueOf(update.getMessage().getChatId());

            log.info(update.getMessage().getFrom().getUserName() + " in " + chatId + ": "
                    + update.getMessage().getText());

            SendMessage message = new SendMessage();
            message.setChatId(chatId);

            api = new APIManager("9619CF7F-A991-4149-8527-D8A856BE258F");

            try {
                ExchangeRate ethusd = api.getExchangeRate("ETH", "USD");
                ExchangeRate btcusd = api.getExchangeRate("BTC", "USD");
                message.setText("ETH/USD: " + ethusd.getRate() + ". BTC/UDS: " + btcusd.getRate() + ". Time: "
                        + ethusd.getTime());

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
        // return System.getenv("BOT_TOKEN");
        return "1569622449:AAE1WVZtOUVLvCekquT-1C7vL9vaU9Nc6LQ";
    }
}
