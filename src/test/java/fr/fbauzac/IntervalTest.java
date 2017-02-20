package fr.fbauzac;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IntervalTest {

    @Test
    public void testGetTags() {
	String text = "+abc";
	List<Tag> result = Interval.getTags(text);
	assertThat(result.size(), equalTo(1));
	assertThat(result.get(0).toString(), equalTo("abc"));
    }

}
