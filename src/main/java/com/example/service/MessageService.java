package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message){
        if (!message.getMessage_text().equals("") && message.getMessage_text().length()<=255){
            return messageRepository.save(message);
        }
        else{
            return null;
        }
    }

    public List<Message> getMessageList(){
        return (List<Message>) messageRepository.findAll();
    }

    public List<Message> getMessageListByPostedBy(Integer posted_by){
        return messageRepository.findAllByPosted_By(posted_by).orElse(null);
    }

    public Message findMessage(Integer message_id){
        return messageRepository.findById(message_id).orElse(null);
    }

    public Integer deleteMessage(Integer message_id){
        messageRepository.deleteById(message_id);
        Message message = messageRepository.findById(message_id).orElse(null);
        if(message == null){
            return 1;
        }
        else{
            return 0;
        }
    }

    public Integer patchMessage(Integer message_id, String message_text){
        Message message = messageRepository.findById(message_id).orElse(null);
        if(message == null || message_text.equals("") || message_text.length() > 255){
            return 0;
        }
        else{
            message.setMessage_text(message_text);
            messageRepository.save(message);
            return 1;
        }
    }
}
