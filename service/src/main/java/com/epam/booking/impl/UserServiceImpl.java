package com.epam.booking.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.booking.IUserService;
import com.epam.booking.IUserSocialDAO;
import com.epam.booking.dto.UserSocialDTO;
import com.epam.booking.entity.UserSocial;
import com.epam.booking.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserSocialDAO dao;

    @Autowired
    public UserServiceImpl(IUserSocialDAO dao) {
        this.dao = dao;
    }

    @Override
    public String getRefreshToken(String idToken) {
       String userId = getIdFromToken(idToken);
       return dao.getRefreshToken(userId);
    }

    @Override
    public void registerUser(UserSocialDTO userSocialDTO) {
        UserSocial user = EntityConverter.convertDtoToUserEntity(userSocialDTO);
        dao.createSocialUser(user);
    }

    private String getIdFromToken(String token){
        String id;
        DecodedJWT jwt = JWT.decode(token);
        id = jwt.getSubject();
        return id;
    }
}
