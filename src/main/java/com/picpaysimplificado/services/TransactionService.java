package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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


    private void createTransaction(TransactionDTO transaction){
        User send = userService.findById(transaction.senderId());
        User receiver = userService.findById(transaction.receiverId());
        userService.validadedTransaction(send, transaction.value());
        boolean isAuthorized = authorizationTransaction(send, transaction.value());
        if(!isAuthorized){
            throw new RuntimeException("Transação não autorizada");
        }
        Transaction transactionSave = Transaction.builder()
                .amount(transaction.value())
                .sender(send)
                .receiver(receiver)
                .build();

        send.setBalance(send.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));
        repository.save(transactionSave);
        userService.save(send);
        userService.save(receiver);
    }

    private boolean authorizationTransaction(User sender, BigDecimal value){
      ResponseEntity<Map> responseAuthorization = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
      if(responseAuthorization.getStatusCode() == HttpStatus.OK){
          return "Autorizado".equals(responseAuthorization.getBody().get("message"));
      }else return false;
    }



}
