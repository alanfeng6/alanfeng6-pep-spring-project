package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message messages(Message message) {
        Optional<Message> optionalMessage = messageRepository.findByMessageText(message.getMessageText());
        int messageLength = message.getMessageText().length();
        if (messageLength == 0 || messageLength > 255) {
            return null;
        }
        if (optionalMessage.isPresent()) {
            return null;
        }
        Optional<Account> optionalAccount = accountRepository.findByAccountId(message.getPostedBy());
        if (!optionalAccount.isPresent()) {
            return null;
        }
        return messageRepository.save(message);
    }
}
