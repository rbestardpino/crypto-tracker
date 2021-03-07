package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.Asset;
import com.rbestardpino.cryptotracker.model.Chat;

import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetCommand extends Command {

    private static AssetCommand instance = null;

    private final APIManager api = APIManager.getInstance();

    @Override
    public void execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {
        Asset asset;
        args = args.stream().map(String::toUpperCase).collect(Collectors.toList());

        StringBuilder string = new StringBuilder();

        if (args.isEmpty()) {
            string.append("Use: `/asset asset_id*`.\n\n");
            string.append("`asset_id` can be one or multiple asset ids.\n\n");
            string.append("Examples: `/asset BTC` or `/asset BTC ETH`");
        } else {
            for (String arg : args) {
                try {
                    asset = api.getAsset(arg);
                    string.append("*" + asset.getName() + "*");
                    string.append(" (" + asset.getId() + ")\n");
                    string.append("Volume: " + asset.getVolume1DayUSD() + " USD\n");
                    string.append("Price: " + asset.getPriceUSD() + " USD\n\n");
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    string.append("Unknown error, try again.");
                } catch (RuntimeException e) {
                    log.error(e.getMessage(), e);
                    string.append("`" + arg + "` is an unknown asset, you might have misspelled it.\n\n");
                }
            }
        }

        bot.execute(SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build());
    }

    private AssetCommand() {
        name = "asset";
        description = "Provides info about specified asset/s";
    }

    public static AssetCommand getInstance() {
        if (instance == null)
            instance = new AssetCommand();
        return instance;
    }
}