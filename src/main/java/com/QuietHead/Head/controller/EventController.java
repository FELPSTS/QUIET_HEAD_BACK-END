package com.QuietHead.Head.controller;
import com.QuietHead.Head.domain.Event;
import com.QuietHead.Head.service.ClienteService;
import com.QuietHead.Head.service.EventService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

}
