package inference

import scala.collection.{ Iterable, IterableLike }
import scala.collection.generic.CanBuildFrom

trait Inference03 {
  abstract class A
  abstract class B[T <: A]
  class C extends A

  class Base extends B[C]
  

  def test01 {

    def foo[U <: B[T], T <: A]( param: U ): Unit = ()
    
    foo(new Base)

    def bar[U[T0 <: A] <: B[T0], T <: A]( param: U[T] ): Unit = ()

    bar(new Base)
    
    ()
  }

  


  def test04 {
    val seq = Seq(1,2,3)

    def partition1[T, CC[X] <: Iterable[X]](xs: CC[T])
      (cond: T => Boolean): (CC[T], CC[T]) = {
        val p1 = xs.takeWhile(cond)
        val p2 = xs.takeWhile(!cond(_))
        (p1, p2)
    }

    def partition3[T, CC[X] <: Iterable[X], To](xs: CC[T])
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

    val res2a = partition3(seq)(_ > 2)

  }
}
