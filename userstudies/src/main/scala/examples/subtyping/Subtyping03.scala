package subtyping

trait Subtyping03 {

  def test1 {
    def foo[T, B >: T](x: Map[T, B]): List[T] = ???
    val x: Map[Number, Integer] = ???
    val y: List[Integer] = foo(x)
    ()
  }

  def test3 {
    def foo[T, B <: T](x: Map[T, B]): List[T] = ???
    val x: Map[Number, Integer] = ???
    val y: List[Integer] = foo(x)
    ()
  }

  def test4 {
    def foo[T, S](x: Map[T, T], y: Map[S, S]) {}
    val x: Map[Number, String] = ???
    val y: Map[Integer, String] = ???
    foo(x, y)
  }

  def test5 {
    def foo[T, S](x: Map[_ <: T, S], y: Map[_ <: T, S]) {}
    val x: Map[Number, String] = ???
    val y: Map[Integer, String] = ???
    foo(x, y)
  }

  def test7 {
    class CovMap[+K, +V]
    def foo[T <: Number](a: Map[_ >: T, _ >: T]) {}
    def bar[S <: Number](a: CovMap[_ >: S, _ >: S]) {}

    val m1: Map[String, Number]     = ???
    val m2: CovMap[String, Number]  = ???

    val m3: Map[Integer, Number]    = ???
    val m4: CovMap[Integer, Number] = ???

    foo(m1)
    bar(m2)

    foo(m3)
    bar(m4)
  }

}