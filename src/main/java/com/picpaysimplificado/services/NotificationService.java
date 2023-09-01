package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    RestTemplate restTemplate;

    public void sendNotification(User user , String message){
        String email = user.getEmail();
        NotificationDTO notificationDTO = new NotificationDTO(message, email);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationDTO, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            log.error("Serviço de notificação indisponível");
            throw new RuntimeException("Serviço de notificação indisponível");
        }
    }
}
