package implicits 

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

    get1(s.scale(h))
    get1(s.scale(v))
    get2(s.scale(h))
    get2(s.scale(v))
    get3(s.scale(h))
    get3(s.scale(v))
  }

  object SizeScale2 {
    implicit def sizeScale1(v: Size) = new {
      def scale(x: Horizontal): Valid.type = Valid
    }

    implicit def square(v: Int): Size = new Size(v, v)

    implicit def result2Invalid(v: Result): Invalid.type = ???
    
    implicit def vertical2Horizontal(v: Vertical): Horizontal = ???
  }

}

trait Implicits08 {
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