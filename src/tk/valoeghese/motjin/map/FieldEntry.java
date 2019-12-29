package tk.valoeghese.motjin.map;

public class FieldEntry extends AbstractEntry {
	public FieldEntry(String obfName, String mappedName, String descriptor, ClassEntry parent) {
		super(obfName, mappedName);

		this.descriptor = descriptor;
		this.parent = parent;
	}

	public final String descriptor;
	public final ClassEntry parent;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("FIELD\t")
				.append(this.parent.obfName)
				.append("\t")
				.append(this.descriptor)
				.append("\t")
				.append(this.obfName)
				.append("\t")
				.append(this.getMappedName());
		return this.finalColumnMapping == null ?
				sb.toString() :
				sb.append("\t").append(this.finalColumnMapping).toString();
	}
}
