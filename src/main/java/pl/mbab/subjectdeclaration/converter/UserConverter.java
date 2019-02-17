package pl.mbab.subjectdeclaration.converter;

import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.user.register.UserDto;

public interface UserConverter {
    User convertFromDto(UserDto userDto);

    UserDto convertToDto(User user);
}
