package com.netpad.main.entities;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Note {
    public String subject;
    public String content;

    public Note(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public Note() {
        this.subject = "";
        this.content = "";
    }

    @Override
    public String toString() {
        Map<String, String> note = new HashMap<String, String>();
        note.put("Subject", this.subject);
        note.put("Content", this.content);
        String noteObj = "";
        try {
            noteObj = new ObjectMapper().writeValueAsString(note);
        } catch(JsonProcessingException e) {
            noteObj = e.toString();
            e.printStackTrace();
        }
        return noteObj;
    }
}