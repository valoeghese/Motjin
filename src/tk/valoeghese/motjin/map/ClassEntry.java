package tk.valoeghese.motjin.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassEntry extends AbstractEntry {
	private ClassEntry(Builder builder) {
		super(builder.obfName, builder.mappedName);
		this.mappedNameOverride = builder.mappedName;
	}

	public final List<FieldEntry> fields = new ArrayList<>();
	public final List<MethodEntry> methods = new ArrayList<>();
	public final Map<String, FieldEntry> fieldMap = new HashMap<>();
	public final Map<String, MethodEntry> methodMap = new HashMap<>();

	private String mappedNameOverride;

	public ClassEntry addField(String obfName, String mappedName, String descriptor) {
		FieldEntry entry = new FieldEntry(obfName, mappedName, descriptor, this);
		this.fields.add(entry);
		this.fieldMap.put(obfName + ":" + descriptor, entry);
		return this;
	}

	public ClassEntry addMethod(String obfName, String mappedName, String signature) {
		MethodEntry entry = new MethodEntry(obfName, mappedName, signature, this);
		this.methods.add(entry);
		this.methodMap.put(obfName + ":" + signature, entry);
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
