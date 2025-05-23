package com.QuietHead.Head.controller;
import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.service.ClienteService;
import com.QuietHead.Head.service.EventService;
import org.apache.catalina.connector.Response;
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
    public ResponseEntity<Event> criarEvent(@RequestBody Event event) {
        Event criado = eventService.salvarEvent(event);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<Event>> listarEvents() {
        List<Event> events = eventService.listarEvents();
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deletarEventPorId(@PathVariable String id) {
        try {
            long eventId = Long.parseLong(id);
            boolean deletado = eventService.deletarPorId(eventId);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build(); 
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
}


}
