package com.advegar.drones.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advegar.drones.model.Map;
import com.advegar.drones.model.Urbanization;
import com.advegar.drones.service.MapBuilderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class MapController {

	private MapBuilderService mapBuilderService;

	@Autowired
	ObjectMapper mapper;

	@GetMapping("/{mapType}/findUrb/")
	public ResponseEntity<ObjectNode> getUrbanizationByCoord(@PathVariable String mapType, @RequestParam double coord1,
			@RequestParam double coord2) {

		ObjectNode objectNode = mapper.createObjectNode();
		Map map = mapBuilderService.buildMap(mapType);
		if (map == null) {
			objectNode.put("Error", "Map " + mapType + " is not supported. Try 3x3, 5x5, 7x7 or 9x9");
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}
		int urbId = 0;

		urbId = mapBuilderService.getUrbanizationByCoord(coord1, coord2, map);

		objectNode.put("Map size", mapType);

		if (mapBuilderService.getUrbanizationByCoord(coord1, coord2, map)==0) {
			objectNode.put("Error", "The coords "+coord1+ " - "+coord2+" does not belong to any urbanization");
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}
		
		else if (urbId == 0) {
			objectNode.put("Urbanization-ID", "Urbanization not found");
		} else {
			objectNode.put("Urbanization-ID", urbId);
		}
		return new ResponseEntity<>(objectNode, HttpStatus.OK);
	}

	// hacer metodos que falten del main para que esten aqui (los 3)
	// ver si poner elde pintar el mapa.
	// revisar todo codigo
	// preparar documentacionl

	@GetMapping("/{mapType}/findAdjacent/{id}/{action}")
	public ResponseEntity<ObjectNode> getAdjacent(@PathVariable String mapType, @PathVariable String action,
			@PathVariable int id) {

		ObjectNode objectNode = mapper.createObjectNode();
		Map map = mapBuilderService.buildMap(mapType);
		if (map == null) {
			objectNode.put("Error", "Map " + mapType + " is not supported. Try 3x3, 5x5, 7x7 or 9x9");
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}

		int adjacent = mapBuilderService.getAdjacent(id, action, map);

		if (adjacent == 0) {

			objectNode.put("Error",
					"The urbanization with ID " + id + " does not have adjacent in direction " + action.toLowerCase());
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}

		else {
			objectNode.put("Actual ID", id);
			objectNode.put("Action", action.toUpperCase());
			objectNode.put("Adjacent ID", adjacent);
		}

		return new ResponseEntity<>(objectNode, HttpStatus.OK);

	}

	@GetMapping("/{mapType}/getmap")
	public ResponseEntity<?> getMap(@PathVariable String mapType) {

		ArrayNode arrayNode = mapper.createArrayNode();
		ObjectNode objectNode = mapper.createObjectNode();
		Map map = mapBuilderService.buildMap(mapType);
		if (map == null) {
			objectNode.put("Error", "Map " + mapType + " is not supported. Try 3x3, 5x5, 7x7 or 9x9");
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}

		for (Urbanization u : map.getUrbList()) {

			ObjectNode objectNode2 = mapper.createObjectNode();
			objectNode2.put("Id", u.getId());
			objectNode2.put("Coord X1",
					u.getSurface().getCoordX1().getA() + " - " + u.getSurface().getCoordX1().getB());
			objectNode2.put("Coord X2",
					u.getSurface().getCoordX2().getA() + " - " + u.getSurface().getCoordX2().getB());
			objectNode2.put("Coord X3",
					u.getSurface().getCoordX3().getA() + " - " + u.getSurface().getCoordX3().getB());
			objectNode2.put("Coord X4",
					u.getSurface().getCoordX4().getA() + " - " + u.getSurface().getCoordX4().getB());
			objectNode2.put("Range", u.getRange());
			arrayNode.add(objectNode2);
		}

		return new ResponseEntity<ArrayNode>(arrayNode, HttpStatus.OK);

	}

	// comprobracion de que esta fuera getUrbanizacionByCoord
	@GetMapping("/{mapType}/geturbanizations/")
	public ResponseEntity<?> dronePath(@PathVariable String mapType, @RequestParam double coord1,
			@RequestParam double coord2, @RequestParam int range) {

		ArrayNode arrayNode = mapper.createArrayNode();
		ObjectNode objectNode = mapper.createObjectNode();
		Map map = mapBuilderService.buildMap(mapType);

		if (map == null) {
			objectNode.put("Error", "Map " + mapType + " is not supported. Try 3x3, 5x5, 7x7 or 9x9");
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}

		int rangeNumber = (int) Math.floor((map.getSize() / 100) / 2);

		
		
		if (range > rangeNumber || range == 0) {
			objectNode.put("Error", "The range must be between 0 and " + rangeNumber);
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}
		
		if (mapBuilderService.getUrbanizationByCoord(coord1, coord2, map)==0) {
			objectNode.put("Error", "The coords "+coord1+ " - "+coord2+" does not belong to any urbanization");
			return new ResponseEntity<>(objectNode, HttpStatus.BAD_REQUEST);
		}
		
		List<Integer> dronePath = new ArrayList<Integer>();

		dronePath = mapBuilderService.droneList(coord1, coord2, map, rangeNumber);
		
		
		return new ResponseEntity<>(dronePath, HttpStatus.OK);

	}

}
