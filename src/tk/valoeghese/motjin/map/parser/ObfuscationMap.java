package tk.valoeghese.motjin.map.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import tk.valoeghese.motjin.map.ClassEntry;
import tk.valoeghese.motjin.util.Debugger;

public abstract class ObfuscationMap {
	protected ObfuscationMap(String type) {
		this.debugger = Debugger.of(3, type);
	}

	protected final Debugger debugger;

	protected final Map<String, ClassEntry> obfToClassMap = new HashMap<>();
	protected final Map<String, ClassEntry> mappedToClassMap = new HashMap<>();

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
		this.obfToClassMap.forEach(callback);
	}

	public static final ObfuscationMap parseTiny(String file) {
		return new TinyParser().startParsing(file);
	}

	public static final ObfuscationMap parseProguard(String file) {
		return new ProguardParser().startParsing(file);
	}
}
