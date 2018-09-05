package com.epam.booking.util;

import com.epam.booking.dto.UserSocialDTO;
import com.epam.booking.dto.RoomDTO;
import com.epam.booking.entity.Room;
import com.epam.booking.entity.UserSocial;
import org.apache.commons.lang3.StringUtils;

public class EntityConverter {

    public static Room convertDtoToRoomEntity(RoomDTO dto){
        Room room = new Room();
        room.setBedsAmount(dto.getBedsAmount());
        room.setType(dto.getType());
        room.setPrice(dto.getPrice());
        return room;
    }

    public static UserSocial convertDtoToUserEntity(UserSocialDTO dto){
        UserSocial userSocial = new UserSocial();
        String userId = dto.getUserId();
        String refreshToken = dto.getRefreshToken();
        if (!StringUtils.isEmpty(userId) &&
                !StringUtils.isEmpty(refreshToken)) {
            userSocial.setSocialUserId(userId);
            userSocial.setRefreshToken(refreshToken);
        }
        return userSocial;
    }
}
