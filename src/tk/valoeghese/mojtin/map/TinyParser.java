package tk.valoeghese.mojtin.map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TinyParser {
	public final Map<String, ClassEntry> entries = new HashMap<>();

	public TinyParser startParsing(String file) {

		try (Stream<String> lineStream = Files.lines(Paths.get(file))) {
			lineStream.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}
