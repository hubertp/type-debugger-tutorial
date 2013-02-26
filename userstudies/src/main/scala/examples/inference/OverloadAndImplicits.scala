

object Test {

  class Base {
    def v: Int
  }
  object Base {
    implicit def b2Int(b: Base): Double = b.v
  }

  class A(val v: Int) extends Base
  object A {
    implicit def a2Int(a: A): Int = a.v
  }
  class B(val v: Int) extends Base

  class Bar {
    def foo(x: Int) = ???
  }

  class Bar2 extends Bar {
    def foo(x: AnyVal) = ???
  }

  def test {
    val a = new A(1)
    val b = new B(1)

    val c = new Bar2()
    c.foo(a)
    c.foo(b)
  }
}