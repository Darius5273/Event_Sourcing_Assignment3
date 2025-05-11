package com.example.trade_system.services;

import com.example.trade_system.events.Event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventStore {

    private final File file;
    private final ObjectMapper mapper;
    private final ObjectWriter writer;
    private final ObjectReader reader;

    public EventStore(String filename) throws IOException {
        this.file = new File(filename);
        this.mapper = new ObjectMapper();

        this.writer = mapper.writerFor(Event.class);
        this.reader = mapper.readerFor(Event.class);

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public synchronized void append(Event event) throws IOException {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String json = writer.writeValueAsString(event);
            bw.write(json);
            bw.newLine();
        }
    }

    public synchronized List<Event> getAllEvents() throws IOException {
        List<Event> events = new ArrayList<>();
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    Event event = reader.readValue(line);
                    events.add(event);
                } catch (Exception e) {
                    System.err.println("Failed to parse event: " + line);
                    e.printStackTrace();
                }
            }
        }
        return events;
    }
}
