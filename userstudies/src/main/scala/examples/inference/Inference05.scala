package inference

trait Inference05 {
  


  def test01(cond: Boolean): Unit = {
    class C
    class D
    class E
    class F
    
    val a = if (cond) ((x: C) => new D) else ((x: E) => new F)
  }

  def test02(cond: Boolean): Unit = {
    trait A  { type T <: A }
    trait B  { type T <: B } 
    
    val b = if (cond) new A{} else new B{}
  }

  def test03(cond: Boolean): Unit = {
    abstract class A { type T }
    class C extends A { type T = C }
    class D extends A { type T = D }
    
    val c = if (cond) new C else new D
  }

  def test04(cond: Boolean): Unit = {
    val d = if (cond) new java.lang.Integer(1) else new java.lang.Double(2)
  }

}
