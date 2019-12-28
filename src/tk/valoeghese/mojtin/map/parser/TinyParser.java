package tk.valoeghese.mojtin.map.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import tk.valoeghese.mojtin.map.ClassEntry;

public class TinyParser extends ObfuscationMap {
	public ObfuscationMap startParsing(String file) {
		try (Stream<String> lineStream = Files.lines(Paths.get(file))) {
			lineStream.forEach(this::parseLine);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}

	private void parseLine(String line) {
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
		final String clazzObfName = in[0];

		ClassEntry classEntry = this.entries.computeIfAbsent(clazzObfName, obfName -> new ClassEntry.Builder().obfName(obfName).build());
		classEntry.addMethod(in[2], in[3], in[1]);
	}

	private void parseField(String[] in) {
		final String clazzObfName = in[0];

		ClassEntry classEntry = this.entries.computeIfAbsent(clazzObfName, obfName -> new ClassEntry.Builder().obfName(obfName).build());
		classEntry.addField(in[2], in[3], in[1]);
	}

	private void parseClass(String[] in) {
		final String obfName = in[0];

		if (this.entries.containsKey(obfName)) {
			this.entries.get(obfName).setMappedName(in[1]);
		} else {
			this.entries.put(obfName, new ClassEntry.Builder()
					.obfName(obfName)
					.mappedName(in[1])
					.build());
		}
	}
}
