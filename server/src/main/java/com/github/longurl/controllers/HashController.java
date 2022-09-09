package com.github.longurl.controllers;

import com.github.longurl.db.Entry;
import com.github.longurl.db.EntryRepository;
import com.github.longurl.api.CreateRequest;
import com.github.longurl.api.CreateResponse;
import com.github.longurl.exceptions.InvalidURLException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class HashController {

    @Autowired
    private EntryRepository entryRepository;

    private String hash(String string, MessageDigest digest) {
        byte[] hashed = digest.digest(
                string.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * hashed.length);
        for (byte b : hashed) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @PostMapping("create")
    public ResponseEntity<CreateResponse> create(@RequestBody CreateRequest body) throws NoSuchAlgorithmException {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(body.url)) {
            throw new InvalidURLException(body.url + " is not a valid URL");
        }

        MessageDigest digest = MessageDigest.getInstance(body.algorithm);

        String hashedUrl = hash(body.url, digest);

        int timesToRepeat = body.length / hashedUrl.length();
        String longUrl = hashedUrl.repeat(timesToRepeat);

        if (entryRepository.findEntryByLongUrl(longUrl) == null) {
            Entry newEntry = new Entry(longUrl, body.url);
            entryRepository.save(newEntry);
        }

        CreateResponse response = new CreateResponse(longUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
