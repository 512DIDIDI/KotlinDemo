package com.dididi.kotlindemo.generic


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 19/07/2020
 * @describe 泛型型变
 * out T  : 协变(生产泛型实例的生产者)
 *       协变小结：
 *          1.子类[Derived]兼容父类[Base]
 *          2.生产者[Producer<Derived>]兼容[Producer<Base>]
 *          3.存在<协变点>的类的泛型参数必须声明为<协变>或<不变>
 *          4.当泛型类作为泛型参数类实例的<生产者>时用<协变>(函数返回值类型为泛型)
 * in T   : 逆变(消费泛型实例的生产者)
 *      逆变小结：
 *          1.子类[Derived]兼容父类[Base]
 *          2.消费者[Consumer<Base>]兼容[Consumer<Derived>]
 *          3.存在<逆变点>的类的泛型参数必须声明为<逆变>或<不变>
 *          4.当泛型类作为泛型参数类实例的<消费者>时用<逆变>(函数参数类型为泛型)
 * T      : 不变(只能推导参数类型的继承关系，不能推导包装类型的继承关系。会出现List<Nothing>不为List<T>的子类)
 */

/**
 * out T:表明泛型协变
 * [Nothing]为T的子类，那么[List<Nothing>]也可以协变为[List<T>]的子类
 * 协变适用于作为生产者的场景，并希望泛型的包装类型也能推导继承关系。例如此例中：
 * [Nil]返回的类型是 [List<Noting>],如果泛型声明为不变，则[Nil]实例无法传入[Cons]构造函数内
 * 声明泛型类型为协变[out T]，则泛型类型的继承关系也能推导到其包装类型，
 * 1.也就是[Nothing] extends [T] ——> [List<Nothing>] extends [List<T>]
 * 2.协变还涉及到生产泛型实例，也就是 <函数返回值类型为泛型类型>，作为 <协变点>
 *     <协变点>的意义在于让子类能够返回子类或其父类参数的实例，而父类只能返回其父类参数的实例
 */
sealed class ListT<out T> {
    object Nil : ListT<Nothing>() {
        override fun toString(): String {
            return "Nil"
        }
    }

    /**注意，这里的[head]也是<协变点>，因为他是成员属性，有[getter]方法*/
    data class Cons<T>(val head: T, val tail: ListT<T>) : ListT<T>() {
        override fun toString(): String {
            return "$head,$tail"
        }
    }

    fun joinToString(sep: Char = ' '): String {
        return when (this) {
            is Nil -> "Nil"
            is Cons -> "$head$sep${tail.joinToString(sep)}"
        }
    }
}

interface Book

interface EduBook : Book

class BookStore<out T : Book> {
    /**函数类型T为协变点*/
    fun getBook(): T {
        TODO()
    }
}

/**
 * in T:声明泛型逆变
 * [Number]为[Int]的父类，但作为逆变场景，[Comparable<Number>]是[Comparable<Int>]的子类
 * 因为此时泛型类型是作为参数传入消费的，能够比较[Number]的[Comparable]，[Int]也一定能够比较，
 * 而根据里氏替换原则，子类一定可以替代父类出现的地方，所以[Comparable<Number>]是[Comparable<Int>]的子类
 * 与协变相反，逆变适用于消费者场景
 * 逆变点也与协变相反，<逆变点>是<函数参数类型为泛型类型>
 */
interface Comparable<in T> {
    /**这里[T]作为函数参数类型，就是逆变点*/
    operator fun compareTo(other: T): Int {
        TODO()
    }
}

open class Waste

class DryWaste : Waste()

class Dustbin<in T : Waste> {
    fun put(t: T) {}
}


fun main() {
    /**协变应用场景*/
    val list = ListT.Cons(1f, ListT.Cons(1f, ListT.Nil))
    println(list)
    val list2 = ListT.Cons(1f, ListT.Nil)
    println(list2.joinToString('-'))
    //教辅书店
    val eduBookStore: BookStore<EduBook> = BookStore<EduBook>()
    //书店
    val bookStore: BookStore<Book> = BookStore<Book>()
    //书店能够生产书
    val book: Book = bookStore.getBook()
    //教辅书店能够生产教辅书，或书(因为教辅书是书的子类)
    val eduBook: EduBook = eduBookStore.getBook()
    /**[bookStore] 无法获取 [EduBook] 因为泛型参数被声明为协变*/
//    val eduBook2: EduBook = bookStore.getBook()

    /**逆变应用场景*/
    //垃圾桶
    val dustbin: Dustbin<Waste> = Dustbin<Waste>()
    //干垃圾桶
    val dryWasteDustbin: Dustbin<DryWaste> = Dustbin<DryWaste>()
    //垃圾
    val waste = Waste()
    //干垃圾
    val dryWaste = DryWaste()
    //垃圾桶能够装下垃圾和干垃圾
    dustbin.put(waste)
    dustbin.put(dryWaste)
    //而干垃圾桶却只能装下干垃圾，不能装垃圾，
    /**根据里氏替换原则，子类能够拓展父类功能，并且能替换父类出现的地方，所以<干垃圾桶是垃圾桶的父类>*/
//    dryWasteDustbin.put(waste)
    dryWasteDustbin.put(dryWaste)
}

