package com.github.longurl;

import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class HashController {

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

    private String hashEveryLetter(String message, MessageDigest digest) {
        StringBuilder messageBuilder = new StringBuilder();
        for (char l : message.toCharArray()) {
            String letter = String.valueOf(l);
            String hashedLetter = hash(letter, digest);
            messageBuilder.append(hashedLetter);
        }

        return messageBuilder.toString();
    }

    private String hashEveryLetterRecursively(String message, MessageDigest digest, int depth) {
        if (depth == 1) {
            return message;
        }

        String allLettersHashed = hashEveryLetter(message, digest);
        return hashEveryLetterRecursively(allLettersHashed, digest, depth - 1);
    }

    @PostMapping("create")
    public ResponseEntity<CreateResponse> create(@RequestBody CreateRequest body) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(body.algorithm);

        String hashedUrl = hash(body.url, digest);

        int timesToRepeat = body.length / hashedUrl.length();

        CreateResponse response = new CreateResponse(hashedUrl.repeat(timesToRepeat));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
