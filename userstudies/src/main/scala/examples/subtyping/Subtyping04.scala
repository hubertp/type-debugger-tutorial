package subtyping

trait Subtyping04 {

  def test1 {
    def foo[T, B >: T](x: Map[T, B]): List[T] = ???
    val x: Map[Number, Integer] = ???
    val y: List[Integer] = foo(x)
    ()
  }

  def test2 {
    def foo[T](x: Map[T, _ >: T]): List[T] = ???
    val x: Map[Number, Integer] = ???
    val y: List[Integer] = foo(x)
    ()
  }

  def test3 {
    def foo[T, B <: T](x: Map[T, B]): List[B] = ???
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

  def test6 {

    def foo[T <: Number](a: Map[_ >: T, _ >: T]) {}
    def bar[T <: Number, S >: T](a: Map[S, S]) {}

    val m: Map[String, Number] = ???

    foo(m)

    bar(m)
  }

  def test7 {
    class CovMap[+K, +V]
    def foo[T <: Number](a: CovMap[_ >: T, _ >: T]) {}
    def bar[T <: Number, S >: T](a: CovMap[S, S]) {}
    
    val m: CovMap[String, Number] = ???

    foo(m)

    bar(m)
  }

}