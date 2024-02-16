package com.example.application.views;

import com.example.application.entities.Client;
import com.example.application.services.ClientService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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

@Route("client")
@PageTitle("Filip Express | Clients")
public class ClientView extends VerticalLayout {

    private final H1 header = new H1("CLIENT Panel");
    private final Grid<Client> grid = new Grid<>(Client.class);

    private final Button addButton = new Button("Add Client", new Icon(VaadinIcon.PLUS));
    private final Button refreshButton = new Button("Refresh", new Icon(VaadinIcon.REFRESH));
    private final Button deleteButton = new Button("Delete", new Icon(VaadinIcon.TRASH));
    private final Button editButton = new Button("Edit", new Icon(VaadinIcon.EDIT));

    private final Dialog clientDetailsDialog = new Dialog();
    private final TextField fullname = new TextField("Full Name");
    private final TextField address = new TextField("Address");
    private final TextField phone = new TextField("Phone Number");
    private final TextField email = new TextField("E-mail");

    private final Button confirmButton = new Button("Confirm");

    private final Binder<Client> binder = new Binder<>(Client.class);

    private final TextField searchField = new TextField("Search by Name");

    private final ClientService clientService;

    public ClientView(ClientService clientService) {
        this.clientService = clientService;

        configureGrid();
        configureForm();

        addButton.addClickListener(e -> openAddClientDialog());
        refreshButton.addClickListener(e -> updateList());
        deleteButton.addClickListener(e -> deleteClient());
        editButton.addClickListener(e -> openEditClientDialog());

        // Create the menuButton
        Button menuButton = new Button(new Icon(VaadinIcon.MENU), e -> UI.getCurrent().navigate("menu"));
        menuButton.getElement().getThemeList().add("primary");

        // Add the search field
        searchField.addValueChangeListener(event -> {
            String searchTerm = event.getValue();
            if (searchTerm != null && !searchTerm.isEmpty()) {
                // Perform search based on the client's name
                grid.setItems(clientService.searchClientsByName(searchTerm));
            } else {
                // Update the list with all clients when the search field is empty
                updateList();
            }
        });

        // Configure layout
        HorizontalLayout buttonRow = new HorizontalLayout(refreshButton, addButton, deleteButton, editButton);
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

        // Make more cool
        grid.addColumn(Client::getClientid).setHeader("Client ID").setSortProperty("clientid");
        grid.addColumn(Client::getFullname).setHeader("Full Name").setSortProperty("fullname");
        grid.addColumn(Client::getAddress).setHeader("Address").setSortProperty("address");
        grid.addColumn(Client::getPhone).setHeader("Phone Number").setSortProperty("phone");
        grid.addColumn(Client::getEmail).setHeader("E-mail").setSortProperty("email");

        grid.asSingleSelect().addValueChangeListener(event -> {
            deleteButton.setEnabled(event.getValue() != null);
            editButton.setEnabled(event.getValue() != null);
        });

        grid.setHeight("500px");
    }

    private void configureForm() {
        binder.bind(fullname, Client::getFullname, Client::setFullname);
        binder.bind(address, Client::getAddress, Client::setAddress);
        binder.bind(phone, Client::getPhone, Client::setPhone);
        binder.bind(email, Client::getEmail, Client::setEmail);

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> clientDetailsDialog.close());
        cancelButton.getElement().getThemeList().add("tertiary");

        confirmButton.addClickListener(e -> {
            saveClient();
            clientDetailsDialog.close();
        });
        confirmButton.getElement().getThemeList().add("primary");
        confirmButton.setText("Confirm");

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        H2 formHeader = new H2("Add new client");

        VerticalLayout formLayout = new VerticalLayout(
                formHeader,
                fullname, address, phone, email,
                new HorizontalLayout(cancelButton, confirmButton)
        );

        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        clientDetailsDialog.add(formLayout);
        clientDetailsDialog.setWidth("400px");

        // Update the form header dynamically when editing
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                formHeader.setText("Update client");
            } else {
                formHeader.setText("Add new client");
            }
        });

        addButton.addClickListener(e -> {
            formHeader.setText("Add new client");
            binder.setBean(new Client());
            clientDetailsDialog.open();
        });

        editButton.addClickListener(e -> {
            formHeader.setText("Update client");
            Client client = grid.asSingleSelect().getValue();
            if (client != null) {
                binder.setBean(client);
                clientDetailsDialog.open();
            }
        });
    }

    private void saveClient() {
        try {
            Client client = binder.getBean();
            if (client != null) {
                // Assuming clientService is not null and properly configured
                clientService.saveClient(client);
                updateList();
                Notification.show("Client saved successfully with ID: " + client.getClientid());
            } else {
                Notification.show("Error saving client: Client is null");
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

    private void deleteClient() {
        try {
            Client client = grid.asSingleSelect().getValue();
            if (client != null) {
                // Create a custom confirmation dialog
                Dialog confirmationDialog = new Dialog();
                confirmationDialog.setCloseOnOutsideClick(false);

                Button cancelButton = new Button("Cancel", event -> confirmationDialog.close());
                Button confirmButton = new Button("Delete", event -> {
                    try {
                        // User confirmed, perform delete
                        clientService.deleteClient(client.getClientid());
                        updateList();
                        Notification.show("Client deleted successfully");
                        confirmationDialog.close();
                    } catch (DataIntegrityViolationException e) {
                        Notification.show("Error deleting client: This client is associated with rental records");
                    }
                });

                // Add a custom style to the "Delete" button
                confirmButton.getElement().getThemeList().add("primary");

                VerticalLayout layout = new VerticalLayout(
                        new H4("Confirm Delete"),
                        new Paragraph("Are you sure you want to delete the client \"" +
                                client.getFullname() + "\" from the database?"),
                        new HorizontalLayout(cancelButton, confirmButton)
                );

                layout.setAlignItems(Alignment.CENTER);
                layout.setJustifyContentMode(JustifyContentMode.CENTER);

                confirmationDialog.add(layout);
                confirmationDialog.open();
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


    private void openAddClientDialog() {
        binder.setBean(new Client());
        clientDetailsDialog.open();
    }

    private void openEditClientDialog() {
        Client client = grid.asSingleSelect().getValue();
        if (client != null) {
            binder.setBean(client);
            clientDetailsDialog.open();
        }
    }

    private void updateList() {
        try {
            List<Client> clients = clientService.getAllClients();

            // Sort the list by client ID
            clients.sort(Comparator.comparing(Client::getClientid));

            grid.setItems(clients);
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
}