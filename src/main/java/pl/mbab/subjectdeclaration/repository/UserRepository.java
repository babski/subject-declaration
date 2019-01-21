package pl.mbab.subjectdeclaration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.model.user.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPesel(String pesel);

    User findOneByPeselAndToken(String pesel, String token);

    User findOneByEmail(String email);

    User findUserByEmailAndPasswordAndUserStatus(String email, String password, UserStatus userStatus);

    User findFirstByEmail(String email);
}