package com.rbestardpino;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);

    }
}
