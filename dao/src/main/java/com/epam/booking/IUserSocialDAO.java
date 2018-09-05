package com.epam.booking;

import com.epam.booking.entity.UserSocial;

public interface IUserSocialDAO {

    void createSocialUser(UserSocial socialUser);

    String getRefreshToken(String userId);

}
