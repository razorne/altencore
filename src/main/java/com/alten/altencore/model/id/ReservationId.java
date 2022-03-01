package com.alten.altencore.model.id;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author aconti
 */
public class ReservationId implements Serializable {
    private Date takenDate;
    private String code;

    public Date getTakenDate() {
        return takenDate;
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
