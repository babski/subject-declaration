package pl.mbab.subjectdeclaration.converter;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import pl.mbab.subjectdeclaration.model.user.Role;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.model.user.UserStatus;
import pl.mbab.subjectdeclaration.user.register.UserDto;

import java.util.Arrays;
import java.util.UUID;

@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User convertFromDto(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPesel(userDto.getPesel());
        user.setSemester(userDto.getSemester());
        user.setGender(userDto.getGender());
        user.setField(userDto.getField());
        user.setEmail(userDto.getEmail());
        user.setPassword(DigestUtils.md5Hex(userDto.getPassword()));
        user.setToken(UUID.randomUUID().toString());
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        user.setUserStatus(UserStatus.NEW);
        return user;
    }

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPesel(user.getPesel());
        userDto.setGender(user.getGender());
        userDto.setSemester(user.getSemester());
        userDto.setField((user.getField()));
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUserStatus(user.getUserStatus());
        return userDto;
    }
}
