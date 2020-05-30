package com.mballen.curso.boot.repositories;


import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mballen.curso.boot.model.User;



@Repository
@Transactional
public interface UserRepository   extends JpaRepository<User, Long> {
    public User findByUserEmailAndEnabled(String userEmail, short enabled);
    public Optional<User> findByUserEmail(String email);
   // public List<User> findAllByEnabled(short s);

    public Optional<User> findById(Long id);

    void deleteById(Long id);
}
