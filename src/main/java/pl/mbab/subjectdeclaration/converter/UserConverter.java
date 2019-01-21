package pl.mbab.subjectdeclaration.converter;

import pl.mbab.subjectdeclaration.dto.UserDto;
import pl.mbab.subjectdeclaration.model.user.User;

public interface UserConverter {
    User convertFromDto(UserDto userDto);

    UserDto convertToDto(User user);
}
