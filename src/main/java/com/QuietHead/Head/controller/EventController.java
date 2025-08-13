package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.dto.EventDetails;
import com.QuietHead.Head.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventDetails details,
            @RequestParam Long administradorId) {
        
        Event createdEvent = eventService.createEvent(details, administradorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.listEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventDetails eventDetails) {
        
        Event updatedEvent = eventService.updateEventById(id, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Event> addParticipant(
            @PathVariable Long eventId,
            @PathVariable Long participantId) {
        
        Event event = eventService.addParticipantToEvent(eventId, participantId);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable Long eventId,
            @PathVariable Long participantId) {
        
        eventService.removeParticipantFromEvent(eventId, participantId);
        return ResponseEntity.noContent().build();
    }
}