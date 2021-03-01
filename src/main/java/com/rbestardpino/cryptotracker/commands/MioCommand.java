package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.ExchangeRate;
import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MioCommand extends Command {

    private static MioCommand instance = null;

    private APIManager api = APIManager.getInstance();

    @Override
    public SendMessage createMessage(Update update, List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getChatId());
        message.setParseMode("markdown");
        ExchangeRate ethusd;
        ExchangeRate btcusd;
        try {
            ethusd = api.getExchangeRate("ETH", "USD");
            btcusd = api.getExchangeRate("BTC", "USD");
            message.setText(
                    "ETH/USD: " + ethusd.getRate() + ". BTC/USD: " + btcusd.getRate() + ". Time: " + ethusd.getTime());
        } catch (IOException e) {
            e.printStackTrace();
            message.setText("Unknown error, try again.");
        }
        return message;
    }

    private MioCommand() {
        name = "mio";
        description = "mi cmd personal";
    }

    public static MioCommand getInstance() {
        if (instance == null)
            instance = new MioCommand();
        return instance;
    }
}