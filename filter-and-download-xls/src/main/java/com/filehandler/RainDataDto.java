package com.filehandler;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RainDataDto {

	@JsonProperty("Start Character")
	public String startChar;
	
	@JsonProperty("StationID")
	public Long stationId;
	
	@JsonProperty("Date")
	@JsonFormat(pattern="dd/mm/yyyy")
	public Date date;
	
	@JsonProperty("Time")
	public String time;
	
	@JsonProperty("Daily Rain")
	public Long dailyRain;
	
	@JsonProperty("Total Rain")
	public Long totalRain;
	
	@JsonProperty("Rain Alarm")
	public Long rainAlarm;
	
	@JsonProperty("Internal Temperature")
	public Double internalTemprature;
	
	@JsonProperty("Battery Voltage")
	public Double batteryVoltage;
	
	@JsonProperty("Powewr Fail")
	public Long powerFail;
	
	@JsonProperty("End Character")
	public String endChar;

	public String getStartChar() {
		return startChar;
	}

	public void setStartChar(String startChar) {
		this.startChar = startChar;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getDailyRain() {
		return dailyRain;
	}

	public void setDailyRain(Long dailyRain) {
		this.dailyRain = dailyRain;
	}

	public Long getTotalRain() {
		return totalRain;
	}

	public void setTotalRain(Long totalRain) {
		this.totalRain = totalRain;
	}

	public Long getRainAlarm() {
		return rainAlarm;
	}

	public void setRainAlarm(Long rainAlarm) {
		this.rainAlarm = rainAlarm;
	}

	public Double getInternalTemprature() {
		return internalTemprature;
	}

	public void setInternalTemprature(Double internalTemprature) {
		this.internalTemprature = internalTemprature;
	}

	public Double getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(Double batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public Long getPowerFail() {
		return powerFail;
	}

	public void setPowerFail(Long powerFail) {
		this.powerFail = powerFail;
	}

	public String getEndChar() {
		return endChar;
	}

	public void setEndChar(String endChar) {
		this.endChar = endChar;
	}

	@Override
	public String toString() {
		return "RainData [startChar=" + startChar + ", stationId=" + stationId + ", date=" + date + ", time=" + time
				+ ", dailyRain=" + dailyRain + ", totalRain=" + totalRain + ", rainAlarm=" + rainAlarm
				+ ", internalTemprature=" + internalTemprature + ", batteryVoltage=" + batteryVoltage + ", powerFail="
				+ powerFail + ", endChar=" + endChar + "]";
	}
}
