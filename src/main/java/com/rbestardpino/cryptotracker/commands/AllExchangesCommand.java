package com.rbestardpino.cryptotracker.commands;

import java.io.IOException;
import java.util.List;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.utils.FileUtils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

//TODO fix
public class AllExchangesCommand extends Command {

    private static AllExchangesCommand instance = null;

    private final APIManager api = APIManager.getInstance();

    @Override
    public SendMessage createMessage(List<String> args, Chat chat) {
        StringBuilder string = new StringBuilder();

        List<Exchange> exchanges;
        try {
            exchanges = api.getAllExchanges();
            for (Exchange exchange : exchanges) {
                string.append("*" + exchange.getName() + "*\n");
                string.append(exchange.getWebsite() + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            string.append("Unknown error, try again.");
        }

        FileUtils.createAndWrite("allexchanges.txt", string.toString());

        return SendMessage.builder().chatId(chat.getId()).parseMode("markdown").text(string.toString()).build();
    }

    private AllExchangesCommand() {
        name = "allexchanges";
        description = "Provide info about all exchanges";
    }

    public static AllExchangesCommand getInstance() {
        if (instance == null)
            instance = new AllExchangesCommand();
        return instance;
    }
}