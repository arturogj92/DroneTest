package com.advegar.drones.service;

import java.util.List;

import com.advegar.drones.model.Map;
import com.advegar.drones.model.Urbanization;

public interface MapBuilderService {

	public List<Urbanization> terrainBuilder(Map map);

	public List<Urbanization> setUrbRanges(Map map, List<Urbanization> urbList);

	public Urbanization findUrbById(List<Urbanization> list, int id);

	public int getUrbanizationByCoord(double x, double y, Map map);

	public int getAdjacent(int id, String direccion, Map map);

	public Map buildMap(String type);

	public List<Integer> droneList(double coord1, double coord2, Map map, int range);

}