package com.epam.booking;

import static com.epam.booking.util.ParameterConstant.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.epam.booking.entity.Room;
import com.epam.booking.impl.RoomServiceImpl;
import com.epam.booking.entity.RoomTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {
	private static final long FIRST_ROOM_ID = 1;
	private Room firstRoom = new Room(1, RoomTypeEnum.ONE_BED, 1, 16);
	private Room secondRoom = new Room(2, RoomTypeEnum.DOUBLE, 2, 100);

	@Mock
	private IRoomDAO dao;

    private IRoomService service;

	@Before
	public void setUp() {
		service = new RoomServiceImpl(dao);
	}

	@Test
	public void shouldReturnTwoRooms(){
		List<Room> rooms = new ArrayList<>();
		rooms.add(firstRoom);
		rooms.add(secondRoom);

	    when(dao.viewRoomsByParams(new HashMap<>())).thenReturn(rooms);
	    assertEquals(rooms, service.viewRoomsByParameters(new HashMap<>()));
	    verify(dao, times(1)).viewRoomsByParams(new HashMap<>());
	}

	@Test
	public void shouldReturnOneRoomByCriteria() {
		List<Room> rooms = new ArrayList<>();
		rooms.add(secondRoom);

		Map<String, String> map = new HashMap<>();
		map.put(TYPE_PARAM, RoomTypeEnum.LUX.name());
		map.put(START_PRICE_PARAM, "90");

		when(dao.viewRoomsByParams(map)).thenReturn(rooms);
		assertEquals(rooms, service.viewRoomsByParameters(map));
		verify(dao, times(1)).viewRoomsByParams(map);
	}

	@Test
	public void shouldReturnAllRoomsWithoutCriteria() {
		List<Room> rooms = new ArrayList<>();
		rooms.add(firstRoom);
		rooms.add(secondRoom);
		Map<String, String> map = new HashMap<>();

		when(service.viewRoomsByParameters(map)).thenReturn(rooms);

		assertEquals(rooms, service.viewRoomsByParameters(map));
		verify(dao, times(1)).viewRoomsByParams(map);
	}
	
	@Test
	public void shouldReturnFirstRoom() throws Exception{
		when(dao.viewRoomById(FIRST_ROOM_ID)).thenReturn(firstRoom);

	    Room roomFromDb = service.viewRoomById(FIRST_ROOM_ID);
	    assertNotNull(roomFromDb);
	    assertEquals(firstRoom, roomFromDb);
	    assertEquals(firstRoom.getType(), roomFromDb.getType());
	}
	
	@Test
	public void shouldReturnTwoRoomsByPrice() {
		List<Room> rooms = new ArrayList<>();
		rooms.add(firstRoom);
		rooms.add(secondRoom);

		Map<String, String> params = new HashMap<>();
		params.put(START_PRICE_PARAM, "10");
		params.put(END_PRICE_PARAM, "40");

	    when(service.viewRoomsByParameters(params)).thenReturn(rooms);

	    assertEquals(rooms, service.viewRoomsByParameters(params));
	    verify(dao, times(1)).viewRoomsByParams(params);
	}

}	
