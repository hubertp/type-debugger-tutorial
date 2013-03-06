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
  class Op1 extends Base
  val a = new Op1

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