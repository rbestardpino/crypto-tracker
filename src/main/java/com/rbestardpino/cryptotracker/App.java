package com.rbestardpino.cryptotracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static PersistenceManager database;

    public static void main(String[] args) {
        logger.info("App started");

        database = PersistenceManager.getInstance();
        logger.info("Database updated");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CryptoTrackerBot());

            logger.info("Bot initialized");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
