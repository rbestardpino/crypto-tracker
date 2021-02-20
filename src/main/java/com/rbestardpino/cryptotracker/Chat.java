package com.rbestardpino.cryptotracker;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Chat {

    @Id
    private String id;

    @Column
    private String default_asset_id_quote = "USD";

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }
}
