package net.higility.advent2022

class Day5Test extends TestBase {
//  val problem = Day5Part1()
//  describe("Parsers") {
//    describe("boxes") {
//      it("parses single box") {
//        assert(problem.parseBoxRow("[A]") == List((0, Box('A'))))
//      }
//      it("parses two boxes") {
//        assert(problem.parseBoxRow("[A] [B]") == List((0, Box('A')), (1, Box('B'))))
//      }
//      it("parses many boxes") {
//        assert(
//          problem
//            .parseBoxRow("[A] [B] [C] [D] [E] [F]") == List(
//            (0, Box('A')),
//            (1, Box('B')),
//            (2, Box('C')),
//            (3, Box('D')),
//            (4, Box('E')),
//            (5, Box('F'))
//          )
//        )
//      }
//      it("parses spaced boxes") {
//        assert(problem.parseBoxRow("    [Z]") == List((1, Box('Z'))))
//      }
//    }
//    describe("stacks") {
//      it("parses 2 row layout") {
//        val sample =
//          """    [A]
//            |[B] [C] [D]
//            | 1   2   3""".stripMargin
//        val lines          = sample.split("\n")
//        val expectedResult = Array(Stack(List(Box('B'))), Stack(List(Box('A'), Box('C'))), Stack(List(Box('D'))))
//        assert(problem.parseStack(lines) sameElements expectedResult)
//      }
//      it("parses 3 row layout") {
//        val sample =
//          """    [A]
//            |[B] [C]
//            |[X] [Y] [Z]
//            | 1   2   3""".stripMargin
//        val lines = sample.split("\n")
//        val expectedResult = Array(
//          Stack(List(Box('B'), Box('X'))),
//          Stack(List(Box('A'), Box('C'), Box('Y'))),
//          Stack(List(Box('Z')))
//        )
//        assert(problem.parseStack(lines) sameElements expectedResult)
//      }
//    }
//    describe("docks") {
//      describe("sandbox") {
//        val trimmed: List[String] = problem.sample.split("\n").toList
//        it("parses dock") {
//          assert(trimmed.exists(s => s.trim().isEmpty))
//        }
//        it("separates") {
//          assert(trimmed.indexWhere(_.trim().isEmpty) == 4)
//        }
//        it("containsDock") {
//          val idx   = trimmed.indexWhere(_.trim().isEmpty)
//          val stack = trimmed.take(idx)
//          assert(stack.reverse.head.stripTrailing() == " 1   2   3")
//          assert(trimmed.drop(idx + 1).head.stripTrailing() == "move 1 from 2 to 1")
//        }
//        it("blah") {
//          val indicesLine = " 1   2   23"
//          //          indicesLine.trim.toCharArray.reverse.takeWhile(_ != ' ').reverse
//          assert(problem.maxIndex(indicesLine) == 23)
//        }
//        it("makes stacks") {
//          val stacks = problem.makeStack(9)
//          assert(stacks.length == 9)
//        }
//        it("properly separates dock lines from instructions") {
//          val separatorIndex = trimmed.indexWhere(_.trim().isEmpty)
//          val stack          = trimmed.take(separatorIndex)
//          assert(stack.reverse.head.trim() == "1   2   3")
//        }
//      }
//      describe("sample") {
//        val sample = problem.sample.split("\n").toList
//        it("parses initial dock from sample") {
//          val expectedDock = Dock(
//            Array(
//              Stack(List(Box('N'), Box('Z'))),
//              Stack(List(Box('D'), Box('C'), Box('M'))),
//              Stack(List(Box('P')))
//            )
//          )
//          assert(problem.parseDock(sample).stacks.sameElements(expectedDock.stacks))
//        }
//      }
//    }
//    describe("movements") {
//      val initialDock =
//        Dock(Array(Stack(List(Box('B'), Box('X'))), Stack(List(Box('A'), Box('C'), Box('Y'))), Stack(List(Box('Z')))))
//
//      it("parses a new command") {
//        val command                 = "move 1 from 2 to 1"
//        val maybeMove: Option[Move] = problem.parseMove(command)
//        assert(maybeMove.contains(Move(1, 2, 1)))
//      }
//
//      it("updates dock") {
//        val command = "move 1 from 2 to 1"
//        val expectedDock =
//          Dock(Array(Stack(List(Box('A'), Box('B'), Box('X'))), Stack(List(Box('C'), Box('Y'))), Stack(List(Box('Z')))))
//
//        assert(problem.update(initialDock, Move(1, 2, 1)).stacks.toList == expectedDock.stacks.toList)
//      }
//
//      it("updates based on a new command") {
//        val command = "move 1 from 2 to 1"
//
//        val expectedDock =
//          Dock(Array(Stack(List(Box('A'), Box('B'), Box('X'))), Stack(List(Box('C'), Box('Y'))), Stack(List(Box('Z')))))
//        val actualDock = problem
//          .parseMove(command)
//          .map(problem.update(initialDock, _))
//        assert(actualDock.map(_.stacks).exists(_.sameElements(expectedDock.stacks)))
//      }
//      it("updates based on multiple commands") {
//        val commands = List("move 1 from 2 to 1", "move 3 from 1 to 3")
//        val initialDock = Dock(
//          Array(Stack(List(Box('N'), Box('Z'))), Stack(List(Box('D'), Box('C'), Box('M'))), Stack(List(Box('P'))))
//        )
//        val expectedDock = Dock(
//          Array(Stack(List()), Stack(List(Box('C'), Box('M'))), Stack(List(Box('Z'), Box('N'), Box('D'), Box('P'))))
//        )
//        val maybeMoves: List[Option[Move]] = commands.map(problem.parseMove)
//        val moves                          = maybeMoves.filterNot(_.isEmpty).map(_.get)
//        val finalMoves: Dock = moves
//          .foldLeft(initialDock)((d, m) => problem.update(d, m))
//        assert(finalMoves.stacks.toList == expectedDock.stacks.toList)
//      }
//    }
//  }
//
}
