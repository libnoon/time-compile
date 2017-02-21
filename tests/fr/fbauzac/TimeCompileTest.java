package fr.fbauzac;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TimeCompileTest {

    @Test
    public void noLine() throws TimeCompileException {
	Summary result = TimeCompile.processLines(Collections.emptyList(), new IdentityTagTransformer());
	assertThat(result.getTotalDuration().getMinutes(), equalTo(0));
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneTextLine() throws TimeCompileException {
	List<String> lines = Arrays.asList("toto");
	Summary result = TimeCompile.processLines(lines, new IdentityTagTransformer());
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneHourLine() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00");
	Summary result = TimeCompile.processLines(lines, new IdentityTagTransformer());
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneUntaggedInterval() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00", "toto", "11h00");
	Summary result = TimeCompile.processLines(lines, new IdentityTagTransformer());
	assertThat(result.getTotalDuration().getMinutes(), equalTo(0));
	assertThat(result.getCategories().size(), equalTo(0));
    }

    @Test
    public void oneTaggedInterval() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00", "+mytag toto", "11h00");
	Summary result = TimeCompile.processLines(lines, new IdentityTagTransformer());
	assertThat(result.getTotalDuration().getMinutes(), equalTo(60));
	assertThat(result.getCategories().size(), equalTo(1));
	Category category = result.getCategories().get(0);
	assertThat(category.getTag(), equalTo(new Tag("mytag")));
	assertThat(category.getDuration().getMinutes(), equalTo(60));
    }

    @Test
    public void twoTaggedIntervals() throws TimeCompileException {
	List<String> lines = Arrays.asList("10h00", "+mytag toto", "11h00", "+myothertag", "11h20");
	Summary result = TimeCompile.processLines(lines, new IdentityTagTransformer());
	assertThat(result.getTotalDuration().getMinutes(), equalTo(80));
	assertThat(result.getCategories().size(), equalTo(2));
	Category category = result.getCategories().get(0);
	assertThat(category.getTag(), equalTo(new Tag("mytag")));
	assertThat(category.getDuration().getMinutes(), equalTo(60));
	Category category2 = result.getCategories().get(1);
	assertThat(category2.getTag(), equalTo(new Tag("myothertag")));
	assertThat(category2.getDuration().getMinutes(), equalTo(20));
    }
}
