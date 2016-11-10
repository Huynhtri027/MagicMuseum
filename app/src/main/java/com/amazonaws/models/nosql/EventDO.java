package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "magicmuseum-mobilehub-821031450-Event")

public class EventDO {
    private Double _eventId;
    private Double _light;
    private String _pieceId;
    private Double _temperature;
    private String _userId;

    @DynamoDBHashKey(attributeName = "eventId")
    @DynamoDBAttribute(attributeName = "eventId")
    public Double getEventId() {
        return _eventId;
    }

    public void setEventId(final Double _eventId) {
        this._eventId = _eventId;
    }
    @DynamoDBAttribute(attributeName = "light")
    public Double getLight() {
        return _light;
    }

    public void setLight(final Double _light) {
        this._light = _light;
    }
    @DynamoDBAttribute(attributeName = "pieceId")
    public String getPieceId() {
        return _pieceId;
    }

    public void setPieceId(final String _pieceId) {
        this._pieceId = _pieceId;
    }
    @DynamoDBAttribute(attributeName = "temperature")
    public Double getTemperature() {
        return _temperature;
    }

    public void setTemperature(final Double _temperature) {
        this._temperature = _temperature;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

}
