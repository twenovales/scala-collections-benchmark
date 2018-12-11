package com.twitter.benchmark

import org.openjdk.jmh.annotations.Benchmark
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

object CollectionsBenchmark {
  private[this] val _testSeqs =
    Gen.containerOfN[Seq, Seq[Int]](
      1000,
      Gen.containerOf[Seq, Int](arbitrary[Int])
    ).sample.get
  private[this] val _testArrays = _testSeqs.map(_.toArray)
  private[this] val _testSets = _testSeqs.map(_.toSet)

  protected def testSeqs = _testSeqs
  protected def testArrays = _testArrays
  protected def testSets = _testSets

	private[benchmark] def mapFn1(i: Int) = i * 2
  private[benchmark] def mapFn2(i: Int) = i * 3

  private[benchmark] val testTupleArrays = testArrays.map(_.map { x => (mapFn1(x), mapFn2(x)) })
}

class CollectionsBenchmark {
  import CollectionsBenchmark._

  @Benchmark
  def existsEmptinessAfter(): Int = testArrays.count(_.nonEmpty)

  @Benchmark
  def existsEmptinessBefore(): Int = testArrays.count(_.exists(_ => true))

  @Benchmark
  def existsToContainsArrayAfter(): Seq[Boolean] = testArrays.map { testArray => testArray.contains(testArray.last) }

  @Benchmark
  def existsToContainsSeqAfter(): Seq[Boolean] = testSeqs.amp { testSeq => testSeq.contains(testSeq.last) }

  @Benchmark
  def existsToContainsSeqBefore(): Seq[Boolean] = testSeqs.map { testSeq => val key = testSeq.last; testSeq.exists(_ == key) }

  @Benchmark
  def existsToContainsSetBefore(): Seq[Boolean] = testSets.map { testSet => val key = testSet.last; testSet.exists(_ == key) }

  @Benchmark
  def firstElementArrayAfter(): Seq[Int] = testArrays.map(_.head)

  @Benchmark
  def firstElementSeqAfter(): Seq[Int] = testSeqs.map(_.head)

  @Benchmark
  def firstElementSeqBefore(): Seq[Int] = testSeqs.map(_(0))

  @Benchmark
  def lastElementArrayAfter(): Seq[Int] = testArrays.map(_.last)

  @Benchmark
  def lastElementSeqAfter(): Seq[Int] = testSeqs.map(_.last)

  @Benchmark
  def lastElementSeqBefore(): Seq[Int] = testSeqs.map { testSeq => testSeq(testSeq.length - 1) }

  @Benchmark
  def mapNotUnzipBefore(): Seq[Array[Int]] = testTupleArrays.map(_.unzip._1)

  @Benchmark
  def minSeqAfter(): Seq[Int] = testSeqs.map(_.min)

  @Benchmark
  def minSeqBefore(): Seq[Int] = testSeqs.map(_.sorted.head)

  @Benchmark
  def reverseMapArrayAfter(): Seq[Array[Int]] = testArrays.map(_.reverseMap(mapFn1))

  @Benchmark
  def reverseMapSeqAfter(): Seq[Seq[Int]] = testSeqs.map(_.reverseMap(mapFn1))

  @Benchmark
  def reverseMapSeqBefore(): Seq[Seq[Int]] = testSeqs.map(_.map(mapFn1).reverse)

  @Benchmark
  def reverseSortSeqAfter(): Seq[Seq[Int]] = testSeqs.map(_.sorted(Ordering[Int].reverse))

  @Benchmark
  def reverseSortSeqBefore(): Seq[Seq[Int]] = testSeqs.map(_.sorted.reverse)

  @Benchmark
  def seqLengthSeqAfter(): Int = testSeqs.count(_.nonEmpty)
}
