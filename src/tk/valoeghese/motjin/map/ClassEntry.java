package tk.valoeghese.motjin.map;

import java.util.ArrayList;
import java.util.List;

public class ClassEntry extends AbstractEntry {
	private ClassEntry(Builder builder) {
		super(builder.obfName, builder.mappedName);

		this.mappedNameOverride = builder.mappedName;
	}

	public final List<FieldEntry> fields = new ArrayList<>();
	public final List<MethodEntry> methods = new ArrayList<>();
	private String mappedNameOverride;

	public ClassEntry addField(String obfName, String mappedName, String descriptor) {
		this.fields.add(new FieldEntry(obfName, mappedName, descriptor, this));
		return this;
	}

	public ClassEntry addMethod(String obfName, String mappedName, String signature) {
		this.methods.add(new MethodEntry(obfName, mappedName, signature, this));
		return this;
	}

	public void setMappedName(String mappedName) {
		this.mappedNameOverride = mappedName;
	}

	@Override
	public String getMappedName() {
		return this.mappedNameOverride;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CLASS\t")
				.append(this.obfName)
				.append("\t")
				.append(this.getMappedName());
		return this.finalColumnMapping == null ? sb.toString() : sb.append("\t").append(this.finalColumnMapping).toString();
	}

	public static class Builder {
		private String obfName;
		private String mappedName;

		public Builder obfName(String obfName) {
			this.obfName = obfName;
			return this;
		}

		public Builder mappedName(String mappedName) {
			this.mappedName = mappedName;
			return this;
		}

		public ClassEntry build() {
			return new ClassEntry(this);
		}
	}
}
