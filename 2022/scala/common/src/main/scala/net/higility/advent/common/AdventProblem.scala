package net.higility.advent.common

import net.higility.advent.common.{Common, Day, Part}

trait AdventProblem {

  def day: Day

  def part: Part

  def fileInput: List[String] = Common.getFileLines(day, part)
}
