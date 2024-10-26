package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.entity.Event;
import com.ruhuna.reservationsystembackend.repository.EventRepository;
import com.ruhuna.reservationsystembackend.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> getEventByDate(LocalDate date) {
        return eventRepository.findEventsByDate(date);
    }

    @Override
    public void addEvent(String eventName, LocalDate date, MultipartFile photo) throws IOException {
        Event event = new Event();

        event.setTitle(eventName);
        event.setDate(date);

        if (!photo.isEmpty()) {
            String photoName = photo.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + photoName);
            Files.write(filePath, photo.getBytes());
            event.setImageURL(photoName);
        }

        eventRepository.save(event);
    }

    @Override
    public void updateEvent(Long id, String eventName, LocalDate date, MultipartFile photo) throws IOException {
        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setTitle(eventName);
            event.setDate(date);

            if (photo != null && !photo.isEmpty()) {
                String photoName = photo.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + photoName);
                Files.write(filePath, photo.getBytes());
                event.setImageURL(photoName);
            }

            eventRepository.save(event);
        } else {
            throw new IOException("Event not found");
        }
    }

    @Override
    public boolean deleteEvent(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(eventId);
            return true;
        } else {
            return false;
        }
    }
}
