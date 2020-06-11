package com.dididi.kotlindemo.function

import java.io.File


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 11/06/2020
 * @describe 使用DSL(领域特定语言)开发html页面
 */

interface Node {
    fun render(): String
}

class BlockNode(val name: String) : Node {

    /**
     * 子节点
     */
    val children = arrayListOf<Node>()

    /**
     * 节点属性
     */
    val properties = hashMapOf<String, Any>()

    /**
     * 生成如 <html xxx='xxx'><head></head></html> 的字符串
     */
    override fun render() = """<$name ${properties.map { "${it.key}='${it.value}'" }
        .joinToString(" ")}>${children.joinToString("") { it.render() }}</$name>""".trimIndent()

    /**
     * 重载 String()操作符 并支持传入lambda表达式
     * 形如 "meta"{} 节点
     */
    operator fun String.invoke(block: BlockNode.() -> Unit): BlockNode {
        val propertyNode = BlockNode(this)
        propertyNode.block()
        this@BlockNode.children += propertyNode
        return propertyNode
    }

    /**
     * 重载 String()操作符
     * 形如 "charset"("UTF-8") 属性
     */
    operator fun String.invoke(property: Any) {
        this@BlockNode.properties[this] = property
    }

    /**
     * 重载 +String 操作符
     * 形如 +"Hello Html DSL!!" 节点
     */
    operator fun String.unaryPlus() {
        this@BlockNode.children += StringNode(this)
    }
}

class StringNode(val content: String) : Node {

    override fun render() = content
}

fun html(block: BlockNode.() -> Unit): BlockNode {
    val html = BlockNode("html")
    html.block()
    return html
}

fun BlockNode.head(block: BlockNode.() -> Unit): BlockNode {
    val head = BlockNode("head")
    head.block()
    this.children += head
    return head
}

fun BlockNode.body(block: BlockNode.() -> Unit): BlockNode {
    val body = BlockNode("body")
    body.block()
    this.children += body
    return body
}

fun main() {
    val htmlContent = html {
        head {
            "meta"{ "charset"("UTF-8") }
        }
        body {
            "div"{
                "style"(
                    """
                    width: 200px;
                    height: 200px;
                    line-height: 200px;
                    background-color: #C9394A;
                    text-align: center
                """.trimIndent()
                )
                "span"{
                    "style"(
                        """
                    color: white;
                    font-family: Microsoft YaHei
                    """.trimIndent()
                    )
                    +"Hello HTML DSL!!"
                }
            }
        }
    }.render()
    File("htmlDsl.html").writeText(htmlContent)
}