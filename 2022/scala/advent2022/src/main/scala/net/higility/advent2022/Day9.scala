package net.higility.advent2022

import net.higility.advent.common.AdventProblem
import net.higility.advent.common.Day.Day9
import net.higility.advent.common.Part.*
import net.higility.advent2022.Direction.*
enum Direction:
  case U, D, L, R

class Day9Problem extends AdventProblem:
  override val day  = Day9
  override val part = Part1

  case class Position(x: Int, y: Int) {
    def moveOne(direction: Direction) = direction match {
      case U => Position(x, y + 1)
      case D => Position(x, y - 1)
      case R => Position(x - 1, y)
      case L => Position(x + 1, y)
    }

    def follow(head: Position): Position =
      val dx = head.x - x
      val dy = head.y - y
      if (dx.abs > 1 || dy.abs > 1) then Position(x + dx.sign, y + dy.sign)
      else this
  }

  def followAll(head: Position, knots: List[Position]): (Position, List[Position]) =
    var prev = head
    val buf  = List.newBuilder[Position]
    for knot <- knots do
      val next = knot.follow(prev)
      buf += next
      prev = next
    (prev, buf.result())
  end followAll

  case class Movement(direction: Direction, magnitude: Int)
  case class State(uniques: Set[Position], head: Position, knots: List[Position])

  def step(dir: Direction, state: State): State =
    val head1          = state.head.moveOne(dir)
    val (last, knots1) = followAll(head1, state.knots)
    State(state.uniques + last, head1, knots1)

  def steps(state: State, direction: Direction): Iterator[State] =
    Iterator.iterate(state)(s => step(direction, s))

  def initialState(knots: Int): State =
    val zero = Position(0, 0)
    State(Set(zero), zero, List.fill(knots - 1)(zero))

  def parse(input: List[String]): List[Movement] = input.map { line =>
    val (s"$dir $mag") = line: @unchecked
    Movement(parseDirection(dir.head), mag.toInt)
  }

  def parseDirection(dir: Char): Direction = dir match {
    case 'U' => U
    case 'D' => D
    case 'L' => L
    case 'R' => R
  }

  def uniquePositions(moves: List[Movement], knots: Int): Int = {
    val endState = moves.foldLeft(initialState(knots)) { case (state, move) =>
      steps(state, move.direction).drop(move.magnitude).next()
    }
    endState.uniques.size
  }

  def parseUniquePositions(input: List[String], knots: Int): Int = {
    uniquePositions(parse(input), knots)
  }

  def part1(input: List[String]): Int =
    parseUniquePositions(input, knots = 2)

  def part2(input: List[String]): Int =
    parseUniquePositions(input, knots = 10)

end Day9Problem

object Day9Solution extends App:
  val sample = """R 4
               |U 4
               |L 3
               |D 1
               |R 4
               |D 1
               |L 5
               |R 2""".stripMargin
    .split("\n")
    .map(_.trim)
    .toList

  val sample2="""R 5
                |U 8
                |L 8
                |D 3
                |R 17
                |D 10
                |L 25
                |U 20""".stripMargin
    .split("\n")
    .map(_.trim)
    .toList

  val problem = Day9Problem()

  println("Part1")
  println(s"Sample: ${problem.part1(sample)}")
  println(s"Live: ${problem.part1(problem.fileInput)}")

  println("Part2")
  println(s"Movement: ${problem.parse(sample2)}")
  println(s"Sample: ${problem.part2(sample2)}")
  println(s"Live: ${problem.part2(problem.fileInput)}")
