package com.alten.altencore.dao;

import com.alten.altencore.model.Reservation;
import com.alten.altencore.model.id.ReservationId;

/**
 *
 * @author aconti
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenericDAO<ReservationId, Reservation> reservationDao = new GenericDAO<>("/altencore_wizard/altencore.cfg.xml", Reservation.class);
        reservationDao.loadAll();
    }
    
}
