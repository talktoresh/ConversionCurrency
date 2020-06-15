package com.etl.currency.conversion.models;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="API_RESPONSE")
public class APIResponse {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name="success")
    private boolean success;

    @Column(name="source")
    private String source;

    @Column(name="timestamp")
    private Long timestamp;

    @ElementCollection
    @CollectionTable(name = "Quotes",
            joinColumns = {@JoinColumn(name = "quote_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "currency_pair")
    @Column(name = "rate")
    private Map<String,Float> quotes = new HashMap<String, Float>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, Float> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Float> quotes) {
        this.quotes = quotes;
    }


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
