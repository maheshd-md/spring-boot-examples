package com.example.parkingsystem.parkingsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingSystemApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(ParkingSystemApplication.class, args);

		List<String> fileContent = readFile();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		Date startTime = format.parse(fileContent.get(0).split(",")[0]);

		Map<String, AverageParkTime> vehicleParkingMap = new HashMap<>();
		for (int i=0; i<fileContent.size(); i++) {

			String parkingEntry = fileContent.get(i);
			String[] arr = parkingEntry .split(",");
			Date time = format.parse(arr[0]);
			String vehicleNumber = arr[1];
			String vehicleType = arr[2];
			String entryType = arr[2];
			
			
			if("Entry".equals(entryType) && !vehicleParkingMap.containsKey(vehicleNumber)) {
				vehicleParkingMap.put(vehicleNumber, new AverageParkTime(vehicleType, vehicleNumber, 0l, time.getTime(), 0l));
			} else if("Entry".equals(entryType) && vehicleParkingMap.containsKey(vehicleNumber)) {
				AverageParkTime entry = vehicleParkingMap.get(vehicleNumber);
				Long averageParkTimeInSec = entry.getAverageParkTimeInSec() + time.getTime() - entry.getEntryTime();
				entry.setAverageParkTimeInSec(averageParkTimeInSec );
			} else if("Exit".equals(entryType) && vehicleParkingMap.containsKey(vehicleNumber)) {
				AverageParkTime entry = vehicleParkingMap.get(vehicleNumber);
				Long averageParkTimeInSec = entry.getAverageParkTimeInSec() + time.getTime() - entry.getEntryTime();
				entry.setAverageParkTimeInSec(averageParkTimeInSec );
			} 
		}

	}

	private static List<String> readFile() {

		List<String> fileContent = new LinkedList<>();
		try {
			File file = new File(
					"/home/emgda/git/maheshd-md/spring-boot-examples/parking-system/src/main/resources/parking.log");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				fileContent.add(line);
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

}
