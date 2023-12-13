package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account findAccount = accountService.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(findAccount == null){
            Account registeredAccount = accountService.register(account);
            if(registeredAccount == null){
                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        else{
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account verifiedAccount = accountService.login(account.getUsername(), account.getPassword());
        if(verifiedAccount == null){
            return ResponseEntity.status(401).build();
        }
        else{
            return ResponseEntity.status(200).body(verifiedAccount);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        if(accountService.findAccount(message.getPosted_by()) == null){
            return ResponseEntity.status(400).build();
        }
        else{
            Message addedMessage = messageService.addMessage(message);
            if(addedMessage == null){
                return ResponseEntity.status(400).build();
            }
            else{
                return ResponseEntity.status(200).body(addedMessage);
            }
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getMessageList());
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){
        return ResponseEntity.status(200).body(messageService.findMessage(message_id));
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer message_id){
        if(messageService.findMessage(message_id) != null){
            return ResponseEntity.status(200).body(messageService.deleteMessage(message_id));
        }
        else{
            return ResponseEntity.status(200).build();
        }
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message message_text, @PathVariable Integer message_id){
        if(messageService.patchMessage(message_id, message_text.getMessage_text()) == 1){
            System.out.println("message text is " +  message_text.getMessage_text());
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer account_id){
        return ResponseEntity.status(200).body(messageService.getMessageListByPostedBy(account_id));
    }




    

}
