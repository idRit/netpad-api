package com.netpad.main.controllers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netpad.main.entities.Note;
import com.netpad.main.repositories.NoteRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
	
	NoteRepository noteRepository = NoteRepository.getInstance();

	@GetMapping("/")
	public String index() {
		Map<String, String> operation = new HashMap<String, String>();
		operation.put("operation", "success");
		String json = "";
		try {
			json = new ObjectMapper().writeValueAsString(operation);
		} catch(JsonProcessingException e) {
			json = e.toString();
			e.printStackTrace();
		}
		return json;
	}

	@PostMapping("/api/postNote")
	public Note postNote(@RequestBody Map<String, String> body) {
		String subject = body.get("Subject");
		String content = body.get("Content");

		Note note = new Note(subject, content);
		
		if (noteRepository.insertNote(note)) {
			return note;
		}
		System.out.println(note.toString());
		return note;
	}

	@GetMapping("/api/getNote/{noteSubject}")
    public String show(@PathVariable String noteSubject){
		Note note = noteRepository.getNoteBySubject(noteSubject);
		if (note.subject.equals("")) {
			Map<String, String> operation = new HashMap<String, String>();
			operation.put("operation", "note not found");
			String json = "";
			try {
				json = new ObjectMapper().writeValueAsString(operation);
			} catch(JsonProcessingException e) {
				json = e.toString();
				e.printStackTrace();
			}
			return json;
		}
        return note.toString();
    }
	
	@DeleteMapping("/api/getNote/{noteSubject}")
	public String delete(@PathVariable String noteSubject) {
		Map<String, String> operation = new HashMap<String, String>();
		if (noteRepository.deleteNote(noteSubject)) {
			operation.put("operation", "successful");
		} else {
			operation.put("operation", "not successful");
		}
		String json = "";
		try {
			json = new ObjectMapper().writeValueAsString(operation);
		} catch(JsonProcessingException e) {
			json = e.toString();
			e.printStackTrace();
		}
		return json;
	}
}

