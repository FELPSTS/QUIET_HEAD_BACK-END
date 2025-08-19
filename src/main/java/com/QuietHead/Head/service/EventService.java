package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.dto.EventDetails;
import com.QuietHead.Head.dto.EventDetails;
import com.QuietHead.Head.exception.ResourceNotFoundException;
import com.QuietHead.Head.repository.AdministratorRepository;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ClientRepository clientRepository;
    private final AdministratorRepository administratorRepository;

    @Transactional
    public Event createEvent(EventDetails details, Long administradorId) {
        Administrator admin = administratorRepository.findById(administradorId)
            .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado com ID: " + administradorId));

        Event event = new Event();
        event.setTitle(details.getTitle());
        event.setDescription(details.getDescription());
        event.setImageUrl(details.getImageUrl());
        event.setLocal(details.getLocal());
        event.setData(details.getData());
        event.setAdministrador(admin);

        return eventRepository.save(event);
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com ID: " + id));
    }

    @Transactional
    public Event updateEventById(Long id, EventDetails eventDetails) {
        Event existingEvent = getEventById(id);

        if (eventDetails.getTitle() != null) {
            existingEvent.setTitle(eventDetails.getTitle());
        }
        if (eventDetails.getDescription() != null) {
            existingEvent.setDescription(eventDetails.getDescription());
        }
        if (eventDetails.getImageUrl() != null) {
            existingEvent.setImageUrl(eventDetails.getImageUrl());
        }
        if (eventDetails.getLocal() != null) {
            existingEvent.setLocal(eventDetails.getLocal());
        }
        if (eventDetails.getData() != null) {
            existingEvent.setData(eventDetails.getData());
        }

        return eventRepository.save(existingEvent);
    }

    @Transactional
    public void deleteEventById(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento não encontrado com ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Transactional
    public Event addParticipantToEvent(Long eventId, Long participantId) {
        Event event = getEventById(eventId);
        Client participant = clientRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado com ID: " + participantId));

        if (!event.getParticipantes().contains(participant)) {
            event.getParticipantes().add(participant);
        }
        return eventRepository.save(event);
    }

    @Transactional
    public Event removeParticipantFromEvent(Long eventId, Long participantId) {
        Event event = getEventById(eventId);
        Client participant = clientRepository.findById(participantId)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado com ID: " + participantId));

        event.getParticipantes().remove(participant);
        return eventRepository.save(event);
    }
}