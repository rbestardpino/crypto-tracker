package com.rbestardpino.cryptotracker;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

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
