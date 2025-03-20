package org.hello.repository;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.hello.entity.User;

import java.util.Optional;

@EzyRepository
public interface UserRepository extends EzyDatabaseRepository<Integer ,User> {

    void deleteById(Integer id);

    Optional<User> findByUsername(String username);
}
