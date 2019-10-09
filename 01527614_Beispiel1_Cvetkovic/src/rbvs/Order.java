//	Dusan Cvetkovic 01527614 
package rbvs;

import java.util.ArrayList;
import java.util.List;

import ict.basics.IDeepCopy;
import rbvs.product.IProduct;
import rbvs.record.Record;

public class Order extends Record implements IDeepCopy {
	
	private List<IProduct> products = new ArrayList<>();
	private OrderState currentState;
	private Table table;
	
	public Order(long identifier, Table table, List<IProduct> products) {
		super(identifier);
		this.table = table;
		this.products = products;
		this.currentState = OrderState.OPEN;
	}
	
	public List<IProduct> getProducts() {
		List<IProduct> copy = new ArrayList<>();
		for ( IProduct c : products ) {
			copy.add((IProduct) c.deepCopy());
		}
		return copy;
	}
	
	public boolean setState(OrderState newStatus) {
		if( !this.isCancelled() && !this.isPaid() ) {
			this.currentState = newStatus;
		
			return true;
		}
		
		return false;
	}
	
	public OrderState getState() {
		return this.currentState;
	}
	
	public boolean isCancelled() {
		if(this.currentState == OrderState.CANCELLED)
			return true;
		
		return false;
	}
	
	public boolean isPaid() {
		if(this.currentState == OrderState.PAID)
			return true;
		
		return false;
	}
	
	public Table getTable() {
		return this.table;
	}

	@Override
	public Order deepCopy() {
		// TODO Auto-generated method stub
		List<IProduct> copy_products = new ArrayList<>();
		for ( IProduct c : this.products ) {
			copy_products.add((IProduct) c.deepCopy());
		}
		
		OrderState copy_state = this.currentState;
		Table copy_table = this.table;
		long copy_identifier = this.getIdentifier();
		Order copy_order = new Order(copy_identifier, copy_table, copy_products);
		copy_order.currentState = copy_state;
		
		return copy_order;
	}
	
	public boolean equals(Object obj) {
		if ( obj instanceof Order ) {
			if ( ((Order) obj).getIdentifier() == this.getIdentifier() 
					&& ((Order) obj).getState() == this.getState()
					&& ((Order) obj).getTable().equals( this.getTable() )
					&& ((Order) obj).getProducts().containsAll(this.getProducts())
					&& this.getProducts().containsAll(((Order) obj).getProducts())) {
				return true;
			}
		}
		return false;
	}

}
