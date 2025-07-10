package com.transacthex.transacthex.individual.job.model;

import java.time.LocalDate;

public record IndividualDto(Long id, String firstName, String lastName, String email, String dateOfBirth,String status) {

    @Override
    public String toString() {
        return "IndividualDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", status='" + status + '\'' +
                '}';
    }
}
