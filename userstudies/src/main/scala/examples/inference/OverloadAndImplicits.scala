package inference
import scala.language.implicitConversions
import scala.language.reflectiveCalls

object Test {

  abstract class Base[T] {
    def v: T
  }
  object Base {
    implicit def b2Int(b: Base[Int]): Int = b.v            // 1
    implicit def b2T[T <: AnyVal](b: Base[T]): T = ???   // 2
  }

  class A(val v: Int) extends Base[Int]
  object A {
    implicit def a2Int(a: A): Int = a.v               // 3
    implicit def a2Double(a: A): Double = a.v         // 4
    implicit def a2b[T](a: A): T = ???                // 5
  }

  class B(val v: AnyRef) extends Base[AnyRef]

  class Foo {
    def foo(x: Int) = ???        // x
  }

  class Bar extends Foo {
    def foo(x: AnyVal) = ???     // y
  }

  def test {
    val a = new A(1)
    val b = new B(new Integer(1))

    val c = new Bar()
    c.foo(a)    // A
    c.foo(b)   // B
  }
}