package com.compact.todosimple.repositories;


import com.compact.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // pesquisa tasks por usuário. 0-0: O atributo do usuário pelo qual queremos pesquisar tem que vir depois de um _
    List<Task> findByUser_id(Long id);

    // mesma query de cima usando sql fake do java
    // @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    // List<Task> findByUser_id(@Param("id") Long id);

    // mesma query de cima usando sql real
    // @Query(value="SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    // List<Task> findByUser_id(@Param("id") Long id);

}
