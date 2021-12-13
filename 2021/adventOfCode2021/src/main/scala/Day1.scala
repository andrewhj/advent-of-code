package net.higility

import net.higility.Common.splitSteps

import scala.annotation.tailrec

enum Delta:
  case NA, Same, Increase, Decrease

object Day1:
  import Delta.*

  val sample =
    """
      |199
      |200
      |208
      |210
      |200
      |207
      |240
      |269
      |260
      |263
      |""".stripMargin

  def compareSteps(steps: List[Int]): List[Delta] = steps match {
    case x :: y :: rest => compareValues(x, y) :: compareSteps(y :: rest)
    case x :: Nil       => NA :: Nil
    case Nil            => Nil
  }

  def compareValues(a: Integer, b: Integer): Delta =
    if a > b then Decrease else if a < b then Increase else Same

  def process(input: String): Unit = {
    val rawSteps: List[String] = splitSteps(input)
    process(rawSteps)
  }

  def process(input: List[String]): Unit = {
    lazy val intSteps: List[Int] = input.map(_.toInt)
    lazy val steps: List[Delta] = compareSteps(intSteps)
    val primedSteps = NA :: steps
    val increasedMeasurements: Int = steps.count(_ == Increase)
    println(s"Measurements increased ${increasedMeasurements} times")
  }

//  def sums(lst: List[Int]): LazyList[Int] =
//    val `lst'` = 0 :: lst
//    val `lst''` = 0 :: 0 :: lst
//    val firstZip: Seq[(Int, Int)] = lst.zip(`lst'`)
//    val secondZip: Seq[((Int, Int), Int)] = firstZip.zip(`lst''`)
////    secondZip.map(((a, b), c) => a + b + c)
//    LazyList.empty

  def slidingWindow(lst: List[Int]): List[(Int, Int, Int)] = lst match {
    case x :: y :: z :: rest => (x, y, z) :: slidingWindow(lst.tail)
    case _                   => Nil
  }

  def sumWindow(lst: List[(Int, Int, Int)]) = lst.map((x, y, z) => x + y + z)

  def part2(input: List[String]) = {
    lazy val intSteps: List[Int] = input.map(_.toInt)
    val sw = slidingWindow(intSteps)
    val sums = sumWindow(sw)
    val steps = compareSteps(sums)
    val primedSteps = NA :: steps
    val increasedMeasurements: Int = steps.count(_ == Increase)
    println(s"Measurements increased ${increasedMeasurements} times")

  }

end Day1

object Day1App extends App:
  println("***** Day 1 *****")
//  Day1.process(Day1.sample)
  println("*** Part 1 ***")
  Day1.process(Common.getFileLines(Day.Day1, Part.Part1))
  println("*** Part 2 ***")
  Day1.part2(Common.getFileLines(Day.Day1, Part.Part1))
  println("***** done *****")

  println("*** Alternative? ***")
