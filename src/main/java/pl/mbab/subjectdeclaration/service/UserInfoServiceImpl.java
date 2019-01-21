package pl.mbab.subjectdeclaration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mbab.subjectdeclaration.converter.UserConverter;
import pl.mbab.subjectdeclaration.dto.UserDto;
import pl.mbab.subjectdeclaration.repository.UserRepository;

import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserDetailsService {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        UserDto userDto = userConverter.convertToDto(userRepository.findFirstByEmail(email));
        return userDto;
    }
}