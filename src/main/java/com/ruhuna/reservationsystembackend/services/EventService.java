package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.EventDto;
import com.ruhuna.reservationsystembackend.entity.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<Event> findAll();
    List<Event> getEventByDate(LocalDate date);
    void addEvent(String eventName, LocalDate date, MultipartFile photo) throws IOException;
    void updateEvent(Long id, String eventName, LocalDate date, MultipartFile photo) throws IOException;
    boolean deleteEvent(Long eventId);

}
