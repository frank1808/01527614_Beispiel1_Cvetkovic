//	Dusan Cvetkovic 01527614 
package rbvs.product;

public class SimpleProduct extends Product {

	public SimpleProduct(String name, float price) {
		super(name, price);
		// TODO Auto-generated constructor stub
	}

	public SimpleProduct deepCopy() {
		SimpleProduct copy = new SimpleProduct(this.getName(), this.getPrice());
		
		return copy;
	}
}
