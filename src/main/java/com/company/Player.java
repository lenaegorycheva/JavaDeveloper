package com.company;

public class Player {
    private Location location;
    private Inventory inventory = new Inventory();

    public Player(Location location, Inventory inventory) {
        this.location = location;
        this.inventory = inventory;
    }

    public Location getLocation() {
        return location;
    }

    public void showActions(){
        System.out.println("Возможные действия: осмотреться, инвентарь, идти направление(запад, восток, север, юг), взять предмет, использовать объект на субъект, выйти");
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void lookAround(){
        System.out.println(location.getDescription());
        for (Direction key : location.getDirections().keySet()) {
            switch (key){
                case UP:
                    System.out.printf("Наверху - %s. ",location.getDirections().get(key).getName());
                    break;
                case DOWN:
                    System.out.printf("Внизу - %s. ",location.getDirections().get(key).getName());
                    break;
                case WEST:
                    System.out.printf("На западе - %s. ",location.getDirections().get(key).getName());
                    break;
                case EAST:
                    System.out.printf("На востоке - %s. ",location.getDirections().get(key).getName());
                    break;
                case NORTH:
                    System.out.printf("На севере - %s. ",location.getDirections().get(key).getName());
                    break;
                case SOUTH:
                    System.out.printf("На юге - %s. ",location.getDirections().get(key).getName());
                    break;
            }
        }
        System.out.println();
        for(int i = 0; i < location.getInventory().getItems().size(); i++){
            System.out.println(location.getInventory().getItems().get(i).getDescription());
        }
    }

    public void go(String strDirection){
        StringBuilder stringBuilder = new StringBuilder();
        switch (strDirection){
            case "наверх":
                stringBuilder.append("UP");
                break;
            case "вниз":
                stringBuilder.append("DOWN");
                break;
            case "запад":
                stringBuilder.append("WEST");
                break;
            case "восток":
                stringBuilder.append("EAST");
                break;
            case "север":
                stringBuilder.append("NORTH");
                break;
            case "юг":
                stringBuilder.append("SOUTH");
                break;
            default:
                System.out.println("Такого направления не существует");
        }
        if (stringBuilder.length() != 0) {
            String direction = stringBuilder.toString();
            if (location.findNext(direction) != null) {
                this.location = location.findNext(direction);
                this.lookAround();
            } else {
                System.out.println("Вы не можете туда пойти.");
            }
        }
    }

    public void take(String item){
        if(this.location.getInventory().find(item)!=null){
            if(this.location.getInventory().find(item).getMoveable()==Moveable.MOBILE) {
                this.inventory.add(this.location.getInventory().find(item));
                this.location.pickUp(item);
                System.out.println("Вы получили этот предмет.");
            } else {
                System.out.println("Этот предмет нельзя взять с собой.");
            }
        } else {
            System.out.println("Такого предмета здесь нету.");
        }
    }

    public void use(String obj, String subj){
        if(this.inventory.find(obj)!=null){
            if (this.location.getInventory().find(subj)!=null){
                this.inventory.remove(this.inventory.find(obj));
                if(this.location.getInventory().find(subj).getMoveable()==Moveable.MOBILE){
                    this.location.getInventory().remove(this.location.getInventory().find(subj));
                }
            } else {
                if(this.inventory.find(subj)!=null){
                    this.inventory.remove(this.inventory.find(obj));
                    this.inventory.remove(this.inventory.find(subj));
                }
            }
        } else {
            System.out.println("У Вас нет в инвентаре этого предмета.");
        }

    }

    public void inventory(){
        inventory.show();
    }
}
