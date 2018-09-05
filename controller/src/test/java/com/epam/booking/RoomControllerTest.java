package com.epam.booking;

import com.epam.booking.controller.ErrorController;
import com.epam.booking.controller.RoomController;
import com.epam.booking.entity.Room;
import com.epam.booking.entity.RoomTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.booking.util.ParameterConstant.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerTest {
    private static final String ENCODING = "UTF-8";
    private Room firstRoom = new Room(1, RoomTypeEnum.ONE_BED, 1, 16);
    private Room secondRoom = new Room(2, RoomTypeEnum.DOUBLE, 2, 40);
    private Room thirdRoom = new Room(3, RoomTypeEnum.TRIPLE, 3, 54);
    private Room fourthRoom = new Room(4, RoomTypeEnum.LUX, 2, 60);

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName(ENCODING));

    private MockMvc mockMvc;

    @Mock
    private IRoomService service;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RoomController(service))
                .setControllerAdvice(new ErrorController())
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void shouldReturnTwoRoomsByPrice() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put(START_PRICE_PARAM, "10");
        params.put(END_PRICE_PARAM, "40");

        List<Room> rooms = new ArrayList<>();
        rooms.add(firstRoom);
        rooms.add(secondRoom);

        when(service.viewRoomsByParameters(params)).thenReturn(rooms);

        mockMvc.perform(get("/rooms").param(START_PRICE_PARAM, "10")
                .param(END_PRICE_PARAM, "40"))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        verify(service, times(1)).viewRoomsByParameters(params);
    }

    @Test
    public void shouldReturnOneLuxRoom() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put(TYPE_PARAM, RoomTypeEnum.LUX.name());

        List<Room> rooms = new ArrayList<>();
        rooms.add(fourthRoom);

        when(service.viewRoomsByParameters(params)).thenReturn(rooms);

        mockMvc.perform(get("/rooms").param(TYPE_PARAM, RoomTypeEnum.LUX.name()))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].roomId", is(4)));
        verify(service, times(1)).viewRoomsByParameters(params);
    }

    @Test
    public void shouldReturnFourRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        rooms.add(firstRoom);
        rooms.add(secondRoom);
        rooms.add(thirdRoom);
        rooms.add(fourthRoom);

        when(service.viewRoomsByParameters(new HashMap<>())).thenReturn(rooms);

        mockMvc.perform(get("/rooms")).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    public void shouldReturnFirstRoom() throws Exception {
        when(service.viewRoomById(1)).thenReturn(firstRoom);

        mockMvc.perform(get("/rooms/1")).andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId", is(1)));
        verify(service, times(1)).viewRoomById(anyInt());
    }

}
