//	Dusan Cvetkovic 01527614 
package rbvs.product;

public class ExtendedProduct extends SimpleProduct {

	private ExtendedProduct savedState;
	
	public ExtendedProduct(String name, float price) {
		super(name, price);
//		this.savedState = null;
	}
	
	public ExtendedProduct(ExtendedProduct product) {
		super(product.getName(), product.getPrice());
		//this.savedState = product.savedState;
	}
	
	public void setName(String name) {
		//this.savedState.setName(this.getName());
		//String old_name = this.getName();
		
		this.savedState = this.deepCopy();
		super.setName(name);
	}
	
	public void setPrice(float price) throws IllegalArgumentException {
		this.savedState = this.deepCopy();
		super.setPrice(price);
	}
	
	public String toString() {
		boolean can_undo;
		if ( this.savedState != null )
			can_undo = true;
		else
			can_undo = false;
		return super.toString() + "undo opperation will have an effect:" + can_undo;
	}
	
	public boolean undo() {
		if (this.savedState != null) {
			String name = this.savedState.getName();
			float price = this.savedState.getPrice();
			this.setName(name);
			this.setPrice(price);
			this.savedState = null;
			return true;
		}
		return false;
	}

	
	public ExtendedProduct deepCopy() {
		ExtendedProduct copy = new ExtendedProduct(this.getName(), this.getPrice());
		return copy;
	}
}
