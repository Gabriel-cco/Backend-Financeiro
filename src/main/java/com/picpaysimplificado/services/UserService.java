package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.enums.UserTypeEnum;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validadedTransaction(User user, BigDecimal amount) {
        if (user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        if(user.getUserType().equals(UserTypeEnum.MERCHANT)){
            throw new RuntimeException("Usuário lojista não estar autorizado à realizar transações bancárias");
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void save(User user) {
        this.userRepository.save(user);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserDTO dataUser) {
        User newUser = new User();
        BeanUtils.copyProperties(dataUser, newUser);
        newUser.setUserType(UserTypeEnum.valueOf(dataUser.userType()));
        return userRepository.save(newUser);
    }
}
