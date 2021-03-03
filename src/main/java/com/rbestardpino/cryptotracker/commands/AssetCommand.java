package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.Asset;
import com.rbestardpino.cryptotracker.model.Chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AssetCommand extends Command {

    private static AssetCommand instance = null;

    private final APIManager api = APIManager.getInstance();

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setParseMode("markdown");

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
                    e.printStackTrace();
                    string.append("Unknown error, try again.");
                } catch (RuntimeException e) {
                    string.append("`" + arg + "` is an unknown asset, you might have misspelled it.\n\n");
                }
            }
        }

        message.setText(string.toString());
        return message;
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