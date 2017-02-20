# -*- Python -*-

java_library(
    name = "time-compile",
    srcs = glob(["src/main/java/**/*.java"]),
    deps = [
        "@jopt//jar",
    ],
)

java_binary(
    name = "time-compile-cli",
    main_class = "fr.fbauzac.TimeCompile",
    runtime_deps = [
        "//:time-compile", 
    ],
)

java_test(
    name = "IntervalTest",
    size = "small",
    srcs = ["src/test/java/fr/fbauzac/IntervalTest.java"],
    deps = [
        ":time-compile",
    ],
)
