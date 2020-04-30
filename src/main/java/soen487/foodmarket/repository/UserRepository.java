package soen487.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soen487.foodmarket.dataobject.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByUsername(String username);

}
