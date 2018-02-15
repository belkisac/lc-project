package com.example.dateproject.controllers;

import com.example.dateproject.models.Event;
import com.example.dateproject.models.data.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@RestController
public class EventController {

    @Autowired
    EventDao eventDao;

    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody List<Event> getEvents() {
        return eventDao.findAll();
    }

}
