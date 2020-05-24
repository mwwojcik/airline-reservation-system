package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.CustomerId;

public class CustomerProfile {

  private CustomerId customerId;

  private CustomerProfile(CustomerId customerId) {
    this.customerId = customerId;
  }

  public static CustomerProfile from(CustomerId customerId) {
    return new CustomerProfile(customerId);
  }
}
