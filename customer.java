package entity;

public class customer  {
private int customerid;
private String customerName;
private double contactNumber;
public customer(int customerid, String customerName, double contactNmae) {
	this.customerid = customerid;
	this.customerName = customerName;
	this.contactNumber = contactNmae;
}
public int getCustomerid() {
	return customerid;
}
public String getCustomerName() {
	return customerName;
}
public double getContactNmae() {
	return contactNumber;
}

public String toString() {
	return "customer [customerid=" + customerid + ", customerName=" + customerName + ", contactNmae=" + contactNumber
			+ "]";
}
public int add(customer customer) {
	return customerid;
	
}

}

