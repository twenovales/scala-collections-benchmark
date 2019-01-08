# CollectionBenchmark

This project demonstrates a performance regression with `Seq`/`Vector` in Scala 2.12, versus 2.11.

The difference appears to be allocation of `ArrayOps.ofInt` instances (per a heap dump of a running benchmark).

2.13.0-M5 (only the `existsEmptinessBefore` benchmark show the issue):
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessAfter:·gc.alloc.rate.norm","thrpt",1,25,0.000451,0.000008,"B/op"`
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessBefore:·gc.alloc.rate.norm","thrpt",1,25,11187.200541,161.059687,"B/op"`

2.12 (both benchmarks show the issue):
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessAfter:·gc.alloc.rate.norm","thrpt",1,25,16104.000465,0.000010,"B/op"`
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessBefore:·gc.alloc.rate.norm","thrpt",1,25,11166.400830,157.527739,"B/op"`

2.11:
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessAfter:·gc.alloc.rate.norm","thrpt",1,25,96.000244,0.000006,"B/op"`
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessBefore:·gc.alloc.rate.norm","thrpt",1,25,96.000255,0.000005,"B/op"`

## Running
```
sbt
jmh:run -prof gc -rff jmh-result-2.12.8.csv
++ 2.11.11
jmh:run -prof gc -rff jmh-result-2.11.11.csv
++ 2.13.0-M5
jmh:run -prof gc -rff jmh-result-2.13.0-M5.csv
```
