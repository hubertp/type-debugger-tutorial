package subtyping

trait Subtyping05 {

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

  // Part 2

  def test4 {
    def foo[T, S](x: Map[T, T], y: Map[S, S]) {}
    val x: Map[Number, String] = ???
    val y: Map[Integer, String] = ???
    foo(x, y)
  }

  def test6 {
    def foo[T, S](x: Map[_ <: T, S], y: Map[_ <: T, S]) {}
    val x: Map[Number, String] = ???
    val y: Map[Integer, String] = ???
    foo(x, y)
  }

  // Part 3
  // omit?
  def test8 {
    class A
    class B extends A
    class C extends A

    trait Map2[-X, +Y]
    trait Map3[+X, +Y]

    def test1 {
      def foo[T](a: Map2[T, T], b: Map2[T, T]): Map2[T, T] = ???
      def x(): Map2[B, B] = ???
      def y(): Map2[C, C] = ???

      foo(x(), y())
    }

    def test2 {
      def bar[T](a: Map3[T, T], b: Map3[T, T]): Map3[T, T] = ????
      def x(): Map3[B, B] = ???
      def y(): Map3[C, C] = ???

      bar(x(), y())
    }
  }

  def test9 {

    def foo[T <: Number](a: Map[_ >: T, _ >: T]) {}
    def bar[T <: Number, S >: T](a: Map[S, S]) {}

    val m: Map[String, Number] = ???

    foo(m)

    bar(m)
  }


  def test10 {
    class CovMap[+K, +V]
    def foo[T <: Number](a: CovMap[_ >: T, _ >: T]) {}
    def bar[T <: Number, S >: T](a: CovMap[S, S]) {}
    
    val m: CovMap[String, Number] = ???

    foo(m)

    bar(m)
  }


}