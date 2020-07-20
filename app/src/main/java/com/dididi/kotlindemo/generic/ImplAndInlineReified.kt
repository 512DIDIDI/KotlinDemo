package com.dididi.kotlindemo.generic


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 20/07/2020
 * @describe 泛型实现原理与内联特化
 * 泛型实现原理：
 *      kotlin java中的泛型实际是"伪"泛型，会在编译时擦除泛型类型
 *      优点：可以节省内存，缺点：增加运行时开销，因为在每次使用时都需要进行类型强转
 * 内联特化：
 *      为了解决"伪"泛型类型擦除导致泛型类型无法当作真实类型的场景
 *      将函数内联到调用处，就能够明确泛型的类型。
 */

/**
 * 编译前
 */
fun <T:Comparable<T>> maxOF(a:T,b:T):T{
    return if (a>b) a else b
}

/**
 * 编译后结果，取了泛型类型的上限，会将泛型类型擦除，kotlin java都是这么实现的
 * 在编译时会擦除泛型类型，运行时并无实际类型生成
 * 例如kotlin中 List<Double> List<String> 实际在运行时生成的都是 List 类型
 */
//fun maxOF(a:Comparable,b:Comparable):Comparable{
//    return if(a>b) a else b
//}

/**
 * 正因为kotlin java当中的泛型是"伪"泛型，会在编译时类型擦除，所以泛型类型无法当作真实类型
 */
fun <T> genericMethod(t:T){
    //因为无法知道T的类型，也不知道T是否有无参构造方法，所以无法构造
//    val t = T()
    //Array虽然写作泛型，但实际编译生成时不会擦除，是会生成对应类型的Array 如Array<Int>等，所以也不能使用泛型
//    val ts = Array<T>(3){ TODO() }
    //因为泛型会被擦除，所以不知道T的类型，所以也不能使用反射
//    val jClass = T::class.java
    //而ArrayList的实例化可以，是因为ArrayList也是接收泛型作为参数，在编译时也会类型擦除，所以不管传什么类型都可以
    val list = ArrayList<T>()
}

/**
 * 解决上述泛型类型无法当作真实类型的方法，kotlin提供了内联特化的方法
 * 因为kotlin内联可以使函数内联到调用处，就能确切知道泛型的类型。
 * 使用[inline]关键字 <reified T> 修饰泛型
 */
inline fun <reified T> genericMethodSolution(t:T){
    //构造方法还是不能，是因为不能确定T是否有无参构造器，所以不能使用
//    val t = T()
    //而下面的都可以使用，因为内联到了调用处，知道了泛型的确切类型
    val ts = Array<T>(3){ TODO()}
    val jClass = T::class.java
    val list = ArrayList<T>()
}