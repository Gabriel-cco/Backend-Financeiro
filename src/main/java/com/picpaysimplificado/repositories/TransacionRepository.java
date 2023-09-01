package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacionRepository extends JpaRepository<Transaction, Long> {
}
