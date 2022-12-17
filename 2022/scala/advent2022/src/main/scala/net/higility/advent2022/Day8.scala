package net.higility.advent2022

import net.higility.advent.common.AdventProblem
import net.higility.advent.common.Day.Day8
import net.higility.advent.common.Part.*

class Day8Problem extends AdventProblem:
  type Field[A]        = List[List[A]]
  type HeightField     = Field[Int]
  type ScoreField      = Field[Int]
  type VisibilityField = Field[Boolean]

  override val day  = Day8
  override val part = Part1

  val sample =
    """30373
      |25512
      |65332
      |33549
      |35390""".stripMargin
      .split("\n")
      .map(_.trim)
      .toList

  def parse(input: List[String]): HeightField = input.map(line => line.map(char => char.asDigit).toList)

  def rollingMax(line: Seq[Int]): Seq[Int] = line.scanLeft(-1) { case (max, curr) => Math.max(max, curr) }.init

  def rollingMax(line: String) = line.scanLeft(-1) { case (max, curr) => Math.max(max, curr) }.init

  def computeVisibility(ls: HeightField): VisibilityField = ls.map { line =>
    val rollingMax = line.scanLeft(-1) { case (max, curr) => Math.max(max, curr) }.init
    rollingMax.zip(line).map { case (max, curr) => max < curr }
  }

  def computeScore(ls: HeightField): ScoreField = ls.map { line =>
    val rollingLengths = line.scanRight(List.fill(10)(0)) { case (curr, lengths) =>
        lengths.zipWithIndex.map { case (v, i) => if i <= curr then 1 else v + 1 }
      }.init
    rollingLengths.zip(line).map { case (lengths, curr) => lengths(curr) }
  }

  def computeScore2(ls: HeightField): ScoreField = ls.map { line =>
    val rollingLengths = line.scanRight(List.fill(10)(0)) {
      case (curr, lengths) =>
        lengths.zipWithIndex.map { case (v, i) => if i <= curr then 1 else v + 1 }
    }.init
    rollingLengths.zip(line).map { case (lengths, curr) => lengths(curr) }
  }

  extension [A](xss: Field[A])
    def megaZip[B](yss: Field[B]): Field[(A, B)] = (xss zip yss).map((xs, ys) => xs zip ys)
    def megaMap[B](f: A => B): Field[B]          = xss.map(_.map(f))
    def megaReduce(f: (A, A) => A): A            = xss.map(_.reduce(f)).reduce(f)
  def combine[A](op: ((A, A)) => A)(f1: Field[A], f2: Field[A]): Field[A] = f1.megaZip(f2).megaMap(op)

  def computeInAllDirections[A, B](xss: Field[A], f: Field[A] => Field[B]): List[Field[B]] =
    for
      transpose <- List(false, true)
      reverse   <- List(false, true)
    yield
      val t   = if transpose then xss.transpose else xss
      val in  = if reverse then t.map(_.reverse) else t
      val res = f(in)
      val r   = if reverse then res.map(_.reverse) else res
      val out = if transpose then r.transpose else r
      out

  def part1(input: List[String]): Int =
    val parsed           = parse(input)
    val visibilityFields = computeInAllDirections(parsed, computeVisibility)
    val visibiliytField  = visibilityFields.reduce(combine(_ | _))
    visibiliytField
      .megaMap(if _ then 1 else 0)
      .megaReduce(_ + _)

  def part2(input: List[String]): Int =
    val parsed      = parse(input)
    val scoreFields = computeInAllDirections(parsed, computeScore)
    val scoreField  = scoreFields.reduce(combine(_ * _))
    scoreField.megaReduce(_ max _)

  def debug(input: List[String]): Unit =
    val parsed = parse(input)
    val scoreFields: List[ScoreField] = computeInAllDirections(parsed, computeScore)
    val scoreField: ScoreField = scoreFields.reduce(combine(_ * _))
    println(s"scoreField $scoreField")
    scoreField.megaReduce(_ max _)

object Day8Solution extends App:
  val problem = Day8Problem()
  println(s"Part1")
//  println(s"Computed: ${problem.computeInAllDirections(problem.parse(problem.sample),problem.computeVisibility)}")
  println(s"Sample: ${problem.part1(problem.sample)}")
  println(s"Input: ${problem.part1(problem.fileInput)}")
  println(s"Part2 is broken")
//  println(s"Parsed: ${problem.parse(problem.sample)}")
//  problem.debug(problem.sample)
//  println(s"Sample: ${problem.part2(problem.sample)}")
//  println(s"Computed: ${problem.computeInAllDirections(problem.parse(problem.sample), problem.computeScore)}")
//  println(s"Computed: ${problem.computeInAllDirections(problem.parse(problem.sample), problem.computeScore2)}")
