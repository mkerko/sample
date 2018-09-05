package com.epam.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ROOMS")
public class Room implements Serializable{

    private long roomId;
    private RoomTypeEnum type;
    private int bedsAmount;
    private int price;
    private Set<Rental> reservations;

    public Room() {
    }

    public Room(long roomId, RoomTypeEnum type, int bedsAmount, int price) {
        this.roomId = roomId;
        this.type = type;
        this.bedsAmount = bedsAmount;
        this.price = price;
    }

    public Room(long roomId, RoomTypeEnum type, int bedsAmount, int price, Set<Rental> reservations) {
        this.roomId = roomId;
        this.type = type;
        this.bedsAmount = bedsAmount;
        this.price = price;
        this.reservations = reservations;
    }

    public Room(RoomTypeEnum type, int bedsAmount, int price) {
        this.type = type;
        this.bedsAmount = bedsAmount;
        this.price = price;
    }

    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Column (name = "TYPE")
    @Enumerated (EnumType.STRING)
    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }

    @Column(name = "BEDS_AMOUNT")
    public int getBedsAmount() {
        return bedsAmount;
    }
    public void setBedsAmount(int bedsAmount) {
        this.bedsAmount = bedsAmount;
    }

    @Column (name = "PRICE")
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", targetEntity = Rental.class)
    public Set<Rental> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Rental> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", type=" + type +
                ", bedsAmount=" + bedsAmount +
                ", price=" + price +
                ", reservations=" + reservations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (roomId != room.roomId) return false;
        if (bedsAmount != room.bedsAmount) return false;
        if (price != room.price) return false;
        return type == room.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (roomId ^ (roomId >>> 32));
        result = 31 * result + type.hashCode();
        result = 31 * result + bedsAmount;
        result = 31 * result + price;
        return result;
    }
}
