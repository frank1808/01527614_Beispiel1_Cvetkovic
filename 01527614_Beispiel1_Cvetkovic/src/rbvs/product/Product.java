//	Dusan Cvetkovic 01527614 
package rbvs.product;


public abstract class Product implements IProduct {
	
	private String name;
	private float price;
	
	public Product(String name) {
		initialize(name, 0);
//		if (name == null) {
//			this.name = "";
//		} else {
//			this.name = name;
//		}
//		
//		this.price = 0;
	}
	
	public Product(String name, float price) {
		initialize(name, price);
//		this(name);
//		this.price = price;

	}
	
	private void initialize(String name, float price) {
		if (name == null) {
			this.name = "";
		} else {
			this.name = name;
		}
		
		this.price = price;
	}

	@Override
	public abstract Product deepCopy();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public void setName(String name) {
		if (name == null) {
			this.name = "";
		} else {
			this.name = name;
		}
	}

	@Override
	public float getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}
	
	public void setPrice(float price) throws IllegalArgumentException {
		if (price < 0) {
			throw new IllegalArgumentException();
		} else {
			this.price = price;
		}
	}
	
	public String toString() {
		return "Product[" + this.getName() + "=" + this.getPrice() + "]";
	}
	
	
	
	
	
	public final boolean equals(Object obj) {
		if (obj instanceof Product) {
			if (((Product) obj).getName().equals(this.getName())) {
				return true;
			}
			return false;
		}
		return false;
	}

//	@Override
//	public String toString() {
//		return "Product [name=" + name + ", price=" + price + "]";
//	}


}
