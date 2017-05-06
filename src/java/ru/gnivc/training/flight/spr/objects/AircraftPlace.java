
package ru.gnivc.training.flight.spr.objects;

/**
 *
 * @author TaylakovSA
 */
public class AircraftPlace {
    
    private long id;
    private long aircraftId;
    private long placeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }
    
    
    
}
