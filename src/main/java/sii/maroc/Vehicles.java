package sii.maroc;

import java.io.StringWriter;

public class Vehicles {

	private float diesel;
	private float fuel;
	private float hybrid;
	
	public Vehicles(String percents) {
		String[] percentsList = percents.split(",");
		
		String dieselString = percentsList[0].split(":")[1].split("%")[0];
		diesel = Float.valueOf(dieselString);
		
		String fuelString = percentsList[1].split(":")[1].split("%")[0];
		fuel = Float.valueOf(fuelString);
		
		String hybridString = percentsList[2].split(":")[1].split("%")[0];
		hybrid = Float.valueOf(hybridString);
		
	}

	public String move(String vehicle, String type, String doors, String speed) {
		StringWriter response = new StringWriter();
	
		int speedValue = Integer.valueOf(speed.split(" ")[0]);
		float consumeValue = 0;
		
		String doorsStatus = checkDoors(vehicle, doors);
		response.append(doorsStatus);
		
		if(doorsStatus.equals("DOORS KO,")) {
			response.append(getDoorsView(vehicle, doors.split(" ")));
			return response.toString();
		}
		
		if(speedValue != 0)
			response.append(" MOVING.");
		
		consumeValue = getConsumeValue(type, speedValue);
		
		response.append(" The "+vehicle+" will consume " +String.format("%.2f", consumeValue).replaceFirst(",", ".") + " L");
		
		return response.toString();
	}

	private String getDoorsView(String vehicle, String[] closedDoors) {
		StringWriter response = new StringWriter();
		
		response.append(" BLOCKED \n"+
                "  _\n");
		if(vehicle.equals("CAR")) {
			if(isDoorClosed(closedDoors, 1))
				response.append(" | ");
			else
				response.append(" / ");
			if(isDoorClosed(closedDoors, 2))
				response.append("|\n");
			else
				response.append("\\\n");
			if(isDoorClosed(closedDoors, 3))
				response.append(" |_");
			else
				response.append(" /_");
			if(isDoorClosed(closedDoors, 4))
				response.append("|");
			else
				response.append("\\");
			
			return response.toString();
		}
		
		if(vehicle.equals("TRUCK")) {
			if(isDoorClosed(closedDoors, 1))
				response.append(" |_");
			else
				response.append(" /_");
			if(isDoorClosed(closedDoors, 2))
				response.append("|");
			else
				response.append("\\");
			
			return response.toString();
		}
		
		return null;
	}

	private boolean isDoorClosed(String[] closedDoors, int i) {
		for (String string : closedDoors) {
			if(Integer.valueOf(string) == i)
				return true;
		}
		return false;
		
	}

	private String checkDoors(String vehicle, String doors) {
		int closedDoors = doors.split(" ").length;
		if(vehicle.equals("CAR")) {
			if(closedDoors == 4)
				return "DOORS OK,";
			else 
				return "DOORS KO,";
		}
		if(vehicle.equals("TRUCK")) {
			if(closedDoors == 2)
				return "DOORS OK,";
			else 
				return "DOORS KO,";
		}
		if(vehicle.equals("MOTORCYCLE"))
			return "DOORS OK,";
		
		return null;
	}

	private float getConsumeValue(String type, int speedValue) {
		if(type.equals("Diesel")) 
			return (diesel/100) *  speedValue;
		if(type.equals("Fuel")) 
			return (fuel/100) *  speedValue;
		if(type.equals("Hybrid")) 
			return (hybrid/100) *  speedValue;
		
		return -1;
	}

}
