package net.higility.advent2022

import net.higility.advent2022.Shape.*

class Day2Test extends TestBase {
  val problem = Day2Part1Problem()
  describe("Paper Rock Scissors game") {
    describe("Rock") {
      it("loses to paper") {
        assert(!Rock.beats(Paper))
      }
      it("beats scissors") {
        assert(Rock.beats(Scissors))
      }
    }
    describe("Paper") {
      it("beats rock") {
        assert(Paper.beats(Rock))
      }
      it("loses to scissors") {
        assert(!Paper.beats(Scissors))
      }
    }
    describe("Scissors") {
      it("loses to rock") {
        assert(!Scissors.beats(Rock))
      }
      it("beats paper") {
        assert(Scissors beats Paper)
      }
    }
    describe("ties") {
      it("Rock ties rock") {
        assert(Rock.ties(Rock))
      }
      it("Rock doesn't tie scissors") {
        assert(!Rock.ties(Scissors))
      }
    }
  }
  describe("Parsing") {
    describe("Symbol") {
      it("loads Rock from A") {
        assert(Shape.fromSymbol('A').contains(Rock))
      }
      it("loads Paper from B") {
        assert(Shape.fromSymbol('B').contains(Paper))
      }
      it("loads Scissors from C") {
        assert(Shape.fromSymbol('C').contains(Scissors))
      }
      it("loads nothing from X") {
        assert(Shape.fromSymbol('X').isEmpty)
      }
    }
    describe("Response") {
      it("loads nothing from A") {
        assert(Shape.fromResponse('A').isEmpty)
      }
      it("loads Rock from X") {
        assert(Shape.fromResponse('X').contains(Rock))
      }
      it("loads Paper from Y") {
        assert(Shape.fromResponse('Y').contains(Paper))
      }
      it("loads Scissors from Z") {
        assert(Shape.fromResponse('Z').contains(Scissors))
      }
    }
    describe("Plan") {
      it("returns empty on row failure") {
        assert(problem.parseRow("2J").isEmpty)
      }
      it("returns empty row on failure to parse response") {
        assert(problem.parseRow("A J").isEmpty)
      }
      it("returns valid plan on proper row") {
        assert(problem.parseRow("A X").contains(Plan(Rock, Rock)))
        assert(problem.parseRow("A Y").contains(Plan(Rock, Paper)))
      }
    }
  }
  describe("Part 2 parsing") {
    val part2 = Day2Part2Problem()
    it("Chooses loser from X") {
      assert(part2.parseRow("A X").contains(Plan(Rock, Scissors)))
    }
    it("Chooses tie from Y") {
      assert(part2.parseRow("B Y").contains(Plan(Paper, Paper)))
    }
    it("Chooses winner from Z") {
      assert(part2.parseRow("C Z").contains(Plan(Scissors, Rock)))
    }
  }
}
