package subtyping

trait Subtyping01 {

  class S
  class T extends S

  class U
  class V extends U

  // no permutation subtyping
  class Record1[+A](x: A)
  class Record2[+A, +B](x: A, y: B) extends Record1(x)

  class MyFun[+From, -To]

  abstract class Base {
    class Inner
  }
  class Ext extends Base
  val a = new Ext

  def test2(f: Record2[U, T] => T)                    = ???
  def test6(f: ((S => Record1[T]) => S) => Any)       = ???
  def test7(f: Record1[S] MyFun Record1[U])    = ???
  def test8(f: Set[Base#Inner] => (Any => Int)) = ???

  def test {
    val arg2: Record2[S, U] => S = ???
    val arg6: (T => Record2[S, S]) => T              = ???
    val arg7: Record1[T] MyFun Record2[V, U]  = ???
    val arg8: Set[a.Inner] => (Int => Nothing)  = ???

    test2 (arg2)
    test6 (arg6)
    test7 (arg7)
    test8 (arg8)
  }

}

trait Subtyping02 {

  def test02 {
    def foo[T, B >: T](x: Map[T, B]): List[T] = ???
    val x: Map[Number, Integer] = ???
    val y: List[Integer] = foo(x)
    ()
  }

  def test03 {
    def foo[T, S](x: Map[T, T], y: Map[S, S]) {}
    val x: Map[Number, String] = ???
    val y: Map[Integer, String] = ???
    foo(x, y)
  }

  def test04 {
    class CovMap[+K, +V] // dummy covariant map
    
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