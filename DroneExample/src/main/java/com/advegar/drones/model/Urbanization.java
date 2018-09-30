package com.advegar.drones.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Urbanization {

	private int id;
	private Surface surface;
	private int range;

}
