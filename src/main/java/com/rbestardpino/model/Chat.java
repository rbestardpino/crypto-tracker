package com.rbestardpino.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Chat {

    private final String id;

    @Getter
    @Setter
    private String default_asset_id_quote = "USD";
}
