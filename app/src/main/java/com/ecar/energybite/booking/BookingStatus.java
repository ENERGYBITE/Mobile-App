package com.ecar.energybite.booking;

/**
 * Created by navin.ketu on 20-10-2019.
 */

public enum BookingStatus {

    NEW('S', "New"),

    PROGRESS('R', "Charge in progress"),

    COMPLETE('C', "Complete");

    char bookingStatusCode;
    String bookingStatusMsg;

    BookingStatus(char bookingStatusCode, String bookingStatusMsg) {
        this.bookingStatusCode = bookingStatusCode;
        this.bookingStatusMsg = bookingStatusMsg;
    }

    public char getBookingStatusCode() {
        return bookingStatusCode;
    }

    public String getBookingStatusMsg() {
        return bookingStatusMsg;
    }

    public static BookingStatus getBookingStatus(char bookingStatusCode) {
        for (BookingStatus bookingStatus : BookingStatus.values()) {
            if (bookingStatus.getBookingStatusCode() == bookingStatusCode) {
                return bookingStatus;
            }
        }
        return NEW;
    }
}
