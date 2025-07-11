package com.example.service;

import java.lang.StackWalker.Option;
import java.util.List;
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

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message messageById(int messageId) {
        Optional<Message> message = messageRepository.findByMessageId(messageId);
        if (message.isPresent()) {
            return message.get();
        }
        return null;
    }

    public int deleteMessageById(int messageId) {
        Optional<Message> message = messageRepository.findByMessageId(messageId);
        if (message.isPresent()) {
            messageRepository.delete(message.get());
            return 1;
        }
        return 0;
    }

    public int updateMessageById(int messageId, Message replacementMessage) {
        String messageText = replacementMessage.getMessageText();
        if (messageText.length() == 0 || messageText.length() > 255) {
            return 0;
        }
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);
        if (!optionalMessage.isPresent()) {
            return 0;
        }
        Message message = optionalMessage.get();
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        List<Message> messagesList = messageRepository.findAll();
        for (int i = messagesList.size() - 1; i >= 0; i--) {
            if (messagesList.get(i).getPostedBy() != accountId) {
                messagesList.remove(i);
            }
        }
        return messagesList;
    }
}
