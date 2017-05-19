package fr.fbauzac.timecompile;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public final class TimeCompileMainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * The mapping as an editable input text.
     */
    private final JTextArea mapTextArea;

    /**
     * The timeline as an editable input text.
     */
    private final JTextArea timeLineTextArea;

    /**
     * Panel containing the current result.
     * 
     * Result updaters remove its child to replace it with another JComponent.
     */
    private final JPanel resultPanel;

    /**
     * Convert a sequence of lines into a single large string containing all
     * these lines.
     * 
     * The line separator is "\n".
     * 
     * @param lines
     *            the sequence of lines as strings.
     * @return the large multiline string.
     */
    private static String stringOfLines(String... lines) {
	return Arrays.asList(lines).stream().map(s -> s + "\n")
		.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    public TimeCompileMainFrame() {
	super("TimeCompile");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel jPanel = new JPanel();
	jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

	String timeLineText = stringOfLines("# Paste your timeline here:", "14h00", "+meeting with J. Bond", "15h00",
		"+support for Charlemagne", "15h34", "+support for Henry VIII", "16h12", "+meeting with A. Einstein",
		"16h49", "+collect daily times", "17h30");
	timeLineTextArea = new JTextArea(timeLineText);

	mapTextArea = new JTextArea(
		stringOfLines("# Put your map here", "#misc: meeting collect", "#maintenance: support"));

	JSplitPane lowerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(timeLineTextArea),
		new JScrollPane(mapTextArea));

	// Use GridLayout so that the child fills the whole area.
	// See http://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html:
	// "One of many examples that use a 1x1 grid to make a component as
	// large as possible."
	resultPanel = new JPanel(new GridLayout(0, 1));

	jPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lowerSplitPane, resultPanel));

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
	computeAndDisplayResults();
	pack();
	setVisible(true);
    }

    /**
     * Replace the result pane with the given JComponent.
     * 
     * @param jComponent
     *            the new result to display, as a JComponent.
     */
    private void setResult(JComponent jComponent) {
	resultPanel.removeAll();
	resultPanel.add(jComponent);
	resultPanel.repaint();
	resultPanel.revalidate();
    }

    /**
     * Update the result panel from the input.
     * 
     * This should be called whenever the input changes.
     */
    private void computeAndDisplayResults() {
	List<String> mapLines = getLinesFromTextArea(mapTextArea);
	Map<String, String> map = MapParser.parse(mapLines);
	final String[] columnNames = { "Category", "Duration", "Percent" };
	final int nbColumns = columnNames.length;

	Summary summary;
	try {
	    summary = TimeCompile.summarize(getLinesFromTextArea(timeLineTextArea), map);
	} catch (TimeCompileException e) {
	    setResult(new JScrollPane(new JLabel("Failed to process input: " + e)));
	    return;
	}

	List<Category> categories = summary.getCategories();
	int totalDuration = summary.getTotalDuration().getMinutes();

	if (categories.size() == 0) {
	    setResult(new JLabel("(nothing to show here)"));
	    return;
	}

	Object[][] tableContents = new Object[categories.size() + 1][nbColumns];
	IntStream.range(0, categories.size()).forEach(i -> {
	    Category category = categories.get(i);
	    Duration duration = category.getDuration();
	    tableContents[i][0] = category.getTag();
	    tableContents[i][1] = duration.toString();
	    tableContents[i][2] = String.format("%.0f%%", 100.0d * duration.getMinutes() / totalDuration);
	});
	tableContents[categories.size()][0] = "TOTAL";
	tableContents[categories.size()][1] = Duration.ofMinutes(totalDuration).toString();
	tableContents[categories.size()][2] = "100%";

	JTable jTable = new JTable(tableContents, columnNames);
	jTable.setFillsViewportHeight(true);
	setResult(new JScrollPane(jTable));
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
