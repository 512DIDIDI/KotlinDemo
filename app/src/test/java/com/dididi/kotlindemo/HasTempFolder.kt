package com.dididi.kotlindemo

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder


/**
 * @author dididi(yechao)
 * @since 16/01/2019
 * @describe
 */

class HasTempFolder {
    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun testUsingTempFolder() {
        val createdFile = folder.newFile("myfile.txt")
        val createdFolder = folder.newFolder("subfolder")
    }
}