trait Implicits13 {

  def test01 {

    implicit def transitive[A, B, C](implicit f: A => B, g: B => C): A => C = f andThen g

    implicit def pre2Main(f: PreProcess): Main = ???
    implicit def main2Final(f: Main): Final    = ???

    abstract class Action

    case class PreProcess(x: Int) extends Action
    case class Main(y: Int, factor: Int) extends Action
    case class Final(res: Int) extends Action {
      def product(): Int = ???
    }

    def doWork(v: PreProcess)(implicit process: PreProcess => Final): Int = 
      process(v).product()

    doWork(PreProcess(21))

  }


  def test02 {
    class Foo(val x: Int) extends Ordered[Foo] {
      def compare(that: Foo): Int = ???
    }

    class Bar(val id: String, x: Int) extends Foo(x) 


    val listOfFoo: List[Foo] = ???
    val listOfBar: List[Bar] = ???

    listOfFoo.sorted

    listOfBar.sorted
  }


  def test03 {

    import scala.collection.immutable.SortedSet
    val xs = List(5,2,6)

    //Q : what is the type of set1? Why 
    val set1 = SortedSet.empty[Int] ++ xs

    //Q : what is the type of set1? Why 
    //Q : where does the diverging implicit come from   
    val set2 = SortedSet.empty ++ xs


  }

}