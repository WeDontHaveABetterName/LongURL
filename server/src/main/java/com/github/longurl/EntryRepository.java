package com.github.longurl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    Entry findEntryByLongUrl(String longUrl);
}
