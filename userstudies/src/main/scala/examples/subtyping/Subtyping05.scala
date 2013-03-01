trait Subtyping05 {
  trait Op[+X <: Op[X]] // def change to contravariant
  class Foo extends Op[Foo]
  class Bar extends Foo

  def app1[U <: Op[U]](x: U): U = x
  def app2[V <: Op[_]](x: V): V = x

  def test {
    val x = new Foo()
    val y = new Bar()

    app1(x)
    app1(y)

    app2(x)
    app2(y)
  }

} 