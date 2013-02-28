package inference

trait Inference05 {
  
  def test01(cond: Boolean): Unit = {
    trait A  { type T <: A }
    trait B  { type T <: B } 
    
    val a = if (cond) new A{} else new B{}
  }

  def test02(cond: Boolean): Unit = {
    class C
    class D
    class E
    class F
    
    val b = if (cond) ((x: C) => new D) else ((x: E) => new F)
  }

}