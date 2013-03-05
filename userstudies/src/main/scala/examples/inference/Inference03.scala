package inference

import scala.collection.{ Iterable, IterableLike }
import scala.collection.generic.CanBuildFrom

trait Inference03 {
  abstract class A
  abstract class B[T <: A]
  class C extends A

  class Base1 extends B[C]
  

  def test01 {

    def foo[U <: B[T], T <: A]( param: U ): Unit = ()
    
    foo(new Base1)
    
    ()
  }

  

  def test02 {

    def bar[U[T0 <: A] <: B[T0], T <: A]( param: U[T] ): Unit = ()

    bar(new Base1)
    
    ()
  }


  def test03 {

    trait With[T] {
      def elem: T
    }

    class Basic[T](x : T) extends With[T] {
      def elem = x
    }

    object Sugar {
      def create[Z, X <: With[Z]](x: X) = x.elem
    }

    val value = new Basic(1)
    Sugar.create(value)
  }

  def test04 {
    val seq = Seq(1,2,3)

    def partition1[T, CC[X] <: Iterable[X]](xs: CC[T])
      (cond: T => Boolean): (CC[T], CC[T]) = {
        val p1 = xs.takeWhile(cond)
        val p2 = xs.takeWhile(!cond(_))
        (p1, p2)
    }

    def partition2[T, CC <: Iterable[T] with IterableLike[T, CC]](xs: CC)
      (cond: T => Boolean): (CC, CC) = {
        val p1 = xs.takeWhile(cond)
        val p2 = xs.dropWhile(!cond(_))
        (p1, p2)
    }

    val res1a = partition2(seq)(_ > 2)
    val res1b: (Seq[Int], Seq[Int]) = partition2(seq)(_ > 2)

    def partition3[T, CC[X] <: Iterable[X], To](xs: CC[T])
      (cond: T => Boolean)(implicit cbf: CanBuildFrom[CC[_], T, To]): (To, To) = {
      
      val bd1 = cbf()
      val bd2 = cbf()
      var first = true
      for (elem <- xs)
        if (first && cond(elem)) bd1 += elem
        else {
          bd2 += elem
          first = false
        }

      // build the collection...
      (bd1.result, bd2.result)
    }

    val res2a = partition3(seq)(_ > 2)
    val res2b: (Seq[Int], Seq[Int]) = partition3(seq)(_ > 2)

  }
}
