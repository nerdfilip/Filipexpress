package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("about")
@PageTitle("Filip Express | About")
public class AboutView extends VerticalLayout {

    public AboutView() {
        H1 header = new H1("Welcome to Filip Express Rentals!");

        Button menuButton = new Button(VaadinIcon.MENU.create());
        menuButton.getElement().getThemeList().add("primary");
        menuButton.addClickListener(e -> UI.getCurrent().navigate("menu"));

        HorizontalLayout headerAndMenuLayout = new HorizontalLayout(header, menuButton);
        headerAndMenuLayout.setWidthFull();
        headerAndMenuLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Divider, can't put same divider
        Hr divider1 = new Hr();
        Hr divider2 = new Hr();
        Hr divider3 = new Hr();
        divider1.getStyle().set("border", "1px solid #ccc");
        divider2.getStyle().set("border", "1px solid #ccc");
        divider3.getStyle().set("border", "1px solid #ccc");

        Paragraph description = new Paragraph(" Providing a seamless and intuitive experience through three primary views: Clients, Cars, Rents, and Audit. "
                + "This comprehensive application empowers administrators with full CRUD functionality, ensuring a hassle-free and efficient management system."
                + "In addition to the core functionalities, our application is designed with user-friendly features, "
                + "making it easy for administrators to navigate and perform tasks efficiently. ");
        description.getStyle().set("font-size", "18px");

        // Clients View Section
        H3 clientsHeader = new H3("CLIENTS VIEW");

        Paragraph clientsDescription = new Paragraph("Effortlessly manage your client database with the Clients view. "
                + "Create, update, and delete client profiles, capturing essential information such as contact details, identification, and rental history. "
                + "Stay organized and responsive with a user-friendly interface that allows you to effortlessly add new clients, modify existing information, and streamline communication.");
        clientsDescription.getStyle().set("font-size", "18px");

        Paragraph clientsRestrictionsText = new Paragraph("Restrictions: "
                + "Can't ADD or UPDATE a client with the same full name as another client ; "
                + "Can't ADD or UPDATE a client with the same e-mail as another client ; "
                + "Can't ADD or UPDATE a client with the same phone number as another client ; ");
        clientsRestrictionsText.getStyle().set("font-size", "16px").set("font-style", "italic");

        VerticalLayout clientsSectionLayout = new VerticalLayout(clientsHeader, clientsDescription, clientsRestrictionsText);
        clientsSectionLayout.setAlignItems(FlexComponent.Alignment.START);

        // Cars View Section
        H3 carsHeader = new H3("CARS VIEW");

        Paragraph carsDescription = new Paragraph("Explore the Cars view for a comprehensive overview of your vehicle fleet. "
                + "Add new cars, update specifications, and remove outdated information with ease. "
                + "Our intuitive interface allows you to track maintenance schedules and categorize vehicles for quick identification. "
                + "With the Cars view, maintaining an up-to-date and diverse fleet has never been more straightforward.");
        carsDescription.getStyle().set("font-size", "18px");

        Paragraph carsRestrictionsText = new Paragraph("Restrictions: "
                + "Manufacturing year must be greater than or equal to 1885 ; "
                + "Engine capacity must be greater than or equal to 0. ");
        carsRestrictionsText.getStyle().set("font-size", "16px").set("font-style", "italic");

        VerticalLayout carsSectionLayout = new VerticalLayout(carsHeader, carsDescription, carsRestrictionsText);
        carsSectionLayout.setAlignItems(FlexComponent.Alignment.START);

        // Rentals View Section
        H3 rentalsHeader = new H3("RENTALS VIEW");

        Paragraph rentalsDescription = new Paragraph("Efficiently manage all rental transactions in the Rents view. "
                + "From reservation to return, this feature enables you to track the entire rental lifecycle. "
                + "Create new rental agreements, update reservation details, and close completed transactions seamlessly. "
                + "Dive into detailed rental histories, track payment status, and ensure a smooth process for both clients and administrators.");
        rentalsDescription.getStyle().set("font-size", "18px");

        Paragraph rentalsRestrictionsText = new Paragraph("Restrictions: "
                + "Make sure that the customer or car exists in the database ; "
                + "Be sure that the car is not rented, when you create a new rental ; "
                + "Period must be greater than or equal to 1 ; "
                + "Rent price must be greater than or equal to $1000. ");
        rentalsRestrictionsText.getStyle().set("font-size", "16px").set("font-style", "italic");

        VerticalLayout rentalsSectionLayout = new VerticalLayout(rentalsHeader, rentalsDescription, rentalsRestrictionsText);
        rentalsSectionLayout.setAlignItems(FlexComponent.Alignment.START);

        // Audit View Section
        H3 auditHeader = new H3("AUDIT");

        Paragraph auditDescription = new Paragraph("This section provides a read-only view of rental transactions in the Rents view. "
                + "From reservation to return, this feature enables you to track the entire rental lifecycle. "
                + "View detailed rental histories, track payment status, and ensure a smooth process administrators. "
                + "Please note that the content in this section is read-only. Deleted rentals are recorded as returns of the cars.");
        auditDescription.getStyle().set("font-size", "18px");

        VerticalLayout auditSectionLayout = new VerticalLayout(auditHeader, auditDescription);
        auditSectionLayout.setAlignItems(FlexComponent.Alignment.START);

        Paragraph scope = new Paragraph("With our Car Rental Application, we aim to simplify the rental process, "
                + "providing a robust platform that meets the diverse needs of administrators. "
                + "Enjoy the power of efficient CRUD operations coupled with a user-friendly design, "
                + "ensuring a smooth and streamlined experience in the world of car rentals.");
        scope.getStyle().set("font-size", "18px");

        // Footer
        Div footer = new Div();
        footer.add(new Paragraph("COPYRIGHT (C) 2023 FILIP EXPRESS RENTALS. ALL RIGHTS RESERVED."));
        footer.getStyle().set("text-align", "center").set("margin-top", "10px").set("color", "#888");

        // Add all sections to the main layout
        add(headerAndMenuLayout, description, clientsSectionLayout, divider1, carsSectionLayout, divider2,
                rentalsSectionLayout, divider3, auditSectionLayout, scope, footer);

        // Set layout properties
        setWidthFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
    }
}
