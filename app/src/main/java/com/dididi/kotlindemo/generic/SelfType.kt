package com.dididi.kotlindemo.generic


/**
 * @author dididi(叶超)
 * @email 512dididi@gmail.com
 * @since 21/07/2020
 * @describe 模拟Scala self-type
 */

typealias OnConfirm = () -> Unit
typealias OnCancel = () -> Unit

val EmptyFunction = fun() {}

//模拟Self type，实现返回this
interface SelfType<Self> {
    val self: Self
        get() = this as Self
}

open class Notification(
    val title: String,
    val content: String
)

class ConfirmNotification(
    title: String,
    content: String,
    val onConfirm: OnConfirm,
    val onCancel: OnCancel
) : Notification(title, content)

/**给泛型参数Self添加约束*/
open class NotificationBuilder<Self:NotificationBuilder<Self>>:SelfType<Self> {
    protected var title: String = ""
    protected var content: String = ""
    fun title(title: String): Self {
        this.title = title
        return self
    }

    fun content(content: String): Self {
        this.content = content
        return self
    }

    open fun build() = Notification(title, content)
}

class ConfirmNotificationBuilder : NotificationBuilder<ConfirmNotificationBuilder>() {
    private var onConfirm: OnConfirm = EmptyFunction
    private var onCancel: OnCancel = EmptyFunction

    fun onConfirm(onConfirm: OnConfirm): ConfirmNotificationBuilder {
        this.onConfirm = onConfirm
        return this
    }

    fun onCancel(onCancel: OnCancel): ConfirmNotificationBuilder {
        this.onCancel = onCancel
        return this
    }

    override fun build() = ConfirmNotification(title, content, onConfirm, onCancel)
}

fun main() {
    ConfirmNotificationBuilder()
        .title("confirm message")
        .content("are you confirm?")
        .onConfirm {
            println("onConfirm")
        }
        .build()
        .onConfirm()
}

