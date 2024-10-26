package com.ruhuna.reservationsystembackend.controller;

import com.ruhuna.reservationsystembackend.entity.Event;
import com.ruhuna.reservationsystembackend.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<String> saveEvent(
            @RequestParam("title") String eventName,
            @RequestParam("date") String date,
            @RequestParam("imageURL") MultipartFile photo) {

        try {
            eventService.addEvent(eventName, LocalDate.parse(date), photo);
            return ResponseEntity.ok("Event saved successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving event");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEvent(
            @PathVariable Long id,
            @RequestParam("title") String eventName,
            @RequestParam("date") String date,
            @RequestParam(value = "imageURL", required = false) MultipartFile photo) {

        try {
            eventService.updateEvent(id, eventName, LocalDate.parse(date), photo);
            return ResponseEntity.ok("Event updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error updating event");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        boolean isDeleted = eventService.deleteEvent(id);
        if (isDeleted) {
            return ResponseEntity.ok("Event deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Event not found");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/get/{date}")
    public ResponseEntity<List<Event>> getEventByDate(@PathVariable LocalDate date) {
        List<Event> event = eventService.getEventByDate(date);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
