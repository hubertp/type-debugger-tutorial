package implicits

trait Implicits01 {
  class A
  class B

  def test01 {
    implicit def int2B(x: Int): B = new B

    val a: A = 0
    val b: B = 1
    ()
  }

}

trait Implicts02 {
  trait Value[-T] {
    def apply(x: T): String
  }

  trait PimpedValue[-T] extends Value[T] {
    implicit val thirdImplicit: Value[Int] = ???
  }


  object UnderstandingScopes {
    implicit val firstImplicit: Value[AnyVal] = ???

    def test01() {
      implicit val secondImplicit: Value[Int] = ???

      foo(1)
    }

    def foo[A](v: A)(implicit tc: Value[A]) = tc(v)

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

  def test() {

    val m = new Matrix {}
    val v = new Vector {}
    val i = 1
    val j = 1.0

    multiplyMe(m, m): Matrix // 1
    multiplyMe(v, m): Matrix // 2
    multiplyMe(m, v): Vector // 3
    multiplyMe(m, v): Matrix // 4
    multiplyMe(m, i): Matrix // 5
    multiplyMe(m, i): Vector // 6
    multiplyMe(m, j): Matrix // 7

    multiplyMe(m, m)         // 8
    multiplyMe(m, v)         // 9
    multiplyMe(v, m)         // 10
    multiplyMe(m, i)         // 11
    multiplyMe(m, j)         // 12
  }

}
