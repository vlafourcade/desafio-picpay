package com.picpay.desafio.android.user.data.mapper

import android.os.Build
import com.picpay.desafio.android.user.data.model.UserEntity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [
        Build.VERSION_CODES.JELLY_BEAN,
        Build.VERSION_CODES.KITKAT,
        Build.VERSION_CODES.LOLLIPOP,
        Build.VERSION_CODES.P]
)
class UserEntityMapperTest {
    @Test
    fun mapEntityToDtoTest() {
        val input = UserEntity(
            name = "User 1",
            username = "user1",
            id = 1,
            img = "img_url"
        )

        val result = input.toDto()

        with(result) {
            Assert.assertEquals(1, this.id)
            Assert.assertEquals("img_url", this.img)
            Assert.assertEquals("User 1", this.name)
            Assert.assertEquals("user1", this.username)
        }
    }
}

