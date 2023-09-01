package com.picpaysimplificado.domain.user;

import com.picpaysimplificado.domain.user.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firtsName;
        private String lastname;
        @Column(unique = true)
        private String email;
        @Column(unique = true)
        private String document;
        private String password;
        private BigDecimal balance;
        @Enumerated(EnumType.STRING)
        private UserTypeEnum userType;
}
