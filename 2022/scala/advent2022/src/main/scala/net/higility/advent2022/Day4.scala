package net.higility.advent2022

import net.higility.advent.common.Day.Day4
import net.higility.advent.common.Part.*
import net.higility.advent.common.{AdventProblem, Day, Part}

import java.util.stream

trait Day4Problems:
  type Assignment = (Range, Range)

class Day4Part1 extends AdventProblem, Day4Problems:
  override val day: Day = Day4
  override val part: Part = Part1

  def parseAssignments(assignmentLine: String): Assignment = {
    val assignmeentSections: Array[String] = assignmentLine.split(",")
    val ranges: Array[Range] = assignmeentSections.map(parseRange)

    //    val tuples: Array[Range] = for {
    //      sections <- assignmeentSections
    //      ranges <- sections.split("-")
    //    } yield ranges(0).toInt to ranges(1).toInt
    //    (tuples.head, tuples.tail.head)
    (ranges.head, ranges.tail.head)
  }

  def parseRange(rangeStr: String): Range.Inclusive = {
    val splitRange: Array[Int] = rangeStr.split("-").map(_.toInt)
    splitRange(0) to splitRange(1)
  }


object Day4Problem:
  val sample: String =
    """2-4,6-8
      |2-3,4-5
      |5-7,7-9
      |2-8,3-7
      |6-6,4-6
      |2-6,4-8""".stripMargin

  @main
  def day4Main(): Unit =
    val process = Day4Part1()
    val assignments = process.fileInput
      .map(l => process.parseAssignments(l))
      .filter((sec1, sec2) => sec1.containsSlice(sec2) || sec2.containsSlice(sec1))

    val assignmentList = assignments.count(_ => true)

    println(s"Assignments: $assignmentList")
    println("TEST")

    val value: List[(Set[Int], Set[Int])] = process.fileInput
      .map(l => process.parseAssignments(l))
      .map { case (a, b) => (a.toSet, b.toSet) }

    val overlapping: List[(Set[Int], Set[Int])] = value
      .filterNot { case (x, y) => x.intersect(y).isEmpty }
    println(s"Any oerlap: ${overlapping.size}")