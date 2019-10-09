//	Dusan Cvetkovic 01527614 
package rbvs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import rbvs.product.ExtendedProduct;
import rbvs.product.IProduct;
import rbvs.product.Product;
import rbvs.product.SimpleProduct;

public class RestaurantChain {
	
	private List<Restaurant> restaurants = new ArrayList<>();
	private String restaurantChainName;
	private List<IProduct> productAssortment = new ArrayList<>();
	
	public RestaurantChain(String name) {
		this.restaurantChainName = name;
	}
	
	public String getName() {
		return this.restaurantChainName;
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
			this.addProduct(c);
		}
		return true;
	}
	
	
	public boolean addRestaurant(Restaurant restaurant) {
		if ( restaurant == null )
			return false;
		for ( Restaurant c : this.restaurants ) {
			if ( c.getName() == restaurant.getName() ) {
				return false;
			}
		}
		restaurant.updateProductAssortment(this.productAssortment);
		return this.restaurants.add((Restaurant) restaurant);
	}
	
	public boolean addNewRestaurant ( String restaurantName, String tableId, int seats )
	{
		for ( Restaurant r : restaurants )
		{
			if (  r.getName().equals ( restaurantName )  )
			{
				if ( seats < 0 )
					r.createTable( tableId );
				else {
					r.createTable( tableId );
					r.getSpecificTable(tableId).setSeatCount(seats);
					return false;
				}
			}
		}
		
		Restaurant res = new Restaurant ( restaurantName );
		
		if ( seats < 0 )
			res.createTable( tableId );
		else {
			res.createTable( tableId );
			res.getSpecificTable(tableId).setSeatCount(seats);
		}
		
		res.updateProductAssortment(this.productAssortment);
		
		return restaurants.add(res);
	}
	
	public void loadFile(String fileName) {
		Scanner scann = null;
		try {
			scann = new Scanner(new File(fileName));
		} catch (Exception e) {
			System.out.println("File not found!");
		}
		
		while ( scann.hasNext() ) {
			String a = scann.nextLine();

			String[] lineArray = a.split(";");
			this.addNewRestaurant(lineArray[0], lineArray[1], Integer.parseInt(lineArray[2]) );
//			if ( !this.containsRestorant(lineArray[0]) ) {
//				this.addRestaurant(new Restaurant(lineArray[0]));
//				System.out.println(lineArray[0] + lineArray[1].toString() + lineArray[2]);
//				getRestaurant(lineArray[0]).createTable(lineArray[1]);
//				getRestaurant(lineArray[0]).getSpecificTable(lineArray[1]).setSeatCount(Integer.parseInt(lineArray[2]));
				
//			} else {			
//				getRestaurant(lineArray[0]).createTable(lineArray[1]);
//				getRestaurant(lineArray[0]).getSpecificTable(lineArray[1]).setSeatCount(Integer.parseInt(lineArray[2]));
//			}
//			System.out.println(lineArray[0]); 
//			System.out.println(lineArray[1]); 
//			System.out.println(lineArray[2]); 
//			for (String c : lineArray) 
//	            System.out.println(c); 
		}
		scann.close();
	}
	
	public Restaurant getRestaurant(String name) {
		for ( Restaurant c : this.getListOfRestaurants() ) {
			if ( c.getName() == name ) {
				return c;
				}
			
		}
		return null;
	}
	public boolean containsRestorant(String name) {
		boolean ret = false;
		for ( Restaurant c : this.restaurants ) {
			if( c.equals(this.getRestaurant(name)))
				ret = true;
		}

		return ret;
	}
	
	
	public List<Restaurant> getListOfRestaurants() {
		return this.restaurants;
	}
	
	public final boolean equals(Object obj) {
		if (obj instanceof Product) {
			if (((Restaurant) obj).getName() == this.getName()) {
				return true;
			}
			return false;
		}
		return false;
	}
	
//	public void loadFile() {
//		Scanner scanner = null;
//		try {
//			scanner = new Scanner(new File("test.txt"));
//		} catch (Exception e) {
//			System.out.println("File not found!");
//		}
//		Scanner valueScanner = null;
//		int index = 0;
//
//		while (scanner.hasNextLine()) {
//		    valueScanner = new Scanner(scanner.nextLine());
//		    valueScanner.useDelimiter(";");
//
//		    while (valueScanner.hasNext()) {
//			String data = valueScanner.next();
//			if (index == 0){
//			    System.out.println(data);
//			}
//			else if (index == 1){
//				System.out.println(data);
//			}
//			else if (index == 2){
//				System.out.println(data);
//			}
//			index++;
//		    }
//		    index = 0;
//		}
//		scanner.close();
//	}
	public static void main(String[] args) 
	{ 
		
		RestaurantChain RES = new RestaurantChain("RES");
		SimpleProduct s1 = new SimpleProduct("Milch", 2);
		try {
			RES.addProduct(s1);
		} catch (DuplicateProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RES.loadFile("test.txt"); 
		Restaurant res1 = new Restaurant("R1");
		Restaurant res2 = new Restaurant("R1");
		
		RES.addRestaurant(res1);
		RES.addRestaurant(res2);
		RES.addNewRestaurant("RES1", "TISCH", -1);
		 for ( Restaurant c : RES.getListOfRestaurants() ) {
			 System.out.println(c.getName());
			 for( String a : c.getTableIdentifiers()) {
				 System.out.println(c.getSpecificTable(a).toString() );
				
			 }
			 System.out.println(c.getProducts());
		 }
		 
		 for ( IProduct p : res1.getProducts()) {
			 System.out.println(p.toString());
		 }
		 
		 for ( Restaurant c : RES.getListOfRestaurants() ) {
			 System.out.println(c.getName());
		 }
		 
		 ExtendedProduct e0 = new ExtendedProduct("product", 5);
		 e0.setName("das");
		 e0.undo();
		 System.out.println(e0.toString());
	}

}
