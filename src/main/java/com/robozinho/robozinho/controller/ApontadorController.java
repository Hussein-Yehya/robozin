package com.robozinho.robozinho.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robozinho.robozinho.service.ApontadorService;

@RestController
@RequestMapping("api")
public class ApontadorController {

	@Autowired
	private ApontadorService apontadorService;

	@GetMapping
	@RequestMapping("robozinho")
	public ResponseEntity<?> robozinho(@PathParam("category") String category) throws IOException, URISyntaxException {

		this.apontadorService.pegaInformacoes(category);
		return ResponseEntity.ok().build();

	}

}
