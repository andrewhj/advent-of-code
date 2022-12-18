package net.higility.advent2022

import net.higility.advent.common.AdventProblem
import net.higility.advent.common.Day.Day11
import net.higility.advent.common.Part.*

import scala.annotation.tailrec

class Day11Problem extends AdventProblem:
  override val day = Day11
  override val part = Part1

  val monkeyMap: Map[Int, Monkey] = Map.empty

  def parseMonkey(input: Seq[String]): Option[Monkey] = {
    val monkeyId = input.headOption.flatMap {
      case s"Monkey ${m}:" => m.toIntOption
    }
    //    monkeyId
    None
  }

  def parseStartingItems(itemLine: String): Seq[Int] = {
    val s"Starting items: $items" = itemLine
    items.split(",").map(_.trim.toInt)
    //    case s"Starting items: $items" => items.split(",").map(_.trim.toIntOption)
    //    val itemList: IndexedSeq[Int] = for {
    //      s"Starting items: $items" <- itemLine
    //      i <- items.trim
    //    } yield i.toIntOption
  }

  def rawToMonkey(input: List[String]): List[List[String]] = {
    @tailrec
    def withAcc(acc: List[List[String]], remaining: List[String]): List[List[String]] = {
      remaining match {
        case Nil => acc.reverse
        case _ =>
          val monkey: List[String] = remaining.takeWhile(!_.isBlank)
          val rest: List[String] = remaining.dropWhile(!_.isBlank)
          withAcc(monkey :: acc, if rest.nonEmpty then rest.tail else rest)
      }
    }

    withAcc(List.empty, input)
  }

  case class Monkey(number: Int, startingItems: Seq[Int], operation: (Int, Int) => Int, trueIndex: Int, falseIndex: Int)

end Day11Problem

object Day11Solution extends App:
  val problem = Day11Problem()
  val sample =
    """Monkey 0:
      |  Starting items: 79, 98
      |  Operation: new = old * 19
      |  Test: divisible by 23
      |    If true: throw to monkey 2
      |    If false: throw to monkey 3
      |
      |Monkey 1:
      |  Starting items: 54, 65, 75, 74
      |  Operation: new = old + 6
      |  Test: divisible by 19
      |    If true: throw to monkey 2
      |    If false: throw to monkey 0
      |
      |Monkey 2:
      |  Starting items: 79, 60, 97
      |  Operation: new = old * old
      |  Test: divisible by 13
      |    If true: throw to monkey 1
      |    If false: throw to monkey 3
      |
      |Monkey 3:
      |  Starting items: 74
      |  Operation: new = old + 3
      |  Test: divisible by 17
      |    If true: throw to monkey 0
      |    If false: throw to monkey 1""".stripMargin
      .split("\n")
      .map(_.trim)
      .toList
