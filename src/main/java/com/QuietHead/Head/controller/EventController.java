package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.dto.EventDetails;
import com.QuietHead.Head.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventDetails details,
            @RequestParam Long administradorId) {
        
        log.info("Creating event: {} by administrator ID: {}", details.getTitle(), administradorId);

        Event createdEvent = eventService.createEvent(details, administradorId);

        log.info("Event created successfully with ID: {}", createdEvent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        log.debug("Fetching all events");
        List<Event> events = eventService.listEvents();
        log.debug("Found {} events", events.size());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        log.debug("Fetching event by ID: {}", id);
        
        Event event = eventService.getEventById(id);
        log.debug("Event found: {}", id);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventDetails eventDetails) {
        
        log.info("Updating event with ID: {}", id);
        
        Event updatedEvent = eventService.updateEventById(id, eventDetails);
        
        log.info("Event updated successfully: {}", id);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.info("Deleting event with ID: {}", id);
        
        eventService.deleteEventById(id);
        
        log.info("Event deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Event> addParticipant(
            @PathVariable Long eventId,
            @PathVariable Long participantId) {
        
        log.info("Adding participant {} to event {}", participantId, eventId);
        
        Event event = eventService.addParticipantToEvent(eventId, participantId);
        
        log.info("Participant {} added successfully to event {}", participantId, eventId);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}/participants/{participantId}")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable Long eventId,
            @PathVariable Long participantId) {
        
        log.info("Removing participant {} from event {}", participantId, eventId);
        
        eventService.removeParticipantFromEvent(eventId, participantId);
        
        log.info("Participant {} removed successfully from event {}", participantId, eventId);
        return ResponseEntity.noContent().build();
    }
}