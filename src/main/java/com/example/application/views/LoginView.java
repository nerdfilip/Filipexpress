package com.example.application.views;

import com.example.application.entities.UserAccount;
import com.example.application.repositories.UserAccountRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("")
@PageTitle("Filip Express Rentals")
public class LoginView extends VerticalLayout {

    @Autowired
    public LoginView(UserAccountRepository userAccountRepository) {

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // White card with shadow
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.getStyle().set("box-shadow", "0 0 10px rgba(0, 0, 0, 0.1)"); // Updated box-shadow
        cardLayout.getStyle().set("border-radius", "8px");
        cardLayout.setPadding(true);
        cardLayout.setSpacing(true);
        cardLayout.setWidth("400px");
        cardLayout.setAlignItems(Alignment.CENTER);
        cardLayout.getStyle().set("padding-top", "10px"); // 10px padding at the top using inline CSS
        cardLayout.getStyle().set("padding-bottom", "10px"); // 10px padding at the bottom using inline CSS
        add(cardLayout);

        // Header
        H2 title = new H2("Authentication");
        cardLayout.add(title);

        // Form
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        Button loginButton = new Button("Login");
        loginButton.getElement().getThemeList().add("primary");

        loginButton.addClickListener(e -> {
            // Get the entered username and password
            String enteredUsername = usernameField.getValue();
            String enteredPassword = passwordField.getValue();

            // Verify credentials
            Optional<UserAccount> userAccountOptional = userAccountRepository.verifyCredentials(enteredUsername, enteredPassword);

            if (userAccountOptional.isPresent()) {
                // Credentials are valid, perform login logic here
                Notification.show("Welcome " + enteredUsername + "!");
                UI.getCurrent().navigate("menu");
            } else {
                // Credentials are not valid, show an error message or handle accordingly
                Notification.show("Invalid username or password!");
            }
        });

        // Apply styling directly to components
        passwordField.getStyle().set("margin-bottom", "10px");
        loginButton.getStyle().set("margin-bottom", "30px");
        title.getStyle().set("margin-top", "30px");

        cardLayout.add(usernameField, passwordField, loginButton);
    }
}
