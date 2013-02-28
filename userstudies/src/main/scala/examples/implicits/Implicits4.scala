package userstudies
package implicits


trait Implicits09 {
  trait Base {
    type Rep[T]
  }

  trait Show[T] {
    def show(a: T): String
  }

  trait Expressions {
    // constants/symbols
    abstract class Exp[T] 

    object Exp {
      implicit def toExp[T: Show](d: Def[T]): Exp[T] = ???
    }

    // operations (composite, defined in subtraits)
    abstract class Def[T]
     
    implicit def genericShow[S <: AnyRef]: Show[S] = new Show[S] {
      def show(a: S): String = a.toString
    }
    
  }

  trait BaseExpr extends Base with Expressions {
    type Rep[T] = Exp[T]
  }

  trait NumericOpsExp extends BaseExpr {
    case class Plus[T:Numeric](x: Rep[T], y: Rep[T])
      extends Def[T]

    def plus[T: Numeric](x: Rep[T], y: Rep[T]): Rep[T] =
      Plus[T](x,y)  
  }
}


trait Implicits10 {

  def test01 {

    class A
    class B
    class C

    def correct(c: C): Unit = ()

    implicit def a2B(a: A): B = ???
    implicit def b2C(b: B): C = ???

    val a = new A()
    val b = new B()

    correct(b)

    correct(a)

  }

  trait SumTuple3 extends Tuple3[Int, Int, Int] {
    def sum: Int
  }

  object ChainImplicits {
    implicit def intToT1(x: Int): Tuple1[Int] = ???
    implicit def t1ToT2[T](prev: T)(implicit toT1: T => Tuple1[Int]): Tuple2[Int, Int] = ???
    implicit def t2ToT3[T](prev: T)(implicit toT2: T => Tuple2[Int, Int]): SumTuple3 = ???
  }

  def test02 {
    import ChainImplicits._

    1.sum

    (2,5).sum
    
    (1,2,3).sum

  }

  def test03 {
    import ChainImplicits._
    implicit def intToSum(x: Int): { def sum: Int } = ???
    
    1.sum
    ()
  }
}

trait Implicits11 {

  // String is a Sequence
  val s: Seq[Char] = "abc"

  def test01 {
    class RichSeq[A](xs: Seq[A]) {
      def repeats(x: A) = false
    }
    implicit def seq2RichSeq[A, S](xs: S)(implicit isSeq: S <:< Seq[A]): RichSeq[A] = new RichSeq(xs)  

    s.repeats('a') // pimp the sequence

    "abc".repeats('a') // but that's the same as above, right?

    val x: Int = 'a'
    s.repeats(1)
  }
}

trait Implicits12 {
  
  class Pair[+T](val x: T, val y: T) {
    def lessThan(second: Pair[T])(implicit evidence: T <:< Ordered[T]) = 
      (x < second.x) && (y < second.y)

    def lessThan2(second: Pair[T])(implicit evidence: T => Ordered[T]) =
      (x < second.x) && (y < second.y)
  }
  
  def test01 {
    val a = new Pair(1, 2)
    val b = new Pair(2, 1)

    // Q: Why does this compile? 
    val someInt: Ordered[Int] = 0

    // Q: Why cannot it prove that Int <: Ordered[Int]


    // Q: Why cannot it prove that Int <: Ordered[Int]
    a lessThan b


    // Q: What implicits did it infer and why?


    // Q: What implicits did it infer and why?
    a lessThan2 b

  }

}
