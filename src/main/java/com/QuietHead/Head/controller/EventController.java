package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event saved = eventService.saveEvent(event);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Event>> listEvents() {
        List<Event> events = eventService.listEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = Optional.ofNullable(eventService.getEventById(id));
        return event.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEventById(@PathVariable Long id,
                                                 @RequestBody Event eventUpdate) {
        Optional<Event> updated = Optional.ofNullable(eventService.updateEventById(id, eventUpdate));
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long id) {
        boolean deleted = eventService.deleteEventById(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
