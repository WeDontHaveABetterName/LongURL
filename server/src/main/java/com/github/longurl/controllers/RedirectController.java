package com.github.longurl.controllers;

import com.github.longurl.db.Entry;
import com.github.longurl.db.EntryRepository;
import com.github.longurl.exceptions.NoEntryFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RedirectController {

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping("/{longUrl}")
    public ModelAndView redirect(@PathVariable("longUrl") String longUrl) {
        Entry correspondingEntry = entryRepository.findEntryByLongUrl(longUrl);
        if (correspondingEntry == null) {
            throw new NoEntryFoundException("No entry found with this long url");
        }

        return new ModelAndView("redirect:" + correspondingEntry.getUrl());
    }
}
