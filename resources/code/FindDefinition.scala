object FindDefinition {

  class Result[T](a: T)

  class CachedResult(random: Boolean) {
    def initial =
      if (random) scala.util.Random.nextLong()
      else 0

    var x = new Result(initial)

    def update(a: Long, b: Long) {
      // do some more computation
      // and finally assign new value
      x = calcSth(a, b)
    }

    def reset {
      var i = initial
      // some interesting calculations
      // happen here on i
      x = new Result(i)
    }

    private def calcSth(x: Long, y: Long): Result[Int] = ???
  }

}