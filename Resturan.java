package restuarant;

import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void displayItem() {
        System.out.println("Name: " + name + " | Price: " + price);
    }
}

class Table {
    private int tableNumber;
    private boolean isOccupied;
    private ArrayList<Order> orders;
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.isOccupied = false;
//        this.orders = new ArrayList<>();  // No need to use the setter here
    }


    public int getTableNumber() { return tableNumber; }
    public boolean isOccupied() { return isOccupied; }

    public void occupyTable() { this.isOccupied = true; }
    public void vacateTable() { this.isOccupied = false; getOrders().clear(); }

    public void addOrder(Order order) {
        getOrders().add(order);
    }

    public void displayOrders() {
        System.out.println("Table " + tableNumber + " Orders:");
        for (Order order : getOrders()) {
            order.displayOrder();
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}

class Order {
    private MenuItem menuItem;
    private int quantity;

    public Order(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public void displayOrder() {
        System.out.println(menuItem.getName() + " x " + quantity + " = $" + (menuItem.getPrice() * quantity));
    }

    public double getOrderTotal() {
        return menuItem.getPrice() * quantity;
    }
}

class RestaurantManagement {
    private ArrayList<MenuItem> menu;
    private ArrayList<Table> tables;

    public RestaurantManagement(int numberOfTables) {
        this.menu = new ArrayList<>();
        this.tables = new ArrayList<>();
        for (int i = 1; i <= numberOfTables; i++) {
            tables.add(new Table(i));
        }
    }

    // Add menu item
    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    // Display the menu
    public void displayMenu() {
        System.out.println("Restaurant Menu:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.print((i + 1) + ". ");
            menu.get(i).displayItem();
        }
    }

    // Place an order
    public void placeOrder(int tableNumber, int menuIndex, int quantity) {
        if (tableNumber > 0 && tableNumber <= tables.size()) {
            Table table = tables.get(tableNumber - 1);
            if (!table.isOccupied()) {
                table.occupyTable();
            }

            MenuItem item = menu.get(menuIndex - 1);
            Order order = new Order(item, quantity);
            table.addOrder(order);

            System.out.println("Order placed: " + item.getName() + " x " + quantity);
        } else {
            System.out.println("Invalid table number.");
        }
    }

    // Display orders for a table
    public void displayTableOrders(int tableNumber) {
        if (tableNumber > 0 && tableNumber <= tables.size()) {
            Table table = tables.get(tableNumber - 1);
            if (table.isOccupied()) {
                table.displayOrders();
            } else {
                System.out.println("Table " + tableNumber + " is not occupied.");
            }
        } else {
            System.out.println("Invalid table number.");
        }
    }

    // Checkout a table
    public void checkoutTable(int tableNumber) {
        if (tableNumber > 0 && tableNumber <= tables.size()) {
            Table table = tables.get(tableNumber - 1);
            if (table.isOccupied()) {
                double total = 0;
                for (Order order : table.getOrders()) {
                    total += order.getOrderTotal();
                }

                System.out.println("Table " + tableNumber + " total: $" + total);
                table.vacateTable();
            } else {
                System.out.println("Table " + tableNumber + " is not occupied.");
            }
        } else {
            System.out.println("Invalid table number.");
        }
    }

    // Display table status
    public void displayTableStatus() {
        System.out.println("Table Status:");
        for (Table table : tables) {
            String status = table.isOccupied() ? "Occupied" : "Available";
            System.out.println("Table " + table.getTableNumber() + ": " + status);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Restaurant Management System");
        System.out.print("Enter the number of tables in the restaurant: ");
        int numberOfTables = sc.nextInt();
        RestaurantManagement restaurant = new RestaurantManagement(numberOfTables);

        // Add some menu items
        restaurant.addMenuItem(new MenuItem("Pasta", 12.99));
        restaurant.addMenuItem(new MenuItem("Pizza", 9.99));
        restaurant.addMenuItem(new MenuItem("Burger", 7.99));
        restaurant.addMenuItem(new MenuItem("Salad", 5.99));
        restaurant.addMenuItem(new MenuItem("Soda", 2.49));

        boolean running = true;
        while (running) {
            System.out.println("\n1. Display Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Display Table Orders");
            System.out.println("4. Checkout Table");
            System.out.println("5. Display Table Status");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    restaurant.displayMenu();
                    break;

                case 2:
                    restaurant.displayMenu();
                    System.out.print("Enter table number: ");
                    int tableNumber = sc.nextInt();
                    System.out.print("Enter menu item number: ");
                    int menuIndex = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    restaurant.placeOrder(tableNumber, menuIndex, quantity);
                    break;

                case 3:
                    System.out.print("Enter table number: ");
                    int tableNumToDisplay = sc.nextInt();
                    restaurant.displayTableOrders(tableNumToDisplay);
                    break;

                case 4:
                    System.out.print("Enter table number to checkout: ");
                    int tableNumToCheckout = sc.nextInt();
                    restaurant.checkoutTable(tableNumToCheckout);
                    break;

                case 5:
                    restaurant.displayTableStatus();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }
}