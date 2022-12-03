package net.higility.advent.common

//object InputLoader:
//  def load(fileName:String)=
//    File.lo
//
//
import scala.io.Source

enum Day(val dirName: String):
  case Day1 extends Day("day1")
  case Day2 extends Day("day2")
  case Day3 extends Day("day3")

enum Part(val name: String):
  case Part1 extends Part("part1")
  case Part2 extends Part("part2")

object Common:
  import Day.*

  def fileLocation(day: Day, part: Part) =
    s"../inputs/${day.dirName}/${part.name}.txt"

  def getFileLines(day: Day, part: Part): List[String] = {
    val f = Source.fromFile(fileLocation(day, part))
    f.getLines().toList
  }

  def splitSteps(steps: String): List[String] =
    steps.trim
      .split("\n")
      .map(_.trim)
      .filterNot(_.isEmpty)
      .toList