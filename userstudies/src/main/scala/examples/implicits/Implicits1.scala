package implicits

trait Implicts02 {
  trait Value[-T] {
    def apply(x: T): String
  }

  trait PimpedValue[-T] extends Value[T] {
    implicit val thirdImplicit: Value[Int] = ???
  }

  object UnderstandingScopes {
    implicit val firstImplicit: Value[AnyVal] = ???

    def foo[A](v: A)(implicit tc: Value[A]) = tc(v)

    def test01() {
      implicit val secondImplicit: Value[Int] = ???

      foo(1)
    }
  }
}


trait Implicts04 {

  trait Matrix
  trait Vector

  trait MultiplicationComp[-A, -B, +R] {
    def compute(a: A, b: B): R
  }

  object MultiplicationComp {
    implicit val mByM: MultiplicationComp[Matrix, Matrix, Matrix] = ???

    implicit val mByV: MultiplicationComp[Matrix, Vector, Vector] = ???

    implicit val mByInt:  MultiplicationComp[Matrix, Int, Matrix] = ???

    implicit val genericMult: MultiplicationComp[AnyRef, AnyRef, AnyRef] = ???

    implicit val generic2Mult: MultiplicationComp[Matrix, AnyRef, AnyRef] = ???

    implicit val generic3Mult: MultiplicationComp[AnyRef, Matrix, AnyRef] = ???
  }

  def multiplyMe[A, B, C](x: A, b: B)(implicit c: MultiplicationComp[A, B, C]): C =
    c.compute(x, b)

  def test {

    val m = new Matrix {}
    val v = new Vector {}
    val i = 1
    val j = 1L

    multiplyMe(m, m): Matrix // 1
    multiplyMe(m, m)         // 2
    multiplyMe(m, v): Vector // 3
    multiplyMe(m, v): Matrix // 4
    multiplyMe(m, i)         // 5
    multiplyMe(m, j)         // 6
  }

}

trait Implicits05 {
  class A { def f: Any }
  class B extends A { def f: Int = 5 }
  class C extends A { def f: Long = 5L }

  def universalComp[T](t1: T, t2: T)(implicit evidence: Ordering[T]) = 1

  implicit val aOrdering: Ordering[A] = ???
  
  def test01 {
    universalComp(new B, new C)
  }

  def test02 {
    universalComp(1, 2)
  }
}

trait Implicits10 {

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
}

trait Implicits13 {

  def test01 {

    implicit def transitive[A, B, C](implicit f: A => B, g: B => C): A => C = f andThen g

    implicit def pre2Main(f: PreProcess): Main = ???
    implicit def main2Final(f: Main): Final    = ???

    abstract class Action

    case class PreProcess(x: Int) extends Action
    case class Main(y: Int, factor: Int) extends Action
    case class Final(res: Int) extends Action {
      def product(): Int = ???
    }

    def doWork(v: PreProcess)(implicit process: PreProcess => Final): Int = 
      process(v).product()

    doWork(PreProcess(21))

  }


  def test02 {
    class Foo(val x: Int) extends Ordered[Foo] {
      def compare(that: Foo): Int = ???
    }

    class Bar(val id: String, x: Int) extends Foo(x) 


    val listOfFoo: List[Foo] = ???
    val listOfBar: List[Bar] = ???
    
    listOfFoo.sorted

    listOfBar.sorted
  }


  def test03 {

    import scala.collection.immutable.SortedSet

    val xs = List(5,2,6)

    val set1 = SortedSet.empty[Int] ++ xs
 
    val set2 = SortedSet.empty ++ xs

  }

}