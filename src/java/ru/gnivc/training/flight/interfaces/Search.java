
package ru.gnivc.training.flight.interfaces;

import java.util.Date;
import java.util.List;
import ru.gnivc.training.flight.objects.*;
import ru.gnivc.training.flight.spr.objects.*;

/**
 *
 * @author TaylakovSA
 */
public interface Search {
    
    List<Flight> searchFlight (Date date, City cityFrom, City cityTo, int placeCount);
    
}
