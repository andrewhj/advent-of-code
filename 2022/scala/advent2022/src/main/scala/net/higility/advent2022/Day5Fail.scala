package net.higility.advent2022

import net.higility.advent.common.AdventProblem
import net.higility.advent.common.Day.Day5
import net.higility.advent.common.Part.*

import java.util.regex.Pattern
import scala.annotation.tailrec

case class Box(box: Char)

case class Stack[T](currentStack: List[T]) {
  def push(elt: T): Stack[T] = Stack(elt :: currentStack)

  def pop(): (Option[T], Stack[T]) = (currentStack.headOption, Stack(currentStack.tail))

  def peek(): Option[T] = currentStack.headOption
}

case class Dock(stacks: Array[Stack[Box]]) {
  def tops: IndexedSeq[Option[Box]] =
    stacks.map(_.peek())
}

case class Move(count: Int, source: Int, dest: Int)

class Day5Part1 extends AdventProblem:
  override val day  = Day5
  override val part = Part1

  val sample: String =
    """    [D]
      |[N] [C]
      |[Z] [M] [P]
      | 1   2   3
      |
      |move 1 from 2 to 1
      |move 3 from 1 to 3
      |move 2 from 2 to 1
      |move 1 from 1 to 2""".stripMargin

  def parseDock(layout: List[String]): Dock =
    val separatorIndex: Int              = layout.indexWhere(_.trim().isEmpty)
    val stack                            = layout.take(separatorIndex)
    val moves: Seq[String]               = layout.drop(separatorIndex + 1)
    val startingStack: Array[Stack[Box]] = parseStack(stack)
    val startingDock                     = Dock(startingStack)
    var dock                             = startingDock
//    for {
//      moveString <- moves
//      move       <- parseMove(moveString)
//    } yield (dock = update(dock, move))
    dock

  def separateDockAndMoves(layout:List[String]): (Dock, Seq[String])=
    val separatorIndex: Int = layout.indexWhere(_.trim().isEmpty)
    val stack = layout.take(separatorIndex)
    val moves: Seq[String] = layout.drop(separatorIndex + 1)
    val startingStack: Array[Stack[Box]] = parseStack(stack)
    val startingDock = Dock(startingStack)
    val dock = startingDock
    (dock, moves)


  def processMoves(currentDock: Dock, moves: Seq[String]): Dock =
    val parsedMoves: Seq[Option[Move]] = moves.map(parseMove)
    parsedMoves.foldLeft(currentDock)((dock,move)=> move.map(update(dock,_)).getOrElse(dock))

  def parseStack(stack: Seq[String]): Array[Stack[Box]] = {
    val value1: Seq[String] = stack.takeWhile(_.trim.nonEmpty).toList.reverse
    val indexLine           = value1.head

    val value: Seq[Seq[(Int, Box)]] = stack.map(parseBoxRow)
    val stacks: Array[Stack[Box]]   = makeStack(maxIndex(indexLine))
    for {
      row        <- value.reverse
      (idx, box) <- row
      s = stacks(idx)
      _ = stacks(idx) = s.push(box)
    } yield (stacks(idx).push(box))
    stacks
  }

  def part1(input: List[String]): Unit = {
//    val startingDock = parseDock(input)
    val (startingDock, moves) = separateDockAndMoves(input)
    val movesToTake=moves
    println(movesToTake)
    println(s"movesToTake=${movesToTake}")
    val finalDock=processMoves(startingDock, movesToTake)
    val message=finalDock.tops.foldLeft("")((s,os) => s+os.map(_.box).getOrElse(""))
    println(s"final Dock ${finalDock.stacks.toList}")
    println(s"final Dock ${finalDock.tops}")
    println(s"message ${message}")
  }

  def parseMove(str: String): Option[Move] =
    val pattern = "move ([0-9]+) from ([0-9]+) to ([0-9]+)".r
    str match {
      case pattern(count, src, dest) =>
        Some(Move(count.toInt, src.toInt, dest.toInt))
      case _ => None
    }

  def update(dock: Dock, move: Move): Dock =
    move match {
      case Move(0, _, _) => dock
      case Move(c: Int, s: Int, d: Int) => {
        val (srcIdx, dstIdx)           = (s - 1, d - 1)
        val stacks: Array[Stack[Box]]  = dock.stacks
        val (extracted, updatedSource) = stacks(srcIdx).pop()
        extracted
          .flatMap(box => {
            val updatedDest = stacks(dstIdx).push(box)
            val updatedStacks: Array[Stack[Box]] =
              dock.stacks.updated(srcIdx, updatedSource).updated(dstIdx, updatedDest)
            val updatedDock: Dock = dock.copy(stacks = updatedStacks)
            Some(update(updatedDock, Move(c - 1, s, d)))
          })
          .getOrElse(dock)
      }
    }

  def makeStack(size: Int): Array[Stack[Box]] =
    @tailrec
    def makeS(acc: Array[Stack[Box]], remaining: Int): Array[Stack[Box]] =
      if (remaining == 0) then acc
      else makeS(Stack(List.empty) +: acc, remaining - 1)

    makeS(Array.empty, size).reverse

  def maxIndex(idxLine: String): Int =
    idxLine.trim.reverse.takeWhile(_ != ' ').reverse.toInt

  def parseBoxRow(box: String): Seq[(Int, Box)] =
    def parseNextBox(str: String, offset: Int): Seq[(Int, Box)] =
      val boxStart = str.indexOf("[")
      val boxEnd   = str.indexOf("]")
      val boxIndex = boxStart / 4

      if (boxStart < 0) then List.empty
      else
        (boxIndex + offset, Box(str.substring(boxStart + 1, boxEnd).toCharArray.head)) +: parseNextBox(
          str.substring(boxEnd + 1),
          offset + 1
        )

    parseNextBox(box, 0)

object Day5Problem extends App:
  val problem = Day5Part1()
//  problem.part1(problem.sample.split("\n").toList)
  problem.part1(problem.fileInput)
