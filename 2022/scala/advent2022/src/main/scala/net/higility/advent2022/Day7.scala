package net.higility.advent2022

import net.higility.advent.common.Day.Day7
import net.higility.advent.common.Part.*
import net.higility.advent.common.{AdventProblem, Day}
import net.higility.advent2022.Command.*
import net.higility.advent2022.TerminalOutput.*

import java.lang.System
import scala.annotation.tailrec
import scala.collection.mutable

enum Command:
  case ChangeDirectory(directory: String)
  case ListFiles

enum TerminalOutput:
  case Cmd(cmd: Command)
  case Directory(name: String)
  case File(size: Int, name: String)

class DirectoryStructure(
    val name: String,
    val subDirectories: mutable.Map[String, DirectoryStructure],
    val files: mutable.Map[String, Int],
    val parent: DirectoryStructure | Null
)

trait Day7Problem:
  def process(input: Seq[String]): Unit

class Day7Part1Problem extends AdventProblem, Day7Problem:
  override val day  = Day7
  override val part = Part1

  def input(str: Seq[String]) = str.map { s =>
    println(s"s=$s")
    s match {
      case s"$$ cd $directory" => Cmd(ChangeDirectory(directory))
      case s"$$ ls"            => Cmd(ListFiles)
      case s"dir $directory"   => Directory(directory)
      case s"$size $file"      => File(size.toInt, file)
    }
  }.toList

  def buildState(
      input: List[TerminalOutput],
      currentDir: DirectoryStructure | Null,
      rootDir: DirectoryStructure
  ): Unit =
    input match
      case Cmd(ChangeDirectory("/")) :: t  => buildState(t, rootDir, rootDir)
      case Cmd(ChangeDirectory("..")) :: t => buildState(t, currentDir.parent, rootDir)
      case Cmd(ChangeDirectory(name)) :: t => buildState(t, currentDir.subDirectories(name), rootDir)
      case Cmd(ListFiles) :: t             => buildState(t, currentDir, rootDir)
      case File(size, name) :: t =>
        currentDir.files.put(name, size)
        buildState(t, currentDir, rootDir)
      case Directory(name) :: t =>
        currentDir.subDirectories.put(name, DirectoryStructure(name, mutable.Map.empty, mutable.Map.empty, currentDir))
        buildState(t, currentDir, rootDir)
      case Nil => ()

  def directorySize(dir: DirectoryStructure): Int =
    dir.files.values.sum + dir.subDirectories.values.map(directorySize).sum

  def collectSizes(dir: DirectoryStructure, criterion: Int => Boolean): Iterable[Int] =
    val mySize   = directorySize(dir)
    val children = dir.subDirectories.values.flatMap(collectSizes(_, criterion))
    if criterion(mySize) then mySize :: children.toList else children

  def buildData(lines: Seq[String]) =
    val rootDir = new DirectoryStructure("/", mutable.Map.empty, mutable.Map.empty, null)
    buildState(input(lines), null, rootDir)
    rootDir

  def part1(lines: Seq[String]): Int =
    val rootDir = buildData(lines)
    collectSizes(rootDir, _ < 100000).sum

  override def process(input: Seq[String]): Unit = {
    val result = part1(input)
    println(s"Part1 result: $result")
  }

end Day7Part1Problem

class Day7Part2Problem extends Day7Part1Problem, Day7Problem:
  override val part = Part2

  override def process(input: Seq[String]): Unit = {
    val result = part2(input)
    println(s"Part2 result: $result")
  }

  def part2(output: Seq[String]): Int =
    val rootDir     = buildData(output)
    val totalUsed   = directorySize(rootDir)
    val totalUnused = 70_000_000 - totalUsed
    val required    = 30_000_000 - totalUnused
    collectSizes(rootDir, _ >= required).min

end Day7Part2Problem

object Day7Solution extends App:
  val sample: String =
    """$ cd /
      |$ ls
      |dir a
      |14848514 b.txt
      |8504156 c.dat
      |dir d
      |$ cd a
      |$ ls
      |dir e
      |29116 f
      |2557 g
      |62596 h.lst
      |$ cd e
      |$ ls
      |584 i
      |$ cd ..
      |$ cd ..
      |$ cd d
      |$ ls
      |4060174 j
      |8033020 d.log
      |5626152 d.ext
      |7214296 k""".stripMargin

  val sampleInput = sample
    .split("\n")
    .map(_.trim)

  val part1              = Day7Part1Problem()
  val fileInput          = part1.fileInput
  val part2: Day7Problem = Day7Part2Problem()
  println("sample")
  part1.process(sampleInput)
  println("Part1")
  part1.process(fileInput)
  println("Part2")
  part2.process(fileInput)
