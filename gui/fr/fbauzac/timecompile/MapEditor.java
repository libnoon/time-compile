package fr.fbauzac.timecompile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public final class MapEditor extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea textArea;
    private JPanel jPanel;

    private Map<String, JPanel> linesMap = new HashMap<>();

    MapEditor() {
	JTabbedPane tabbedPane = new JTabbedPane();
	textArea = new JTextArea("# Write your mappings here");
	tabbedPane.addTab("Text view", textArea);
	jPanel = new JPanel();
	jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
	tabbedPane.addTab("Drag view", jPanel);
	add(tabbedPane);
    }

    private void addLineTag(String tag) {
	Objects.requireNonNull(tag);

	JPanel line = new JPanel();
	line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
	line.add(new TagLabel(tag));
	jPanel.add(line);
	linesMap.put(tag, line);
    }

    void setMap(Map<String, String> map) {
	Objects.requireNonNull(map);

	List<Entry<String, String>> entries = map.entrySet().stream().collect(Collectors.toList());
	entries.sort((a, b) -> a.getKey().compareTo(b.getKey()));
	for (Map.Entry<String, String> entry : entries) {
	    String source = entry.getKey();
	    String target = entry.getValue();
	    if (!linesMap.containsKey(target)) {
		addLineTag(target);
	    }
	    JPanel line = linesMap.get(target);
	    line.add(new TagLabel(source));
	}
    }
}
