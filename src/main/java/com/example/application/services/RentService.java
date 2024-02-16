package com.example.application.services;

import com.example.application.entities.Rent;
import com.example.application.entities.Audit;
import com.example.application.repositories.RentRepository;
import com.example.application.repositories.AuditRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;



    @Autowired
    private AuditRepository auditRepository;

    public void saveRent(Rent rent) {
        Integer vehicleId = rent.getCar().getVehicleid();

        if (!isVehicleAlreadyRented(vehicleId)) {
            // Vehicle is not rented, proceed with saving the rent
            rentRepository.save(rent);
            Notification.show("Rent saved successfully with ID: " + rent.getRentid());
        } else {
            // Vehicle is already rented, show a notification or handle the validation accordingly
            showNotification("Vehicle " + rent.getCar().getCarbrand() + " " + rent.getCar().getCarmodel() + " is already rented");
        }
    }

    public void updateRent(Rent rent) {
        rentRepository.save(rent);
    }

    private boolean isVehicleAlreadyRented(Integer vehicleId) {
        return rentRepository.existsByVehicleId(vehicleId);
    }

    // For notification
    private void showNotification(String message) {
        Notification notification = new Notification(message, 3000, Notification.Position.BOTTOM_START);
        notification.open();
    }

    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    public void deleteRent(Integer rentId) {
        Optional<Rent> optionalRent = rentRepository.findById(rentId);
        if (optionalRent.isPresent()) {
            Rent rent = optionalRent.get(); // Initialize rent
            if (rent.getClient() != null) {
                Audit auditRent = new Audit();

                // Client
                auditRent.setFullname(rent.getClient().getFullname());
                auditRent.setAddress(rent.getClient().getAddress());
                auditRent.setPhone(rent.getClient().getPhone());
                auditRent.setEmail(rent.getClient().getEmail());

                // Vehicle
                auditRent.setCarbrand(rent.getCar().getCarbrand());
                auditRent.setCarmodel(rent.getCar().getCarmodel());

                // Period
                auditRent.setPeriod(rent.getPeriod());
                auditRent.setRentaldate(rent.getRentaldate());
                auditRent.setReturnDate(rent.getRentaldate());

                // Price
                auditRent.setRentpriceTVA(rent.getRentpriceTVA());
                auditRent.setTotal(rent.getTotal());

                auditRepository.save(auditRent);
                showNotification("Audit was saved successfully with AuditID: " + auditRent.getAuditid());
            }
            else
            {
                showNotification("For this rent audit can't be saved!");
            }
            rentRepository.deleteById(rentId);
        }
    }

    // Additional
    public List<Rent> getOverdueRents() {
        List<Rent> overdueRents = new ArrayList<>();
        try {
            List<Rent> allRents = getAllRents();
            for (Rent rent : allRents) {
                LocalDate endRental = rent.getRentaldate().plusDays(rent.getPeriod());

                // Check if the end date is after the current date
                if (endRental.isBefore(LocalDate.now())) {
                    overdueRents.add(rent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overdueRents;
    }

    public void updateOverdueRents(List<Rent> overdueRents) {
        try {
            for (Rent rent : overdueRents) {
                // Calculate fines based on the number of days overdue
                long daysOverdue = ChronoUnit.DAYS.between(rent.getRentaldate().plusDays(rent.getPeriod()), LocalDate.now());
                double fines = 250 * daysOverdue;

                // Update fines in the database
                rent.setFines(fines);
                rent.setTotal(rent.getRentpriceTVA() + fines);
                updateRent(rent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Rent> searchRentByClientName(String clientName) {
        return rentRepository.findByClientFullnameContainingIgnoreCase(clientName);
    }
}