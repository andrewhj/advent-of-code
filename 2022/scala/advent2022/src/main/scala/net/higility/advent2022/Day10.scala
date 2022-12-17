package net.higility.advent2022

import net.higility.advent.common.AdventProblem
import net.higility.advent.common.Day.Day10
import net.higility.advent.common.Part.*

class Day10Problem extends AdventProblem:
  val initialState = State(1)

  override def day = Day10

  override def part = Part1

  def part1(input: List[String]): Unit = {
    val instructions = input.map(parseInstruction)
    val finalState: CycleState = instructions.foldLeft(CycleState.initial)((state, maybeInstruction) => {
      maybeInstruction.fold(state)(state.append)
    })
    val idxValues = List(20, 60, 100, 140, 180, 220)
    val finalSum = idxValues.map(finalState.specialValue)
      .sum
    println(s"sum=$finalSum")

  }

  def parseInstruction(str: String): Option[Instruction] =
    str match {
      case "noop" => Some(Nop)
      case s if s.startsWith("addx") => {
        val (s"addx $num") = s: @unchecked
        Some(Add(num.toInt))
      }
      case _ => None
    }

  sealed trait Instruction:
    def cyclesToComplete: Int

  case class State(x: Int)

  case class NextState(x: Int, cyclesRemaining: Int)

  case class CycleState(valueAtCycle: Map[Int, Int]) {
    lazy val lastCycle = valueAtCycle.keys.max
    lazy val currentValue = valueAtCycle(lastCycle)

    def append(instruction: Instruction): CycleState =
      val cycleIndex = lastCycle + instruction.cyclesToComplete
      val newValue = instruction match
        case Nop => currentValue
        case Add(a) => currentValue + a
      val updatedValues = valueAtCycle + (cycleIndex -> newValue)
      CycleState(updatedValues)

    def specialValue(cycle: Int): Int =
      valueAt(cycle) * cycle

    def valueAt(cycle: Int): Int =
      val idx = valueAtCycle.filter((idx, _) => idx < cycle).keySet.toList.sortWith((a, b) => a > b).headOption
      idx.flatMap(valueAtCycle.get).getOrElse(1)
  }

  //    override val instructionPattern = s"addx $amount"

  case class Add(amt: Integer) extends Instruction:
    override val cyclesToComplete = 2

  object CycleState:
    def initial = CycleState(Map[Int, Int](0 -> 1))

  case object Nop extends Instruction:
    override val cyclesToComplete = 1
end Day10Problem

object Day10Solution extends App:
  val smallSample =
    """noop
      |addx 3
      |addx -5""".stripMargin
      .split("\n")
      .map(_.trim)
      .toList
  val sample =
    """addx 15
      |addx -11
      |addx 6
      |addx -3
      |addx 5
      |addx -1
      |addx -8
      |addx 13
      |addx 4
      |noop
      |addx -1
      |addx 5
      |addx -1
      |addx 5
      |addx -1
      |addx 5
      |addx -1
      |addx 5
      |addx -1
      |addx -35
      |addx 1
      |addx 24
      |addx -19
      |addx 1
      |addx 16
      |addx -11
      |noop
      |noop
      |addx 21
      |addx -15
      |noop
      |noop
      |addx -3
      |addx 9
      |addx 1
      |addx -3
      |addx 8
      |addx 1
      |addx 5
      |noop
      |noop
      |noop
      |noop
      |noop
      |addx -36
      |noop
      |addx 1
      |addx 7
      |noop
      |noop
      |noop
      |addx 2
      |addx 6
      |noop
      |noop
      |noop
      |noop
      |noop
      |addx 1
      |noop
      |noop
      |addx 7
      |addx 1
      |noop
      |addx -13
      |addx 13
      |addx 7
      |noop
      |addx 1
      |addx -33
      |noop
      |noop
      |noop
      |addx 2
      |noop
      |noop
      |noop
      |addx 8
      |noop
      |addx -1
      |addx 2
      |addx 1
      |noop
      |addx 17
      |addx -9
      |addx 1
      |addx 1
      |addx -3
      |addx 11
      |noop
      |noop
      |addx 1
      |noop
      |addx 1
      |noop
      |noop
      |addx -13
      |addx -19
      |addx 1
      |addx 3
      |addx 26
      |addx -30
      |addx 12
      |addx -1
      |addx 3
      |addx 1
      |noop
      |noop
      |noop
      |addx -9
      |addx 18
      |addx 1
      |addx 2
      |noop
      |noop
      |addx 9
      |noop
      |noop
      |noop
      |addx -1
      |addx 2
      |addx -37
      |addx 1
      |addx 3
      |noop
      |addx 15
      |addx -21
      |addx 22
      |addx -6
      |addx 1
      |noop
      |addx 2
      |addx 1
      |noop
      |addx -10
      |noop
      |noop
      |addx 20
      |addx 1
      |addx 2
      |addx 2
      |addx -6
      |addx -11
      |noop
      |noop
      |noop""".stripMargin
      .split("\n")
      .map(_.trim)
      .toList

  val problem = Day10Problem()
  println("Part1")
  problem.part1(sample)
  problem.part1(problem.fileInput)

