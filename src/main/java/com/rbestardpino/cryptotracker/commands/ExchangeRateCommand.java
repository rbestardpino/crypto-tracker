package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.ExchangeRate;
import com.rbestardpino.cryptotracker.model.Chat;

import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ExchangeRateCommand extends Command {

    private static ExchangeRateCommand instance = null;

    private final APIManager api = APIManager.getInstance();

    @Override
    public void execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {
        ExchangeRate exchangerate;
        args = args.stream().map(String::toUpperCase).collect(Collectors.toList());

        StringBuilder string = new StringBuilder();

        if (args.isEmpty()) {
            string.append("Use: `/exchangerate asset_id_base [asset_id_quote]`.\n\n");
            string.append("`asset_id_quote` is optional, default value is ");
            string.append(chat.getDefaultAssetIdQuote() + ".");
            string.append(" You can change it with /settings.\n\n");
            string.append("Examples: `/exchangerate BTC` or `/exchangerate BTC ETH`");
        } else if (args.size() == 1) {
            try {
                exchangerate = api.getExchangeRate(args.get(0), chat.getDefaultAssetIdQuote());
                string.append("1 " + exchangerate.getAssetIdBase() + " = " + exchangerate.getRate() + " "
                        + exchangerate.getAssetIdQuote());
            } catch (IOException e) {
                e.printStackTrace();
                string.append("Unknown error, try again.");
            } catch (RuntimeException e) {
                string.append("`" + args.get(0) + "` is an unknown asset, you might have misspelled it.");
            }
        } else {
            try {
                exchangerate = api.getExchangeRate(args.get(0), args.get(1));
                string.append("1 " + exchangerate.getAssetIdBase() + " = " + exchangerate.getRate() + " "
                        + exchangerate.getAssetIdQuote());
            } catch (IOException e) {
                e.printStackTrace();
                string.append("Unknown error, try again.");
            } catch (RuntimeException e) {
                string.append("`" + args.get(0) + "` or `" + args.get(1)
                        + "` is an unknown asset, you might have misspelled it.");
            }
        }

        bot.execute(SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build());
    }

    private ExchangeRateCommand() {
        name = "exchangerate";
        description = "Provides current rate between the given assets";
    }

    public static ExchangeRateCommand getInstance() {
        if (instance == null)
            instance = new ExchangeRateCommand();
        return instance;
    }
}