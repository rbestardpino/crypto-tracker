package com.rbestardpino.cryptotracker.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.rbestardpino.cryptotracker.api.APIManager;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.model.Chat;
import com.rbestardpino.cryptotracker.model.CryptoTrackerBot;
import com.rbestardpino.cryptotracker.utils.FileUtils;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AllExchangesCommand extends Command {

    private static AllExchangesCommand instance = null;

    private final APIManager api = APIManager.getInstance();

    @Override
    public String execute(List<String> args, Chat chat, CryptoTrackerBot bot) throws TelegramApiException {

        Message message = bot.execute(SendMessage.builder().chatId(chat.getId()).parseMode("markdown")
                .text("This might take a few seconds...").build());

        StringBuilder string = new StringBuilder();
        List<Exchange> exchanges;
        try {
            exchanges = api.getAllExchanges();
            for (Exchange exchange : exchanges) {
                string.append("*" + exchange.getName() + "*\n");
                string.append(exchange.getWebsite() + "\n");
                string.append("Monthly volume (USD): " + exchange.getVolume1MthUSD() + "\n\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            string.append("Unknown error, try again.");
        }

        File file = FileUtils.createAndWrite("allexchanges.txt", string.toString());

        bot.execute(EditMessageText.builder().chatId(chat.getId()).messageId(message.getMessageId())
                .text("The message was too long for me to send it, but I have made a file with what you requested.")
                .build());

        bot.execute(SendDocument.builder().chatId(chat.getId()).parseMode("markdown").document(new InputFile(file))
                .build());

        return string.toString();
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