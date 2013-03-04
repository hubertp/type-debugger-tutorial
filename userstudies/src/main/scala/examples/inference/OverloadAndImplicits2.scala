package inference
import scala.language.implicitConversions

class Base[T]
class NatA[T] extends Base[T]
class NatB[T] extends Base[T]
class AA[A]

object Test {
 implicit def conv1(i: Int) = new NatA[Int]
 implicit def conv2(i: Int) = new NatB[Int]
 implicit def conv3(op: AA[String]) = new Base[String]
 implicit def conv4(op: AA[Int])    = new Base[Int]

 def aFunc[A](a: NatA[A])  = new AA[String]   // 1
 def aFunc[A](a: NatB[A])  = new AA[Int]      // 2
 
 def bFunc[T](e1: Base[T]) = e1
  
 def test01(p: Int): Base[Int] = {
   val x = aFunc(p)  // A
   bFunc(x)
 }
 def test02(p: Int): Base[Int] =
   bFunc(aFunc(p))   // B
}
