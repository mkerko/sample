package com.epam.booking.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "RENTALS")
public class Rental implements Serializable{

    private long rentalId;

    private User user;

    private UserSocial userSocial;

    private Room room;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    public Rental() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RENTAL_ID")
    public long getRentalId() {
        return rentalId;
    }

    public void setRentalId(long rentalId) {
        this.rentalId = rentalId;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", updatable = false)
    public Room getRoom() {
        return room;
    }

    @ManyToOne
    @JoinColumn(name = "SOCIAL_USER_ID", updatable = false)
    public UserSocial getUserSocial() {
        return userSocial;
    }

    public void setUserSocial(UserSocial userSocial) {
        this.userSocial = userSocial;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Column(name = "CHECK_IN_DATE", insertable = false, updatable= false)
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    @Column(name = "CHECK_IN_DATE", insertable = false, updatable= false)
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

}