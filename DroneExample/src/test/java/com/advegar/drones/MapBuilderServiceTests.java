package com.advegar.drones;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.advegar.drones.controller.MapController;
import com.advegar.drones.model.Map;
import com.advegar.drones.model.Urbanization;
import com.advegar.drones.service.MapBuilderService;
import com.advegar.drones.service.impl.MapBuilderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MapBuilderServiceTests {

	@InjectMocks
	private MapController mapController;

	@Mock
	private MapBuilderService mapBuilderService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(mapController).build();
		
		
	}

	@Test
	public void checkGetUrbanizationByCoord_OK() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("3x3");
		Assert.assertEquals(mapBuilder.getUrbanizationByCoord(133.3, 22.2, map), 2);

	}

	@Test
	public void checkGetUrbanizationByCoord_KO() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("3x3");
		Assert.assertEquals(mapBuilder.getUrbanizationByCoord(133.3, 22.2, map), 2);

	}

	@Test
	public void checkTerrainBuilder() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("5x5");
		List<Urbanization> urbs = mapBuilder.terrainBuilder(map);
		Assert.assertEquals(urbs.get(0).getId(), 1);

	}

	@Test
	public void checkSetRanges_OK() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("5x5");
		List<Urbanization> urbs = mapBuilder.setUrbRanges(map, map.getUrbList());
		Assert.assertEquals(urbs.get(0).getRange(), 2);

	}

	@Test
	public void checkSetRanges_KO() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("5x5");
		List<Urbanization> urbs = mapBuilder.setUrbRanges(map, map.getUrbList());
		Assert.assertNotEquals(urbs.get(0).getRange(), 3);

	}

	@Test
	public void checkGetAdjacents_OK() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("5x5");
		Assert.assertEquals(mapBuilder.getAdjacent(3, "right", map), 4);

	}

	@Test
	public void checkGetAdjacents_KO() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("5x5");
		Assert.assertNotEquals(mapBuilder.getAdjacent(3, "right", map), 2);

	}

	@Test
	public void checkDroneList_OK() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("3x3");

		List<Integer> droneList = new ArrayList<Integer>();
		droneList.add(1);
		droneList.add(2);
		droneList.add(3);
		droneList.add(4);
		droneList.add(6);
		droneList.add(7);
		droneList.add(8);
		droneList.add(9);
		droneList.add(5);

		Assert.assertEquals(mapBuilder.droneList(136.12, 121.37, map, 1), droneList);

	}

	@Test
	public void checkDroneList_KO() {

		MapBuilderServiceImpl mapBuilder = new MapBuilderServiceImpl();
		Map map = mapBuilder.buildMap("3x3");

		List<Integer> droneList = new ArrayList<Integer>();
		droneList.add(1);
		droneList.add(2);
		droneList.add(3);
		droneList.add(4);
		droneList.add(6);
		droneList.add(7);
		droneList.add(8);
		droneList.add(9);

		Assert.assertNotEquals(mapBuilder.droneList(136.12, 121.37, map, 1), droneList);

	}



}
