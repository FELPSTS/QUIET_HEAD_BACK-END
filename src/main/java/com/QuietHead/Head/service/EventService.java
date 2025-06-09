package com.QuietHead.Head.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository, ServiceQuiet serviceQuiet) {
        this.eventRepository = eventRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public Event searchById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event updateEventById(Long id, Event EventUpdate) {
        Optional<Event> eventExisting = eventRepository.findById(id);
        if (eventExisting .isPresent()) {
            Event event = eventExisting.get();
            event.setId(EventUpdate.getId());

            return eventRepository.save(event);
        } else {
            return null;
        }
    }

    public boolean deleteEventById(Long id) {
        Optional<Event> eventExisting = eventRepository.findById(id);
        if (eventExisting.isPresent()) {
            eventRepository.delete(eventExisting.get());
            return true;
        } else {
            return false;
        }
    }
}
