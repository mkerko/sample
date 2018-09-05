package com.epam.booking;

import com.epam.booking.dto.RoomDTO;
import com.epam.booking.entity.Room;
import com.epam.booking.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;


public interface IRoomService {

	void insertRoom(RoomDTO room);

	void updateRoom(Room room);

	void removeRoom(long roomId);
	
	List<Room> viewRoomsByParameters(Map<String, String> params);

	Room viewRoomById(long roomId) throws ResourceNotFoundException;
}
