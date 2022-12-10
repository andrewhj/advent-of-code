package net.higility.advent2022

import org.scalatest.matchers.must.Matchers.*
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.prop.TableDrivenPropertyChecks.*

class Day6Test extends TestBase with TableDrivenPropertyChecks:
  describe("Day6") {
    val problem = Day6Problem()
    describe("first datastream") {
      val input = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
      problem.part1(input) must be(7)
    }
    describe("more examples") {
      val inputs = Table(
        ("buffer", "first marker after character"),
        ("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7),
        ("bvwbjplbgvbhsrlpgdmjqwftvncz", 5),
        ("nppdvjthqldpwncqszvftbrmjlhg", 6),
        ("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10),
        ("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11)
      )

      forAll(inputs) { (buf, idx) =>
        problem.part1(buf) must be(idx)
      }
    }
    describe("part2") {
      val inputs = Table(
        ("buffer", "first marker after character"),
        ("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 19),
        ("bvwbjplbgvbhsrlpgdmjqwftvncz", 23),
        ("nppdvjthqldpwncqszvftbrmjlhg", 23),
        ("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 29),
        ("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 26)
      )

      forAll(inputs) { (buf, idx) =>
        problem.part2(buf) must be(idx)
      }
    }
  }
