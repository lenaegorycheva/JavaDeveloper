package com.company;

import java.util.*;

public class Main {
    private static final String INTRODUCTION = "Вы очнулись на песчаном берегу необитаемого острова. Последние ваши воспоминания: самолет, гроза, авиакатастрофа" + "\n" + "Вы понимаете, что для выживания необходимо немедленно действовать.";
    private static final String EPILOGUE = "Вдалеке вы слышите шум вертолета и решаетесь запустить сигнальную ракету. Вскоре за вами прилетает спасение. Вам удалось выжить";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Предметы
        Item toy = new Item("игрушка", "Среди разбросанных багажей вы видите мягкую игрушку", Moveable.MOBILE);
        Item cave = new Item("пещера", "Рядом располагается темная сырая пещера", Moveable.STATIONARY);
        Item monkey = new Item("обезьяна","На одной из веток притаилась обезьяна", Moveable.STATIONARY);
        Item lighter = new Item("зажигалка","", Moveable.MOBILE);
        Item box = new Item("ящик","Небольшой закрытый стальной ящик", Moveable.MOBILE);
        Item tusk = new Item("клык","",Moveable.MOBILE);
        Item key = new Item("ключ","",Moveable.MOBILE);
        Item liana = new Item("лиана", "Тропическую растительность плотно опутывают лианы", Moveable.STATIONARY);
        Item rope = new Item("веревка","",Moveable.MOBILE);
        Item flare = new Item("сигнальная ракета","",Moveable.MOBILE);
        Item branch = new Item("ветка","Ваши внимание привлекает ветка высокого тропического дерева. На ней застрял небольшой металлический ящик",Moveable.STATIONARY);
        //Взаимодействие предметов
        Combo combo1 = new Combo(toy,monkey,key,"Обезьяна что-то выронила, быстро выхватила игрушку из ваших рук и скрылась среди деревьев. Вы подняли блестящий предмет, это оказался ключ");
        Combo combo2 = new Combo(box,key,flare,"Вы открываете стальной ящик и находите внутри сигнальные ракеты");
        Combo combo3 = new Combo(lighter,cave,tusk,"В пещере вы нашли скелет животного. Вы решили оставить себе его клык");
        Combo combo4 = new Combo(key,box,flare,"Вы открываете стальной ящик и находите внутри сигнальные ракеты");
        Combo combo5 = new Combo(tusk,liana,rope,"Вы срезаете необходимо количество лианы и сплетаете из нее плотную веревку");
        Combo combo6 = new Combo(rope,branch,box,"Вам удается зацепить ветку дерева. Металлический ящик освобождается и с грохотом падает вам под ноги, но не открывается");
        ArrayList<Combo> combos = new ArrayList<>();
        combos.add(combo1);
        combos.add(combo2);
        combos.add(combo3);
        combos.add(combo4);
        combos.add(combo5);
        combos.add(combo6);
        //Локации
        Location beach = new Location("пляж", "Вы находитесь на необитаемом пляже" + "\n" + "Горячий песок под ногами жалит ступни, бесконечной океан нагоняет тоску по дому, к берегу прибило обломки самолета" + "\n" + "Где же спасение?");
        Location crag = new Location("скалы","Вы из последних сил взбираетесь на скалу. Перед вашими глазами только куча камней");
        Location jungle = new Location("джунгли","Вы заходите в густые заросли джунглей");
        Location glade = new Location("поляна","Поляна среди джунглей завалена кучей бесполезных обломков от самолета");
        //Инвентарь
        beach.putOn(toy);
        crag.putOn(cave);
        jungle.putOn(liana);
        jungle.putOn(monkey);
        glade.putOn(branch);
        //Направления
        HashMap<Direction, Location> directionsBeach = new HashMap<>();
        directionsBeach.put(Direction.WEST,crag);
        directionsBeach.put(Direction.NORTH,jungle);
        HashMap<Direction, Location> directionCrag = new HashMap<>();
        directionCrag.put(Direction.EAST,beach);
        HashMap<Direction, Location> directionJungle = new HashMap<>();
        directionJungle.put(Direction.SOUTH,beach);
        directionJungle.put(Direction.NORTH,glade);
        HashMap<Direction, Location> directionGlade = new HashMap<>();
        directionGlade.put(Direction.SOUTH,jungle);
        //Переход в локации
        beach.setDirections(directionsBeach);
        crag.setDirections(directionCrag);
        jungle.setDirections(directionJungle);
        glade.setDirections(directionGlade);
        //Игра
        Inventory playerInventory = new Inventory();
        playerInventory.add(lighter);
        Player player = new Player(beach,playerInventory);

        System.out.println(INTRODUCTION);
        while (!playerInventory.getItems().contains(flare)){
            player.showActions();
            String input = scanner.nextLine();
            int kolSpace = input.length() - input.replaceAll(" ","").length();
            if(kolSpace==0){
                switch (input){
                    case "осмотреться":
                        player.lookAround();
                        break;
                    case "инвентарь":
                        player.inventory();
                        break;
                    case "выйти":
                        System.exit(0);
                    default:
                        System.out.println("Такого действия не существует");
                }
            } else if(kolSpace==1){
                String[] actionTwo = input.split(" ");
                switch (actionTwo[0]){
                    case "идти":
                        player.go(actionTwo[1]);
                        break;
                    case "взять":
                        player.take(actionTwo[1]);
                        break;
                    default:
                        System.out.println("Такого действия не существует");
                }
            } else if(kolSpace==3){
                String[] actionThree = input.split(" ");
                if ("использовать".equals(actionThree[0])) {
                    Item obj = player.getInventory().find(actionThree[1]);
                    Item subj = player.getLocation().getInventory().find(actionThree[3]);
                    if (obj != null && subj != null){
                        Combo combo = new Combo(obj, subj);
                        if(combos.contains(combo)) {
                            player.use(actionThree[1], actionThree[3]);
                            for (Combo value : combos) {
                                if (value.equals(combo)) {
                                    player.getInventory().add(value.getResult());
                                    System.out.println(value.getMessage());
                                }
                            }
                        } else {
                            System.out.println("Тут это применить нельзя");
                        }
                    } else if(obj != null && subj == null){
                        Item newSubj = player.getInventory().find(actionThree[3]);
                        if(newSubj != null){
                            Combo combo = new Combo(obj, newSubj);
                            if(combos.contains(combo)) {
                                player.use(actionThree[1], actionThree[3]);
                                for (Combo value : combos) {
                                    if (value.equals(combo)) {
                                        player.getInventory().add(value.getResult());
                                        System.out.println(value.getMessage());
                                    }
                                }
                            } else {
                                System.out.println("Тут это применить нельзя");
                            }
                        } else {
                            System.out.println("Этого субъекта в инвентаре/на локации нет");
                        }
                    } else {
                        System.out.println("Такого объекта и/или субъекта не было найдено");
                    }
                } else {
                    System.out.println("Такого действия не существует");
                }
            }
            else{
                System.out.println("Некорректный ввод");
            }
        }
        System.out.println(EPILOGUE);
        scanner.close();
    }
}
