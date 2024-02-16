package com.example.application.views;

import com.example.application.entities.Car;
import com.example.application.entities.Client;
import com.example.application.entities.Rent;
import com.example.application.services.RentService;
import com.example.application.services.CarService;
import com.example.application.services.ClientService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Route("rent")
@PageTitle("Filip Express | Rentals")
public class RentView extends VerticalLayout {

    private final H1 header = new H1("RENTAL Panel");
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final Grid<Rent> grid = new Grid<>(Rent.class);

    private final Button addButton = new Button("Add Rent", new Icon(VaadinIcon.PLUS));
    private final Button refreshButton = new Button("Refresh", new Icon(VaadinIcon.REFRESH));
    private final Button deleteButton = new Button("Delete", new Icon(VaadinIcon.TRASH));
    private final Button editButton = new Button("Edit", new Icon(VaadinIcon.EDIT));

    private final Dialog rentDetailsDialog = new Dialog();
    private final DatePicker rentalDate = new DatePicker("Rental Date");
    private final IntegerField period = new IntegerField("Period");
    private final NumberField rentPrice = new NumberField("Rent Price");
    private final TextField rentSearchField = new TextField("Search by Client Name");

    private final Binder<Rent> binder = new Binder<>(Rent.class);

    private Boolean isUpdateMode = false;

    private final RentService rentService;
    private final CarService carService;
    private final ClientService clientService;

    public RentView(RentService rentService, ClientService clientService, CarService carService) {
        this.rentService = rentService;
        this.carService = carService;
        this.clientService = clientService;

        configureGrid();
        configureForm();

        addButton.addClickListener(e -> openAddRentDialog());
        refreshButton.addClickListener(e -> updateList());
        deleteButton.addClickListener(e -> deleteRent());
        editButton.addClickListener(e -> openEditRentDialog());

        // Create the menuButton
        Button menuButton = new Button(new Icon(VaadinIcon.MENU), e -> UI.getCurrent().navigate("menu"));
        menuButton.getElement().getThemeList().add("primary");

        // Configure layout
        HorizontalLayout buttonRow = new HorizontalLayout(
                refreshButton, addButton, deleteButton, editButton
        );
        buttonRow.setSpacing(true);

        VerticalLayout searchLayout = new VerticalLayout(rentSearchField);
        searchLayout.setHorizontalComponentAlignment(Alignment.END, rentSearchField);
        rentSearchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                // Perform search based on the client's name
                grid.setItems(rentService.searchRentByClientName(searchTerm));
            } else {
                updateList();
            }
        });

        HorizontalLayout contentLayout = new HorizontalLayout(buttonRow);
        contentLayout.setAlignItems(Alignment.BASELINE);
        contentLayout.setSpacing(true);
        contentLayout.add(searchLayout);

        // Create the header layout
        HorizontalLayout headerLayout = new HorizontalLayout(header, menuButton);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        add(headerLayout, contentLayout, grid);
        updateList();
    }

    private void configureGrid() {
        grid.removeAllColumns();

        grid.addColumn(Rent::getRentid).setHeader("Rent ID").setSortProperty("rentid");
        grid.addColumn(rent -> rent.getClient().getFullname()).setHeader("Client Name").setSortable(true);
        grid.addColumn(rent -> rent.getCar().getCarbrand() + " " + rent.getCar().getCarmodel())
                .setHeader("Car Rented").setSortable(true);
        grid.addColumn(Rent::getRentaldate).setHeader("Rental Date").setSortProperty("rentaldate");
        grid.addColumn(Rent::getPeriod).setHeader("Period").setSortProperty("period");
        grid.addColumn(rent -> formatPrice(rent.getRentprice())).setHeader("Rent Price").setSortProperty("rentprice");
        grid.addColumn(rent -> formatPrice(rent.getRentpriceTVA())).setHeader("Rent Price TVA").setSortProperty("rentpriceTVA");
        grid.addColumn(rent -> formatPrice(rent.getFines())).setHeader("Fines").setSortProperty("fines");
        grid.addColumn(rent -> formatPrice(rent.getTotal())).setHeader("Total Amount").setSortProperty("total");

        grid.asSingleSelect().addValueChangeListener(event -> {
            deleteButton.setEnabled(event.getValue() != null);
            editButton.setEnabled(event.getValue() != null);
        });

        grid.setHeight("470px");
    }

    private void configureForm() {
        ComboBox<Client> clientComboBox = new ComboBox<>("Select Client");
        clientComboBox.setItemLabelGenerator(Client::getFullname);
        clientComboBox.setItems(clientService.getAllClients());

        ComboBox<Car> carComboBox = new ComboBox<>("Select Car");
        carComboBox.setItemLabelGenerator(car -> car.getCarbrand() + " " + car.getCarmodel());
        carComboBox.setItems(carService.getAllCars());

        binder.bind(carComboBox, Rent::getCar, Rent::setCar);
        binder.bind(clientComboBox, Rent::getClient, Rent::setClient);
        binder.bind(rentalDate, Rent::getRentaldate, Rent::setRentaldate);
        binder.bind(period, Rent::getPeriod, Rent::setPeriod);
        binder.bind(rentPrice, Rent::getRentprice, Rent::setRentprice);

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> rentDetailsDialog.close());
        cancelButton.getElement().getThemeList().add("tertiary");

        Button confirmButton = new Button("Confirm");
        confirmButton.addClickListener(e -> {
            if (isUpdateMode) {
                saveUpdatedRent();
            } else {
                saveRent();
            }
            rentDetailsDialog.close();
        });
        confirmButton.getElement().getThemeList().add("primary");

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        H2 formHeader = new H2("Add new rent");

        VerticalLayout formLayout = new VerticalLayout(
                formHeader,
                clientComboBox,
                carComboBox,
                rentalDate,
                period,
                rentPrice,
                new HorizontalLayout(cancelButton, confirmButton)
        );
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        formLayout.setSpacing(true);

        rentDetailsDialog.add(formLayout);
        rentDetailsDialog.setWidth("400px");

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                formHeader.setText("Update rent");
            } else {
                formHeader.setText("Add new rent");
            }
        });

        addButton.addClickListener(e -> {
            formHeader.setText("Add new rent");
            binder.setBean(new Rent());
            rentDetailsDialog.open();
        });

        editButton.addClickListener(e -> {
            formHeader.setText("Update rent");
            Rent rent = grid.asSingleSelect().getValue();
            if (rent != null) {
                binder.setBean(rent);
                rentDetailsDialog.open();
            }
        });
    }

    private void saveRent() {
        try {
            Rent rent = binder.getBean();
            if (rent != null) {
                rentService.saveRent(rent);
                updateList();
            } else {
                Notification.show("Error saving rent: Rent is null");
            }
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = "";

            for (ConstraintViolation<?> violation : violations) {
                errorMessage += violation.getMessage() + "; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
        }
    }

    private void saveUpdatedRent() {
        try {
            Rent rent = binder.getBean();
            if (rent != null) {
                rentService.saveRent(rent);
                updateList();
            } else {
                Notification.show("Error update rent: Rent is null");
            }
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = "";

            for (ConstraintViolation<?> violation : violations) {
                errorMessage += violation.getMessage() + "; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
        }
    }

    private void deleteRent() {
        try {
            Rent rent = grid.asSingleSelect().getValue();
            if (rent != null) {
                // Create a custom confirmation dialog
                Dialog confirmationDialog = new Dialog();
                confirmationDialog.setCloseOnOutsideClick(false);

                Button cancelButton = new Button("Cancel", event -> confirmationDialog.close());
                Button confirmButton = new Button("Delete", event -> {
                    // User confirmed, perform delete
                    rentService.deleteRent(rent.getRentid());
                    updateList();
                    Notification.show("Rent deleted successfully");
                    confirmationDialog.close();
                });

                // Add a custom style to the "Delete" button
                confirmButton.getElement().getThemeList().add("primary");

                VerticalLayout layout = new VerticalLayout(
                        new H4("Confirm Delete"),
                        new Paragraph("Are you sure you want to delete the selected rent from the database?"),
                        new HorizontalLayout(cancelButton, confirmButton)
                );

                layout.setAlignItems(Alignment.CENTER);
                layout.setJustifyContentMode(JustifyContentMode.CENTER);

                confirmationDialog.add(layout);
                confirmationDialog.open();
            }
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = "";

            for (ConstraintViolation<?> violation : violations) {
                errorMessage += violation.getMessage() + "; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
        }
    }

    private void openAddRentDialog() {
        binder.setBean(new Rent());
        rentDetailsDialog.open();
    }

    private void openEditRentDialog() {
        Rent rent = grid.asSingleSelect().getValue();
        if (rent != null) {
            binder.setBean(rent);
            rentDetailsDialog.open();
            isUpdateMode = true; // For no more problems with rented car
        }
    }

    private void updateList() {
        try {
            List<Rent> overdueRents = rentService.getOverdueRents();
            rentService.updateOverdueRents(overdueRents);

            List<Rent> rents = rentService.getAllRents();
            rents.sort(Comparator.comparing(Rent::getRentid));
            grid.setItems(rents);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error updating rent list: " + e.getMessage());
        }
    }

    // Additional
    private String formatPrice(Double value) {
        if (value != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            return decimalFormat.format(value);
        } else {
            return "0";
        }
    }
}