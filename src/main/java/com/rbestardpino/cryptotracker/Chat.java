package com.rbestardpino.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
public class Chat {

    @Getter
    @Id
    private final String id;

    @Getter
    @Setter
    @Column
    private String default_asset_id_quote = "USD";
}
