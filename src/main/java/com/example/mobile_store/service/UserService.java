package com.example.mobile_store.service;

import com.example.mobile_store.dto.RegisterDTO;
import com.example.mobile_store.entity.Role;
import com.example.mobile_store.entity.User;
import com.example.mobile_store.exception.AlreadyExistsException;
import com.example.mobile_store.exception.NotFoundException;
import com.example.mobile_store.mapper.UserMapper;
import com.example.mobile_store.repository.RoleRepository;
import com.example.mobile_store.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    public RegisterDTO saveUser(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new AlreadyExistsException("User", "username", registerDTO.getUsername());
        }

        Role role = roleRepository.findById(registerDTO.getRole()).orElseThrow(() ->
                new NotFoundException(registerDTO.getRole()));

        User user = userMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(role);

        return userMapper.toDTO(userRepository.save(user));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
