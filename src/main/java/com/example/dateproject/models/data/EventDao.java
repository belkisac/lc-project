package com.example.dateproject.models.data;

import com.example.dateproject.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface EventDao extends CrudRepository<Event, Integer> {

    List<Event> findAll();

    List<Event> findByStartAfter(LocalDate startDate);
}
