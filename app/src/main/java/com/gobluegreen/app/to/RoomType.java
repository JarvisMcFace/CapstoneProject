package com.gobluegreen.app.to;

/**
 * Created by David on 7/6/17.
 */

public enum RoomType {

    LIVING("Living Room"),
    DINING("Dining Room"),
    DEN_OFFICE("Den/Office"),
    MASTER("Bedroom - Master"),
    BEDROOM_2("Bedroom #2"),
    FAMILY_GREAT("Family/Great Room"),
    RECREATION("Recreation Room"),
    STAIRWAY_LANDING("Stairway Landing"),
    HALLWAY("Hallway"),
    ADDITIONAL_ROOM1("Additional Room 1"),
    ADDITIONAL_ROOM2("Additional Room 2");

    private String description;

    RoomType(String description) {
        this.description = description;
    }

    public static RoomType lookupRoomType(String roomDescription) {

        for (RoomType roomType : RoomType.values()) {
            if (roomDescription.equals(roomType.getDescription())) {
                return roomType;
            }
        }

        throw new IllegalArgumentException("'" + roomDescription + "' is invalide room type");
    }

    public String getDescription() {
        return description;
    }
}
