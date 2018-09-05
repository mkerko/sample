package com.epam.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS_SOCIAL")
public class UserSocial implements Serializable {
    private long userId;
    private String socialUserId;
    private String refreshToken;
    private Set<Rental> reservations;

    public UserSocial() {
    }

    public UserSocial(long userId, String socialUserId, String refreshToken) {
        this.userId = userId;
        this.socialUserId = socialUserId;
        this.refreshToken = refreshToken;
    }

    public UserSocial(String socialUserId, String refreshToken) {
        this.socialUserId = socialUserId;
        this.refreshToken = refreshToken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "SOCIAL_USER_ID", nullable = false)
    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    @Column(name = "REFRESH_TOKEN", nullable = false)
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userSocial", targetEntity = Rental.class)
    @JsonIgnore
    public Set<Rental> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Rental> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "UserSocial{" +
                "userId='" + userId + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
