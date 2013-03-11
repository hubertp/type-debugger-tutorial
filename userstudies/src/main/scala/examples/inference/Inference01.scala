package inference

import scala.collection.generic.CanBuildFrom


trait Inference01 {
  abstract class Base

  class A extends Base
  class B extends Base

  class C extends A


  case class Foo(x: Int) extends Base
  case class Bar(y: Int) extends Base

  def test01: Unit = {
    def common(cond: Boolean) = if (cond) Foo(0) else Bar(0)
    var x = common(false)
    val sth: Base = Foo(1)

    x = sth               // error
    ()
  }

  // --------------------

  def test02(cond: Boolean): Unit = {
    class C
    class D
    class E
    class F
    
    val a = if (cond) ((x: C) => new D) else ((x: E) => new F)
  }

  // --------------------
  
  def test03(cond: Boolean): Unit = {
    abstract class A { type T }
    class C extends A { type T = C }
    class D extends A { type T = D }
    
    val b = if (cond) new C else new D
  }

  // --------------------

  def test04 {

    def foo[T <: X, X](a: X)(b: T): T = b

    val res1 = foo(1) _
    res1(2)

    def bar[T <: X, X](a: X)(b: T): Int = 0

    val res2 = bar(1) _
    res2(2)

  }

  // --------------------

  def test05 {
    abstract class A
    abstract class B[T <: A]
    class C extends A

    class Base extends B[C]

    
  
    def foo[U <: B[T], T <: A]( param: U ): Unit = ()
    
    foo(new Base)

    def bar[U[T0 <: A] <: B[T0], T <: A]( param: U[T] ): Unit = ()

    bar(new Base)
    
    ()
  }

  // --------------------

  def test06 {
    val seq = Seq(1,2,3)

    def partition1[T, CC[X] <: Iterable[X]](xs: CC[T])
      (cond: T => Boolean): (CC[T], CC[T]) = {
        val p1 = xs.takeWhile(cond)
        val p2 = xs.takeWhile(!cond(_))
        (p1, p2)
    }

    def partition2[T, CC[X] <: Iterable[X], To](xs: CC[T])
      (cond: T => Boolean)(implicit cbf: CanBuildFrom[CC[_], T, To]): (To, To) = {
      
      val bd1 = cbf()
      val bd2 = cbf()
      var first = true
      // a primitive way of splitting the collection
      for (elem <- xs)
        if (first && cond(elem)) bd1 += elem
        else {
          bd2 += elem
          first = false
        }

      // build the collection...
      (bd1.result, bd2.result)
    }

    val res2a = partition2(seq)(_ > 2)

  }

  // --------------------

  def test07 {
    import scala.collection.BitSet
    import scala.collection.mutable.LinkedList

    val lList: LinkedList[Int] = ???
    val bSet: BitSet           = ???

    val a = lList map (_.toFloat)
    
    val b = bSet map (_.toFloat)
  }


}