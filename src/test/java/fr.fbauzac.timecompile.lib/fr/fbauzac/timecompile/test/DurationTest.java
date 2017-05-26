package fr.fbauzac.timecompile.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import fr.fbauzac.timecompile.lib.Duration;

public final class DurationTest {
    @Test
    public void test8HoursAsString() {
	Duration duration = Duration.ofMinutes(8 * 60);
	assertThat(duration.toString(), equalTo("1d1h"));
    }
}
