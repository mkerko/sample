package com.epam.booking.dto;


import com.epam.booking.entity.RoomTypeEnum;

public class RoomDTO {
    private RoomTypeEnum type;
    private int bedsAmount;
    private int price;

    public RoomTypeEnum getType() {
        return type;
    }

    public void setType(RoomTypeEnum type) {
        this.type = type;
    }

    public int getBedsAmount() {
        return bedsAmount;
    }

    public void setBedsAmount(int bedsAmount) {
        this.bedsAmount = bedsAmount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "type='" + type.name() + '\'' +
                ", bedsAmount=" + bedsAmount +
                ", price=" + price +
                '}';
    }
}
