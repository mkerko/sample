package com.epam.booking.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.booking.IRoomDAO;
import com.epam.booking.config.TestConfig;
import com.epam.booking.entity.RoomTypeEnum;
import com.epam.booking.util.ColumnSensingReplacementDataSetLoader;
import com.github.springtestdbunit.annotation.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import com.epam.booking.entity.Room;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

import static com.epam.booking.util.ParameterConstant.*;
import static com.epam.booking.util.ParameterConstant.START_PRICE_PARAM;
import static com.epam.booking.util.ParameterConstant.TYPE_PARAM;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
		loader=AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
							TransactionalTestExecutionListener.class,  DirtiesContextTestExecutionListener.class})
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:data/init-data.xml")
@DatabaseTearDown(value = "classpath:data/no-data.xml", type = DatabaseOperation.DELETE_ALL)
@DbUnitConfiguration(dataSetLoader = ColumnSensingReplacementDataSetLoader.class)
public class RoomDAOTest {
	@Autowired
	private IRoomDAO dao;

	private static final long SECOND_ROOM_ID = 2;
	private static final int ZERO_ROOMS = 0;
	private static final String DOUBLE_ROOM_TYPE = "double";

	@Test
	public void shouldReturnOneDoubleRoom() {
		Map<String, String> params = new HashMap<>();
		params.put(TYPE_PARAM, DOUBLE_ROOM_TYPE);
		List<Room> rooms = dao.viewRoomsByParams(params);
		assertEquals(1, rooms.size());
	}

	@Test
	public void shouldReturnOneAvailableRoom() {
		Map<String, String> params = new HashMap<>();
		params.put(TYPE_PARAM, RoomTypeEnum.DOUBLE.name());
		params.put(START_PRICE_PARAM, "10");
		params.put(END_PRICE_PARAM, "50");

		List<Room> rooms = dao.viewRoomsByParams(params);
		assertEquals(1, rooms.size());
	}

	@Test
	@DatabaseSetup(value = "classpath:data/no-data.xml")
	public void shouldReturnNoAvailableRoom() {
		Map<String, String> params = new HashMap<>();
		List<Room> availableRooms = dao.viewRoomsByParams(params);
		assertEquals(ZERO_ROOMS, availableRooms.size());
	}

	@Test
	public void shouldReturnNoRooms() {
		Map<String, String> params = new HashMap<>();
		params.put(TYPE_PARAM, "romantic");
		params.put(START_PRICE_PARAM, "150");

		List<Room> rooms = dao.viewRoomsByParams(params);
		assertEquals(ZERO_ROOMS, rooms.size());
	}

	@Test
	public void shouldReturnTwoAvailableRoomsByDates() {
		Map<String, String> params = new HashMap<>();
		params.put(CHECK_IN_DATE, "2017-08-15");
		params.put(CHECK_OUT_DATE, "2017-09-18");

		List<Room> rooms = dao.viewRoomsByParams(params);
		assertEquals(2, rooms.size());
	}

	@Test
	public void shouldReturnThreeAvailableRoomsByDates() {
		Map<String, String> params = new HashMap<>();
		params.put(CHECK_IN_DATE, "2017-08-10");
		params.put(CHECK_OUT_DATE, "2017-08-18");

		List<Room> rooms = dao.viewRoomsByParams(params);
		assertEquals(3, rooms.size());
	}

	@Test
	public void shouldReturnFourAvailableRoomsByDates(){
		Map<String, String> params = new HashMap<>();
		params.put(CHECK_IN_DATE, "2017-08-10");
		params.put(CHECK_OUT_DATE, "2017-08-15");

		List<Room> rooms = dao.viewRoomsByParams(params);
		assertEquals(4, rooms.size());
	}

	/*--- Testing search room by id ---*/

	@Test
	public void shouldReturnFirstRoom() {
		Room room = dao.viewRoomById(SECOND_ROOM_ID);
		assertEquals(SECOND_ROOM_ID, room.getRoomId());
	}


	/*--- Testing update room  ---*/
	@Test
	public void shouldUpdateRoom(){
		Room room = dao.viewRoomById(SECOND_ROOM_ID);
		room.setBedsAmount(4);
		dao.updateRoom(room);

		Room actualRoom = dao.viewRoomById(SECOND_ROOM_ID);
		assertEquals(4, actualRoom.getBedsAmount());
	}

}
