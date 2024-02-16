package com.example.application.views;

import com.example.application.entities.Car;
import com.example.application.entities.Vehicle;
import com.example.application.services.CarService;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Route("car")
@PageTitle("Filip Express | Cars")
public class CarView extends VerticalLayout {

    private final H1 header = new H1("CAR Panel");
    private final HorizontalLayout buttonLayout = new HorizontalLayout();
    private final Grid<Car> grid = new Grid<>(Car.class);

    private final Button addButton = new Button("Add Car", new Icon(VaadinIcon.PLUS));
    private final Button refreshButton = new Button("Refresh", new Icon(VaadinIcon.REFRESH));
    private final Button deleteButton = new Button("Delete", new Icon(VaadinIcon.TRASH));
    private final Button editButton = new Button("Edit", new Icon(VaadinIcon.EDIT));

    private final Dialog carDetailsDialog = new Dialog();
    private final TextField carBrand = new TextField("Brand");
    private final TextField carModel = new TextField("Model");
    private final IntegerField manufacturingYear = new IntegerField("Manufacturing Year");
    private final NumberField engineCapacity = new NumberField("Engine Capacity");

    private final Button confirmButton = new Button("Confirm");

    private final Binder<Car> binder = new Binder<>(Car.class);

    private final TextField searchField = new TextField("Search by Brand");

    private final ComboBox<String> transmissionField = new ComboBox<>("Transmission");
    private final ComboBox<String> fuelTypeField = new ComboBox<>("Fuel Type");
    private final ComboBox<String> bodyTypeField = new ComboBox<>("Body Type");
    private final ComboBox<Integer> seatsField = new ComboBox<>("Seats");

    private final CarService carService;

    public CarView(CarService carService) {
        this.carService = carService;

        configureGrid();
        configureForm();

        addButton.addClickListener(e -> openAddCarDialog());
        refreshButton.addClickListener(e -> updateList());
        deleteButton.addClickListener(e -> deleteCar());
        editButton.addClickListener(e -> openEditCarDialog());

        // Create the menuButton
        Button menuButton = new Button(new Icon(VaadinIcon.MENU), e -> UI.getCurrent().navigate("menu"));
        menuButton.getElement().getThemeList().add("primary");

        // Add the search field
        searchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                // Perform search based on the car's brand
                grid.setItems(carService.searchCarsByBrand(searchTerm));
            } else {
                // Update the list with all cars when the search field is empty
                updateList();
            }
        });

        // Configure layout
        HorizontalLayout buttonRow = new HorizontalLayout(
                refreshButton, addButton, deleteButton, editButton
        );
        buttonRow.setSpacing(true);

        VerticalLayout searchLayout = new VerticalLayout(searchField);
        searchLayout.setHorizontalComponentAlignment(Alignment.END, searchField);

        // Create the header layout
        HorizontalLayout headerLayout = new HorizontalLayout(header, menuButton);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout contentLayout = new HorizontalLayout(buttonRow, searchLayout);
        contentLayout.setAlignItems(Alignment.BASELINE);
        contentLayout.setSpacing(true);

        add(headerLayout, contentLayout, grid);
        headerLayout.getStyle().set("margin-bottom", "-25px");
        updateList();
    }

    private void configureGrid() {
        grid.removeAllColumns();

        // Car properties
        grid.addColumn(Car::getVehicleid).setHeader("Vehicle ID").setSortProperty("vehicleid");
        grid.addColumn(Car::getCarbrand).setHeader("Car Brand").setSortProperty("carbrand");
        grid.addColumn(Car::getCarmodel).setHeader("Car Model").setSortProperty("carmodel");
        grid.addColumn(Car::getManufacturingyear).setHeader("Manufacturing Year").setSortProperty("manufacturingyear");
        grid.addColumn(Car::getEnginecapacity).setHeader("Engine Capacity").setSortProperty("enginecapacity");

        // Superclass properties
        grid.addColumn(car -> ((Vehicle) car).getBodytype())
                .setHeader("Body Type")
                .setSortable(true);

        grid.addColumn(car -> ((Vehicle) car).getFueltype())
                .setHeader("Fuel Type")
                .setSortable(true);

        grid.addColumn(car -> ((Vehicle) car).getTransmission())
                .setHeader("Transmission")
                .setSortable(true);

        grid.addColumn(Car::getSeats).setHeader("Seats").setSortProperty("seats");

        grid.asSingleSelect().addValueChangeListener(event -> {
            deleteButton.setEnabled(event.getValue() != null);
            editButton.setEnabled(event.getValue() != null);
        });

        grid.setHeight("500px");
    }

    private void configureForm() {
        binder.bind(carBrand, Car::getCarbrand, Car::setCarbrand);
        binder.bind(carModel, Car::getCarmodel, Car::setCarmodel);
        binder.bind(engineCapacity, Car::getEnginecapacity, Car::setEnginecapacity);
        binder.bind(manufacturingYear, Car::getManufacturingyear, Car::setManufacturingyear);
        binder.bind(seatsField, Car::getSeats, Car::setSeats);

        // Add components for Vehicle superclass fields
        seatsField.setItems(1, 2, 3, 4, 5, 6, 7, 8);
        seatsField.setPlaceholder("Select Seats");
        bodyTypeField.setItems("Sedan", "SUV", "Pickup Truck", "Crossover", "Supercar", "Convertible", "Hatchback");
        bodyTypeField.setPlaceholder("Select Body Type");
        fuelTypeField.setItems("Petrol", "Diesel", "Hybrid", "Electric");
        fuelTypeField.setPlaceholder("Select Fuel Type");
        transmissionField.setItems("Automatic", "Manual");
        transmissionField.setPlaceholder("Select Transmission");

        // Bind Vehicle superclass fields
        binder.bind(bodyTypeField, car -> ((Vehicle) car).getBodytype(), (car, value) -> ((Vehicle) car).setBodytype(value));
        binder.bind(fuelTypeField, car -> ((Vehicle) car).getFueltype(), (car, value) -> ((Vehicle) car).setFueltype(value));
        binder.bind(transmissionField, car -> ((Vehicle) car).getTransmission(), (car, value) -> ((Vehicle) car).setTransmission(value));

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> carDetailsDialog.close());
        cancelButton.getElement().getThemeList().add("tertiary");

        confirmButton.addClickListener(e -> {
            saveCar();
            carDetailsDialog.close();
        });
        confirmButton.getElement().getThemeList().add("primary");
        confirmButton.setText("Confirm");

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        H2 formHeader = new H2("Add new car");

        // Create a grid for the form fields
        VerticalLayout formLayout = new VerticalLayout(
                formHeader,
                new HorizontalLayout(carBrand, carModel),
                new HorizontalLayout(engineCapacity, manufacturingYear),
                new HorizontalLayout(bodyTypeField, fuelTypeField),
                new HorizontalLayout(transmissionField, seatsField),
                new HorizontalLayout(cancelButton, confirmButton)
        );

        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        formLayout.setSpacing(true);

        carDetailsDialog.add(formLayout);

        carDetailsDialog.setWidth("600px");

        // Update the form header dynamically when editing
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                formHeader.setText("Update car");
                transmissionField.setValue(event.getValue().getTransmission());
                fuelTypeField.setValue(event.getValue().getFueltype());
                bodyTypeField.setValue(event.getValue().getBodytype());
            } else {
                formHeader.setText("Add new car");
                transmissionField.clear();
                fuelTypeField.clear();
                bodyTypeField.clear();
            }
        });

        addButton.addClickListener(e -> {
            formHeader.setText("Add new car");
            binder.setBean(new Car());
            carDetailsDialog.open();
        });

        editButton.addClickListener(e -> {
            formHeader.setText("Update car");
            Car car = grid.asSingleSelect().getValue();
            if (car != null) {
                binder.setBean(car);
                carDetailsDialog.open();
            }
        });
    }

    private void saveCar() {
        try {
            Car car = binder.getBean();
            if (car != null) {
                carService.saveCar(car);
                updateList();
                Notification.show("Car saved successfully with ID: " + car.getVehicleid());
            } else {
                Notification.show("Error saving car: Car is null");
            }
        } catch (DataIntegrityViolationException e) {
            Notification.show("Error saving client: There is already a customer with the same field in the database.");
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = "";

            for (ConstraintViolation<?> violation : violations) {
                errorMessage += violation.getMessage() + " ; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error saving rent: " + e.getMessage());
        }
    }

    private void deleteCar() {
        try {
            Car car = grid.asSingleSelect().getValue();
            if (car != null) {
                // Create a custom confirmation dialog
                Dialog confirmationDialog = new Dialog();
                confirmationDialog.setCloseOnOutsideClick(false);

                Button cancelButton = new Button("Cancel", event -> confirmationDialog.close());
                Button confirmButton = new Button("Delete", event -> {
                    try {
                        // User confirmed, perform delete
                        carService.deleteCar(car.getVehicleid());
                        updateList();
                        Notification.show("Car deleted successfully");
                        confirmationDialog.close();
                    } catch (DataIntegrityViolationException e) {
                        // Catch the exception for data integrity violation (foreign key constraint)
                        Notification.show("Error deleting car: This car is associated with rental records");
                    }
                });

                // Add a custom style to the "Delete" button
                confirmButton.getElement().getThemeList().add("primary");

                VerticalLayout layout = new VerticalLayout(
                        new H4("Confirm Delete"),
                        new Paragraph("Are you sure you want to delete " +
                                car.getCarbrand() + " " + car.getCarmodel() + " from the database?"),
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
                errorMessage += violation.getMessage() + " ; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error saving rent: " + e.getMessage());
        }
    }

    private void openAddCarDialog() {
        binder.setBean(new Car());
        carDetailsDialog.open();
    }

    private void openEditCarDialog() {
        Car car = grid.asSingleSelect().getValue();
        if (car != null) {
            binder.setBean(car);
            carDetailsDialog.open();
        }
    }

    private void updateList() {
        try {
            List<Car> cars = carService.getAllCars();

            // Sort the list by vehicle ID
            cars.sort(Comparator.comparing(Car::getVehicleid));

            grid.setItems(cars);
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = "";

            for (ConstraintViolation<?> violation : violations) {
                errorMessage += violation.getMessage() + " ; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error saving rent: " + e.getMessage());
        }
    }
}
