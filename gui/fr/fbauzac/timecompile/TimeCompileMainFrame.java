package fr.fbauzac.timecompile;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import fr.fbauzac.timecompile.Category;
import fr.fbauzac.timecompile.Duration;
import fr.fbauzac.timecompile.Summary;
import fr.fbauzac.timecompile.TimeCompile;
import fr.fbauzac.timecompile.TimeCompileException;

public final class TimeCompileMainFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextArea timeLineTextArea;
    private JTextArea mapTextArea;
    private JTextArea resultTextArea;

    TimeCompileMainFrame() {
	super("TimeCompile");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	timeLineTextArea = new JTextArea("Paste your timeline here");
	mapTextArea = new JTextArea("# Write your mappings here");
	resultTextArea = new JTextArea("The results will be displayed here");
	resultTextArea.setEditable(false);
	resultTextArea.setFont(new Font("monospaced", Font.PLAIN, resultTextArea.getFont().getSize()));
	getContentPane().add(timeLineTextArea, BorderLayout.CENTER);
	getContentPane().add(mapTextArea, BorderLayout.LINE_START);
	getContentPane().add(resultTextArea, BorderLayout.LINE_END);
	timeLineTextArea.getDocument().addDocumentListener(new DocumentListener() {

	    @Override
	    public void insertUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		computeAndDisplayResults();
	    }

	});
	pack();
	setVisible(true);
    }

    private void computeAndDisplayResults() {
	// Use identity for the moment.
	HashMap<String, String> map = new HashMap<>();

	Document document = timeLineTextArea.getDocument();
	String text;
	try {
	    text = document.getText(0, document.getLength());
	} catch (BadLocationException e) {
	    System.out.println("Cannot get text: " + e);
	    return;
	}

	Summary summary;
	try {
	    summary = TimeCompile.summarize(Arrays.asList(text.split("\n")), map);
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
	String result = sb.toString();
	if (result.isEmpty()) {
	    resultTextArea.setText("(nothing to show here)");
	} else {
	    resultTextArea.setText(result);
	}
    }
}
