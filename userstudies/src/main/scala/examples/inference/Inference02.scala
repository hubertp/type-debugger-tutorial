package inference

trait Inference02 {

  def test01: Unit = {

    def foo[T <: X, X](a: X)(b: T): T = b

    val res1 = foo(1) _
    res1(2)

    def bar[T <: X, X](a: X)(b: T): Int = 0

    val res2 = bar(1) _
    res2(2)

  }


  def test02 {
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

  def test03 {
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

    val res2a = partition3(seq)(_ > 2)

  }

  def test02 {
    import scala.collection.BitSet

    val lList: LinkedList[Int] = ???
    val bSet: BitSet           = ???

    val a = lList map (_.toFloat)
    
    val b = bSet map (_.toFloat)
  }


}