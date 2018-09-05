package com.epam.booking;

import com.epam.booking.dto.UserSocialDTO;

public interface IUserService {

    String getRefreshToken(String idToken);

    void registerUser(UserSocialDTO userSocialDTO);
}
