package net.higility

import Common.splitSteps

import net.higility.Day2.Part2.AimedPosition

object Day2:
  val sample =
    """
      |forward 5 
      |down 5 
      |forward 8 
      |up 3 
      |down 8 
      |forward 2
      |""".stripMargin

  enum Direction:
    case Forward, Down, Up

  sealed trait Position(horizontal: Int, depth: Int) {
    def move(instruction: Instruction): Position
//    def move(forwardAmt: Int, depthAmt: Int): Position
    def magnitude: Int = horizontal * depth
  }

  case class StandardPosition(horizontal: Int, depth: Int)
      extends Position(horizontal, depth) {

    override def move(instruction: Instruction): StandardPosition =
      instruction match {
        case Instruction(Direction.Down, amt) =>
          StandardPosition(horizontal, depth + amt)
        case Instruction(Direction.Up, amt) =>
          StandardPosition(horizontal, depth - amt)
        case Instruction(Direction.Forward, amt) =>
          StandardPosition(horizontal + amt, depth)
      }
  }

  object Direction:
    def fromString(s: String): Option[Direction] = s match {
      case "forward" => Some(Forward)
      case "down"    => Some(Down)
      case "up"      => Some(Up)
      case _         => None
    }

  case class Instruction(direction: Direction, amount: Int)

  object Instruction:
    import Day2.Direction.*

    def fromLine(line: String): Option[Instruction] = {
      val tokens: Array[String] = line.split(" ")
      for {
        direction <- Direction.fromString(tokens(0))
        amount <- tokens(1).toIntOption
      } yield Instruction(direction, amount)
    }

  def parseInstructions(unparsedLines: Seq[String]): Seq[Instruction] = {
    for {
      unparsedLine <- unparsedLines
      instruction <- Instruction.fromLine(unparsedLine)
    } yield instruction
  }

  def process(instructions: Seq[Instruction]): StandardPosition = {
    def proc(
        remaining: Seq[Instruction],
        position: StandardPosition
    ): StandardPosition =
      remaining match {
        case x :: xs => proc(xs, position.move(x))
        case Nil     => position
      }
    proc(instructions, StandardPosition(0, 0))
  }

  def processInstructionList(instructionList: String): Unit = {
    val steps: Seq[String] = splitSteps(instructionList)
    processInstructionList(steps)
  }

  def processInstructionList(instructionText: Seq[String]): Unit = {
    val instructions = parseInstructions(instructionText)
    val finalPosition: StandardPosition = process(instructions)
    println(s"final position $finalPosition")
//    finalPosition.magnitude
    println(s"magnitude ${finalPosition.magnitude}")
  }

  object Part2:
    case class AimedPosition(horizontal: Int, depth: Int, aim: Int)
        extends Position(horizontal, depth) {

      override def move(instruction: Instruction): AimedPosition =
        instruction match {
          case Instruction(Direction.Down, amt) =>
            AimedPosition(horizontal, depth, aim + amt)
          case Instruction(Direction.Up, amt) =>
            AimedPosition(horizontal, depth, aim - amt)
          case Instruction(Direction.Forward, amt) =>
            AimedPosition(horizontal + amt, depth + (aim * amt), aim)
        }

    }

    def process(instructions: Seq[Instruction]): AimedPosition = {
      def proc(
          remaining: Seq[Instruction],
          position: AimedPosition
      ): AimedPosition = remaining match {
        case x :: xs => {
          val newPosition = position.move(x)
          println(s"new position $newPosition")
          proc(xs, position.move(x))
        }
        case _ => position
      }
      proc(instructions, AimedPosition(0, 0, 0))
    }

    def processInstructionList(instructionText: Seq[String]): Unit = {
      val instructions = parseInstructions(instructionText)
      val finalPosition: AimedPosition = process(instructions)
      println(s"final position $finalPosition")
      println(s"final magnituce ${finalPosition.magnitude}")
    }

object Day2App extends App:
  private val rawLines: List[String] = Common.getFileLines(Day.Day2, Part.Part1)
  private val sampleRawLines: List[String] =
    Day2.sample.trim.split("\r\n").toList
  println(s"lines $sampleRawLines")
  Day2.processInstructionList(rawLines)
  Day2.Part2.processInstructionList(sampleRawLines)
  println("*** Part 2 ***")
  Day2.Part2.processInstructionList(rawLines)
