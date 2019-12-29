package tk.valoeghese.motjin.map;

public class MethodEntry extends AbstractEntry {
	public MethodEntry(String obfName, String mappedName, String signature, ClassEntry parent) {
		super(obfName, mappedName);

		this.signature = signature;
		this.parent = parent;
	}

	public final String signature;
	public final ClassEntry parent;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("METHOD\t")
				.append(this.parent.obfName)
				.append("\t")
				.append(this.signature)
				.append("\t")
				.append(this.obfName)
				.append("\t")
				.append(this.getMappedName());
		return this.finalColumnMapping == null ?
				sb.toString() :
				sb.append("\t").append(this.finalColumnMapping).toString();
	}

	public static String signature(String returnType, String...parameters) {
		StringBuilder sb = new StringBuilder("(");

		for (String parameter : parameters) {
			sb.append(parameter);
		}
		return sb.append(")").append(returnType).toString();
	}
}
