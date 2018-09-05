package com.epam.booking.controller;
import com.epam.booking.IRoomService;
import com.epam.booking.dto.RoomDTO;
import com.epam.booking.entity.Room;
import com.epam.booking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoomController {
    private IRoomService service;

    @Autowired
    public RoomController(IRoomService service) {
        this.service = service;
    }

    @GetMapping(value = "/rooms")
    public List<Room> viewRoomsByCriteria(@RequestParam Map<String,String> allRequestParams) {
        return service.viewRoomsByParameters(allRequestParams);
    }

    @GetMapping(value = "/rooms/{id}")
    public Room viewSingleRoom(@PathVariable Long id) throws ResourceNotFoundException {
        return service.viewRoomById(id);
    }

    @DeleteMapping(value = "/rooms/{id}")
    public void deleteRoom(@PathVariable Long id) {
        service.removeRoom(id);
    }

    @PostMapping(value = "/rooms/add")
    public void addRoom(@RequestBody RoomDTO room) {
        service.insertRoom(room);
    }

    @PostMapping(value = "/rooms/{id}/edit")
    public void editRoom(@PathVariable Long id,  @RequestBody Room room) {
        room.setRoomId(id);
        service.updateRoom(room);
    }
}