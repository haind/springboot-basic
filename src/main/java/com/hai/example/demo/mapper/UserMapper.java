package com.hai.example.demo.mapper;

import com.hai.example.demo.dto.UserDTO;
import com.hai.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.util.Optional;

/*@Mapper
public interface UserMapper {

    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "fullName", source = "user.name"),
            @Mapping(target = "emailAddress", source = "user.email"),
    })
    UserDTO userModelToUserView(User user);
}*/
@Mapper
public abstract class UserMapper {
    public UserDTO userModelToUserView(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(new BigDecimal(user.getId()));
        dto.setFullName(user.getName() + " XX");
        dto.setEmailAddress(user.getEmail().toUpperCase());

        return dto;
    }

    public abstract Iterable<UserDTO> userModelToUserView(Iterable<User> users);
}