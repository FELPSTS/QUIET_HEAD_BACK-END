package com.QuietHead.Head.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.repository.EventRepository;
import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;




@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ServiceQuiet serviceQuiet;

    @Autowired
    public EventService(EventRepository eventRepository, ServiceQuiet serviceQuiet) {
        this.eventRepository = eventRepository;
        this.serviceQuiet = serviceQuiet;
    }

    public Event salvarEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> listarEvents() {
        return eventRepository.findAll();
    }

    public Event buscarPorId(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public boolean deletarPorId(Long id) {
        Optional<Event> eventExistente = eventRepository.findById(id);
        if (eventExistente.isPresent()) {
            eventRepository.delete(eventExistente.get());
            return true;
        } else {
            return false;
        }
    }

    @PostConstruct
    public void testarConexaoNeo4j() {
        try {
            List<Event> events = eventRepository.findAll();
            System.out.println("✅ Conexão com o Neo4j funcionando. Total de clientes: " + events.size());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com o Neo4j: " + e.getMessage());
        }
    }
}
