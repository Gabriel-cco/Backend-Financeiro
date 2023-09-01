package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransacionRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction){
        User send = userService.findById(transaction.sender());
        User receiver = userService.findById(transaction.receiver());
        userService.validadedTransaction(send, transaction.amount());
        boolean isAuthorized = authorizationTransaction(send, transaction.amount());
        if(!isAuthorized){
            throw new RuntimeException("Transação não autorizada");
        }
        Transaction transactionSave = Transaction.builder()
                .amount(transaction.amount())
                .sender(send)
                .receiver(receiver)
                .build();

        send.setBalance(send.getBalance().subtract(transaction.amount()));
        receiver.setBalance(receiver.getBalance().add(transaction.amount()));
        repository.save(transactionSave);
        userService.save(send);
        userService.save(receiver);
/*        notificationService.sendNotification(send, "Transação relizada com sucesso");
        notificationService.sendNotification(receiver, "Transação relizada com sucesso");*/
        return transactionSave;

    }

    private boolean authorizationTransaction(User sender, BigDecimal value){
      ResponseEntity<Map> responseAuthorization = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
      if(responseAuthorization.getStatusCode() == HttpStatus.OK){
          return "Autorizado".equals(responseAuthorization.getBody().get("message"));
      }else return false;
    }



}
