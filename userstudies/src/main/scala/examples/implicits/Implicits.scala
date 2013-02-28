package userstudies
package implicits

import scala.collection.mutable
import scala.collection.immutable

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

trait Implicits07 {
  sealed abstract class Result
  object Valid extends Result
  object Invalid extends Result

  abstract class Scale
  class Horizontal(factor: Int) extends Scale
  class Vertical(factor: Int) extends Scale

  class Size(x: Int, y: Int) {
    def calculate(): Int = x * y
  }

  object SizeScale1 {
    implicit def sizeScale1(v: Size) = new {
      def scale(x: Horizontal): Valid.type = Valid
    }

    implicit def sizeScale2(v: Size) = new {
      def scale(x: Scale): Invalid.type = Invalid
    }
  }

  def test01 {

    import SizeScale1._

    val h = new Horizontal(10)
    val v = new Vertical(10)
    val s = new Size(2, 4)

    def get1(x: Result) = ()
    def get2(x: Valid.type) = ()
    def get3(x: Invalid.type) = ()
    def get4(x: Any) = ()

    get1(s.scale(h)) // Q: Does the arg
    get1(s.scale(v)) // Q: Which implicit is picked
                     // Q: Why is there no ambiguity    
    get2(s.scale(h))
    get2(s.scale(v)) // Q: Why has implicit been 
    get3(s.scale(h))
    get3(s.scale(v))
    get4(s.scale(h)) // Q: Why is sizeScale1 not more specific than sizeScale2?
  }

  object SizeScale2 {
    implicit def sizeScale1(v: Size) = new {
      def scale(x: Horizontal): Valid.type = Valid
    }

    implicit def square(v: Int): Size = new Size(v, v)

    implicit def result2Invalid(v: Result): Invalid.type = ???

    // let's say we predefine it for fun
    implicit def vertical2Horizontal(v: Vertical): Horizontal = ???
  }

  def test02 {
    import SizeScale2._

    val h = new Horizontal(10)
    val v = new Vertical(10)
    val s = new Size(2, 4)

    def onInvalid(x: Invalid.type) = ()

    //Q: It looks like we are chaining implicits here
    //Q: How many implicits were applied
    //Q: Is the argument 's.scale(*)' first applied to result2Invalid?
    onInvalid(s.scale(h)) 
    //Q: How many implicits were applied
    //Q: Is the argument 'v' first applied to vertical2Horizontal
    onInvalid(s.scale(v))

    //Q: Did we really allow chaining of implicits here?
    //Q: Did you expect the following expression to typecheck?
    //Q: How would you apply implicits for the expression to succeed?
    //Q: Did implicit search fail to find the correct implicit?
    //Q: Which implicit is the culprit? 
    onInvalid(1.scale(h))
  }
}

trait Implicits08 {
  class A { def f: Any }
  class B extends A { def f: Int = 5 }
  class C extends A { def f: Long = 5L }

  def universalComp[T](t1: T, t2: T)(implicit evidence: Ordering[T]) = 1

  def test01 {
    implicit val aOrdering: Ordering[A] = ???

    // Q: Is the compiler capable of infering type argument for T (different from Nothing and Any)?
    // Q: If yes, what is it's type?
    // Q: Is aOrdering in scope and is it checked during implicit search?
    // Q: How many implicits are eligible for this application
    // Q: Why does it not pick aOrdering implicit for the evidence
    // Q: Suggest a simple modification to fix the type error
    universalComp(new B, new C)

    // Q: How many implicits are eligible for this application?
    // Q: If more than one, why is there no ambiguity?
    // Q: Is aOrdering implicit a selected implicit as an argument?
    // Q: Where is the successful implicit argument defined?
    universalComp(1, 2)
  }

}
