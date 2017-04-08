# -*- Python -*-

java_test(
    name = "IntervalTest",
    size = "small",
    srcs = ["fr/fbauzac/timecompile/IntervalTest.java"],

    # Why do I need to specify this?
    test_class = "fr.fbauzac.timecompile.IntervalTest",

    deps = [
        "//lib:time-compile",
    ],
)

java_test(
    name = "TimeCompileTest",
    size = "small",
    srcs = ["fr/fbauzac/timecompile/TimeCompileTest.java"],
    test_class = "fr.fbauzac.timecompile.TimeCompileTest",
    deps = ["//lib:time-compile"],
)

java_test(
    name = "DurationTest",
    size = "small",
    srcs = ["fr/fbauzac/timecompile/DurationTest.java"],
    test_class = "fr.fbauzac.timecompile.DurationTest",
    deps = ["//lib:time-compile"],
)
