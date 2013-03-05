package subtyping

trait Subtyping01 {
  class S
  class T extends S

  class U
  class V extends U

  // no permutation subtyping
  class Record1[+A](x: A)
  class Record2[+A, +B](x: A, y: B) extends Record1(x)

  class MyFunction[+From, -To]

  abstract class Base {
    class Inner
  }
  class Op1 extends Base
  val a = new Op1

  def test1(f: Any)                                   = ???
  def test2(f: Record2[U, T] => T)                    = ???
  def test3(f: V => Any)                              = ???
  def test4(f: (V => S) => V)                         = ???
  def test5(f: (Record2[T, V] => S) => Record2[T, V]) = ???
  def test6(f: ((S => Record1[T]) => S) => Any)       = ???
  def test7(f: MyFunction[Record1[S], Record1[U]])    = ???
  def test8(f: Set[a.Inner] => Int)                   = ???

  def test {
    val arg1: Any => Any         = ???
    val arg2: Record2[S, U] => S = ???
    val arg3: Any => S           = ??? 
    val arg4: (S => T) => V      = ???
    val arg5: (Record1[S] => T) => Record1[U]        = ??? 
    val arg6: (T => Record2[S, S]) => T              = ???
    val arg7: MyFunction[Record1[T], Record2[V, U]]  = ???
    val arg8: Set[Base#Inner] => Int                 = ???

    test1 (arg1)
    test2 (arg2)
    test3 (arg3)
    test4 (arg4)
    test5 (arg5)
    test6 (arg6)
    test7 (arg7)
    test8 (arg8)
  }

}