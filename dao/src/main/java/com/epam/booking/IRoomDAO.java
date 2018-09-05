package com.epam.booking;
import com.epam.booking.entity.Room;

import java.util.List;
import java.util.Map;

public interface IRoomDAO {

	void insertRoom(Room room);

	List<Room> viewRoomsByParams(Map<String, String> params);
	
	Room viewRoomById(long roomId);

	void updateRoom(Room room);

	void removeRoom(long roomId);

}
