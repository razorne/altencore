package com.alten.altencore.model;

import com.alten.altencore.model.id.ReservationId;
import java.io.Serializable;

/**
 *
 * @author aconti
 */
public class Reservation implements Serializable {
    private ReservationId id;
    private String username;

    public ReservationId getId() {
        return id;
    }

    public void setId(ReservationId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
