package com.hai.example.demo.mapper;

import com.hai.example.demo.dto.SessionDTO;
import com.hai.example.demo.dto.UserDTO;
import com.hai.example.demo.model.Session;

import com.hai.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    @Mappings({
            @Mapping(target = "sessionId", source = "id"),
            @Mapping(target = "sessionUuid", source = "uuid"),
            @Mapping(target = "sessionContent", source = "content")
    })
    SessionDTO sessionToSessionDTO(Session session);

    Map<String, SessionDTO> sessionToSessionDTO(Map<String, Session> session);
}
