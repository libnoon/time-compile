package fr.fbauzac.timecompile.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import fr.fbauzac.timecompile.lib.Interval;
import fr.fbauzac.timecompile.lib.Category;
import fr.fbauzac.timecompile.lib.TimeCompile;

public class IntervalTest {

    @Test
    public void testGetTags() {
	String text = "+abc";
	List<String> result = Interval.getTags(text);
	assertThat(result.size(), equalTo(1));
	assertThat(result.get(0).toString(), equalTo("abc"));
    }

    @Test
    public void testTagNotAtStart() {
	String text = "toto\n+abc";
	List<String> result = Interval.getTags(text);
	assertThat(result.size(), equalTo(1));
	assertThat(result.get(0).toString(), equalTo("abc"));
    }

}
