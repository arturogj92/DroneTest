package com.advegar.drones;

import java.util.ArrayList;
import java.util.List;

import com.advegar.drones.model.Constants;
import com.advegar.drones.model.Map;
import com.advegar.drones.model.Urbanization;
import com.advegar.drones.service.impl.MapBuilderServiceImpl;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Refactorizar el de vecinos, refactorizar el de rangos, hacer una clase de MapToolsService y el controlador.
		// Habrá un mapa por cada endpoint (de momento 3 5 7 9) o ver que el mapa cambie por parametro /{mapa}/searchid/{id}
		
		
		MapBuilderServiceImpl mapBuilderService = new MapBuilderServiceImpl();
//		List<Urbanization> list = new ArrayList<>();
//		
//		Map m1 = new Map(Constants.MAP_7X7);
//		System.out.println(m1.getArea());
//		list = mapBuilderService.terrainBuilder(m1);
//		m1.setUrbList(list);
		
		
		Map m1 = mapBuilderService.buildMap("");
		

		for(Urbanization u: m1.getUrbList()) {
			System.out.println("id = "+ u.getId()+" | X1: "+ u.getSurface().getCoordX1().getA()+ " - " + u.getSurface().getCoordX1().getB()+ 
												  " | X2: "+ u.getSurface().getCoordX2().getA()+ " - " + u.getSurface().getCoordX2().getB()+ 
												  " | X3: "+ u.getSurface().getCoordX3().getA()+ " - " + u.getSurface().getCoordX3().getB()+
												  " | X4: "+ u.getSurface().getCoordX4().getA()+ " - " + u.getSurface().getCoordX4().getB()+"");
		}
		
		
		for(Urbanization urb : m1.getUrbList()) {
			System.out.println("Id  + "+urb.getId() +" Rango + "+urb.getRange());
		}
		
		
		int numRangos = (int) Math.floor((m1.getSize() / 100) / 2);
		for(int i=0;i<=numRangos;i++) {
		System.out.println();
		System.out.println(" Rango + "+i);
		for(Urbanization urb : m1.getUrbList()) {

			if(urb.getRange()==i) {
				System.out.print(urb.getId()+" - ");
			}

		}
		}
		
		
//		System.out.println("DIRECCIOOON "+mapBuilderService.getByDirection(4, "arriba", m1) );
//		
//		System.out.println();
//		System.out.println("ELEMENTOOO :"+mapBuilderService.getUrbanizacionByCoord(23.00, 324.13, m1));
		
		
	}

}
