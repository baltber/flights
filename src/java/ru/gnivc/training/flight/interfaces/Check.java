
package ru.gnivc.training.flight.interfaces;

import ru.gnivc.training.flight.objects.Reservation;

/**
 *
 * @author TaylakovSA
 */
public interface Check {
    
    Reservation checkReservation(String code);           
    
}
