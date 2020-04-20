package com.sanjsii.thirdd;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sanjsii.thirdd.model.Car;
import com.google.gson.Gson;

@Path("cars")
public class CarResourse {
	
	
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public String test() {
//		return "Yes Working!";
//	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		//Jackson
		//GSON
		
		List<Car> cars = getCars();
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(cars);
		
		return Response
				.status(200)
				.entity(jsonString)
				.build();
		
	}
	
	
	//Database Simulation.
	public List<Car> getCars(){
		List<Car> carList = new ArrayList<Car>();
		
		Car car = new Car(1, "520D", "BMW", 2020);
		carList.add(car);
		
		Car car1 = new Car(2, "Axio", "Toyota", 2017);
		carList.add(car1);
		
		Car car2 = new Car(3, "A7", "Audi", 2020);
		carList.add(car2);
		
		
		return carList;
	}

}

