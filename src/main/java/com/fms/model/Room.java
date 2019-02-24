package com.fms.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;

public class Room {

    public Room(int id, int buildingId, int roomNumber) {
        this.id = id;
        this.buildingId = buildingId;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String toString() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }

    public static Room fromJson(String room) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(room));
        reader.beginObject();

        int id = 0;
        int buildingId = 0;
        int roomNumber = 0;

        while(reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                String key = reader.nextName();

                switch(key)
                {
                    case "id": {
                        id = reader.nextInt();
                        break;
                    }
                    case "buildingId": {
                        buildingId = reader.nextInt();
                        break;
                    }
                    case "roomNumber": {
                        roomNumber = reader.nextInt();
                        break;
                    }
                    default:
                        System.out.println("no match");
                }
            }
        }

        return new Room(id, buildingId, roomNumber);
    }

    @Override
    public boolean equals(Object o) {
        final Room room = (Room) o;
        if(room == this) {
            return true;
        }

        return id == room.id &&
                buildingId == room.buildingId &&
                roomNumber == room.roomNumber;
    }

    private int id;
    private int buildingId;
    private int roomNumber;
}
