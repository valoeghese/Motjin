package tk.valoeghese.mojtin.map.parser;

import tk.valoeghese.mojtin.map.ClassEntry;

public class TinyParser extends ObfuscationMap {
	@Override
	protected void parseLine(String line) {
		line = line.trim();
		String type = line.substring(0, 5);

		if (type.equals("CLASS")) {
			this.parseClass(line.substring(5).split("\t"));
		} else if (type.equals("FIELD")) {
			this.parseField(line.substring(5).split("\t"));
		} else {
			type = line.substring(0, 6);

			if (type.equals("METHOD")) {
				this.parseMethod(line.substring(6).split("\t"));
			}
		}
	}

	private void parseMethod(String[] in) {
		final String clazzObfName = in[0].trim();

		ClassEntry classEntry = this.entries.computeIfAbsent(clazzObfName, obfName -> new ClassEntry.Builder().obfName(obfName).build());
		classEntry.addMethod(in[2].trim(), in[3].trim(), in[1].trim());
	}

	private void parseField(String[] in) {
		final String clazzObfName = in[0].trim();

		ClassEntry classEntry = this.entries.computeIfAbsent(clazzObfName, obfName -> new ClassEntry.Builder().obfName(obfName).build());
		classEntry.addField(in[2].trim(), in[3].trim(), in[1].trim());
	}

	private void parseClass(String[] in) {
		final String obfName = in[0].trim();

		if (this.entries.containsKey(obfName)) {
			this.entries.get(obfName).setMappedName(in[1].trim());
		} else {
			this.entries.put(obfName, new ClassEntry.Builder()
					.obfName(obfName)
					.mappedName(in[1].trim())
					.build());
		}
	}
}
