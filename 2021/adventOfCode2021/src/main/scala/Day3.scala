package net.higility

object Day3:
  val sampleInput: List[String] =
    """
      |00100
      |11110
      |10110
      |10111
      |10101
      |01111
      |00111
      |11100
      |10000
      |11001
      |00010
      |01010
      |""".stripMargin.trim.split("\r\n").toList

  val sampleList = sampleInput

  def mapSizes(row: List[Char]): Map[Int, Char] =
    row.groupBy(identity).map((a, b) => (b.size, a))
  def mostFreq(row: Map[Int, Char]) = {
    if (row.keys.max == row.keys.min) '1' else row(row.keys.max)
  }
  def leastFreq(row: Map[Int, Char]) = {
    if (row.keys.max == row.keys.min) '0' else row(row.keys.min)
  }

  def process(inputList: List[String]) =
    lazy val transposedList = inputList.transpose
    lazy val sizedList = transposedList.map(mapSizes)
    val gamma = sizedList.map(mostFreq).mkString
    val epsilon = sizedList.map(leastFreq).mkString
    val gammaNumber = Integer.parseInt(gamma, 2)
    val epsilonNumber = Integer.parseInt(epsilon, 2)
    gammaNumber * epsilonNumber

  object Part2:
    val oxygenMask = 0
    val co2Mask = 0

    def oxygenReading(lst: List[String]) = {
      val resultMask = freqProcessor(lst, mostFreq)
      Integer.parseInt(resultMask, 2)
    }

    def co2Reading(lst: List[String]) = {
      val resultMask = freqProcessor(lst, leastFreq)
      Integer.parseInt(resultMask, 2)
    }

    def process(lst: List[String]) = {
      val oxygen = oxygenReading(lst)
      val co2 = co2Reading(lst)
      oxygen * co2
    }

    def freqProcessor(
        lst: List[String],
        comparisonFunc: Map[Int, Char] => Char
    ): String = {
      def freqInner(
          lst: List[String],
          freqMap: String,
          cmp: Map[Int, Char] => Char
      ): List[String] = lst match {
        case x :: Nil => lst
        case x :: xs =>
          val frequencyMap: Map[Int, Char] = lst
            .map(_(freqMap.length))
            .groupBy(identity)
            .map((a, b) => (b.size, a))
          val newMask = freqMap + cmp(frequencyMap)
          freqInner(lst.filter(_.indexOf(newMask) == 0), newMask, cmp)
        case _ => Nil
      }
      freqInner(lst, "", comparisonFunc).head
    }

    def mostFrequent(lst: List[String]): String = {
      freqProcessor(lst, mostFreq)
    }

    def leastFrequent(lst: List[String]): String = {
      freqProcessor(lst, leastFreq)
    }

end Day3

object Day3App extends App:
  println("*** starting ***")
  val sampleList = Day3.sampleList
  println("sample")
  println("Part 1")
  println(s"Result: ${Day3.process(sampleList)}")
  println("Part 2")
  println(s"Result: ${Day3.Part2.process(sampleList)}")
  println("live")
  val rawLines = Common.getFileLines(Day.Day3, Part.Part1)
  println("*** Part 1 ***")
  val result = Day3.process(rawLines)
  println(s"Result: $result")
  println("*** Part 2 ***")
  println(s"Result: ${Day3.Part2.process(rawLines)}")
