# -*- Python -*-

java_test(
    name = "IntervalTest",
    size = "small",
    srcs = ["fr/fbauzac/IntervalTest.java"],

    # Why do I need to specify this?
    test_class = "fr.fbauzac.IntervalTest",

    deps = [
        "//lib:time-compile",
    ],
)

java_test(
    name = "TimeCompileTest",
    size = "small",
    srcs = ["fr/fbauzac/TimeCompileTest.java"],
    test_class = "fr.fbauzac.TimeCompileTest",
    deps = ["//lib:time-compile"],
)
