package implicits

import collection.mutable.LinkedList

trait Implicits14 {

  def test01 {
    import scala.collection.BitSet

    val lList: LinkedList[Int] = ???
    val bSet: BitSet           = ???

    val a = lList map (_ + 1)

    val b = bSet map (_ + 1)

    val c = lList map (_.toFloat)
    
    val d = bSet map (_.toFloat)
  }

}