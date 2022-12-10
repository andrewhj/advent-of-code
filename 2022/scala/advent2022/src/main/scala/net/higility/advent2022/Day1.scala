package net.higility.advent2022

import net.higility.advent
import net.higility.advent.common
import net.higility.advent.common.*
import net.higility.advent.common.Day.Day1
import net.higility.advent.common.Part.*

import scala.io.{BufferedSource, Source}
import scala.util.Using

object Day1Part1 extends AdventProblem:
  override val day: Day   = Day1
  override val part: Part = Part1

  private val sample =
    """1000
      |2000
      |3000
      |
      |4000
      |
      |5000
      |6000
      |
      |7000
      |8000
      |9000
      |
      |10000""".stripMargin

//  private def inputSource: List[List[Int]] = {
//    var elves: List[List[Int]] = List.empty
//    var currentElf: List[Int]  = List.empty
//    Using(Source.fromFile(Common.fileLocation(day, part))) { file =>
//      for (line <- file.getLines()) {
//        if (line.trim.isBlank) {
//          elves = currentElf.reverse :: elves
//          currentElf = List.empty
//        } else {
//          currentElf = line.toInt :: currentElf
//        }
//      }
//    }
//    elves.reverse
//  }

  private def inputSource: List[List[Int]] = {
    def innerTake(lst: List[String]): List[List[Int]] = {
      val elf: List[String] = lst.takeWhile(_ != "")
      elf.map(_.toInt) :: innerTake(lst.dropWhile(_ != ""))
    }

    innerTake(fileInput)
  }

  private def splitElves(str: String): List[String] =
    str.split("\n\n").toList

  private def splitMeals(str: List[String]): List[List[Int]] =
    str.map(_.split("\n").map(_.toInt).toList)

  private def totalsWithIndex(lst: List[List[Int]]): List[(Int, Int)] =
    lst
      .map(_.sum)
      .zipWithIndex
      .map((a, b) => (a, b + 1))

  private def sortedTotals(lst: List[(Int, Int)]): List[(Int, Int)] =
    lst.sortWith { case ((a, _), (b, _)) => a > b }

  private def sampleProcess() =
    val steps = splitElves andThen splitMeals andThen process
    steps(sample)

  private def process(lst: List[List[Int]]): List[(Int, Int)] =
    val steps = totalsWithIndex andThen sortedTotals
    steps(lst)

  private def part2Process(part1List: List[(Int, Int)]) =
    part1List
      .take(3)
      .map((a, _) => a)
      .sum

  @main
  def main(): Unit =
    println("processing")
    val result = sampleProcess()
    println(s"result ${result.head}")
    val inputResult = process(inputSource)
    println(s"inputResult ${inputResult.head}")
    println("PART2")
    println(s"result ${part2Process(result)}")
    println(s"inputResult ${part2Process(inputResult)}")
