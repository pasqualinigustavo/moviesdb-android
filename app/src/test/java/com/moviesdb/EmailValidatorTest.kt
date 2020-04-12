package com.moviesdb

import br.com.biologiatotal.common.extension.isEmailValid
import org.junit.Assert
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        Assert.assertEquals("name@email.com".isEmailValid(), true)
    }
}