package com.example.dateproject.controllers;

import com.example.dateproject.models.Event;
import com.example.dateproject.models.data.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ListController {

    @Autowired
    EventDao eventDao;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<Event> events() {
        return eventDao.findByStartAfter(LocalDate.now());
    }
}
