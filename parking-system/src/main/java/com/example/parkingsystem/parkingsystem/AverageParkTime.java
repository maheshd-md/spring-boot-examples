package com.example.parkingsystem.parkingsystem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AverageParkTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String vehicleType;
	
	private String vehicleNumber;
	
	private Long averageParkTimeInSec;

	
	public AverageParkTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AverageParkTime(String vehicleType, String vehicleNumber, Long averageParkTimeInSec) {
		super();
		this.vehicleType = vehicleType;
		this.vehicleNumber = vehicleNumber;
		this.averageParkTimeInSec = averageParkTimeInSec;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Long getAverageParkTimeInSec() {
		return averageParkTimeInSec;
	}

	public void setAverageParkTimeInSec(Long averageParkTimeInSec) {
		this.averageParkTimeInSec = averageParkTimeInSec;
	}
}