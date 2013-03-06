package implicits

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

    s.repeats('a')

    "abc".repeats('a')

    val x: Int = 'a'
    s.repeats(1)
  }
}
