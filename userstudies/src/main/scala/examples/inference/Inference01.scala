package inference

trait Inference01 {
  abstract class Base

  class A extends Base
  class B extends Base

  class C extends A


  case class Foo(x: Int) extends Base
  case class Bar(y: Int) extends Base

  def test02: Unit = {
    def common(cond: Boolean) = if (cond) Foo(0) else Bar(0)
    var x = common(false)
    val sth: Base = Foo(1)

    x = sth               // error
    ()
  }

  def test04(cond: Boolean): Unit = {
    class C
    class D
    class E
    class F
    
    val a = if (cond) ((x: C) => new D) else ((x: E) => new F)
  }
  
  def test05(cond: Boolean): Unit = {
    abstract class A { type T }
    class C extends A { type T = C }
    class D extends A { type T = D }
    
    val b = if (cond) new C else new D
  }

  def test06(cond: Boolean): Unit = {
    val c = if (cond) new java.lang.Integer(1) else new java.lang.Double(2)
  }

}