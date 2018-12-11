# CollectionBenchmark

This project demonstrates a performance regression with `Seq`/`Vector` in Scala 2.12, versus 2.11.

The difference appears to be allocation of `ArrayOps.ofInt` instances (per a heap dump of a running benchmark).

2.12:
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessAfter:·gc.alloc.rate.norm","thrpt",1,25,16104.000447,0.000019,"B/op"`
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessBefore:·gc.alloc.rate.norm","thrpt",1,25,11224.000773,138.177630,"B/op"`

2.11:
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessAfter:·gc.alloc.rate.norm","thrpt",1,25,96.000244,0.000006,"B/op"`
`"com.twitter.benchmark.CollectionsBenchmark.existsEmptinessBefore:·gc.alloc.rate.norm","thrpt",1,25,96.000255,0.000005,"B/op"`

## Running
```
sbt
jmh:run -prof gc -rff jmh-result-2.12.7.csv
++ 2.11.11
jmh:run -prof gc -rff jmh-result-2.11.11.csv
```