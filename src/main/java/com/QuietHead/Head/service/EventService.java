package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.dto.CreateEventDTO;
import com.QuietHead.Head.dto.EventDetails;
import com.QuietHead.Head.exception.ResourceNotFoundException;
import com.QuietHead.Head.repository.AdministratorRepository;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.repository.EventRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ClientRepository clientRepository;
    private final AdministratorRepository administratorRepository;

    @Autowired
    public EventService(EventRepository eventRepository, 
                       ClientRepository clientRepository,
                       AdministratorRepository administratorRepository) {
        this.eventRepository = eventRepository;
        this.clientRepository = clientRepository;
        this.administratorRepository = administratorRepository;
    }

    @Transactional
    public Event createEvent(CreateEventDTO eventDTO) {
    Administrator admin = administratorRepository.findById(eventDTO.getAdministradorId())
        .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));

    Event event = new Event();
    event.setName(eventDTO.getName());
    event.setLocal(eventDTO.getLocal());
    event.setData(eventDTO.getData());
    event.setAdministrador(admin);
    
    return eventRepository.save(event);
}

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
    }

    @Transactional
    public Event updateEventById(Long id, EventDetails eventDetails) {
        Event existingEvent = getEventById(id);
        
        if (eventDetails.getLocal() != null) {
            existingEvent.setLocal(eventDetails.getLocal());
        }
        if (eventDetails.getData() != null) {
            existingEvent.setData(eventDetails.getData());
        }
        if (eventDetails.getName() != null) {
            existingEvent.setName(eventDetails.getName());
        }
        
        return eventRepository.save(existingEvent);
    }

    @Transactional
    public boolean deleteEventById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Event addParticipantToEvent(Long eventId, Long participantId) {
        Event event = getEventById(eventId);
        Client participant = clientRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado com ID: " + participantId));

        if (!event.getParticipantes().contains(participant)) {
            event.getParticipantes().add(participant);
            return eventRepository.save(event);
        }
        return event;
    }

    @Transactional
    public Event removeParticipantFromEvent(Long eventId, Long participantId) {
        Event event = getEventById(eventId);
        Client participant = clientRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado com ID: " + participantId));

        event.getParticipantes().remove(participant);
        return eventRepository.save(event);
    }

    @Transactional
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event createEvent(EventDetails details, Long administradorId) {
        throw new UnsupportedOperationException("Unimplemented method 'createEvent'");
    }
}