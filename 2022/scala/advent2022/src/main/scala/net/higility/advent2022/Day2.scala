package net.higility.advent2022

import net.higility.advent.common.Day.Day2
import net.higility.advent.common.Part.*
import net.higility.advent.common.{AdventProblem, Day, Part}

enum Shape(val symbol: Char, val response: Char, val index: Int):
  def beats(s: Shape): Boolean = index == (s.index + 1) % 3

  def ties(s: Shape): Boolean = this == s

  case Rock extends Shape('A', 'X', 0)
  case Paper extends Shape('B', 'Y', 1)
  case Scissors extends Shape('C', 'Z', 2)

object Shape:
  def fromSymbol(str: String): Option[Shape] = str.toCharArray.headOption.flatMap(fromSymbol)

  def fromSymbol(symbol: Char): Option[Shape] = Shape.values.find(_.symbol == symbol)

  def fromResponse(str: String): Option[Shape] = str.toCharArray.headOption.flatMap(fromResponse)

  def fromResponse(response: Char): Option[Shape] = Shape.values.find(_.response == response)

  def findWinner(move: Shape): Shape = Shape.values.find(_.beats(move)).get

  def findLoser(move: Shape): Shape = Shape.values.find(move.beats _).get

end Shape

case class Plan(move: Shape, response: Shape)

object Day2Part1Problem:
  val sample =
    """A Y
      |B X
      |C Z
      |""".stripMargin


class Day2Part1Problem extends AdventProblem:
  override val day: Day = Day2
  override val part: Part = Part1

  def computePlan(str: Seq[String]): Int =
    val rounds: Seq[Int] = for {
      row <- str
      plan <- parseRow(row)
    } yield (roundPoints(plan.move, plan.response))
    rounds.sum

  def roundPoints(opponent: Shape, you: Shape): Int =
    def outcome =
      if you.beats(opponent) then 6
      else if you.ties(opponent) then 3
      else 0

    outcome + you.index + 1

  def parseRow(row: String): Option[Plan] = {
    val splitRow: Array[String] = row.split(" ")

    val maybeShape: Option[Shape] = splitRow.headOption
      .flatMap(r => Shape.fromSymbol(r))

    for {
      shape <- maybeShape
      maybeTail <- splitRow.tail.headOption
      responseShape <- parseResponse(shape, maybeTail)
    } yield Plan(shape, responseShape)
  }

  def parseResponse(move: Shape, resp: String): Option[Shape] =
    Shape.fromResponse(resp)

class Day2Part2Problem extends Day2Part1Problem:
  override val part = Part2

  override def parseResponse(move: Shape, resp: String): Option[Shape] =
    resp match {
      case "X" => Some(Shape.findLoser(move))
      case "Y" => Some(move)
      case "Z" => Some(Shape.findWinner(move))
      case _ => None
    }

object Day2Problem:
  val sample =
    """A Y
      |B X
      |C Z
      |""".stripMargin

  @main
  def runapp =
    println("Part1")
    process(Day2Part1Problem())
    println("Part2")
    process(Day2Part2Problem())

  def process(problem: Day2Part1Problem): Unit =
    val result = problem.computePlan(sample.split("\n"))
    println(s"result ${result}")
    val liveResult = problem.computePlan(problem.fileInput)
    println(s"liveResult ${liveResult}")
