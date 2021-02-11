package com.rbestardpino.tezt;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestClass {

    @Getter
    @Setter
    @Id
    private String id;

    @Getter
    @Setter
    @Column
    private double price;

    @Getter
    @Setter
    @Column
    private Instant time;
}
