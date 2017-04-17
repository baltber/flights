
package ru.gnivc.training.flight.interfaces;

import ru.gnivc.training.flight.objects.Flight;
import ru.gnivc.training.flight.objects.Reservation;
import ru.gnivc.training.flight.spr.objects.Place;

/**
 *
 * @author TaylakovSA
 */
public interface Buy {
    
   Reservation buyTicket(Flight flight, Place place, String addInfo );
}
