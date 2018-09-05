package com.epam.booking.impl;
import com.epam.booking.IRoomDAO;
import com.epam.booking.entity.RoomTypeEnum;
import com.epam.booking.util.QueryBuilder;

import com.epam.booking.entity.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RoomDAOImpl implements IRoomDAO {

    private SessionFactory factory;

    @Autowired
    public RoomDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public void insertRoom(Room room) {
        if (room != null && room.getType() != null) {
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
            session.close();
        }
    }

    public List<Room> viewRoomsByParams(Map<String, String> params){
        Session session = factory.openSession();
        String queryForSearch = QueryBuilder.createRoomQueryByParams(params);
        Query query = session.createQuery(queryForSearch);
        List<Room> rooms = query.list();
        session.close();
        return rooms;
    }

    public Room viewRoomById(long roomId) {
        Session session = factory.openSession();
        Room room = session.get(Room.class, roomId);
        session.close();
        return room;
    }

    public void updateRoom(Room room) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Room roomFromDB = session.get(Room.class, room.getRoomId());
        roomFromDB.setBedsAmount(room.getBedsAmount());
        roomFromDB.setPrice(room.getPrice());
        roomFromDB.setType(room.getType());
        transaction.commit();
        session.close();
    }

    public void removeRoom(long roomId) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Room room = session.get(Room.class, roomId);
        session.remove(room);
        transaction.commit();
        session.close();
    }
}
