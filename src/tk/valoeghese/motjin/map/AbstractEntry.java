package tk.valoeghese.motjin.map;

public abstract class AbstractEntry {
	protected AbstractEntry(String obfName, String mappedName) {
		this.obfName = obfName;
		this.mappedName = mappedName;
	}

	public final String obfName;
	private final String mappedName;

	public String finalColumnMapping = null;
	
	public String getMappedName() {
		return this.mappedName;
	}
}
