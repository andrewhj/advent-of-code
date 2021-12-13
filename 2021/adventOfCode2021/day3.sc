enum Binary:
  case One, Zero

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
    |""".stripMargin.trim.split('\n').toList

val sampleList: List[String] = sampleInput
val firstRow = sampleList.map(_.charAt(0))
val secondRow = sampleList.map(_.charAt(1))
firstRow.groupBy(identity).map((c, l) => (c, l.size))
sampleList.transpose.head == firstRow
sampleList.transpose.tail.head == secondRow

val transposedList = sampleList.transpose

for {
  row <- transposedList
  grouped = row.groupBy(identity)
  freq = grouped.map((a, b) => (a, b.size))
} yield freq

transposedList.head.groupBy(identity).map((a, b) => (b.size, a))

def something(row: List[Char]) =
  row.groupBy(identity).map((a, b) => (b.size, a))

something(transposedList.head)

def mostFreq(m: Map[Int, Char]) = m(m.keys.max)
def leastFreq(m: Map[Int, Char]) = m(m.keys.min)
mostFreq(something(transposedList.head))
leastFreq(something(transposedList.head))

for {
  row <- transposedList
  s = something(row)
  gamma = mostFreq(s)
  eps = leastFreq(s)
} yield eps

val gammaStr: String = transposedList.map(something).map(mostFreq).mkString
val epsilonStr = transposedList.map(something).map(leastFreq).mkString

val gamma = Integer.parseInt(gammaStr, 2)
val epsilon = Integer.parseInt(epsilonStr, 2)

gamma * epsilon

something(sampleList.transpose.head)
def mostFrequent(lst: List[String])= {
  val value: Map[Char, List[Char]] =
    lst.transpose
      .map(_.head)
      .groupBy(identity)
  //          .map((a, b) => (b.size, a))
  value
}
val row =mostFrequent( sampleList)
  .map((a,b) => (b.size, a))
row(row.keys.max)


"abc".indexOf("ab")