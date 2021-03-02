package com.example.parkingsystem.parkingsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParkingSystemApplication {

	@Autowired
	AverageParkTimeRepository averageParkTimeRepository;
	
	public static void main(String[] args) throws ParseException {
		SpringApplication.run(ParkingSystemApplication.class, args);

		List<String> fileContent = readFile();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		Map<String, AverageParkTime> vehicleParkingMap = new HashMap<>();
		for (int i=0; i<fileContent.size(); i++) {

			String parkingEntry = fileContent.get(i);
			String[] arr = parkingEntry .split(",");
			Date time = format.parse(arr[0]);
			String vehicleNumber = arr[1];
			String vehicleType = arr[2];
			String entryType = arr[2];
			
			
			if("Entry".equals(entryType) && !vehicleParkingMap.containsKey(vehicleNumber)) {
				AverageParkTime entry = new AverageParkTime(vehicleType, vehicleNumber, 0l, time.getTime(), 0l);
				entry.setTotalParkedTimeInSec(0l);
				vehicleParkingMap.put(vehicleNumber, entry);
			} else if("Entry".equals(entryType) && vehicleParkingMap.containsKey(vehicleNumber)) {
				AverageParkTime entry = vehicleParkingMap.get(vehicleNumber);
				entry.setEntryTime(time.getTime());
			} else if("Exit".equals(entryType) && vehicleParkingMap.containsKey(vehicleNumber)) {
				AverageParkTime entry = vehicleParkingMap.get(vehicleNumber);
				entry.setExitTime(time.getTime());

				Long totalParkedTime = entry.getTotalParkedTimeInSec() + time.getTime() - entry.getEntryTime();
				Long totalParkedTimeInSec = totalParkedTime/1000;
				entry.setTotalParkedTimeInSec(totalParkedTimeInSec );
				
				LocalDate start = Instant.ofEpochMilli(entry.getFistEntryTime()).atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate end = Instant.ofEpochMilli(entry.getExitTime()).atZone(ZoneId.systemDefault()).toLocalDate();
				long daysBetween = Duration.between(start, end).toDays();
				entry.setAverageParkTimeInSec(totalParkedTimeInSec/daysBetween );
			} 
		}

		new ParkingSystemApplication().averageParkTimeRepository.saveAll(vehicleParkingMap.values());
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
