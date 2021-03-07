package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.model.Chat;

import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExchangeCommand extends Command {

    private static ExchangeCommand instance = null;

    private final APIManager api = APIManager.getInstance();

    @Override
    public String execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {
        Exchange exchange;
        args = args.stream().map(String::toUpperCase).collect(Collectors.toList());

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
                    log.error(e.getMessage(), e);
                    string.append("Unknown error, try again.");
                } catch (RuntimeException e) {
                    log.error(e.getMessage(), e);
                    string.append("`" + arg + "` is an unknown exchange, you might have misspelled it.\n\n");
                }
            }
        }

        bot.execute(SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build());

        return string.toString();
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