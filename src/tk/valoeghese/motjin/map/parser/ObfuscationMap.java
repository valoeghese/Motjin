package tk.valoeghese.motjin.map.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import tk.valoeghese.motjin.map.ClassEntry;

public abstract class ObfuscationMap {
	public final Map<String, ClassEntry> entries = new HashMap<>();

	public ObfuscationMap startParsing(String file) {
		try (Stream<String> lineStream = Files.lines(Paths.get(file))) {
			lineStream.forEach(line -> {
				if (!line.trim().isEmpty()) {
					this.parseLine(line);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}
	
	protected abstract void parseLine(String line);

	public static ObfuscationMap parseTiny(String file) {
		return new TinyParser().startParsing(file);
	}
	
	public static ObfuscationMap parseProguard() {
		return null; // TODO
	}
}
