package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public @ResponseBody ResponseEntity<String> register(@RequestBody Account account) {
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body("invalid username or password");
        }
        Account newAccount = accountService.register(account);
        if (newAccount != null) {
            return ResponseEntity.status(200).body("successfully registered");
        }
        else {
            return ResponseEntity.status(409).body("username taken");
        }
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) {
        Account newAccount = accountService.login(account);
        if (newAccount != null) {
            return ResponseEntity.status(200).body(newAccount);
        }
        else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> messages(@RequestBody Message message) {
        Message newMessage = messageService.messages(message);
        if (newMessage != null) {
            return ResponseEntity.status(200).body(newMessage);
        }
        else {
            return ResponseEntity.status(400).body(null);
        }
    }
}
