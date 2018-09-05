package com.epam.booking.impl;
import java.util.List;
import java.util.Map;

import com.epam.booking.IRoomDAO;
import com.epam.booking.IRoomService;
import com.epam.booking.dto.RoomDTO;
import com.epam.booking.entity.Room;
import com.epam.booking.exception.ResourceNotFoundException;
import com.epam.booking.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements IRoomService {

	private IRoomDAO dao;

	@Autowired
	public RoomServiceImpl(IRoomDAO dao) {
		this.dao = dao;
	}

	public void insertRoom(RoomDTO dto) {
		Room room = EntityConverter.convertDtoToRoomEntity(dto);
		dao.insertRoom(room);
	}

	public void updateRoom(Room room) {
		dao.updateRoom(room);
	}

	public void removeRoom(long roomId) {
		dao.removeRoom(roomId);
	}

	public List<Room> viewRoomsByParameters(Map<String, String> params){
		return dao.viewRoomsByParams(params);
	}
	
	public Room viewRoomById(long roomId) throws ResourceNotFoundException{
		Room room = dao.viewRoomById(roomId);
		if (room == null) {
			throw new ResourceNotFoundException("Resource not found.");
		}
		return room;
	}

}
