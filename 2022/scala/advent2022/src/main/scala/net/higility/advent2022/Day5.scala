package net.higility.advent2022

//import net.higility.advent.common.Day.Day5
//
//case class Box(box: Char)
//
//case class Stack(boxes: List[Box])
//
//case class Dock(stacks: List[Stack])
//
//class Day5Part1 extends AdventProblem:
//  override val day = Day5
//  override val part = Part1
//
//  val sample =
//    """[D]
//      |[N] [C]
//      |[Z] [M] [P]
//      | 1   2   3 """.stripMargin
//
//  def dockParser(layout: List[String]): Dock = ???
//
//  def boxParser(box: String): Box =
//    val boxStart = box.indexOf("[")
//    val boxEnd = box.indexOf("]")
//    Box(box.substring(boxStart, boxEnd).toCharArray.head)
