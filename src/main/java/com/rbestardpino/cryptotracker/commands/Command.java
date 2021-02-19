package com.rbestardpino.cryptotracker.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Command {

    public String name;
    public String description;

    public SendMessage createMessage(Update update, List<String> args) {
        return null;
    }
}
