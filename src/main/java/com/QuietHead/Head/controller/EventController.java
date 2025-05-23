package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event saved = eventService.saveEvent(event);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Event>> listEvents() {
        List<Event> events = eventService.listEvents();
        return ResponseEntity.ok(events);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Event> updateEventById(@PathVariable Long id, @RequestBody Event eventUpdate) {
        Event eventUpdateBanco = eventService.updateEventById(id, eventUpdate);
        if (eventUpdateBanco != null) {
            return ResponseEntity.ok(eventUpdateBanco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteEventById(@PathVariable String id) {
        try {
            long eventId = Long.parseLong(id);
            boolean deleted = eventService.deleteEventById(eventId);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build(); 
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
}


}
