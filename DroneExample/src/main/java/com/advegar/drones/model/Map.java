package com.advegar.drones.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Map {

	private double size;
	private double area;

	private List<Urbanization> urbList;

	public Map(double size) {
		this.size = size;
		this.area = size * size;
	}


}