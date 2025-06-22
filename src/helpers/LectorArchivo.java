package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.*;

import java.io.File;
import java.util.List;

public class LectorArchivo {
	public static List<Cofre> cofres(String path) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return List.of(mapper.readValue(new File(path), Cofre[].class));
	}
	
	public static List<Robopuerto> robopuertos(String path) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return List.of(mapper.readValue(new File(path), Robopuerto[].class));
	}
	
	public static List<Robot> robots(String path) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return List.of(mapper.readValue(new File(path), Robot[].class));
	}
}