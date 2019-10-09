//	Dusan Cvetkovic 01527614 
package rbvs.product;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeProduct extends Product {
	 
	private Collection<Product> containedProducts  = new ArrayList<>();
	private float discount;
	
	public CompositeProduct(String name, float discountPercentage) {
		super(name);
		
		if (discountPercentage < 0)
			this.discount = 0;
		else if (discountPercentage > 100) 
			this.discount = 100;
		else
			this.discount = discountPercentage;
	}
	
	public CompositeProduct(String name, float discountPercentage, Collection<Product> products) {
		this(name, discountPercentage);
		
		if(products != null)
			this.containedProducts.addAll(products);
	}
	
	public void addProduct(Product product) {
		if (product != null)
			this.containedProducts.add(product);
	}
	
	public boolean removeProduct(Product product) {
		if (product != null && this.containedProducts.contains(product)) {
			this.containedProducts.remove(product);
			return true;
		}
		return false;
	}
	
	public Collection<Product> getProducts() {
		return this.containedProducts;
	}
	
	public float getPrice() {
		float sum = 0;
		
		for(Product c : this.containedProducts) {
			sum += c.getPrice();
		}
		
		return sum*(100 - this.discount)/100;
	}
	
	public String toString() { 
		String ret = "CompositeProduct[" /*+ this.getName() + "=" + this.getPrice() + ","*/;
		int i = 0;
		
		for(Product c : this.containedProducts) {
			if ( c instanceof CompositeProduct)
				ret += ( (CompositeProduct) c ).toString();
			ret += c.getName() + "=" + c.getPrice();
			i++;
			if ( i != this.containedProducts.size() ) {
				ret += ",";
			}
		}
		
		return ret + "]";
	}
	
	public CompositeProduct deepCopy() { 
		Collection<Product> copy_containedProducts  = new ArrayList<>();
		for ( Product c : this.containedProducts ) {
			copy_containedProducts.add(c.deepCopy()); //zna se tacno koji ce se deepCopy() koristiti u zavisnosti od toga sta je c
//			if ( c instanceof CompositeProduct)
//				( (CompositeProduct) c ).deepCopy();
//			copy_containedProducts.add((Product) c.deepCopy());
		}
		
		CompositeProduct copy = new CompositeProduct(this.getName(), this.discount, copy_containedProducts);
		return copy;
	}


}
