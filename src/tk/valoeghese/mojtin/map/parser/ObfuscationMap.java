package tk.valoeghese.mojtin.map.parser;

import java.util.HashMap;
import java.util.Map;

import tk.valoeghese.mojtin.map.ClassEntry;

public abstract class ObfuscationMap {
	public final Map<String, ClassEntry> entries = new HashMap<>();

	public abstract ObfuscationMap startParsing(String file);

	public static ObfuscationMap parseTiny(String file) {
		return new TinyParser().startParsing(file);
	}
	
	public static ObfuscationMap parseProguard() {
		return null; // TODO
	}
}
