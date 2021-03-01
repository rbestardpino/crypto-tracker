package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ExchangeCommand extends Command {

    private static ExchangeCommand instance = null;

    private APIManager api = APIManager.getInstance();

    @Override
    public SendMessage createMessage(Update update, List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getChatId());
        message.setParseMode("markdown");

        Exchange exchange;
        args = args.stream().map(arg -> arg.toUpperCase()).collect(Collectors.toList());

        StringBuilder string = new StringBuilder();

        if (args.isEmpty()) {
            string.append("Use: `/exchange exchange_id*`.\n\n");
            string.append("`exchange_id` can be one or multiple exchange ids.\n\n");
            string.append("Examples: `/exchange binance` or `/exchange binance coinbase`");
        } else {
            for (String arg : args) {
                try {
                    exchange = api.getExchange(arg);
                    string.append("*" + exchange.getName() + "*\n");
                    string.append(exchange.getWebsite() + "\n\n");
                } catch (IOException e) {
                    e.printStackTrace();
                    string.append("Unknown error, try again.");
                } catch (RuntimeException e) {
                    string.append("`" + arg + "` is an unknown exchange, you might have misspelled it.\n\n");
                }
            }
        }

        message.setText(string.toString());
        return message;
    }

    private ExchangeCommand() {
        name = "exchange";
        description = "Provides info about specified exchange/s";
    }

    public static ExchangeCommand getInstance() {
        if (instance == null)
            instance = new ExchangeCommand();
        return instance;
    }
}