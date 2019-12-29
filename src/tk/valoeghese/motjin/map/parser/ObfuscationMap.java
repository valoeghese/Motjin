package tk.valoeghese.motjin.map.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import tk.valoeghese.motjin.map.ClassEntry;
import tk.valoeghese.motjin.util.Debugger;

public abstract class ObfuscationMap {
	protected ObfuscationMap(String type) {
		this.debugger = Debugger.of(5, type);
	}

	protected final Debugger debugger;

	private final Map<String, ClassEntry> obfToClassMap = new HashMap<>();
	private final Map<String, ClassEntry> mappedToClassMap = new HashMap<>();
	private final List<String> obfNamesInOrder = new ArrayList<>();
	
	protected void addClassEntry(String obf, String mapped, ClassEntry entry) {
		this.addClassEntry(obf, mapped, entry, true);
	}

	protected void addClassEntry(String obf, String mapped, ClassEntry entry, boolean mapObf) {
		if (mapObf) {
			this.obfToClassMap.put(obf, entry);
		}
		this.mappedToClassMap.put(mapped, entry);

		if (!this.obfNamesInOrder.contains(obf)) {
			this.obfNamesInOrder.add(obf);
		}
	}

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

	public ClassEntry getClassEntryForObf(String obfName) {
		return this.obfToClassMap.get(obfName);
	}

	public ClassEntry getClassEntryForMapped(String mappedName) {
		return this.mappedToClassMap.get(mappedName);
	}

	public final void forEachObf(BiConsumer<String, ClassEntry> callback) {
		this.obfNamesInOrder.forEach(obfName -> {
			callback.accept(obfName, this.obfToClassMap.get(obfName));
		});
	}
	
	protected ClassEntry computeIfAbsentForObf(String key, Function<String, ClassEntry> mappingFunction) {
		return this.obfToClassMap.computeIfAbsent(key, mappingFunction);
	}
	
	protected boolean containsKeyForObf(String obfName) {
		return this.obfToClassMap.containsKey(obfName);
	}

	public static final ObfuscationMap parseTiny(String file) {
		return new TinyParser().startParsing(file);
	}

	public static final ObfuscationMap parseProguard(String file) {
		return new ProguardParser().startParsing(file);
	}
}
