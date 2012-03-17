package com.placecruncher.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.placecruncher.server.application.Constants;

@Entity
@Table(name = SeedDataLog.DB_TABLE)
@SequenceGenerator(name = SeedDataLog.DB_SEQ, sequenceName = SeedDataLog.DB_SEQ)
public class SeedDataLog extends AbstractEntity {
    public static final String DB_TABLE = "SEED_DATA_LOG";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    private Integer id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return this.id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, unique=true, length=Constants.NAME_MAXLEN)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
