package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("menu")
@PageTitle("Filip Express Rentals")
public class MenuView extends VerticalLayout {

    public MenuView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // White card with shadow
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.getStyle().set("box-shadow", "0 0 10px rgba(0, 0, 0, 0.1)");
        cardLayout.getStyle().set("border-radius", "8px");
        cardLayout.setPadding(true);
        cardLayout.setSpacing(true);
        cardLayout.setWidth("400px");
        cardLayout.setAlignItems(Alignment.CENTER);

        add(cardLayout);

        // Header
        H2 title = new H2("Menu Selector");
        title.getStyle().set("margin-bottom", "10px");
        title.getStyle().set("margin-top", "10px");
        cardLayout.add(title);

        // Buttons
        Button button1 = new Button("Client Panel", new Icon(VaadinIcon.USER));
        button1.getElement().getThemeList().add("primary");
        button1.addClickListener(e -> UI.getCurrent().navigate("client"));

        Button button2 = new Button("Car Panel", new Icon(VaadinIcon.CAR));
        button2.getElement().getThemeList().add("primary");
        button2.addClickListener(e -> UI.getCurrent().navigate("car"));

        Button button3 = new Button("Rental Panel", new Icon(VaadinIcon.DOLLAR));
        button3.getElement().getThemeList().add("primary");
        button3.addClickListener(e -> UI.getCurrent().navigate("rent"));

        Button button4 = new Button("Audit Panel", new Icon(VaadinIcon.CLIPBOARD_TEXT));
        button4.getElement().getThemeList().add("primary");
        button4.addClickListener(e -> UI.getCurrent().navigate("audit"));

        Button button5 = new Button("About", new Icon(VaadinIcon.QUESTION_CIRCLE_O));
        button5.getElement().getThemeList().add("primary");
        button5.addClickListener(e -> UI.getCurrent().navigate("about"));

        Button button6 = new Button("Log Out");
        button6.addClickListener(e -> {
            UI.getCurrent().navigate("");
            Notification.show("You have been logged out, have a nice day!");
        });

        // Set the width for all buttons
        String buttonWidth = "200px"; // Adjust the width as needed
        button1.setWidth(buttonWidth);
        button2.setWidth(buttonWidth);
        button3.setWidth(buttonWidth);
        button4.setWidth(buttonWidth);
        button5.setWidth(buttonWidth);
        button6.setWidth(buttonWidth);

        // Add buttons to the card layout
        cardLayout.add(button1, button2, button3, button4, button5, button6);

        // Apply spacing to buttons
        button1.getStyle().set("margin-bottom", "10px");
        button2.getStyle().set("margin-bottom", "10px");
        button3.getStyle().set("margin-bottom", "10px");
        button4.getStyle().set("margin-bottom", "10px");
        button5.getStyle().set("margin-bottom", "10px");
        button6.getStyle().set("margin-bottom", "10px");
    }
}