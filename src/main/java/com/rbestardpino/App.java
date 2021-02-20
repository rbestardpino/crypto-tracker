package com.rbestardpino;

import com.rbestardpino.cryptotracker.CryptoTrackerBot;
import com.rbestardpino.cryptotracker.PersistenceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static PersistenceManager database;

    public static void main(String[] args) {
        log.info("App started");

        database = PersistenceManager.getInstance();
        log.info("Database updated");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CryptoTrackerBot());

            log.info("Bot initialized");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
