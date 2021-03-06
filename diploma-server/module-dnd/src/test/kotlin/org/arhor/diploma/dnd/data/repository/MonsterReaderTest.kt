package org.arhor.diploma.dnd.data.repository

import org.arhor.diploma.dnd.TestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@Tag("integration-test")
@SpringJUnitConfig(TestConfig::class)
class MonsterReaderTest {

    @Autowired
    private lateinit var service: MonsterProvider

    @Test
    fun fetchAllMonsters() {
        // when
        val monsters = service.getList()

        // then
        assertThat(monsters)
            .isNotNull
            .isNotEmpty
    }
}
