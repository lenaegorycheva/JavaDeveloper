package com.company;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getItems() {
        return items;
    }

    public void add(Item item){
        this.items.add(item);
    }

    public void remove(Item item){
        this.items.remove(item);
    }

    public void show(){
        if (this.items.isEmpty()){
            System.out.println("пусто");
        } else {
            for(int i = 0; i < this.items.size(); i++){
                if(this.items.size()==i+1){
                    System.out.println(this.items.get(i).getName());
                } else {
                    System.out.print(this.items.get(i).getName() + ", ");
                }
            }
        }
    }

    public Item find(String item){
        Item obj = new Item(item);
        if(this.items.contains(obj)){
            for (Item value : this.items) {
                if (value.equals(obj)) {
                    return value;
                }
            }
        } return null;
    }
}
