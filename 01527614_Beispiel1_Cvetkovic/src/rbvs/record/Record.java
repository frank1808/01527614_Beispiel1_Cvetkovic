//	Dusan Cvetkovic 01527614 
package rbvs.record;

public abstract class Record implements IRecord {
	
	private long id;
	
	public Record(long identifier) {
		this.id = identifier;
	}

	@Override
	public long getIdentifier() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
