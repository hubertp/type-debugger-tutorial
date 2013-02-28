package userstudies
package implicits

import collection.mutable

trait Implicits14 {

  def test01 {
    import scala.collection.BitSet

    val lList: mutable.LinkedList[Int] = ???
    val bSet: BitSet = ???

    // Q: What is the type of a? Why
    val a = lList map (_ + 1)

    // Q: What is the type of b? Why
    val b = bSet map (_ + 1)

    // Q: What is the type of c? Why
    val c = lList map (_.toFloat)

    // Q: What is the type of d? Why
    val d = bSet map (_.toFloat)
  }

}