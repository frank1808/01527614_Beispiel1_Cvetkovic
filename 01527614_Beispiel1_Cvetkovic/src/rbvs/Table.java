//	Dusan Cvetkovic 01527614 
package rbvs;

public class Table {
	
	private String id;
	private int seats;
	
	public Table(String id) {
		this.id = id;
		this.seats = 2;
	}
	
	public Table(String id, int seats) {
		this.id = id;
		this.seats = seats;
	}
	
	public int getSeatCount() {
		return this.seats;
	}
	
	public void setSeatCount(int seatCount) {
		this.seats = seatCount;
	}
	
	public String getTableIdentifier() {
		return this.id;
	}
	
	public String toString() {
		return "[Table=" + this.getTableIdentifier() + ",Seats=" + this.getSeatCount() + "]";
	}

	public boolean equals(Object obj) {
		if( obj instanceof Table ) {
			if ( ((Table) obj).getSeatCount() == this.getSeatCount() && ((Table) obj).getTableIdentifier() == this.getTableIdentifier() ) {
				return true;
			}
		}
		return false;
	}
}
