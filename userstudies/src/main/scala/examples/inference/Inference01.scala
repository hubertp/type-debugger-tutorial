package inference

trait Inference01 {
  abstract class Base
  class A extends Base
  class B extends Base

  class C extends A

  def test01(cond: Boolean)(t: => A)(f: => B) =
    if (cond) t else f

  case class Foo(x: Int) extends Base
  case class Bar(y: Int) extends Base

  def test02: Unit = {
    def common(cond: Boolean) = if (cond) Foo(0) else Bar(0)
    var x = common(false)
    val sth: Base = Foo(1)

    x = sth               // error
    ()
  }

  def test03: Unit = {
    def foo[T >: A <: AnyRef](a: T): Unit = ()
    val x = new C()

    foo(x)
    ()
  }
}