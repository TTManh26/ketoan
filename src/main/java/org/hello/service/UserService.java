package org.hello.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.security.BCrypt;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.Data;
import org.hello.dto.request.UserCreationRequest;
import org.hello.dto.request.UserUpdateRequest;
import org.hello.entity.User;
import org.hello.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Data
@EzySingleton
@Service
public class UserService {
    @EzyAutoBind
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();

        user.setUsername(request.getUsername());
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        user.setEmail(request.getEmail());

        userRepository.save(user);
        return user;
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, UserUpdateRequest request){
        User user= getUser(id);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        userRepository.save(user);
        return user;
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
