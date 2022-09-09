package com.github.longurl.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    Entry findEntryByLongUrl(String longUrl);

    Entry findEntryByUrl(String url);
}
