package inference
import scala.language.implicitConversions

object Test {

  class Base {
    def v: Int
  }
  object Base {
    implicit def b2Int(b: Base): Int = b.v
  }

  class A(val v: Int) extends Base
  object A {
    implicit def a2Int(a: A): Int = a.v       
  }

  class B(val v: Int) extends Base

  class Foo {
    def foo(x: Int) = ???     // 1
  }

  class Bar extends Foo {
    def foo(x: AnyVal) = ???  // 2
  }

  object Bar {
    // Bar which applies foo to Base
    implicit def foo2Base(a: Foo): { def foo(x: Base): Nothing } = ???
  }

  def test {
    val a = new A(1)
    val b = new B(1)

    val c = new Bar()
    c.foo(a)    // A
    c.foo(b)    // B
  }
}