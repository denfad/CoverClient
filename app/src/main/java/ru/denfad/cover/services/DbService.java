package ru.denfad.cover.services;



import ru.denfad.cover.models.Place;

import java.util.ArrayList;
import java.util.List;

public class DbService {

    private ArrayList<Place> places = new ArrayList<>();

    public DbService() {
//        addPlace(new Place(1,"Гос. учреждение", 0.3, 56.857159, 35.914097));
//        addPlace(new Place(2,"Больница", 0.9, 56.859058, 35.912376));
//        addPlace(new Place(3,"Магазин", 0.6, 56.855938, 35.913609));
//        addPlace(new Place(4,"Общественное место", 0.3, 56.857689, 35.914631));
//        addPlace(new Place(5,"Гос. учреждение", 0.9, 56.858371, 35.916741));

    }

    public Place getPlace(int id){
        for(Place p: places) if(p.getPlaceId() == id) return p;
        return null;
    }

    public Place getPlace(double x,double y){
        //for(Place p: places) if(p.getX() == x & p.getY() == y) return p;
        return null;
    }

    public void addPlace(Place place){
        places.add(place);
    }

    public List<Place> getPlaces(){
        return places;
    }
}
