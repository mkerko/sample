package com.epam.booking.dto;

public class UserSocialDTO {
    private String userId;
    private String refreshToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "UserSocialDTO{" +
                "userId='" + userId + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
