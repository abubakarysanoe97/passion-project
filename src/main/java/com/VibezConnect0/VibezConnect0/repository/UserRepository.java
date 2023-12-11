package com.VibezConnect0.VibezConnect0.repository;

import com.VibezConnect0.VibezConnect0.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
