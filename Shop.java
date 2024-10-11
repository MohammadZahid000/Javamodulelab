package service;

import java.util.ArrayList;

import entity.Product;
import entity.customer;

public class Shop {

	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<customer> Customer = new ArrayList<>();

	
	public void addcustomer(customer customer) {
        customer.add(customer);
        try{
        	customer.getCustomerid();
        
        
        System.out.println("Customer Registration Successfully.");
    } 
	catch (Exception e){
    	System.out.println("Error"+ e.getMessage());
    }
}
    
    public void addProduct(Product product) {
        products.add(product);
        try{
        	product.getId();
        
        
        System.out.println("Product added successfully.");
    } 
        catch (Exception e){
        System.out.println("Error:"+ e.getMessage());
        }
    }

    
    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Available Products:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
        	public void sellProduct(int productId, int quantity) {
        		try {
        	    for (Product product : products) {
        	        if (product.getId() == productId) {
        	            if (product.getQuantity() >= quantity) {
        	                product.setQuantity(product.getQuantity() - quantity);
        	                System.out.println("Sold " + quantity + " units of " + product.getName() + ".");
        	                System.out.println("Total price: Rs." + (product.getPrice() * quantity));
        	            } else {
        	                System.out.println("Insufficient stock!");
        	            }
        	            return;
        	        }
        	    }
        	    
        	    System.out.println("Product not found.");
        }
        		catch (Exception e){
        	        System.out.println("Error in Selling Product:"+ e.getMessage());
        	        }
        	}
        		
        
    public void deleteProduct(int productId) {
        boolean found = false;
        for (Product product : products) {
            if (product.getId() == productId) {
                products.remove(product);
                found = true;
                System.out.println("Product removed successfully.");
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    

	

		
}

