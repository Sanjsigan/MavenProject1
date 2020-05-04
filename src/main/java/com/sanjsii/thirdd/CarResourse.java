package com.sanjsii.thirdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sanjsii.thirdd.dao.CarDao;
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
		
		List<Car> cars = getCarsFromDB();
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(cars);
		
		return Response
				.status(200)
				.entity(jsonString)
				.build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getACar(@PathParam("id") String id) {
		
		int carid = Integer.parseInt(id);
		
		Car car = getACarFromDB(carid);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(car);
		
		return Response
				.status(200)
				.entity(jsonString)
				.build();
		
	}
	
	//Fetching data from database.
	public List<Car> getCarsFromDB(){
		List<Car> carList = new ArrayList<Car>();
		
		String connURL = "jdbc:mysql://localhost:3306/cardb";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(connURL, "root", "sanjsi");
			
			String sql = "SELECT * FROM car";//Query to be execute
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery(); //Query execution.
			
			while(resultSet.next()) {
				Car car = new Car();
				
				car.setId(resultSet.getInt("id"));
				car.setModel(resultSet.getString("model"));
				car.setBrand(resultSet.getString("brand"));
				car.setYear(resultSet.getInt("year"));
				car.setColor(resultSet.getString("color"));
				car.setType(resultSet.getString("type"));
				
				carList.add(car);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error : "+e.getMessage());
		}
		
		return carList;
		
	}
	
	//Fetching a single data from database.
	public Car getACarFromDB(int id){
		Car car = new Car();
		
		String connURL = "jdbc:mysql://localhost:3306/cardb";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(connURL, "root", "sanjsi");
			
			String sql = "SELECT * FROM car WHERE id = ?";//Query to be execute
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id); //binding the parameter value, 1 is for specify first parameter.
			
			ResultSet resultSet = preparedStatement.executeQuery(); //Query execution.
			
			while(resultSet.next()) {
				
				car.setId(resultSet.getInt("id"));
				car.setModel(resultSet.getString("model"));
				car.setBrand(resultSet.getString("brand"));
				car.setYear(resultSet.getInt("year"));
				car.setColor(resultSet.getString("color"));
				car.setType(resultSet.getString("type"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error : "+e.getMessage());
		}
		
		return car;
		
	}
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void addCar(@FormParam("id") int id,
			@FormParam("model") String model,
							@FormParam("brand") String brand,
							@FormParam("year") int year,
							@FormParam("color") String color,
							@FormParam("type") String type) throws ClassNotFoundException, SQLException {
		
		Car car= new Car();
		car.setId(id);
		car.setModel(model);
		car.setBrand(brand);
		car.setYear(year);
		car.setColor(color);
		car.setType(type);
		
		boolean result = CarDao.getInstance().add(car);
	
	}
	
		
	//Database Simulation.
//	public List<Car> getCars(){
//		List<Car> carList = new ArrayList<Car>();
//		
//		Car car = new Car(1, "520D", "BMW", 2020);
//		carList.add(car);
//		
//		Car car1 = new Car(2, "Axio", "Toyota", 2017);
//		carList.add(car1);
//		
//		Car car2 = new Car(3, "A7", "Audi", 2020);
//		carList.add(car2);
//		
//		
//		return carList;
//	}
	
	 
	

}