package fr.fbauzac.timecompile;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.fbauzac.timecompile.Category;
import fr.fbauzac.timecompile.Summary;
import fr.fbauzac.timecompile.TimeCompile;
import fr.fbauzac.timecompile.TimeCompileException;

public class TimeCompileTest {
    private final Map<String, String> identity = new HashMap<>();

    @Test
    public void noLine() throws TimeCompileException {
	Summary result = TimeCompile.summarize(Collections.emptyList(), identity);
	assertThat(result.getTotalDuration().getMinutes(), equalTo(0));
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneTextLine() throws TimeCompileException {
	List<String> lines = Arrays.asList("toto");
	Summary result = TimeCompile.summarize(lines, identity);
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneHourLine() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00");
	Summary result = TimeCompile.summarize(lines, identity);
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneUntaggedInterval() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00", "toto", "11h00");
	Summary result = TimeCompile.summarize(lines, identity);
	assertThat(result.getTotalDuration().getMinutes(), equalTo(0));
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneTaggedInterval() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00", "+mytag toto", "11h00");
	Summary result = TimeCompile.summarize(lines, identity);
	assertThat(result.getTotalDuration().getMinutes(), equalTo(60));
	assertThat(result.getCategories().size(), equalTo(1));
	Category category = result.getCategories().get(0);
	assertThat(category.getTag(), equalTo("mytag"));
	assertThat(category.getDuration().getMinutes(), equalTo(60));
    }

    @Test
    public void twoTaggedIntervals() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00", "+mytag toto", "11h00", "+myothertag", "11h20");
	Summary result = TimeCompile.summarize(lines, identity);
	assertThat(result.getTotalDuration().getMinutes(), equalTo(80));
	assertThat(result.getCategories().size(), equalTo(2));
	Category category = result.getCategories().get(0);
	assertThat(category.getTag(), equalTo("mytag"));
	assertThat(category.getDuration().getMinutes(), equalTo(60));
	Category category2 = result.getCategories().get(1);
	assertThat(category2.getTag(), equalTo("myothertag"));
	assertThat(category2.getDuration().getMinutes(), equalTo(20));
    }
}
