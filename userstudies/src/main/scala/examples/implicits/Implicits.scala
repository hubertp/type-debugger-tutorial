package ch.epfl.lamp
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
  trait Value[T] {
    def apply(x: T): String
  }


  object UnderstandingScopes {
    implicit val firstImplicit = new Value[Int] {
      def apply(x: Int): String = x.toString
    }

    def test01() {
      implicit val secondImplicit = new Value[Int] {
        def apply(x: Int): String = (x + 1).toString
      }

      // Q: Is firstImplicit visible 
      // Q: Does secondImplicit have higher priority
      foo(1)
    }

    def foo[A](v: A)(implicit tc: Value[A]) = tc(v)

  }
}

trait Implicts03 {
  trait Value[T] {
    def apply(x: T): String
  }

  object Helpers {
    implicit val firstImplicit = new Value[Int] {
      def apply(x: Int): String = x.toString
    }
  }

  object UnderstandingScopes {

    def test01() {
      import Helpers._

      implicit val secondImplicit = new Value[Int] {
        def apply(x: Int): String = (x + 1).toString
      }

      // Q: Do we have an ambiguity here?
      // Q: If no, which one will be selected
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

  object ImplicitsForComp {
    implicit val mByM = new MultiplicationComp[Matrix, Matrix, Matrix] {
      def compute(a: Matrix, b: Matrix): Matrix = ???
    }

    implicit val mByV = new MultiplicationComp[Matrix, Vector, Vector] {
      def compute(a: Matrix, b: Vector): Vector = ???
    }

    implicit val mByInt = new MultiplicationComp[Matrix, Long, Matrix] {
      def compute(a: Matrix, b: Long): Matrix = ???
    }

    implicit val genericMult = new MultiplicationComp[AnyRef, AnyRef, AnyRef] {
      // primitive, run by instance matching
      def compute(a: AnyRef, b: AnyRef): AnyRef = ???

    }
  }

  object Specific {
    import ImplicitsForComp._

    val m = new Matrix {}
    val v = new Vector {}
    val i = 1
    val j = 1.0


    def test01() {

      // todo: questions?
      multiplyMe(m, m): Matrix
      multiplyMe(v, m): Matrix
      multiplyMe(m, v): Vector
      multiplyMe(m, v): Matrix
      multiplyMe(m, i): Matrix
      multiplyMe(m, i): Vector
      multiplyMe(m, j): Matrix

      multiplyMe(m, m)
      multiplyMe(m, v)
      multiplyMe(v, m)
      multiplyMe(m, i)
      multiplyMe(m, j)
    }

    def multiplyMe[A, B, C](x: A, b: B)(implicit c: MultiplicationComp[A, B, C]): C =
      c.compute(x, b)
  }
}

trait Action[T] {
  def apply(x: T): String
}

object Action {
  implicit val generalImpl: Action[Any] = new Action[Any] {
    def apply(x: Any): String = ???
  }
  implicit val longImpl: Action[Double] = new Action[Double] {
    def apply(x: Double): String = ???
  }
}

trait Implicits05 {

  object PackageObjectScope {
    def test01 {
      implicit val localImpl: Action[Int] = new Action[Int] {
        def apply(x: Int): String = ???
      }

      // Q: Will we get an ambiguity here
      // Q: If no, which implicit will get picked
      // Q: Is generalImpl implicit applicable here
      foo(1)
      foo(1.0)
    }

    def test02 {
      // Q: Will we get an imbiguity here
      // Q: Which implicit will get picked here
      // Q: Is generalImpl implicit applicable here
      foo(1)
      foo(1.0)
    }

    def foo[A](v: A)(implicit tc: Action[A]) = tc(v)
  }
}

trait GAction[-T, +S] {
  def apply(x: T): S
}

object GAction {
  implicit val generalImpl: GAction[Any, Any] = new GAction[Any, Any] {
    def apply(x: Any): Any = ???
  }
}

trait Implicits06 {
  object PackageObjectScope {
    def test01 {
      implicit val localImpl: GAction[Int, Int] = new GAction[Int, Int] {
        def apply(x: Int): String = ???
      }

      // same question to both
      // Q: Will we get an ambiguity here
      // Q: If no, which implicit will get picked (out of three)
      // Q: Is generalImpl implicit applicable here
      foo(1)
      foo(1.0)
      identity(foo(1))
      identityByAny(foo(1))

      // Q: Will we get an ambiguity here
      // Q: If no, which implicit will get picked
    }

    def test02 {
      // Q: Will we get an imbiguity here
      // Q: Which implicit will get picked here
      // Q: Is generalImpl implicit applicable here
      foo(1)
      foo(1.0)
      identity(foo(1))
      identityByAny(foo(1))
    }

    def identity[T](x: T): T = x
    def identityByAny(x: Any): Any = x
    def foo[A, B](v: A)(implicit tc: GAction[A, B]) = tc(v)
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

    // Q: Why is there no mismatch between Rep[T] and Def[T] (or Plus[T])?
    // Q: How many implicit arguments do we search for in the body of `plus`?
    // Q: Why do we search for an implicit of type Show[T]
    // Q: Is genericShow in scope and if yes why is it not picked?
    def plus[T: Numeric](x: Rep[T], y: Rep[T]): Rep[T] =
      Plus[T](x,y)  
  }
}


trait Implicits10 {

  class A
  class B
  class C

  object Convert {
    implicit def a2B(a: A): B = ???
    implicit def b2C(b: B): C = ???
  }

  def correct(c: C): Unit

  def test01 {
    import Convert._

    val a = new A()
    val b = new B()

    // Q: Why does it work for b
    correct(b)

    // Q: Which implicits are applicable?
    // Q: Why does it fail? correct(b2C(a2B(c))) works.
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

    // Q: Which implicit(s) is initially applicable?
    // Q: Is there a direct implicit Int => SumTuple3?
    // Q: Which implicits are applied to typecheck this expression?
    1.sum

    // Q: Which implicit(s) is initially applicable?
    // Q: How does the expression look like after applying implicit conversion?
    (2,5).sum
    
    // Q: Is there any implicit initially applicable? If yes, which?
    (1,2,3).sum
  }

  def test03 {
    import ChainImplicits._
    implicit def intToSum(x: Int): { def sum: Int } = ???
    
    // Q: Which implicit(s) is applicable to adapt the qualifier `1` to member sum?
    // Q: If there are more any 1, why is there no ambiguity?
    1.sum
    ()
  }

  def test04 {
    implicit def t2Int[T](v: T)(implicit conv: T => Tuple2[Int, Int]): Int = {
      val (v1, v2) = conv(v)
      v1 + v2
    }

    // Q: Why is t2Int applicable for this Tuple2? Where does argument for `conv` come from?
    (2, 2): Int
  }
}

trait Implicits11 {

  // String is a Sequence
  val s: Seq[Char] = "abc"

  def test04 {
    class RichSeq[A](xs: Seq[A]) {
      def repeats(x: A) = false
    }
    implicit def seq2RichSeq[A, S](xs: S)(implicit isSeq: S <:< Seq[A]): RichSeq[A] = new RichSeq(xs)  

    // What is the type of the expression?
    // How does the typechecker infer type argument for `A`?  // too vague?
    // What is the inferred type argument of `A`?
    // Which implicit argument was inferred for `isSeq`?
    s.repeats('a') // pimp the sequence

    // Q: Why is seq2RichSeq not applicable now
    // Q: Why is String not a subtype of Seq[Char] (see definition of s)
    // Q: Can the same implicit argument as in the previous example be used?
    // Q: Why does it not infer the type of the qualifier to be Seq[Char]?
    "abc".repeats('a') // but that's the same as above, right?

    // Q: Why does this assignment succeed?
    val x: Int = 'a'
    // Q: But compiler claims that this one fails because it cannot prove that
    //    Seq[Char] <:< Seq[Int].
    // Is that correct?
    // Q: Cannpt it use the same method as above (for `x`)?
    s.repeats(1)
  }

  trait Factory[S[_]] {
    def point[A](v: A): S[A]
  }

  object Factory {
    implicit val factoryForOption: Factory[Option] = ???
  }


  def test05 {
    implicit def genOptFromFactory[B[_], C <: Factory[B], T](implicit t: B[T], f: C): B[T] = ???

    def opt[T](implicit o: Option[T]): Option[T] = o

    // Q: What does the diverging implicit expansion mean?
    // Q: Why does method genOptFromFactory diverge?
    // Q: What type arguments are inferred for genOptFromFactory?
    // Q: How would the diverging expansion look like?
    opt[Int]
  }


  def test06 {
    class Foo(val x: Int) extends Ordered[Foo] {
      def compare(that: Foo): Int = ???
    }

    class Bar(val id: String, x: Int) extends Foo(x) 


    val as: List[Foo] = ???
    val bs: List[Bar] = ???

    // sorted method clearly takes an implicit parameter that can diverge.
    // Q: Which implicit(s) is/are applied to satisfy the typechecking of `sorted.
    as.sorted

    // Q: Which implicit(s) is/are taken into account when searching for implicit argument?
    // Q: Which implicit(s) does `List[Bar].sorted` cannot find, in order to
    // succeed in the same way as `as`? Why does it fail to select it?
    // Q: What is the sequence that causes the divergence?
    // Q: How can you fix it? Briefly.
    bs.sorted
  }

}

trait Implicits12 {

  def test06 {
    import scala.collection.BitSet

    val lList: mutable.LinkedList[Int] = ???
    val bSet: BitSet = ???

    // Q: What is the type of a? Why
    val a = lList map (_ + 1)

    // Q: What is the type of b? Why
    val b = bSet map (_ + 1)

    // Q: What is the type of c? Why
    val c = lList map (_.toFloat)

    // Q: What is the type of d? Why
    val d = bSet map (_.toFloat)
  }

  
  def test07 {

    import scala.collection.immutable.SortedSet
    val xs = List(5,2,6)

    //Q : what is the type of set1? Why 
    val set1 = SortedSet.empty[Int] ++ xs

    //Q : what is the type of set1? Why 
    //Q : where does the diverging implicit come from   
    val set2 = SortedSet.empty ++ xs


  }


}

trait Implicits13 {
  
  class Pair[+T](val x: T, val y: T) {
    def lessThan(second: Pair[T])(implicit evidence: T <:< Ordered[T]) = 
      (x < second.x) && (y < second.y)

    def lessThan2[S](second: Pair[S])(implicit ev: T => Ordered[T], conv: S => T) =
      (x < second.x) && (y < second.y)
  }
  
  def test01 {
    val a = new Pair(1, 2)
    val b = new Pair(2, 1)
    val c = new Pair('a', 'b')

    // Q: Why does this compile? 
    val someInt: Ordered[Int] = 0

    // Q: Why cannot it prove that Int <: Ordered[Int]
    a lessThan a

    // Q: Why cannot it prove that Int <: Ordered[Int]
    a lessThan b

    // Q: Why do we get an error here
    a lessThan c


    // Q: What implicits did it infer and why?
    a lessThan2 a

    // Q: What implicits did it infer and why?
    a lessThan2 b

    // Q: What implicits did it infer and why?
    a lessThan2 c
  }

}

trait Implicits14 {
  // some examples for magnet pattern?

}

