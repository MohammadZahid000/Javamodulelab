package test;

import java.util.Scanner;

import entity.Product;
import entity.customer;
import service.Shop;

public class ShopManagementSystem {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Shop shop = new Shop();

		while (true) {
			System.out.println("\n **SHOP MANAGEMENT SYSTEM** ");
			System.out.println("1. Register New Customer");
			System.out.println("2. Add Product");
			System.out.println("3. List Product");
			System.out.println("4. Delete Product");
			System.out.println("5. Sell Product");
			System.out.println("6. Exit.....");
			System.out.println("Enter Your Choice");
			int choice = scanner.nextInt();
			
		
			switch (choice) {
			
			case 1:
				System.out.println("Enter Customer ID Number:");
				int customerid=scanner.nextInt();
				scanner.nextLine();
				
				System.out.println("Enter Customer Name:");
				String customerName=scanner.nextLine();
				
				System.out.println("Enter Customer Number ");
				double contactNumber=scanner.nextDouble();
				customer Customer=new customer(customerid, customerName, contactNumber);
				shop.addcustomer(Customer);
				break;
				
			case 2:
				System.out.print("Enter Product ID: ");
				int id = scanner.nextInt();
				scanner.nextLine(); 

				System.out.print("Enter Product Name: ");
				String name = scanner.nextLine();

				System.out.print("Enter Product Price: ");
				double price = scanner.nextDouble();

				System.out.print("Enter Product Quantity: ");
				int quantity = scanner.nextInt();

				Product product = new Product(id, name, price, quantity);
				shop.addProduct(product);
				break;

			case 3:

				shop.listProducts();
				break;

			case 4:

				System.out.print("Enter Product ID to delete: ");
				int productIdToDelete = scanner.nextInt();
				shop.deleteProduct(productIdToDelete);
				break;

			case 5:

				System.out.print("Enter Product ID to sell: ");
				int productIdToSell = scanner.nextInt();
				System.out.print("Enter quantity to sell: ");
				int quantityToSell = scanner.nextInt();
     			shop.sellProduct(productIdToSell, quantityToSell);
				break;

			case 6:

				System.out.println("Exiting...");
				return;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}
}
