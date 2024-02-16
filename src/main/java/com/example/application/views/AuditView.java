package com.example.application.views;

import com.example.application.entities.Audit;
import com.example.application.services.AuditService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Route("audit")
@PageTitle("Filip Express | Audit")
public class AuditView extends VerticalLayout {

    private final H1 header = new H1("AUDIT Panel");
    private final Grid<Audit> grid = new Grid<>(Audit.class);
    private final Button refreshButton = new Button("Refresh", new Icon(VaadinIcon.REFRESH));
    private final Button detailsButton = new Button("See Details", new Icon(VaadinIcon.EYE));
    private final TextField searchField = new TextField("Search by Name");

    private final AuditService auditService;

    public AuditView(AuditService auditService) {
        this.auditService = auditService;

        configureGrid();

        refreshButton.addClickListener(e -> updateList());
        detailsButton.setEnabled(false);
        grid.asSingleSelect().addValueChangeListener(event -> {
            detailsButton.setEnabled(event.getValue() != null);
        });
        detailsButton.addClickListener(event -> showDetailsDialog());

        // Create the menuButton
        Button menuButton = new Button(new Icon(VaadinIcon.MENU), e -> UI.getCurrent().navigate("menu"));
        menuButton.getElement().getThemeList().add("primary");

        // Add the search field
        searchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                // Perform search based on the client's name
                grid.setItems(auditService.searchAuditByName(searchTerm));
            } else {
                updateList();
            }
        });

        // Configure layout
        HorizontalLayout buttonRow = new HorizontalLayout(
                refreshButton, detailsButton
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

    private void configureGrid()
    {
        grid.removeAllColumns();

        grid.addColumn(Audit::getAuditid).setHeader("Audit ID").setSortProperty("auditid");
        grid.addColumn(Audit::getFullname).setHeader("Full Name").setSortProperty("fullname");
        grid.addColumn(Audit::getAddress).setHeader("Address").setSortProperty("address");
        grid.addColumn(Audit::getPhone).setHeader("Phone").setSortProperty("phone");
        grid.addColumn(Audit::getEmail).setHeader("E-mail").setSortProperty("email");
        grid.addColumn(audit -> audit.getCarbrand() + " " + audit.getCarmodel())
                .setHeader("Car Rented").setSortable(true);
        grid.addColumn(Audit::getPeriod).setHeader("Period").setSortProperty("period");
        grid.addColumn(Audit::getRentaldate).setHeader("Rental").setSortProperty("rentaldate");
        grid.addColumn(Audit::getReturnDate).setHeader("Return").setSortProperty("returndate");
        grid.addColumn(audit -> formatPrice(audit.getRentpriceTVA())).setHeader("Rent Price TVA").setSortProperty("rentpriceTVA");
        grid.addColumn(audit -> formatPrice(audit.getFines())).setHeader("Fines").setSortProperty("fines");
        grid.addColumn(audit -> formatPrice(audit.getTotal())).setHeader("Total").setSortProperty("total");

        grid.setHeight("480px");
    }

    private void showDetailsDialog() {
        Audit audit = grid.asSingleSelect().getValue();
        if (audit != null) {
            // Create a dialog to display details
            Dialog detailsDialog = new Dialog();
            detailsDialog.setCloseOnOutsideClick(true);

            // Create form components and set their values
            TextField auditIdField = createReadOnlyTextField("Audit ID", String.valueOf(audit.getAuditid()));
            TextField auditFullName = createReadOnlyTextField("Client Full Name", audit.getFullname());
            TextField auditAddress = createReadOnlyTextField("Client Address", audit.getAddress());
            TextField auditPhone = createReadOnlyTextField("Client Phone", audit.getPhone());
            TextField auditEmail = createReadOnlyTextField("Client E-mail", audit.getEmail());
            TextField auditCar = createReadOnlyTextField("Car Rented", audit.getCarbrand() + " " + audit.getCarmodel());
            TextField auditPeriod = createReadOnlyTextField("Expected Period Rented", String.valueOf(audit.getPeriod()));
            TextField auditRentalDate = createReadOnlyTextField("Rental Date", String.valueOf(audit.getRentaldate()));
            TextField auditReturnDate = createReadOnlyTextField("Return Date", String.valueOf(audit.getReturnDate()));
            TextField auditRentPriceTVA = createReadOnlyTextField("Rent Price with TVA", String.valueOf(audit.getRentpriceTVA()));
            TextField auditFines = createReadOnlyTextField("Fines", String.valueOf(audit.getFines() != null ? audit.getFines() : "0"));
            TextField auditTotalAmount = createReadOnlyTextField("Total Amount ($)", String.valueOf(audit.getTotal()));

            // Create a close button for the dialog
            Button closeButton = new Button("Close");
            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            closeButton.addClickListener(e -> detailsDialog.close());

            H1 header = new H1("Audit Details");

            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.add(header);

            // Add components to the dialog layout
            dialogLayout.add(
                    auditIdField,
                    auditFullName,
                    auditAddress,
                    auditPhone,
                    auditEmail,
                    auditCar,
                    auditPeriod,
                    auditRentalDate,
                    auditReturnDate,
                    auditRentPriceTVA,
                    auditFines,
                    auditTotalAmount,
                    closeButton
            );
            dialogLayout.setAlignItems(Alignment.CENTER);
            dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);
            dialogLayout.setSpacing(true);

            detailsDialog.add(dialogLayout);
            detailsDialog.setWidth("400px");
            detailsDialog.open();
        }
    }

    private void updateList() {
        try {
            List<Audit> audits = auditService.getAllAudits();

            // Sort the list by audit ID
            audits.sort(Comparator.comparing(Audit::getAuditid));

            grid.setItems(audits);
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            String errorMessage = "";

            for (ConstraintViolation<?> violation : violations) {
                errorMessage += violation.getMessage() + "; ";
            }

            Notification.show("Error saving rent: " + errorMessage);
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

    private TextField createReadOnlyTextField(String label, String value) {
        TextField textField = new TextField(label);
        textField.setValue(value);
        textField.setReadOnly(true);
        return textField;
    }
}