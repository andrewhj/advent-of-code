package net.higility.advent2022

import net.higility.advent.common.Day.Day3
import net.higility.advent.common.Part.*
import net.higility.advent.common.{AdventProblem, Day, Part}

class Day3Part1 extends AdventProblem {
  type Rucksack = String
  type Compartment = (String, String)

  override val day: Day = Day3
  override val part: Part = Part1
  val sample: String =
    """vJrwpWtwJgWrhcsFMMfFFhFp
      |jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
      |PmmdzqPrVvPwwTWBwg
      |wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
      |ttgJtRGJQctTZtZT
      |CrZsJsPPZsGzwwsLwLmpwMDw""".stripMargin

  def part1: Int = {
    val cpts: Seq[Int] = fileInput
      .map(compartments)
      .map(commonItems)
      .flatMap(_.toList.map(priority))

    cpts.sum
  }

  def commonItems(compartment: Compartment): Set[Char] =
    val first = compartment._1.toSet
    val second = compartment._2.toSet
    first.intersect(second)

  def compartments(rucksack: Rucksack): Compartment = {
    val midpoint: Int = rucksack.length / 2
    val secondCompartment = rucksack.substring(midpoint)
    val firstCompartemnt = rucksack.substring(0, midpoint)
    (firstCompartemnt, secondCompartment)
  }

  def priority(item: Char): Int =
    if item.isUpper then
      (item - 'A') + 27
    else
      (item - 'a') + 1

  def part2: Int = {
    //    val sampleInput: Seq[Rucksack] = rucksacks(sample)
    val sampleInput: Seq[Rucksack] = fileInput
    val groupedSacks = sampleInput.grouped(3)
    val contained = for {
      gs <- groupedSacks
      ci <- containedItems(gs)
      prio = priority(ci)
    } yield prio
    contained
      .sum
  }

  def containedItems(sack: Seq[Rucksack]): Set[Char] =
    val chars = ('a' to 'z').toSet.union(('A' to 'Z').toSet)
    val charSet: Seq[Set[Char]] = sack.map(_.toSet)
    charSet.foldLeft(chars)(_.intersect(_))

  def rucksacks(str: String): List[Rucksack] =
    sample.split("\n").toList
}

object Day3Problem:
  @main
  def process(): Unit =
    val problem = Day3Part1()
    val result = problem.part1
    println(s"result=$result")
    val part2Result = problem.part2
    println(s"Part2 result=$part2Result")
