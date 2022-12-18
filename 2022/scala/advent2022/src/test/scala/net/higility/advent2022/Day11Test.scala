package net.higility.advent2022

import org.scalatest.matchers.must.Matchers.*

class Day11Test extends TestBase {
  val problem = Day11Problem()
  describe("parsing") {
    describe("raw") {
      it("separates into monkeys") {
        val input: List[String] =
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
            |    If false: throw to monkey 0""".stripMargin
            .split("\n")
            .map(_.trim)
            .toList

        val firstMonkey: List[String] =
          """Monkey 0:
            |  Starting items: 79, 98
            |  Operation: new = old * 19
            |  Test: divisible by 23
            |    If true: throw to monkey 2
            |    If false: throw to monkey 3""".stripMargin
            .split("\n")
            .map(_.trim)
            .toList
        val secondMonkey: List[String] =
          """Monkey 1:
            |  Starting items: 54, 65, 75, 74
            |  Operation: new = old + 6
            |  Test: divisible by 19
            |    If true: throw to monkey 2
            |    If false: throw to monkey 0""".stripMargin
            .split("\n")
            .map(_.trim)
            .toList

        assert(problem.rawToMonkey(input) == List(firstMonkey, secondMonkey))

      }
    }
    describe("monkey") {
      val firstMonkey: List[String] =
        """Monkey 0:
          |  Starting items: 79, 98
          |  Operation: new = old * 19
          |  Test: divisible by 23
          |    If true: throw to monkey 2
          |    If false: throw to monkey 3""".stripMargin
          .split("\n")
          .map(_.trim)
          .toList
      //      it("parses into domain object") {
      //        problem.parseMonkey(firstMonkey)
      //      }
      it("parses starting items") {
        assert(problem.parseStartingItems(firstMonkey.drop(1).head).toList == List(79, 98))
      }
    }
  }

}