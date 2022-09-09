package com.github.longurl;

import javax.persistence.*;

@Entity
@Table(name = "Entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "longUrl", length = 2000, unique = true)
    private String longUrl;

    @Column(name = "url", length = 2000)
    private String url;

    public Entry() {}

    public Entry(String longUrl, String url) {
        this.longUrl = longUrl;
        this.url = url;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getUrl() {
        return url;
    }
}
