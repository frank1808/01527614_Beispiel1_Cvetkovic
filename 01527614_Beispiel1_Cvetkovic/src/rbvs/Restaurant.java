//	Dusan Cvetkovic 01527614 
package rbvs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import rbvs.product.CompositeProduct;
import rbvs.product.ExtendedProduct;
import rbvs.product.IProduct;
import rbvs.product.SimpleProduct;

public class Restaurant {
	private String name;
	private List<Table> tables = new ArrayList<>();
	private List<IProduct> productAssortment = new ArrayList<>();
	private List<Order> orderHistory = new ArrayList<>();
	private long uniqueOrderIdentifier = 0;
	
	public Restaurant(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean createTable(String tableIdentifier) {
		for ( Table c : this.tables ) {
			if (c.getTableIdentifier() == tableIdentifier) {
				return false;
			}	
		}
		if ( this.tables.add(new Table(tableIdentifier)) )
			return true;
		return false;
	}
	
	public List<String> getTableIdentifiers() {
		List<String> ret = new ArrayList<>();
		for ( Table c : this.tables ) {
			ret.add(c.getTableIdentifier());
		}
		return ret;
	}
	
	public Table getSpecificTable(String identifier) {
		for ( Table c : this.tables ) {
			if ( c.getTableIdentifier() == identifier ) {
				return c;
			}
		}
		return null;
	}
	
	public boolean addProduct(IProduct product) throws DuplicateProductException {
		if ( product == null )
			return false;
		for ( IProduct c : this.productAssortment ) {
			if ( c.equals(product) ) {
				throw new DuplicateProductException(c);
			}
		}
		return this.productAssortment.add((IProduct) product.deepCopy());
	}
	
	public boolean addProduct(Collection<IProduct> products) throws DuplicateProductException {
		for ( IProduct c : products ) {
			this.addProduct((IProduct) c.deepCopy());
		}
		return true;
	}
	
	public List<IProduct> getProducts() {
		List<IProduct> ret = new ArrayList<>();
		for ( IProduct c : this.productAssortment ) {
			ret.add((IProduct) c.deepCopy());
		}
		return ret;
	}
	
	public boolean orderProductForTable(Table table, IProduct product) {
		if ( table != null && product != null 
				&& this.containsProduct(product) 
				&& this.tables.contains(table) ) {
			
			IProduct found_product = this.findProduct(product);
			List<IProduct> products = new ArrayList<>();
			products.add(found_product);
			Order order = new Order(this.generateUniqueIdentifier(), table, products );
			if ( this.orderHistory.add(order) )
				return true;
		}
		return false;
	}
	
	public boolean orderProductForTable(Table table, IProduct product, int count) {
		if ( table != null && product != null 
				&& this.containsProduct(product) 
				&& this.tables.contains(table) ) {
			
			IProduct found_product = this.findProduct(product);
			List<IProduct> products = new ArrayList<>();
			for ( int i = 0; i < count; i++ ) {
				products.add(found_product);
			}
			Order order = new Order(this.generateUniqueIdentifier(), table, products );
			if ( this.orderHistory.add(order) )
				return true;
		}
		return false;
	}
	
	public boolean containsProduct(IProduct compareProduct) {
		for ( IProduct c : this.productAssortment ) {
			if ( c.equals(compareProduct) ) {
				return true;
			}
			
			
//			if ( this.getProducts().contains(compareProduct) ) 
//				return true;
//			return false;
		}
		return false;
	}
	
	public IProduct findProduct(String productName) {
		for ( IProduct c : this.productAssortment ) {
			if ( c.getName().equals(productName) ) {
				return c;
			}
		}
		return null;
	}

	private IProduct findProduct(IProduct compareProduct) {
		for ( IProduct c : this.productAssortment ) {
			if ( c.equals(compareProduct) ) {
				return c;
			}
		}
		return null;
	}
	
	private long generateUniqueIdentifier() {
		return this.uniqueOrderIdentifier++;
	}
	
	public static List<IProduct> generateSimpleProducts() {
		List<IProduct> ret = new ArrayList<>();
		
		SimpleProduct s1 = new SimpleProduct("s1", 1);
		SimpleProduct s2 = new SimpleProduct("s2", 2);
		SimpleProduct s3 = new SimpleProduct("s3", 3);
		SimpleProduct s4 = new SimpleProduct("s4", 4);
		SimpleProduct s5 = new SimpleProduct("s5", 5);
		
		ret.add(s1);
		ret.add(s2);
		ret.add(s3);
		ret.add(s4);
		ret.add(s5);
		
		return ret;
	}
	
	public static List<IProduct> generateCompositeProducts() {
		List<IProduct> ret = new ArrayList<>();
		
		SimpleProduct s1 = new SimpleProduct("s1", 1);
		SimpleProduct s2 = new SimpleProduct("s2", 2);
		SimpleProduct s3 = new SimpleProduct("s3", 3);
		SimpleProduct s4 = new SimpleProduct("s4", 4);
		SimpleProduct s5 = new SimpleProduct("s5", 5);
		SimpleProduct s6 = new SimpleProduct("s6", 6);
		
		CompositeProduct c0 = new CompositeProduct("c0", 20);
		c0.addProduct(s1);
		c0.addProduct(s2);
		c0.addProduct(s3);
		
		CompositeProduct c1 = new CompositeProduct("c1", 5);
		CompositeProduct c2 = new CompositeProduct("c2", 10);
		CompositeProduct c3 = new CompositeProduct("c3", 15);
		CompositeProduct c4 = new CompositeProduct("c4", 20);
		CompositeProduct c5 = new CompositeProduct("c5", 25);
		
		c1.addProduct(c0);
		c1.addProduct(s3);
		c1.addProduct(s4);
		
		c2.addProduct(c0);
		c2.addProduct(s1);
		c2.addProduct(s5);
		
		c3.addProduct(s6);
		c3.addProduct(s5);
		c3.addProduct(c0);
		
		c4.addProduct(s6);
		c4.addProduct(s4);
		c4.addProduct(c0);
		
		c5.addProduct(s2);
		c5.addProduct(c0);
		c5.addProduct(s5);
		
		ret.add(c1);
		ret.add(c2);
		ret.add(c3);
		ret.add(c4);
		ret.add(c5);
		
		
		return ret;
	}
	
	public void updateProductAssortment(List<IProduct> products) {
		this.productAssortment = products;
	}
	
	public String toString() {
		String ret = "\nTables: ";
		ret += "\n";
		
		for ( String c : this.getTableIdentifiers()) {
			ret += this.getSpecificTable(c).toString();
			
			ret += "\n";
		}
		ret += "\n";
		ret += "Products: \n";
		
		for ( IProduct c : this.getProducts() ) {
			ret += c.toString();
		}
	
		
		return ret + "\n";
	}
	
	
	public static void main(String[] args) {
		
		Restaurant res1 = new Restaurant("Restaurant");
		
		SimpleProduct s1 = new SimpleProduct("s1", 1);
		SimpleProduct s2 = new SimpleProduct("s2", 2);
		SimpleProduct s3 = new SimpleProduct("s3", 3);
		SimpleProduct s4 = new SimpleProduct("s4", 4);
		SimpleProduct s5 = new SimpleProduct("s5", 5);
		
		ExtendedProduct e1 = new ExtendedProduct("e1", 1.5f);
		ExtendedProduct e2 = new ExtendedProduct("e2", 2.5f);
		ExtendedProduct e3 = new ExtendedProduct("e3", 3.5f);
		ExtendedProduct e4 = new ExtendedProduct("e4", 4.5f);
		ExtendedProduct e5 = new ExtendedProduct("e5", 5.5f);
		e5.setPrice(1);
		System.out.println(e5.toString());
		e5.undo();
		
		CompositeProduct c1 = new CompositeProduct("c1", 10);
		CompositeProduct c2 = new CompositeProduct("c2", 20);
		CompositeProduct c3 = new CompositeProduct("c3", 30);
		CompositeProduct c4 = new CompositeProduct("c4", 40);
		CompositeProduct c5 = new CompositeProduct("c5", 50);
		c1.addProduct(s1);
		c1.addProduct(s2);
		c2.addProduct(e1);
		c2.addProduct(e2);
		c3.addProduct(s3);
		c3.addProduct(e3);
		c4.addProduct(c1);
		c4.addProduct(c2);
		c5.addProduct(s4);
		c5.addProduct(e4);
		c5.addProduct(c3);
		
		try {
			res1.addProduct(s1);
			res1.addProduct(s2);
			res1.addProduct(s3);
			res1.addProduct(s4);
			res1.addProduct(s5);
			
			res1.addProduct(e1);
			res1.addProduct(e2);
			res1.addProduct(e3);
			res1.addProduct(e4);
			res1.addProduct(e5);
			
			res1.addProduct(c1);
			res1.addProduct(c2);
			res1.addProduct(c3);
			res1.addProduct(c4);
			res1.addProduct(c5);
			
			//res1.addProduct(c5);
			
		} catch (DuplicateProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		for ( IProduct c : res1.getProducts() ) {
			System.out.println(c.toString());
		}
		
		res1.createTable("t1");
		res1.createTable("t2");
		res1.createTable("t3");
		
		res1.orderProductForTable(res1.getSpecificTable("t1"), e1);
		res1.orderProductForTable(res1.getSpecificTable("t1"), c1);
		
		res1.orderProductForTable(res1.getSpecificTable("t2"), s1, 3);
		res1.orderProductForTable(res1.getSpecificTable("t2"), c2);
		
		res1.orderProductForTable(res1.getSpecificTable("t3"), e1);
		res1.orderProductForTable(res1.getSpecificTable("t3"), c3);
		
		for( Order c : res1.orderHistory ) {
			System.out.println(c.getIdentifier());
			System.out.println(c.getTable());
			System.out.println(c.getProducts());
			System.out.println(c.getState());
		}
		
		
		for ( IProduct c : generateSimpleProducts()) {
			System.out.println(c.toString());
		}
		
		for ( IProduct c : generateCompositeProducts()) {
			System.out.println(c.toString());
		}
		
		System.out.println(res1.toString());
		System.out.println(res1.productAssortment);
		
		int end = 1;
		while ( end == 1 ) {
		
			System.out.println("1 - Search for a product based on its name");
			System.out.println("2 - Adding a new product to the product assortment.");
			System.out.println("0 - Exit");
			
			System.out.println("Choose:");
			
		    Scanner scanner = new Scanner(System.in);
		    int choice = scanner.nextInt();

		    switch (choice) {
		        case 1:
		        	
		        	Scanner keyboard = new Scanner(System.in);
		    		System.out.println("Enter an product: ");
		    		String mystr = keyboard.nextLine();

		    		if ( res1.findProduct(mystr) != null ) {
		    			System.out.println("Product " + mystr + " found: ");
		    			System.out.println(res1.findProduct(mystr).toString());
		    		} else
		    			System.out.println("Product not found!");
		    		
		            break;
		        case 2:
		        	System.out.println("Enter products name:");
		        	Scanner scan_name = new Scanner(System.in);	
		    		String prod_name = scan_name.nextLine();
		    		
		        	System.out.println("Enter products price:");
		        	Scanner scan_price = new Scanner(System.in);
		        	Float prod_price = scan_price.nextFloat();
		    		
				try {
					res1.addProduct( new SimpleProduct(prod_name, prod_price) );
					System.out.println("Product added: " + res1.findProduct(prod_name).toString());
				} catch (DuplicateProductException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        	
		            break;
		        case 0:
		        		scanner.close();
		            end = 0;
		            break;

		        default:
		        	
		        		break;
			
			}
		}
	}
}
