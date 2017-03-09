package fr.fbauzac.timecompile;

import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public final class TimeCompileMainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTextArea mapTextArea;
    private final JTextArea resultTextArea;
    private final JTextArea timeLineTextArea;

    private static String stringOfLines(String... lines) {
	return Arrays.asList(lines).stream().map(s -> s + "\n")
		.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public TimeCompileMainFrame() {
	super("TimeCompile");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel jPanel = new JPanel();
	jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

	String timeLineText = stringOfLines("# Paste your timeline here:", "14h00", "+m meeting with J. Bond", "15h00",
		"+s support for Charlemagne", "15h34", "+s support for Henry VIII", "16h12",
		"+m meeting with A. Einstein", "16h49", "+collect daily times", "17h30");
	timeLineTextArea = new JTextArea(timeLineText);
	jPanel.add(timeLineTextArea);

	mapTextArea = new JTextArea(stringOfLines("# Put your map here", "#ac: s collect"));
	jPanel.add(mapTextArea);

	resultTextArea = new JTextArea("The results will be displayed here");
	resultTextArea.setEditable(false);
	resultTextArea.setFont(new Font("monospaced", Font.PLAIN, resultTextArea.getFont().getSize()));
	jPanel.add(resultTextArea);

	timeLineTextArea.getDocument().addDocumentListener(new DocumentListener() {

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	    @Override
	    public void insertUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }
	});

	mapTextArea.getDocument().addDocumentListener(new DocumentListener() {

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	    @Override
	    public void insertUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }
	});

	add(jPanel);
	pack();
	setVisible(true);
    }

    private void computeAndDisplayResults() {
	List<String> mapLines = getLinesFromTextArea(mapTextArea);
	Map<String, String> map = MapParser.parse(mapLines);

	Summary summary;
	try {
	    summary = TimeCompile.summarize(getLinesFromTextArea(timeLineTextArea), map);
	} catch (TimeCompileException e) {
	    resultTextArea.setText("Failed to process input: " + e);
	    return;
	}
	StringBuilder sb = new StringBuilder();
	int totalDuration = summary.getTotalDuration().getMinutes();
	for (Category category : summary.getCategories()) {
	    Duration duration = category.getDuration();
	    sb.append(String.format("%10s  %8s  %.1f%%%n", category.getTag(), duration,
		    100.0 * duration.getMinutes() / totalDuration));
	}
	sb.append(String.format("%10s  %8s%n", "TOTAL", Duration.ofMinutes(totalDuration)));
	String result = sb.toString();
	if (result.isEmpty()) {
	    resultTextArea.setText("(nothing to show here)");
	} else {
	    resultTextArea.setText(result);
	}
    }

    private static List<String> getLinesFromTextArea(JTextArea textArea) {
	Document document = textArea.getDocument();
	String text;
	try {
	    text = document.getText(0, document.getLength());
	} catch (BadLocationException e) {
	    throw new RuntimeException(e);
	}
	return Arrays.asList(text.split("\n"));
    }

}
