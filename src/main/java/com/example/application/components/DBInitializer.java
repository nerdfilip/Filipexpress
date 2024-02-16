package com.example.application.components;

import com.example.application.entities.Car;
import com.example.application.entities.Client;
import com.example.application.entities.Rent;
import com.example.application.entities.UserAccount;
import com.example.application.repositories.CarRepository;
import com.example.application.repositories.ClientRepository;
import com.example.application.repositories.RentRepository;
import com.example.application.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DBInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final UserAccountRepository userAccountRepository;
    private final CarRepository carRepository;
    private final RentRepository rentRepository;

    @Autowired
    public DBInitializer(ClientRepository clientRepository, UserAccountRepository userAccountRepository,
                         CarRepository carRepository, RentRepository rentRepository) {
        this.clientRepository = clientRepository;
        this.userAccountRepository = userAccountRepository;
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeClients();
        initializeUserAccounts();
        initializeCars();
        initializeRents();
    }

    private void initializeClients() {
        if (clientRepository.count() == 0) {
            Client client1 = new Client(null, "Florea Stefan", "Iasi, Stefan cel Mare", "0770 453 213", "stefan.florea@gmail.com");
            Client client2 = new Client(null, "Lupu Raluca", "Iasi, Lupu Vasile", "0743 145 333", "lupu.raluca@gmail.com");
            Client client3 = new Client(null, "Ionesi David", "Iasi, Alee Copou", "0765 987 654", "david.ionesi@yahoo.com");
            Client client4 = new Client(null, "Strugari Ionut", "Iasi, Piata Unirii", "0732 456 789", "ionut.strugari@yahoo.com");
            Client client5 = new Client(null, "Lapa Alexandru", "Iasi, Strada Arcu", "0711 222 333", "alex.lapa@gmail.com");
            Client client6 = new Client(null, "Lita Cosmin", "Iasi, Strada Alba", "0712 345 678", "cosmin.lita@gmail.com");
            Client client7 = new Client(null, "Laur Teodor", "Iasi, Strada Arcu", "0721 987 654", "teodor.laur@yahoo.com");
            Client client8 = new Client(null, "Balan Rares", "Iasi, Piata Unirii", "0730 654 321", "rares.balan@gmail.com");
            Client client9 = new Client(null, "Dominte Miruna", "Iasi, Stefan cel Mare", "0761 123 456", "miruna.dominte@yahoo.com");
            Client client10 = new Client(null, "Grigorcea Lorena", "Iasi, Lupu Vasile", "0750 987 654", "lorena.grigorcea@gmail.com");
            Client client11 = new Client(null, "Brasov Tudor", "Iasi, Alee Copou", "0720 456 789", "tudor.brasov@yahoo.com");
            Client client12 = new Client(null, "Ipiroti Vlad", "Iasi, Piata Unirii", "0710 123 456", "vlad.ipiroti@gmail.com");
            Client client13 = new Client(null, "Bujoi Gabriela", "Iasi, Alee Copou", "0740 654 321", "gabriela.bujoi@yahoo.com");
            Client client14 = new Client(null, "Ciobanu Uliana", "Iasi, Stefan cel Mare", "0751 123 456", "uliana.ciobanu@gmail.com");
            Client client15 = new Client(null, "Pantiru Bianca", "Iasi, Alee Copou", "0731 654 321", "bianca.pantiru@yahoo.com");
            Client client16 = new Client(null, "Danila Daniel", "Iasi, Lupu Vasile", "0722 123 456", "daniel.danila@gmail.com");

            clientRepository.saveAll(List.of(client1, client2, client3, client4, client5,
                    client6, client7, client8, client9, client10,
                    client11, client12, client13, client14, client15, client16));
        }
    }


    private void initializeUserAccounts() {
        if (userAccountRepository.count() == 0) {
            UserAccount user1 = new UserAccount(null, "Administrator", "Adm*123");
            UserAccount user2 = new UserAccount(null, "Manager", "Manager123");

            userAccountRepository.saveAll(List.of(user1, user2));
        }
    }

    private void initializeCars() {
        if (carRepository.count() == 0) {
            Car car1 = new Car(null, "Sedan", "Petrol", "Automatic", "Toyota", "Camry", 2022, 2.5, 5);
            Car car2 = new Car(null, "SUV", "Diesel", "Manual", "Ford", "Escape", 2021, 2.0, 5);
            Car car3 = new Car(null, "Convertible", "Diesel", "Automatic", "BMW", "4 Series", 2023, 2.0, 4);
            Car car4 = new Car(null, "Crossover", "Hybrid", "Automatic", "Lexus", "RX", 2022, 3.5, 4);
            Car car5 = new Car(null, "Pickup Truck", "Diesel", "Manual", "Chevrolet", "Silverado", 2021, 5.3, 5);
            Car car6 = new Car(null, "SUV", "Electric", "Automatic", "Audi", "E-tron", 2023, 3.0, 4);
            Car car7 = new Car(null, "Sedan", "Petrol", "Automatic", "Mercedes", "AMG E63", 2022, 4.0, 2);
            Car car8 = new Car(null, "Hatchback", "Hybrid", "Automatic", "Toyota", "Prius", 2022, 1.8, 4);
            Car car9 = new Car(null, "Supercar", "Petrol", "Automatic", "Lamborghini", "Huracán", 2021, 5.2, 2);
            Car car10 = new Car(null, "Coupe", "Electric", "Automatic", "Porsche", "Taycan", 2023, 4.0, 4);
            Car car11 = new Car(null, "SUV", "Petrol", "Automatic", "Honda", "CR-V", 2022, 2.4, 5);
            Car car12 = new Car(null, "Sedan", "Diesel", "Manual", "Volkswagen", "Jetta", 2021, 1.9, 5);
            Car car13 = new Car(null, "Hatchback", "Electric", "Automatic", "Nissan", "Leaf", 2023, 110.0, 4);
            Car car14 = new Car(null, "Crossover", "Hybrid", "Automatic", "Subaru", "Outback", 2022, 2.5, 5);
            Car car15 = new Car(null, "Convertible", "Petrol", "Automatic", "Maserati", "GranCabrio", 2021, 4.7, 4);
            Car car16 = new Car(null, "Coupe", "Diesel", "Manual", "Alfa Romeo", "Giulia", 2023, 2.2, 4);
            Car car17 = new Car(null, "SUV", "Electric", "Automatic", "Tesla", "Model X", 2022,  5.5, 7);
            Car car18 = new Car(null, "Pickup Truck", "Petrol", "Manual", "Ram", "1500", 2021, 5.7, 6);
            Car car19 = new Car(null, "Sedan", "Hybrid", "Automatic", "Hyundai", "Sonata", 2022, 2.0, 5);
            Car car20 = new Car(null, "Supercar", "Petrol", "Automatic", "Ferrari", "488 GTB", 2023, 3.9, 2);

            carRepository.saveAll(List.of(car1, car2, car3, car4, car5, car6, car7, car8, car9, car10,
                    car11, car12, car13, car14, car15, car16, car17, car18, car19, car20));
        }
    }

    private void initializeRents() {
        if (rentRepository.count() == 0) {
            Rent rent1 = new Rent();
            rent1.setCar(carRepository.findById(1).orElse(null));
            rent1.setClient(clientRepository.findById(1).orElse(null));
            rent1.setRentaldate(LocalDate.now());
            rent1.setPeriod(7);
            rent1.setRentprice(1200.00);
            rentRepository.save(rent1);

            Rent rent2 = new Rent();
            rent2.setCar(carRepository.findById(2).orElse(null));
            rent2.setClient(clientRepository.findById(2).orElse(null));
            rent2.setRentaldate(LocalDate.now().plusDays(3));
            rent2.setPeriod(5);
            rent2.setRentprice(2000.50);
            rentRepository.save(rent2);

            Rent rent3 = new Rent();
            rent3.setCar(carRepository.findById(3).orElse(null));
            rent3.setClient(clientRepository.findById(3).orElse(null));
            rent3.setRentaldate(LocalDate.of(2024, 1, 5));
            rent3.setPeriod(5);
            rent3.setRentprice(2750);
            rentRepository.save(rent3);

            Rent rent4 = new Rent();
            rent4.setCar(carRepository.findById(4).orElse(null));
            rent4.setClient(clientRepository.findById(4).orElse(null));
            rent4.setRentaldate(LocalDate.now().plusDays(2));
            rent4.setPeriod(3);
            rent4.setRentprice(1500.75);
            rentRepository.save(rent4);

            Rent rent5 = new Rent();
            rent5.setCar(carRepository.findById(5).orElse(null));
            rent5.setClient(clientRepository.findById(5).orElse(null));
            rent5.setRentaldate(LocalDate.of(2024, 1, 10));
            rent5.setPeriod(7);
            rent5.setRentprice(1800.25);
            rentRepository.save(rent5);

            Rent rent6 = new Rent();
            rent6.setCar(carRepository.findById(6).orElse(null));
            rent6.setClient(clientRepository.findById(6).orElse(null));
            rent6.setRentaldate(LocalDate.now().plusDays(4));
            rent6.setPeriod(6);
            rent6.setRentprice(2200.50);
            rentRepository.save(rent6);

            Rent rent7 = new Rent();
            rent7.setCar(carRepository.findById(7).orElse(null));
            rent7.setClient(clientRepository.findById(7).orElse(null));
            rent7.setRentaldate(LocalDate.of(2024, 1, 15));
            rent7.setPeriod(4);
            rent7.setRentprice(1600.75);
            rentRepository.save(rent7);

            Rent rent8 = new Rent();
            rent8.setCar(carRepository.findById(8).orElse(null));
            rent8.setClient(clientRepository.findById(8).orElse(null));
            rent8.setRentaldate(LocalDate.now().plusDays(5));
            rent8.setPeriod(5);
            rent8.setRentprice(1900.25);
            rentRepository.save(rent8);

            Rent rent9 = new Rent();
            rent9.setCar(carRepository.findById(9).orElse(null));
            rent9.setClient(clientRepository.findById(9).orElse(null));
            rent9.setRentaldate(LocalDate.of(2024, 1, 20));
            rent9.setPeriod(7);
            rent9.setRentprice(2500.00);
            rentRepository.save(rent9);

            Rent rent10 = new Rent();
            rent10.setCar(carRepository.findById(10).orElse(null));
            rent10.setClient(clientRepository.findById(10).orElse(null));
            rent10.setRentaldate(LocalDate.now().plusDays(6));
            rent10.setPeriod(4);
            rent10.setRentprice(1750.50);
            rentRepository.save(rent10);

            rentRepository.saveAll(List.of(rent1, rent2, rent3, rent4, rent5, rent6, rent7, rent8, rent9, rent10));
        }
    }

    // Can't initialize audit record because of notification and we don't have any method to save audit, only deleted rentals.
}
