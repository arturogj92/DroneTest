package com.advegar.drones.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.advegar.drones.model.Constants;
import com.advegar.drones.model.Coords;
import com.advegar.drones.model.Map;
import com.advegar.drones.model.Surface;
import com.advegar.drones.model.Urbanization;
import com.advegar.drones.service.MapBuilderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MapBuilderServiceImpl implements MapBuilderService {

	@Override
	public List<Urbanization> terrainBuilder(Map map) {
		List<Urbanization> list = new ArrayList<>();

		int n = 1;
		double size = Constants.URB_SIZE;
		double a = 0.0;
		double b = 0.0;
		double c = Constants.URB_SIZE;
		double d = Constants.URB_SIZE;
		int urbRange = -1;

		for (int i = 1; i <= map.getSize() / Constants.URB_SIZE; i++) {
			for (int j = 1; j <= map.getSize() / Constants.URB_SIZE; j++, n++) {

				urbRange = -1;
				Coords x1 = new Coords(0.00, a);
				Coords x2 = new Coords(size, b);
				Coords x3 = new Coords(0.00, c);
				Coords x4 = new Coords(size, d);

				if (j > 1) {
					x1.setA(x2.getA());
					x1.setB(x2.getB());
					x3.setA(x2.getA());
					x2.setA(x2.getA() + Constants.URB_SIZE);
					x4.setA(x4.getA() + Constants.URB_SIZE);
					size += Constants.URB_SIZE;
				}

				Surface s1 = new Surface(x1, x2, x3, x4);
				Urbanization urb = new Urbanization(n, s1, urbRange);

				list.add(urb);

			}

			a += Constants.URB_SIZE;
			b += Constants.URB_SIZE;
			c += Constants.URB_SIZE;
			d += Constants.URB_SIZE;
			size = Constants.URB_SIZE;

		}
		list = setUrbRanges(map, list);
		return list;

	}

	@Override
	public List<Urbanization> setUrbRanges(Map map, List<Urbanization> urbList) {

		int rangeNumber = (int) Math.floor((map.getSize() / 100) / 2);
		int actualRange = rangeNumber;
		int startRange = rangeNumber + 1;
		int up;
		int down;
		int left;
		int right;

		for (int i = 0; i < rangeNumber; i++) {

			for (Urbanization urb : urbList) {
				up = getAdjacent(urb.getId(), "up", map);
				down = getAdjacent(urb.getId(), "down", map);
				left = getAdjacent(urb.getId(), "left", map);
				right = getAdjacent(urb.getId(), "right", map);

				if (i == 0 && (up == 0 | down == 0 | left == 0 | right == 0)) {
					urb.setRange(actualRange);
				}

				if ((i != 0) && urb.getRange() != rangeNumber
						&& urb.getRange() == -1 && (findUrbById(urbList, up).getRange() == startRange
								|| findUrbById(urbList, down).getRange() == startRange
								|| findUrbById(urbList, left).getRange() == startRange
								|| findUrbById(urbList, right).getRange() == startRange)) {
					urb.setRange(actualRange);
				}

			}
			actualRange--;
			startRange--;

		}

		return urbList;

	}

	@Override
	public Urbanization findUrbById(List<Urbanization> list, int id) {
		for (Urbanization urb : list) {
			if (urb.getId() == id) {
				return urb;
			}
		}
		return null;
	}

	@Override
	public int getUrbanizationByCoord(double x, double y, Map map) {

		for (Urbanization urb : map.getUrbList()) {
			if (x >= urb.getSurface().getCoordX1().getA() && x <= urb.getSurface().getCoordX2().getA()
					&& y >= urb.getSurface().getCoordX1().getB() && y <= urb.getSurface().getCoordX3().getB()) {
				return urb.getId();
			}

		}

		return 0;

	}

	@Override
	public int getAdjacent(int id, String direccion, Map map) {

		if (id <= 0 | id > map.getArea() / Constants.SCALE) {
			return 0;
		}

		int idNeighbor = 0;
		int latSize = (int) (map.getSize() / Constants.URB_SIZE);
		int mapSize = (int) (map.getArea() / Constants.SCALE);

		switch (direccion.toLowerCase()) {

		case "up":
			idNeighbor = id + latSize;
			return idNeighbor <= mapSize ? idNeighbor : 0;

		case "down":
			idNeighbor = id - latSize;
			return idNeighbor > 0 ? idNeighbor : 0;

		case "left":
			idNeighbor = id - 1;
			return ((id - 1) % latSize) != 0 ? idNeighbor : 0;

		case "right":
			idNeighbor = id + 1;
			return (id % latSize) != 0 ? idNeighbor : 0;

		}

		return 0;
	}

	@Override
	public Map buildMap(String type) {

		List<Urbanization> list = new ArrayList<>();
		Map map = null;

		if (type.equals("3x3")) {
			map = new Map(Constants.MAP_3X3);
		}

		else if (type.equals("5x5")) {
			map = new Map(Constants.MAP_5X5);
		}

		else if (type.equals("7x7")) {
			map = new Map(Constants.MAP_7X7);
		}

		else if (type.equals("9x9")) {
			map = new Map(Constants.MAP_9X9);
		}

		if (map != null) {
			list = terrainBuilder(map);
			map.setUrbList(list);
		}

		return map;
	}

	@Override
	public List<Integer> droneList(double coord1, double coord2, Map map, int range) {

		List<Integer> droneList = map.getUrbList().stream().filter(u -> u.getRange() == range).map(Urbanization::getId)
				.collect(Collectors.toList());

		droneList.add(getUrbanizationByCoord(coord1, coord2, map));

		return droneList;
	}

}
