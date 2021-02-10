package com.filehandler;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CanalDataDto {

	@JsonProperty("Start Character")
	public String startChar;
	
	@JsonProperty("StationID")
	public Long stationId;
	
	@JsonProperty("Date")
	@JsonFormat(pattern="dd/mm/yyyy")
	public Date date;
	
	@JsonProperty("Time")
	public String time;
	
	@JsonProperty("Canal Level")
	public Double canelLevel;
	
	@JsonProperty("Discharge Rate")
	public Double dischargeRate;
	
	@JsonProperty("Level Sensor Alarm")
	public Long levelSensorAlarm;
	
	@JsonProperty("High Level Alarm")
	public Long highLevelAlarm;
	
	@JsonProperty("Internal Temperature")
	public Double internalTemprature;
	
	@JsonProperty("Battery Voltage")
	public Double batteryVoltage;
	
	@JsonProperty("Powewr Fail")
//	@JsonSerialize(using=NumericBooleanSerializer.class)
//    @JsonDeserialize(using=NumericBooleanDeserializer.class)
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
	public Double getCanelLevel() {
		return canelLevel;
	}
	public void setCanelLevel(Double canelLevel) {
		this.canelLevel = canelLevel;
	}
	public Double getDischargeRate() {
		return dischargeRate;
	}
	public void setDischargeRate(Double dischargeRate) {
		this.dischargeRate = dischargeRate;
	}
	public Long getLevelSensorAlarm() {
		return levelSensorAlarm;
	}
	public void setLevelSensorAlarm(Long levelSensorAlarm) {
		this.levelSensorAlarm = levelSensorAlarm;
	}
	public Long getHighLevelAlarm() {
		return highLevelAlarm;
	}
	public void setHighLevelAlarm(Long highLevelAlarm) {
		this.highLevelAlarm = highLevelAlarm;
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
	public Long isPowerFail() {
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
		return "CanalDataDto [startChar=" + startChar + ", stationId=" + stationId + ", date=" + date + ", time=" + time
				+ ", canelLevel=" + canelLevel + ", dischargeRate=" + dischargeRate + ", levelSensorAlarm="
				+ levelSensorAlarm + ", highLevelAlarm=" + highLevelAlarm + ", internalTemprature=" + internalTemprature
				+ ", batteryVoltage=" + batteryVoltage + ", powerFail=" + powerFail + ", endChar=" + endChar + "]";
	}
	
//	public static class NumericBooleanSerializer extends JsonSerializer<Boolean> {
//
//	    @Override
//	    public void serialize(Boolean bool, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
//	        generator.writeString(bool ? "1" : "0");
//	    }   
//	}
//
//	public static class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {
//
//	    @Override
//	    public Boolean deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
//	        return !"0".equals(parser.getText());
//	    }       
//	}
}
