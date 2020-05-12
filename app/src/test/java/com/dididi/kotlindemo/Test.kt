package com.dididi.kotlindemo

import com.dididi.kotlindemo.annotation.testAnnoProcessing
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * @author dididi(yechao)
 * @since 17/01/2019
 * @describe
 */

@RunWith(JUnit4::class)
class AnnotationTest{

    @Test
    fun testNo(){
        testAnnoProcessing()
    }

}