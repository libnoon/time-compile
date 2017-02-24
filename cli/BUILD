# -*- Python -*-

java_binary(
    name = "cli",
    srcs = glob(["**/*.java"]),
    deps = [
        "//lib:time-compile",
        "@jopt//jar",
    ],
    main_class = "fr.fbauzac.timecompile.TimeCompileCli",
)
