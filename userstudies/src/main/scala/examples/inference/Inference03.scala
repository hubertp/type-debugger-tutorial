trait Inference03 {
  abstract class A
  abstract class B[T <: A]
  class C extends A

  class Base1 extends B[C]
  

  def foo[U <: B[T], T <: A]( param: U ): Unit = ()

  def test01: Unit = {
    foo(new Base1)
    ()
  }

  def bar[U[T0 <: A] <: B[T0], T <: A]( param: U[T] ): Unit = ()

  def test02: Unit = {
    bar(new Base1)
    ()
  }
}
