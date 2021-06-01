package com.company;

import java.util.HashMap;

public class Location {
    private final String name;
    private final String description;
    private Inventory inventory = new Inventory();
    private HashMap<Direction, Location> directions;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<Direction, Location> getDirections() {
        return directions;
    }

    public void setDirections(HashMap<Direction, Location> directions) {
        this.directions = directions;
    }

    public Location findNext(String direction){
        Direction key = Direction.valueOf(direction);
        return directions.getOrDefault(key, null);
    }

    public void putOn(Item item){
        this.inventory.add(item);
    }

    public void pickUp(String item){
        this.inventory.remove(inventory.find(item));
    }
}
