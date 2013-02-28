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

trait Implicts03 {
  trait Store[+T] {
    def get(): T
  }

  object Helpers {
    implicit val firstImplicit = new Store[AnyVal] {
      def get(): AnyVal = ???
    }
  }

  object UnderstandingScopes {

    def test01() {
      import Helpers._

      implicit val secondImplicit = new Store[Int] {
        def get(): Int = ???
      }

      foo(1)
    }

    def foo[A](v: A)(implicit tc: Store[A]) = tc.get()

  }
}

trait Implicts04 {

  trait Matrix
  trait Vector

  trait MultiplicationComp[-A, -B, +R] {
    def compute(a: A, b: B): R
  }

  object ImplicitsForComp {
    implicit val mByM = new MultiplicationComp[Matrix, Matrix, Matrix] {
      def compute(a: Matrix, b: Matrix): Matrix = ???
    }

    implicit val mByV = new MultiplicationComp[Matrix, Vector, Vector] {
      def compute(a: Matrix, b: Vector): Vector = ???
    }

    implicit val mByInt = new MultiplicationComp[Matrix, Int, Matrix] {
      def compute(a: Matrix, b: Int): Matrix = ???
    }

    implicit val genericMult = new MultiplicationComp[AnyRef, AnyRef, AnyRef] {
      // default case, invalid
      def compute(a: AnyRef, b: AnyRef): AnyRef = ???

    }
  }

  object Specific {
    import ImplicitsForComp._

    val m = new Matrix {}
    val v = new Vector {}
    val i = 1
    val j = 1.0


    def test() {
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

    def multiplyMe[A, B, C](x: A, b: B)(implicit c: MultiplicationComp[A, B, C]): C =
      c.compute(x, b)
  }
}
