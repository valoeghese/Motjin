package tk.valoeghese.motjin.map.parser;

import tk.valoeghese.motjin.map.ClassEntry;

public final class TinyParser extends ObfuscationMap {
	TinyParser() {
		super("tinyParser");
	}

	@Override
	protected void parseLine(String line) {
		line = line.trim();
		String type = line.substring(0, 5);

		if (type.equals("CLASS")) {
			this.parseClass(line.substring(6).split("\t"));
		} else if (type.equals("FIELD")) {
			this.parseField(line.substring(6).split("\t"));
		} else {
			type = line.substring(0, 6);

			if (type.equals("METHOD")) {
				this.parseMethod(line.substring(7).split("\t"));
			}
		}
	}

	private void parseMethod(String[] in) {
		final String clazzObfName = in[0].trim();

		ClassEntry classEntry = this.obfToClassMap.computeIfAbsent(clazzObfName, obfName -> new ClassEntry.Builder().obfName(obfName).build());
		classEntry.addMethod(in[2].trim(), in[3].trim(), in[1].trim());
	}

	private void parseField(String[] in) {
		final String clazzObfName = in[0].trim();

		ClassEntry classEntry = this.obfToClassMap.computeIfAbsent(clazzObfName, obfName -> new ClassEntry.Builder().obfName(obfName).build());
		classEntry.addField(in[2].trim(), in[3].trim(), in[1].trim());
	}

	private void parseClass(String[] in) {
		final String obfName = in[0].trim();
		final String mappedName = in[1].trim();

		if (this.obfToClassMap.containsKey(obfName)) {
			ClassEntry classEntry = this.obfToClassMap.get(obfName);

			classEntry.setMappedName(mappedName);
			this.mappedToClassMap.put(mappedName, classEntry);
		} else {
			ClassEntry classEntry = new ClassEntry.Builder()
					.obfName(obfName)
					.mappedName(mappedName)
					.build();

			this.obfToClassMap.put(obfName, classEntry);
			this.mappedToClassMap.put(mappedName, classEntry);
		}
	}
}
