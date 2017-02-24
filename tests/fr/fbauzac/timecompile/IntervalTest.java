package fr.fbauzac.timecompile;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import fr.fbauzac.timecompile.Interval;

public class IntervalTest {

    @Test
    public void testGetTags() {
	String text = "+abc";
	List<String> result = Interval.getTags(text);
	assertThat(result.size(), equalTo(1));
	assertThat(result.get(0).toString(), equalTo("abc"));
    }

}
