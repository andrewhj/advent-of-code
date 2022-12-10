package net.higility.advent2022

import net.higility.advent.common.Day.Day6
import net.higility.advent.common.Part.*
import net.higility.advent.common.{AdventProblem, Common}

class Day6Problem extends AdventProblem:
  override val day  = Day6
  override val part = Part1

  val sample = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"

//  sample.sliding(4).filter(isUnique).take(1)

  def isUnique(str: String): Boolean = str.length == str.distinct.length

  def findMarker(input: String, window: Int): String =
    input.sliding(window).filter(isUnique).next

  def part1(input: String): Int =
    val marker = findMarker(input, 4)
    input.indexOf(marker) + marker.length

  def part2(input: String): Int =
    val marker = findMarker(input, 14)
    input.indexOf(marker) + marker.length

end Day6Problem

object Day6Solution extends App:
  println("Part1")
  val part1         = Day6Problem()
  val part1Solution = part1.part1(part1.fileInput.head)
  println(s"solution $part1Solution")
  println("Part2")
  val part2Solution = part1.part2(part1.fileInput.head)
  println(s"solution $part2Solution")
