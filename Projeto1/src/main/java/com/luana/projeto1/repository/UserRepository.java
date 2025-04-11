package com.luana.projeto1.repository;

import com.luana.projeto1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findByName(String name); // Buscar por nome
    List<User> findByNameAndEmail(String name, String email); // Buscar por nome e email

    long count(); // Conta o total de usuários no banco

    List<User> findByNameContaining(String partOfName); // Busca usuários por parte do nome

    List<User> findByNameStartingWith(String prefix); // Busca usuários cujo nome começa com o prefixo

    List<User> findAllByOrderByNameAsc(); // Retorna todos os usuários ordenados por nome

    List<User> findTop3ByOrderByNameDesc(); // Retorna as últimas 3 pessoas ordenadas por nome decrescente

    List<User> findByPhones_Size(int size); // Busca usuários com um número específico de telefones

    /*
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.name = :name AND u.email = :email")
    List<User> findByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Query("SELECT u FROM User u JOIN u.phones p WHERE p.number = :phoneNumber")
    List<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
     */

}