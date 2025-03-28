package com.luana.projeto1.repository;

import com.luana.projeto1.model.Phone;
import com.luana.projeto1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    long countByUser(User user);
}
