package com.compact.todosimple.repositories;

// usado para executar comandos de sql sobre o modelo User

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compact.todosimple.models.User;

@Repository                                        // modelo, tipo do id
public interface UserRepository extends JpaRepository<User, Long> {
}
